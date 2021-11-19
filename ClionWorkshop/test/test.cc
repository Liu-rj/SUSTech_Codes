extern "C" {
#include "postgres.h"
#include "fmgr.h"

PG_MODULE_MAGIC;

PG_FUNCTION_INFO_V1(test);
}

extern "C" Datum test(PG_FUNCTION_ARGS)
{
    int lat_radians = PG_GETARG_INT32(0);
    int lng_radians = PG_GETARG_INT32(1);
    PG_RETURN_BOOL(lat_radians == lng_radians);
}
