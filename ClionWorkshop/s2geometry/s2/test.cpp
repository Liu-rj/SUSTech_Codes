//
// Created by lrj on 2021/10/31.
//

#include "postgres.h"

PG_MODULE_MAGIC;

PG_FUNCTION_INFO_V1(RadiansToPoint);

Datum RadiansToPoint(PG_FUNCTION_ARGS) {
    double lat_radians = PG_GETARG_FLOAT8(0);
    double lng_radians = PG_GETARG_FLOAT8(1);
    PG_RETURN_BOOL(lat_radians == lng_radians);
}