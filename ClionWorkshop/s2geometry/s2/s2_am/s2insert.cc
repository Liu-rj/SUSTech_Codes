//
// Created by lrj on 2021/12/4.
//

extern "C"
{
#include "s2.h"
}
/*
 *	s2insert -- wrapper for s2 tuple insertion.
 *
 *	  This is the public interface routine for tuple insertion in GiSTs.
 *	  It doesn't do any work; just locks the relation and passes the buck.
 */
bool
s2insert(Relation r, Datum *values, bool *isnull,
           ItemPointer ht_ctid, Relation heapRel,
           IndexUniqueCheck checkUnique,
           IndexInfo *indexInfo)
{
    GISTSTATE  *giststate = (GISTSTATE *) indexInfo->ii_AmCache;
    IndexTuple	itup;
    MemoryContext oldCxt;

    /* Initialize GISTSTATE cache if first call in this statement */
    if (giststate == NULL)
    {
        oldCxt = MemoryContextSwitchTo(indexInfo->ii_Context);
        giststate = initGISTstate(r);
        giststate->tempCxt = createTempGistContext();
        indexInfo->ii_AmCache = (void *) giststate;
        MemoryContextSwitchTo(oldCxt);
    }

    oldCxt = MemoryContextSwitchTo(giststate->tempCxt);

    itup = gistFormTuple(giststate, r,
                         values, isnull, true /* size is currently bogus */ );
    itup->t_tid = *ht_ctid;

    gistdoinsert(r, itup, 0, giststate, heapRel, false);

    /* cleanup */
    MemoryContextSwitchTo(oldCxt);
    MemoryContextReset(giststate->tempCxt);

    return false;
}