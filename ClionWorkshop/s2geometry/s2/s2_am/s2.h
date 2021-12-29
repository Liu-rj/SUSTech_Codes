//
// Created by lrj on 2021/12/4.
//

#ifndef S2GEOMETRY_S2_H
#define S2GEOMETRY_S2_H

#endif //S2GEOMETRY_S2_H

extern "C" {
#include "postgres.h"
#include "access/amapi.h"
#include "access/transam.h"
#include "access/xlog.h"
#include "access/xlogdefs.h"
#include "storage/block.h"
#include "storage/bufpage.h"
#include "utils/relcache.h"
#include "access/itup.h"
#include "fmgr.h"
#include "lib/pairingheap.h"
#include "storage/bufmgr.h"
#include "storage/buffile.h"
#include "utils/hsearch.h"
#include "access/genam.h"

/* Buffer lock modes */
#define S2_SHARE    BUFFER_LOCK_SHARE
#define S2_EXCLUSIVE    BUFFER_LOCK_EXCLUSIVE
#define S2_UNLOCK BUFFER_LOCK_UNLOCK

/*
* Page opaque data in a GiST index page.
*/
#define F_LEAF                (1 << 0)    /* leaf page */
#define F_DELETED            (1 << 1)    /* the page has been deleted */
#define F_TUPLES_DELETED    (1 << 2)    /* some tuples on the page were
										 * deleted */
#define F_FOLLOW_RIGHT        (1 << 3)    /* page to the right has no downlink */
#define F_HAS_GARBAGE        (1 << 4)    /* some tuples on the page are dead,
										 * but not deleted yet */

typedef XLogRecPtr S2NSN;

/*
 * A bogus LSN / NSN value used during index build. Must be smaller than any
 * real or fake unlogged LSN, so that after an index build finishes, all the
 * splits are considered completed.
 */
#define S2BuildLSN    ((XLogRecPtr) 1)

/*
 * For on-disk compatibility with pre-9.3 servers, NSN is stored as two
 * 32-bit fields on disk, same as LSNs.
 */
typedef PageXLogRecPtr PageS2NSN;

typedef struct S2PageOpaqueData
{
    PageS2NSN nsn;            /* this value must change on page split */
    BlockNumber rightlink;        /* next page if any */
    uint16 flags;            /* see bit definitions above */
    uint16 gist_page_id;    /* for identification of GiST indexes */
} S2PageOpaqueData;

typedef S2PageOpaqueData *S2PageOpaque;

#define S2PageGetOpaque(page) ( (S2PageOpaque) PageGetSpecialPointer(page) )
#define S2PageIsDeleted(page) ( S2PageGetOpaque(page)->flags & F_DELETED)

/*
 * A bogus LSN / NSN value used during index build. Must be smaller than any
 * real or fake unlogged LSN, so that after an index build finishes, all the
 * splits are considered completed.
 */
#define S2BuildLSN    ((XLogRecPtr) 1)

/*
 * The page ID is for the convenience of pg_filedump and similar utilities,
 * which otherwise would have a hard time telling pages of different index
 * types apart.  It should be the last 2 bytes on the page.  This is more or
 * less "free" due to alignment considerations.
 */
#define S2_PAGE_ID        0xFF81
}

