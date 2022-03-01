//
// Created by lrj on 2022/2/7.
//

extern "C" {
#include "postgres.h"
#include "fmgr.h"
#include <cmath>
}

extern "C" {
PG_FUNCTION_INFO_V1(proj_dist);
Datum proj_dist(PG_FUNCTION_ARGS)
{
    double tolerance = PG_GETARG_FLOAT8(0);
    PG_RETURN_FLOAT8(2 * sin(0.5 * Min(M_PI, (tolerance / 6371010.0))));
}
}