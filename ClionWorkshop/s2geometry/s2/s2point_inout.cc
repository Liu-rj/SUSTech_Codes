//
// Created by lrj on 2021/11/10.
//

#include "s2point.h"
#include "s2latlng.h"
#include "s2_am/geobase.h"
#include <string>
#include <cstdio>
#include <cstdlib>

extern "C" {
#include "postgres.h"
#include "fmgr.h"

PG_FUNCTION_INFO_V1(s2point_in);
PG_FUNCTION_INFO_V1(s2point_out);

#ifdef PG_MODULE_MAGIC
PG_MODULE_MAGIC;
#endif
}

extern "C" {
Datum s2point_in(PG_FUNCTION_ARGS)
{
    char *str = PG_GETARG_CSTRING(0);
    GeoBase *base;
    double lat, lng;

    if (sscanf(str, " ( %lf , %lf )", &lng, &lat) != 2)
        ereport(ERROR,
                (errcode(ERRCODE_INVALID_TEXT_REPRESENTATION),
                        errmsg("invalid input syntax for complex: \"%s\"",
                               str)));

    base = (GeoBase *) palloc(sizeof(GeoBase));
    base->lat = lat;
    base->lng = lng;
    PG_RETURN_POINTER(base);
}

Datum s2point_out(PG_FUNCTION_ARGS)
{
    GeoBase *base = (GeoBase *) PG_GETARG_POINTER(0);
    char *result;

    result = psprintf("(%g,%g)", base->lng, base->lat);
    PG_RETURN_CSTRING(result);
}
}