extern "C"
{
/*
* Storage type for GiST's reloptions
*/
typedef struct S2Options
{
    int32 vl_len_;        /* varlena header (do not touch directly!) */
    int fillfactor;        /* page fill factor in percent (0..100) */
    int bufferingModeOffset;    /* use buffering build? */
} S2Options;

/*
 * S2STATE: information needed for any GiST index operation
 *
 * This struct retains call info for the index's opclass-specific support
 * functions (per index column), plus the index's tuple descriptor.
 *
 * scanCxt holds the GISTSTATE itself as well as any data that lives for the
 * lifetime of the index operation.  We pass this to the support functions
 * via fn_mcxt, so that they can store scan-lifespan data in it.  The
 * functions are invoked in tempCxt, which is typically short-lifespan
 * (that is, it's reset after each tuple).  However, tempCxt can be the same
 * as scanCxt if we're not bothering with per-tuple context resets.
 */
typedef struct S2STATE
{
    MemoryContext scanCxt;        /* context for scan-lifespan data */
    MemoryContext tempCxt;        /* short-term context for calling functions */

    TupleDesc leafTupdesc;    /* index's tuple descriptor */
    TupleDesc nonLeafTupdesc; /* truncated tuple descriptor for non-leaf
								 * pages */
    TupleDesc fetchTupdesc;    /* tuple descriptor for tuples returned in an
								 * index-only scan */

    FmgrInfo consistentFn[INDEX_MAX_KEYS];
    FmgrInfo unionFn[INDEX_MAX_KEYS];
    FmgrInfo compressFn[INDEX_MAX_KEYS];
    FmgrInfo decompressFn[INDEX_MAX_KEYS];
    FmgrInfo penaltyFn[INDEX_MAX_KEYS];
    FmgrInfo picksplitFn[INDEX_MAX_KEYS];
    FmgrInfo equalFn[INDEX_MAX_KEYS];
    FmgrInfo distanceFn[INDEX_MAX_KEYS];
    FmgrInfo fetchFn[INDEX_MAX_KEYS];

    /* Collations to pass to the support functions */
    Oid supportCollation[INDEX_MAX_KEYS];
} S2STATE;

void freeS2state(S2STATE *giststate);

extern IndexBuildResult *s2build(Relation heapRelation,
                                 Relation indexRelation,
                                 IndexInfo *indexInfo);
extern void s2buildempty(Relation index);
extern bool s2insert(Relation r, Datum *values, bool *isnull,
                     ItemPointer ht_ctid, Relation heapRel,
                     IndexUniqueCheck checkUnique,
                     struct IndexInfo *indexInfo);
extern IndexScanDesc s2beginscan(Relation r, int nkeys, int norderbys);
extern void s2rescan(IndexScanDesc scan, ScanKey key, int nkeys,
                     ScanKey orderbys, int norderbys);
extern void s2endscan(IndexScanDesc scan);
extern bool s2validate(Oid opclassoid);
extern IndexBulkDeleteResult *s2bulkdelete(IndexVacuumInfo *info, IndexBulkDeleteResult *stats,
                                           IndexBulkDeleteCallback callback, void *callback_state);
extern IndexBulkDeleteResult *s2vacuumcleanup(IndexVacuumInfo *info, IndexBulkDeleteResult *stats);
extern void s2costestimate(PlannerInfo *root, IndexPath *path, double loop_count,
                           Cost *indexStartupCost, Cost *indexTotalCost,
                           Selectivity *indexSelectivity, double *indexCorrelation,
                           double *indexPages);
bytea *s2options(Datum reloptions, bool validate);
Buffer s2NewBuffer(Relation r);
void S2InitBuffer(Buffer b, uint32 f);
IndexTuple s2FormTuple(S2STATE *s2State, Relation r,
                       Datum attdata[], bool isnull[], bool isleaf);
void s2doinsert(Relation r, IndexTuple itup, Size freespace,
                S2STATE *giststate, Relation heapRel, bool is_build);

/*
 * On a deleted page, we store this struct. A deleted page doesn't contain any
 * tuples, so we don't use the normal page layout with line pointers. Instead,
 * this struct is stored right after the standard page header. pd_lower points
 * to the end of this struct. If we add fields to this struct in the future, we
 * can distinguish the old and new formats by pd_lower.
 */
typedef struct S2DeletedPageContents
{
    /* last xid which could see the page in a scan */
    FullTransactionId deleteXid;
} S2DeletedPageContents;

static inline FullTransactionId S2PageGetDeleteXid(Page page)
{
    Assert(S2PageIsDeleted(page));

    /* Is the deleteXid field present? */
    if (((PageHeader) page)->pd_lower >= MAXALIGN(SizeOfPageHeaderData) +
                                         offsetof(S2DeletedPageContents, deleteXid) + sizeof(FullTransactionId))
    {
        return ((S2DeletedPageContents *) PageGetContents(page))->deleteXid;
    } else
        return FullTransactionIdFromEpochAndXid(0, FirstNormalTransactionId);
}
}