//
// Created by lrj on 2021/12/4.
//

extern "C"
{
#include "s2.h"
}

extern "C"
{
void s2costestimate(PlannerInfo *root, IndexPath *path, double loop_count,
                 Cost *indexStartupCost, Cost *indexTotalCost,
                 Selectivity *indexSelectivity, double *indexCorrelation,
                 double *indexPages)
{
    IndexOptInfo *index = path->indexinfo;
    GenericCosts costs;
    Cost		descentCost;

    MemSet(&costs, 0, sizeof(costs));

//    genericcostestimate(root, path, loop_count, &costs);

    /*
     * We model index descent costs similarly to those for btree, but to do
     * that we first need an idea of the tree height.  We somewhat arbitrarily
     * assume that the fanout is 100, meaning the tree height is at most
     * log100(index->pages).
     *
     * Although this computation isn't really expensive enough to require
     * caching, we might as well use index->tree_height to cache it.
     */
    if (index->tree_height < 0) /* unknown? */
    {
        if (index->pages > 1)	/* avoid computing log(0) */
            index->tree_height = (int) (log(index->pages) / log(100.0));
        else
            index->tree_height = 0;
    }

    /*
     * Add a CPU-cost component to represent the costs of initial descent. We
     * just use log(N) here not log2(N) since the branching factor isn't
     * necessarily two anyway.  As for btree, charge once per SA scan.
     */
    if (index->tuples > 1)		/* avoid computing log(0) */
    {
        descentCost = ceil(log(index->tuples)) * cpu_operator_cost;
        costs.indexStartupCost += descentCost;
        costs.indexTotalCost += costs.num_sa_scans * descentCost;
    }

    /*
     * Likewise add a per-page charge, calculated the same as for btrees.
     */
    descentCost = (index->tree_height + 1) * 50.0 * cpu_operator_cost;
    costs.indexStartupCost += descentCost;
    costs.indexTotalCost += costs.num_sa_scans * descentCost;

    *indexStartupCost = costs.indexStartupCost;
    *indexTotalCost = costs.indexTotalCost;
    *indexSelectivity = costs.indexSelectivity;
    *indexCorrelation = costs.indexCorrelation;
    *indexPages = costs.numIndexPages;
}
}