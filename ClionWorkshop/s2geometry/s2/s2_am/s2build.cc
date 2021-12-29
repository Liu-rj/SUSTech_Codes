//
// Created by lrj on 2021/12/4.
//

extern "C" {
#include "s2.h"
#include "access/tableam.h"
#include "access/xloginsert.h"
#include "catalog/index.h"
#include "miscadmin.h"
#include "optimizer/optimizer.h"
#include "storage/bufmgr.h"
#include "storage/smgr.h"
#include "utils/memutils.h"
#include "utils/rel.h"
}

extern "C" {
/* Working state for s2build and its callback */
typedef struct S2BuildState
{
    Relation indexrel;
    Relation heaprel;
    S2STATE *s2State;

    int64 indtuples;        /* number of tuples indexed */
    int64 indtuplesSize;    /* total size of all indexed tuples */

    Size freespace;        /* amount of free space to leave on pages */

//    /*
//     * Extra data structures used during a buffering build. 'gfbb' contains
//     * information related to managing the build buffers. 'parentMap' is a
//     * lookup table of the parent of each internal page.
//     */
//    GISTBuildBuffers *gfbb;
//    HTAB	   *parentMap;
//
//    GistBufferingMode bufferingMode;
} S2BuildState;
/*
* Per-tuple callback for table_index_build_scan.
*/
static void s2BuildCallback(Relation index,
                            HeapTuple htup,
                            Datum *values,
                            bool *isnull,
                            bool tupleIsAlive,
                            void *state)
{
    S2BuildState *buildstate = (S2BuildState *) state;
    IndexTuple itup;
    MemoryContext oldCtx;

    oldCtx = MemoryContextSwitchTo(buildstate->s2State->tempCxt);

    /* form an index tuple and point it at the heap tuple */
    itup = s2FormTuple(buildstate->s2State, index, values, isnull, true);
    itup->t_tid = htup->t_self;

    s2doinsert(index, itup, buildstate->freespace,
               buildstate->s2State, buildstate->heaprel, true);

//    if (buildstate->bufferingMode == GIST_BUFFERING_ACTIVE)
//    {
//        /* We have buffers, so use them. */
//        gistBufferingBuildInsert(buildstate, itup);
//    } else
//    {
//        /*
//         * There's no buffers (yet). Since we already have the index relation
//         * locked, we call gistdoinsert directly.
//         */
//        gistdoinsert(index, itup, buildstate->freespace,
//                     buildstate->s2State, buildstate->heaprel, true);
//    }

    /* Update tuple count and total size. */
    buildstate->indtuples += 1;
    buildstate->indtuplesSize += IndexTupleSize(itup);

    MemoryContextSwitchTo(oldCtx);
    MemoryContextReset(buildstate->s2State->tempCxt);

//    if (buildstate->bufferingMode == GIST_BUFFERING_ACTIVE &&
//        buildstate->indtuples % BUFFERING_MODE_TUPLE_SIZE_STATS_TARGET == 0)
//    {
//        /* Adjust the target buffer size now */
//        buildstate->gfbb->pagesPerBuffer =
//                calculatePagesPerBuffer(buildstate, buildstate->gfbb->levelStep);
//    }
//
//    /*
//     * In 'auto' mode, check if the index has grown too large to fit in cache,
//     * and switch to buffering mode if it has.
//     *
//     * To avoid excessive calls to smgrnblocks(), only check this every
//     * BUFFERING_MODE_SWITCH_CHECK_STEP index tuples
//     */
//    if ((buildstate->bufferingMode == GIST_BUFFERING_AUTO &&
//         buildstate->indtuples % BUFFERING_MODE_SWITCH_CHECK_STEP == 0 &&
//         effective_cache_size < smgrnblocks(index->rd_smgr, MAIN_FORKNUM)) ||
//        (buildstate->bufferingMode == GIST_BUFFERING_STATS &&
//         buildstate->indtuples >= BUFFERING_MODE_TUPLE_SIZE_STATS_TARGET))
//    {
//        /*
//         * Index doesn't fit in effective cache anymore. Try to switch to
//         * buffering build mode.
//         */
//        gistInitBuffering(buildstate);
//    }
}

IndexBuildResult *s2build(Relation heap,
                          Relation index,
                          IndexInfo *indexInfo)
{
    IndexBuildResult *result;
    double reltuples;
    S2BuildState buildstate;
    Buffer buffer;
    Page page;
    MemoryContext oldcxt = CurrentMemoryContext;
    int fillfactor;

    /* initialize the root page */
    buffer = s2NewBuffer(index);
    Assert(BufferGetBlockNumber(buffer) == GIST_ROOT_BLKNO);
    page = BufferGetPage(buffer);

    START_CRIT_SECTION();

    S2InitBuffer(buffer, F_LEAF);

    MarkBufferDirty(buffer);
    PageSetLSN(page, S2BuildLSN);

    UnlockReleaseBuffer(buffer);

    END_CRIT_SECTION();

    /*
     * Do the heap scan.
     */
    reltuples = table_index_build_scan(heap, index, indexInfo, true, true,
                                       s2BuildCallback,
                                       (void *) &buildstate, nullptr);

//    /*
//     * If buffering was used, flush out all the tuples that are still in the
//     * buffers.
//     */
//    if (buildstate.bufferingMode == GIST_BUFFERING_ACTIVE)
//    {
//        elog(DEBUG1, "all tuples processed, emptying buffers");
//        gistEmptyAllBuffers(&buildstate);
//        gistFreeBuildBuffers(buildstate.gfbb);
//    }

    /* okay, all heap tuples are indexed */
    MemoryContextSwitchTo(oldcxt);
    MemoryContextDelete(buildstate.s2State->tempCxt);

    freeS2state(buildstate.s2State);

    /*
     * We didn't write WAL records as we built the index, so if WAL-logging is
     * required, write all pages to the WAL now.
     */
    if (RelationNeedsWAL(index))
    {
        log_newpage_range(index, MAIN_FORKNUM,
                          0, RelationGetNumberOfBlocks(index),
                          true);
    }

    /*
     * Return statistics
     */
    result = (IndexBuildResult *) palloc(sizeof(IndexBuildResult));

    result->heap_tuples = reltuples;
    result->index_tuples = (double) buildstate.indtuples;

    return result;
}

void s2buildempty(Relation indexRelation)
{
    Buffer buffer;

    /* Initialize the root page */
    buffer = ReadBufferExtended(index, INIT_FORKNUM, P_NEW, RBM_NORMAL, NULL);
    LockBuffer(buffer, BUFFER_LOCK_EXCLUSIVE);

    /* Initialize and xlog buffer */
    START_CRIT_SECTION();
    GISTInitBuffer(buffer, F_LEAF);
    MarkBufferDirty(buffer);
    log_newpage_buffer(buffer, true);
    END_CRIT_SECTION();

    /* Unlock and release the buffer */
    UnlockReleaseBuffer(buffer);
}
}