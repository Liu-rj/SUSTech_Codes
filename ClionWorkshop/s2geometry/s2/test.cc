//
// Created by lrj on 2021/11/8.
//

#include "s2point.h"
#include "s2latlng.h"

extern "C" {
#include "postgres.h"
#include "fmgr.h"

#ifdef PG_MODULE_MAGIC
PG_MODULE_MAGIC;
#endif

PG_FUNCTION_INFO_V1(test_equal);
}

extern "C" Datum test_equal(PG_FUNCTION_ARGS)
{
    int lat_radians = PG_GETARG_INT32(0);
    int lng_radians = PG_GETARG_INT32(1);
    int FLAGS_num_index_points = 10;
    for (int i = 0; i < FLAGS_num_index_points; ++i) {
        S2Point point(S2LatLng::FromDegrees(0, 0));
    }
    PG_RETURN_BOOL(lat_radians == lng_radians);
}