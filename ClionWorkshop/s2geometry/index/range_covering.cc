//
// Created by lrj on 2022/2/8.
//

#include "s2/s2earth.h"
#include "s2/s2closest_point_query.h"
#include "datatype/s2cover.h"

extern "C" {
#include "postgres.h"
#include "fmgr.h"
}

extern "C" {
PG_FUNCTION_INFO_V1(range_cover);
Datum range_cover(PG_FUNCTION_ARGS)
{
    auto *cell = (S2CellId *) PG_GETARG_POINTER(0);
    double max_distance = PG_GETARG_FLOAT8(1);
    std::vector<S2CellId> covering;
    S2CellId *out;
//    S2Cover *cover;

    S2MinDistance distance(S1Angle::Radians(S2Earth::MetersToRadians(max_distance)));
    S2Point point = cell->ToPoint();
    S2ClosestPointQuery<int>::PointTarget target(point);
    S2Cap cap = target.GetCapBound();
    S2RegionCoverer coverer;
    coverer.mutable_options()->set_max_cells(1);
    S1ChordAngle radius = cap.radius() + distance.GetChordAngleBound();
    S2Cap search_cap(cap.center(), radius);
    coverer.GetFastCovering(search_cap, &covering);
    out = new S2CellId(covering[0]);
//    S2CellId cells[covering.size()];
//    for (int i = 0; i < covering.size(); ++i)
//    {
//        cells[i] = covering[i];
//    }
//    cover = (S2Cover *) palloc(VARHDRSZ + sizeof(cells));
//    SET_VARSIZE(cover, VARHDRSZ + sizeof(cells));
//    memcpy(cover->cells, cells, sizeof(cells));

    PG_RETURN_POINTER(out);
}
}