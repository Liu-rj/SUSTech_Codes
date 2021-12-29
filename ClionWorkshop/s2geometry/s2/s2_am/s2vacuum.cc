//
// Created by lrj on 2021/12/4.
//

extern "C"
{
#include "s2.h"
}

extern "C"
{
/*
* VACUUM bulkdelete stage: remove index entries.
*/
IndexBulkDeleteResult *s2bulkdelete(IndexVacuumInfo *info, IndexBulkDeleteResult *stats,
                                    IndexBulkDeleteCallback callback, void *callback_state)
{
    GistBulkDeleteResult *gist_stats = (GistBulkDeleteResult *) stats;

    /* allocate stats if first time through, else re-use existing struct */
    if (gist_stats == NULL)
        gist_stats = create_GistBulkDeleteResult();

    gistvacuumscan(info, gist_stats, callback, callback_state);

    return (IndexBulkDeleteResult *) gist_stats;
}

/*
 * VACUUM cleanup stage: delete empty pages, and update index statistics.
 */
IndexBulkDeleteResult *s2vacuumcleanup(IndexVacuumInfo *info, IndexBulkDeleteResult *stats)
{
    GistBulkDeleteResult *gist_stats = (GistBulkDeleteResult *) stats;

    /* No-op in ANALYZE ONLY mode */
    if (info->analyze_only)
        return stats;

    /*
     * If gistbulkdelete was called, we need not do anything, just return the
     * stats from the latest gistbulkdelete call.  If it wasn't called, we
     * still need to do a pass over the index, to obtain index statistics.
     */
    if (gist_stats == NULL)
    {
        gist_stats = create_GistBulkDeleteResult();
        gistvacuumscan(info, gist_stats, NULL, NULL);
    }

    /*
     * If we saw any empty pages, try to unlink them from the tree so that
     * they can be reused.
     */
    gistvacuum_delete_empty_pages(info, gist_stats);

    /* we don't need the internal and empty page sets anymore */
    MemoryContextDelete(gist_stats->page_set_context);
    gist_stats->page_set_context = NULL;
    gist_stats->internal_page_set = NULL;
    gist_stats->empty_leaf_set = NULL;

    /*
     * It's quite possible for us to be fooled by concurrent page splits into
     * double-counting some index tuples, so disbelieve any total that exceeds
     * the underlying heap's count ... if we know that accurately.  Otherwise
     * this might just make matters worse.
     */
    if (!info->estimated_count)
    {
        if (gist_stats->stats.num_index_tuples > info->num_heap_tuples)
            gist_stats->stats.num_index_tuples = info->num_heap_tuples;
    }

    return (IndexBulkDeleteResult *) gist_stats;
}
}