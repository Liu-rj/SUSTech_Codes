//
// Created by lrj on 2022/2/9.
//

#include "s2/s2earth.h"
#include "s2/s2closest_point_query.h"
#include "datatype/s2cover.h"

extern "C" {
#include "postgres.h"
#include "fmgr.h"
}

extern "C" {
PG_FUNCTION_INFO_V1(s2_dwithin);
Datum s2_dwithin(PG_FUNCTION_ARGS)
{
    auto *cell = (S2CellId *) PG_GETARG_POINTER(0);
    auto *center = (S2CellId *) PG_GETARG_POINTER(1);
//    int length = ((cover->length >> 2) - VARHDRSZ) / (int32) sizeof(cover->cells[0]);

//    if (cell->range_max() < cover->cells[0].range_min() || cell->range_min() > cover->cells[length - 1].range_max())
//    {
//        PG_RETURN_BOOL(false);
//    }
//    for (int i = 0; i < length; ++i)
//    {
//        S2CellId cur = cover->cells[i];
//        if (cell->range_min() >= cur.range_min() && cell->range_max() <= cur.range_max())
//        {
//            PG_RETURN_BOOL(true);
//        }
//    }
    if (cell->range_min() >= center->range_min() && cell->range_max() <= center->range_max())
    {
        PG_RETURN_BOOL(true);
    } else
    {
        PG_RETURN_BOOL(false);
    }
}

PG_FUNCTION_INFO_V1(s2_distance);
Datum s2_distance(PG_FUNCTION_ARGS)
{
    auto *cell1 = (S2CellId *) PG_GETARG_POINTER(0);
    auto *cell2 = (S2CellId *) PG_GETARG_POINTER(1);

    double distance = S1ChordAngle(cell1->ToPoint(), cell2->ToPoint()).length2();

    PG_RETURN_FLOAT8(distance);
}
}