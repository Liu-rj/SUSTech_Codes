//
// Created by lrj on 2022/2/8.
//

#include "s2/s2point.h"
#include "s2/s2latlng.h"
#include "s2cover.h"
#include "s2/s2min_distance_targets.h"
#include "s2/s2region_coverer.h"
#include "s2/s2earth.h"
#include "s2/s2closest_point_query.h"
#include <string>
#include <cstdio>
#include <cstdlib>
#include <s2/s2cell_id.h>

extern "C" {
#include "postgres.h"
#include "fmgr.h"

PG_FUNCTION_INFO_V1(s2cover_in);
PG_FUNCTION_INFO_V1(s2cover_out);
}

extern "C" {
Datum s2cover_in(PG_FUNCTION_ARGS)
{
    char *str = PG_GETARG_CSTRING(0);
    double lat, lng, max_distance;
    std::vector<S2CellId> covering;
    S2Cover *cover;

    if (sscanf(str, " ( %lf , %lf , %lf )", &lng, &lat, &max_distance) != 3)
        ereport(ERROR,
                (errcode(ERRCODE_INVALID_TEXT_REPRESENTATION),
                        errmsg("invalid input syntax for s2cover: \"%s\"",
                               str)));

    S2MinDistance distance(S1Angle::Radians(S2Earth::MetersToRadians(max_distance)));
    S2Point point(S2LatLng::FromDegrees(lat, lng));
    S2ClosestPointQuery<int>::PointTarget target(point);
    S2Cap cap = target.GetCapBound();
    S2RegionCoverer coverer;
    coverer.mutable_options()->set_max_cells(4);
    S1ChordAngle radius = cap.radius() + distance.GetChordAngleBound();
    S2Cap search_cap(cap.center(), radius);
    coverer.GetFastCovering(search_cap, &covering);
    S2CellId cells[covering.size()];
    for (int i = 0; i < covering.size(); ++i)
    {
        cells[i] = covering[i];
    }
    cover = (S2Cover *) palloc(VARHDRSZ + sizeof(cells));
    SET_VARSIZE(cover, VARHDRSZ + sizeof(cells));
    memcpy(cover->cells, cells, sizeof(cells));

    PG_RETURN_POINTER(cover);
}

Datum s2cover_out(PG_FUNCTION_ARGS)
{
    auto *cover = (S2Cover *) PG_GETARG_POINTER(0);
    char *result;
    int length = ((cover->length >> 2) - VARHDRSZ) / (int32) sizeof(cover->cells[0]);

    result = psprintf("%lu", cover->cells[0].id());

    for (int i = 1; i < length; ++i)
    {
        strcat(result, psprintf(",%lu", cover->cells[i].id()));
    }

    PG_RETURN_CSTRING(result);
}
}