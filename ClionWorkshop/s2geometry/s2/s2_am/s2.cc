//
// Created by lrj on 2021/12/4.
//

extern "C" {
#include "s2.h"
#include "catalog/pg_collation.h"
#include "miscadmin.h"
#include "storage/lmgr.h"
#include "storage/predicate.h"
#include "nodes/execnodes.h"
#include "utils/builtins.h"
#include "utils/index_selfuncs.h"
#include "utils/memutils.h"
#include "utils/rel.h"
PG_FUNCTION_INFO_V1(s2handler);
}

extern "C" {
/*
* GiST handler function: return IndexAmRoutine with access method parameters
* and callbacks.
*/
Datum s2handler(PG_FUNCTION_ARGS)
{
    IndexAmRoutine *amroutine = makeNode(IndexAmRoutine);

    amroutine->amstrategies = 0;
    amroutine->amsupport = 9;
    amroutine->amcanorder = false;
    amroutine->amcanorderbyop = false;
    amroutine->amcanbackward = false;
    amroutine->amcanunique = false;
    amroutine->amcanmulticol = false;
    amroutine->amoptionalkey = false;
    amroutine->amsearcharray = false;
    amroutine->amsearchnulls = false;
    amroutine->amstorage = true;
    amroutine->amclusterable = false;
    amroutine->ampredlocks = false;
    amroutine->amcanparallel = false;
    amroutine->amcaninclude = false;
    amroutine->amkeytype = InvalidOid;

    amroutine->ambuild = s2build;
    amroutine->ambuildempty = s2buildempty;
    amroutine->aminsert = s2insert;
    amroutine->ambulkdelete = s2bulkdelete;
    amroutine->amvacuumcleanup = s2vacuumcleanup;
    amroutine->amcanreturn = nullptr; /* can be NULL */
    amroutine->amcostestimate = s2costestimate;
    amroutine->amoptions = s2options;
    amroutine->amproperty = nullptr; /* can be NULL */
    amroutine->ambuildphasename = nullptr; /* can be NULL */
    amroutine->amvalidate = s2validate;
    amroutine->ambeginscan = s2beginscan;
    amroutine->amrescan = s2rescan;
    amroutine->amgettuple = nullptr; /* can be NULL */
    amroutine->amgetbitmap = nullptr; /* can be NULL */
    amroutine->amendscan = s2endscan;
    amroutine->ammarkpos = nullptr; /* can be NULL */
    amroutine->amrestrpos = nullptr; /* can be NULL */
    amroutine->amestimateparallelscan = nullptr; /* can be NULL */
    amroutine->aminitparallelscan = nullptr; /* can be NULL */
    amroutine->amparallelrescan = nullptr; /* can be NULL */

    PG_RETURN_POINTER(amroutine);
}

void freeS2state(S2STATE *giststate)
{
    /* It's sufficient to delete the scanCxt */
    MemoryContextDelete(giststate->scanCxt);
}

/*
 * Workhouse routine for doing insertion into a s2 index. Note that
 * this routine assumes it is invoked in a short-lived memory context,
 * so it does not bother releasing palloc'd allocations.
 */
void s2doinsert(Relation r, IndexTuple itup, Size freespace,
                S2STATE *giststate, Relation heapRel, bool is_build)
{
    ItemId iid;
    IndexTuple idxtuple;
    S2InsertStack firststack;
    S2InsertStack *stack;
    S2InsertState state;
    bool xlocked = false;

    memset(&state, 0, sizeof(S2InsertState));
    state.freespace = freespace;
    state.r = r;
    state.heapRel = heapRel;
    state.is_build = is_build;

    /* Start from the root */
    firststack.blkno = GIST_ROOT_BLKNO;
    firststack.lsn = 0;
    firststack.retry_from_parent = false;
    firststack.parent = NULL;
    firststack.downlinkoffnum = InvalidOffsetNumber;
    state.stack = stack = &firststack;

    /*
     * Walk down along the path of smallest penalty, updating the parent
     * pointers with the key we're inserting as we go. If we crash in the
     * middle, the tree is consistent, although the possible parent updates
     * were a waste.
     */
    for (;;)
    {
        /*
         * If we split an internal page while descending the tree, we have to
         * retry at the parent. (Normally, the LSN-NSN interlock below would
         * also catch this and cause us to retry. But LSNs are not updated
         * during index build.)
         */
        while (stack->retry_from_parent)
        {
            if (xlocked)
                LockBuffer(stack->buffer, GIST_UNLOCK);
            xlocked = false;
            ReleaseBuffer(stack->buffer);
            state.stack = stack = stack->parent;
        }

        if (XLogRecPtrIsInvalid(stack->lsn))
            stack->buffer = ReadBuffer(state.r, stack->blkno);

        /*
         * Be optimistic and grab shared lock first. Swap it for an exclusive
         * lock later if we need to update the page.
         */
        if (!xlocked)
        {
            LockBuffer(stack->buffer, GIST_SHARE);
            gistcheckpage(state.r, stack->buffer);
        }

        stack->page = (Page) BufferGetPage(stack->buffer);
        stack->lsn = xlocked ?
                     PageGetLSN(stack->page) : BufferGetLSNAtomic(stack->buffer);
        Assert(!RelationNeedsWAL(state.r) || !XLogRecPtrIsInvalid(stack->lsn));

        /*
         * If this page was split but the downlink was never inserted to the
         * parent because the inserting backend crashed before doing that, fix
         * that now.
         */
        if (GistFollowRight(stack->page))
        {
            if (!xlocked)
            {
                LockBuffer(stack->buffer, GIST_UNLOCK);
                LockBuffer(stack->buffer, GIST_EXCLUSIVE);
                xlocked = true;
                /* someone might've completed the split when we unlocked */
                if (!GistFollowRight(stack->page))
                    continue;
            }
            gistfixsplit(&state, giststate);

            UnlockReleaseBuffer(stack->buffer);
            xlocked = false;
            state.stack = stack = stack->parent;
            continue;
        }

        if ((stack->blkno != GIST_ROOT_BLKNO &&
             stack->parent->lsn < GistPageGetNSN(stack->page)) ||
            GistPageIsDeleted(stack->page))
        {
            /*
             * Concurrent split or page deletion detected. There's no
             * guarantee that the downlink for this page is consistent with
             * the tuple we're inserting anymore, so go back to parent and
             * rechoose the best child.
             */
            UnlockReleaseBuffer(stack->buffer);
            xlocked = false;
            state.stack = stack = stack->parent;
            continue;
        }

        if (!GistPageIsLeaf(stack->page))
        {
            /*
             * This is an internal page so continue to walk down the tree.
             * Find the child node that has the minimum insertion penalty.
             */
            BlockNumber childblkno;
            IndexTuple newtup;
            GISTInsertStack *item;
            OffsetNumber downlinkoffnum;

            downlinkoffnum = gistchoose(state.r, stack->page, itup, giststate);
            iid = PageGetItemId(stack->page, downlinkoffnum);
            idxtuple = (IndexTuple) PageGetItem(stack->page, iid);
            childblkno = ItemPointerGetBlockNumber(&(idxtuple->t_tid));

            /*
             * Check that it's not a leftover invalid tuple from pre-9.1
             */
            if (GistTupleIsInvalid(idxtuple))
                ereport(ERROR,
                        (errmsg("index \"%s\" contains an inner tuple marked as invalid",
                                RelationGetRelationName(r)),
                                errdetail(
                                        "This is caused by an incomplete page split at crash recovery before upgrading to PostgreSQL 9.1."),
                                errhint("Please REINDEX it.")));

            /*
             * Check that the key representing the target child node is
             * consistent with the key we're inserting. Update it if it's not.
             */
            newtup = gistgetadjusted(state.r, idxtuple, itup, giststate);
            if (newtup)
            {
                /*
                 * Swap shared lock for an exclusive one. Beware, the page may
                 * change while we unlock/lock the page...
                 */
                if (!xlocked)
                {
                    LockBuffer(stack->buffer, GIST_UNLOCK);
                    LockBuffer(stack->buffer, GIST_EXCLUSIVE);
                    xlocked = true;
                    stack->page = (Page) BufferGetPage(stack->buffer);

                    if (PageGetLSN(stack->page) != stack->lsn)
                    {
                        /* the page was changed while we unlocked it, retry */
                        continue;
                    }
                }

                /*
                 * Update the tuple.
                 *
                 * We still hold the lock after gistinserttuple(), but it
                 * might have to split the page to make the updated tuple fit.
                 * In that case the updated tuple might migrate to the other
                 * half of the split, so we have to go back to the parent and
                 * descend back to the half that's a better fit for the new
                 * tuple.
                 */
                if (gistinserttuple(&state, stack, giststate, newtup,
                                    downlinkoffnum))
                {
                    /*
                     * If this was a root split, the root page continues to be
                     * the parent and the updated tuple went to one of the
                     * child pages, so we just need to retry from the root
                     * page.
                     */
                    if (stack->blkno != GIST_ROOT_BLKNO)
                    {
                        UnlockReleaseBuffer(stack->buffer);
                        xlocked = false;
                        state.stack = stack = stack->parent;
                    }
                    continue;
                }
            }
            LockBuffer(stack->buffer, GIST_UNLOCK);
            xlocked = false;

            /* descend to the chosen child */
            item = (GISTInsertStack *) palloc0(sizeof(GISTInsertStack));
            item->blkno = childblkno;
            item->parent = stack;
            item->downlinkoffnum = downlinkoffnum;
            state.stack = stack = item;
        } else
        {
            /*
             * Leaf page. Insert the new key. We've already updated all the
             * parents on the way down, but we might have to split the page if
             * it doesn't fit. gistinserthere() will take care of that.
             */

            /*
             * Swap shared lock for an exclusive one. Be careful, the page may
             * change while we unlock/lock the page...
             */
            if (!xlocked)
            {
                LockBuffer(stack->buffer, GIST_UNLOCK);
                LockBuffer(stack->buffer, GIST_EXCLUSIVE);
                xlocked = true;
                stack->page = (Page) BufferGetPage(stack->buffer);
                stack->lsn = PageGetLSN(stack->page);

                if (stack->blkno == GIST_ROOT_BLKNO)
                {
                    /*
                     * the only page that can become inner instead of leaf is
                     * the root page, so for root we should recheck it
                     */
                    if (!GistPageIsLeaf(stack->page))
                    {
                        /*
                         * very rare situation: during unlock/lock index with
                         * number of pages = 1 was increased
                         */
                        LockBuffer(stack->buffer, GIST_UNLOCK);
                        xlocked = false;
                        continue;
                    }

                    /*
                     * we don't need to check root split, because checking
                     * leaf/inner is enough to recognize split for root
                     */
                } else if ((GistFollowRight(stack->page) ||
                            stack->parent->lsn < GistPageGetNSN(stack->page)) &&
                           GistPageIsDeleted(stack->page))
                {
                    /*
                     * The page was split or deleted while we momentarily
                     * unlocked the page. Go back to parent.
                     */
                    UnlockReleaseBuffer(stack->buffer);
                    xlocked = false;
                    state.stack = stack = stack->parent;
                    continue;
                }
            }

            /* now state.stack->(page, buffer and blkno) points to leaf page */

            gistinserttuple(&state, stack, giststate, itup,
                            InvalidOffsetNumber);
            LockBuffer(stack->buffer, GIST_UNLOCK);

            /* Release any pins we might still hold before exiting */
            for (; stack; stack = stack->parent)
                ReleaseBuffer(stack->buffer);
            break;
        }
    }
}
}