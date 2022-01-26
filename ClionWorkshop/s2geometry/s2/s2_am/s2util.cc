//
// Created by lrj on 2021/12/4.
//

extern "C"
{
#include "s2.h"
#include "s2xlog.h"
#include "access/htup_details.h"
#include "access/reloptions.h"
#include "catalog/pg_opclass.h"
#include "storage/indexfsm.h"
#include "storage/lmgr.h"
#include "utils/syscache.h"
#include "utils/snapmgr.h"
#include "utils/lsyscache.h"
}

extern "C"
{
bytea *s2options(Datum reloptions, bool validate)
{
    relopt_value *options;
    S2Options *rdopts;
    int numoptions;
    static const relopt_parse_elt tab[] = {
            {"fillfactor", RELOPT_TYPE_INT,    offsetof(S2Options, fillfactor)},
            {"buffering",  RELOPT_TYPE_STRING, offsetof(S2Options, bufferingModeOffset)}
    };

    options = parseRelOptions(reloptions, validate, RELOPT_KIND_GIST,
                              &numoptions);

    /* if none set, we're done */
    if (numoptions == 0)
        return nullptr;

    rdopts = static_cast<S2Options *>(allocateReloptStruct(sizeof(S2Options), options, numoptions));

    fillRelOptions((void *) rdopts, sizeof(S2Options), options, numoptions,
                   validate, tab, lengthof(tab));

    pfree(options);

    return (bytea *) rdopts;
}

/*
 * Verify that a freshly-read page looks sane.
 */
void s2checkpage(Relation rel, Buffer buf)
{
    Page page = BufferGetPage(buf);

    /*
     * ReadBuffer verifies that every newly-read page passes
     * PageHeaderIsValid, which means it either contains a reasonably sane
     * page header or is all-zero.  We have to defend against the all-zero
     * case, however.
     */
    if (PageIsNew(page))
        ereport(ERROR,
                (errcode(ERRCODE_INDEX_CORRUPTED),
                        errmsg("index \"%s\" contains unexpected zero page at block %u",
                               RelationGetRelationName(rel),
                               BufferGetBlockNumber(buf)),
                        errhint("Please REINDEX it.")));

    /*
     * Additionally check that the special area looks sane.
     */
    if (PageGetSpecialSize(page) != MAXALIGN(sizeof(S2PageOpaqueData)))
        ereport(ERROR,
                (errcode(ERRCODE_INDEX_CORRUPTED),
                        errmsg("index \"%s\" contains corrupted page at block %u",
                               RelationGetRelationName(rel),
                               BufferGetBlockNumber(buf)),
                        errhint("Please REINDEX it.")));
}

/* Can this page be recycled yet? */
bool s2PageRecyclable(Page page)
{
    if (PageIsNew(page))
        return true;
    if (S2PageIsDeleted(page))
    {
        /*
         * The page was deleted, but when? If it was just deleted, a scan
         * might have seen the downlink to it, and will read the page later.
         * As long as that can happen, we must keep the deleted page around as
         * a tombstone.
         *
         * Compare the deletion XID with RecentGlobalXmin. If deleteXid <
         * RecentGlobalXmin, then no scan that's still in progress could have
         * seen its downlink, and we can recycle it.
         */
        FullTransactionId deletexid_full = S2PageGetDeleteXid(page);
        FullTransactionId recentxmin_full = GetFullRecentGlobalXmin();

        if (FullTransactionIdPrecedes(deletexid_full, recentxmin_full))
            return true;
    }
    return false;
}

/*
 * Allocate a new page (either by recycling, or by extending the index file)
 *
 * The returned buffer is already pinned and exclusive-locked
 *
 * Caller is responsible for initializing the page by calling GISTInitBuffer
 */
Buffer s2NewBuffer(Relation r)
{
    Buffer buffer;
    bool needLock;

    /* First, try to get a page from FSM */
    for (;;)
    {
        BlockNumber blkno = GetFreeIndexPage(r);

        if (blkno == InvalidBlockNumber)
            break;                /* nothing left in FSM */

        buffer = ReadBuffer(r, blkno);

        /*
         * We have to guard against the possibility that someone else already
         * recycled this page; the buffer may be locked if so.
         */
        if (ConditionalLockBuffer(buffer))
        {
            Page page = BufferGetPage(buffer);

            /*
             * If the page was never initialized, it's OK to use.
             */
            if (PageIsNew(page))
                return buffer;

            s2checkpage(r, buffer);

            /*
             * Otherwise, recycle it if deleted, and too old to have any
             * processes interested in it.
             */
            if (s2PageRecyclable(page))
            {
                /*
                 * If we are generating WAL for Hot Standby then create a WAL
                 * record that will allow us to conflict with queries running
                 * on standby, in case they have snapshots older than the
                 * page's deleteXid.
                 */
                if (XLogStandbyInfoActive() && RelationNeedsWAL(r))
                    s2XLogPageReuse(r, blkno, S2PageGetDeleteXid(page));

                return buffer;
            }

            LockBuffer(buffer, S2_UNLOCK);
        }

        /* Can't use it, so release buffer and try again */
        ReleaseBuffer(buffer);
    }

    /* Must extend the file */
    needLock = !RELATION_IS_LOCAL(r);

    if (needLock)
        LockRelationForExtension(r, ExclusiveLock);

    buffer = ReadBuffer(r, P_NEW);
    LockBuffer(buffer, S2_EXCLUSIVE);

    if (needLock)
        UnlockRelationForExtension(r, ExclusiveLock);

    return buffer;
}

/*
 * Initialize a new index page
 */
void S2InitBuffer(Buffer b, uint32 f)
{
    S2PageOpaque opaque;
    Page page;
    Size pageSize;

    pageSize = BufferGetPageSize(b);
    page = BufferGetPage(b);
    PageInit(page, pageSize, sizeof(S2PageOpaqueData));

    opaque = S2PageGetOpaque(page);
    /* page was already zeroed by PageInit, so this is not needed: */
    /* memset(&(opaque->nsn), 0, sizeof(GistNSN)); */
    opaque->rightlink = InvalidBlockNumber;
    opaque->flags = f;
    opaque->gist_page_id = S2_PAGE_ID;
}

IndexTuple s2FormTuple(S2STATE *s2State, Relation r,
                         Datum attdata[], bool isnull[], bool isleaf)
{
    Datum compatt[INDEX_MAX_KEYS];
    int i;
    IndexTuple res;

    /*
     * Call the compress method on each attribute.
     */
    for (i = 0; i < IndexRelationGetNumberOfKeyAttributes(r); i++)
    {
        if (isnull[i])
            compatt[i] = (Datum) 0;
        else
        {
            S2ENTRY centry;
            S2ENTRY *cep;

            gistentryinit(centry, attdata[i], r, NULL, (OffsetNumber) 0,
                          isleaf);
            /* there may not be a compress function in opclass */
            if (OidIsValid(s2State->compressFn[i].fn_oid))
                cep = (GISTENTRY * )
                        DatumGetPointer(FunctionCall1Coll(&s2State->compressFn[i],
                                                          s2State->supportCollation[i],
                                                          PointerGetDatum(&centry)));
            else
                cep = &centry;
            compatt[i] = cep->key;
        }
    }

    if (isleaf)
    {
        /*
         * Emplace each included attribute if any.
         */
        for (; i < r->rd_att->natts; i++)
        {
            if (isnull[i])
                compatt[i] = (Datum) 0;
            else
                compatt[i] = attdata[i];
        }
    }

    res = index_form_tuple(isleaf ? s2State->leafTupdesc :
                           s2State->nonLeafTupdesc,
                           compatt, isnull);

    /*
     * The offset number on tuples on internal pages is unused. For historical
     * reasons, it is set to 0xffff.
     */
    ItemPointerSetOffsetNumber(&(res->t_tid), 0xffff);
    return res;
}
}