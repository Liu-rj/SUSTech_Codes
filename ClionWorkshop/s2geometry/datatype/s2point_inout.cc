//
// Created by lrj on 2021/11/10.
//

#include "s2/s2point.h"
#include "s2/s2latlng.h"
#include <string>
#include <cstdio>
#include <cstdlib>
#include <s2/s2cell_id.h>

extern "C" {
#include "postgres.h"
#include "fmgr.h"

#ifdef PG_MODULE_MAGIC
PG_MODULE_MAGIC;
#endif
}

extern "C" {
PG_FUNCTION_INFO_V1(s2point_in);
Datum s2point_in(PG_FUNCTION_ARGS)
{
    char *str = PG_GETARG_CSTRING(0);
    S2CellId *cell;
    double lat, lng;

    if (sscanf(str, " ( %lf , %lf )", &lng, &lat) != 2)
        ereport(ERROR,
                (errcode(ERRCODE_INVALID_TEXT_REPRESENTATION),
                        errmsg("invalid input syntax for s2point: \"%s\"",
                               str)));

    cell = new S2CellId(S2Point(S2LatLng::FromDegrees(lat, lng)));
    PG_RETURN_POINTER(cell);
}

PG_FUNCTION_INFO_V1(s2point_out);
Datum s2point_out(PG_FUNCTION_ARGS)
{
    auto *cell = (S2CellId *) PG_GETARG_POINTER(0);
    char *result;

    result = psprintf("%lu", cell->id());

    PG_RETURN_CSTRING(result);
}

static int cell_cmp_internal(S2CellId *a, S2CellId *b)
{
    if (a->id() < b->id())
        return -1;
    if (a->id() > b->id())
        return 1;
    return 0;
}

PG_FUNCTION_INFO_V1(cell_lt);
Datum cell_lt(PG_FUNCTION_ARGS)
{
    auto *a = (S2CellId *) PG_GETARG_POINTER(0);
    auto *b = (S2CellId *) PG_GETARG_POINTER(1);

    PG_RETURN_BOOL(cell_cmp_internal(a, b) < 0);
}

PG_FUNCTION_INFO_V1(cell_le);
Datum cell_le(PG_FUNCTION_ARGS)
{
    auto *a = (S2CellId *) PG_GETARG_POINTER(0);
    auto *b = (S2CellId *) PG_GETARG_POINTER(1);

    PG_RETURN_BOOL(cell_cmp_internal(a, b) <= 0);
}

PG_FUNCTION_INFO_V1(cell_eq);
Datum cell_eq(PG_FUNCTION_ARGS)
{
    auto *a = (S2CellId *) PG_GETARG_POINTER(0);
    auto *b = (S2CellId *) PG_GETARG_POINTER(1);

    PG_RETURN_BOOL(cell_cmp_internal(a, b) == 0);
}

PG_FUNCTION_INFO_V1(cell_ge);
Datum cell_ge(PG_FUNCTION_ARGS)
{
    auto *a = (S2CellId *) PG_GETARG_POINTER(0);
    auto *b = (S2CellId *) PG_GETARG_POINTER(1);

    PG_RETURN_BOOL(cell_cmp_internal(a, b) >= 0);
}

PG_FUNCTION_INFO_V1(cell_gt);
Datum cell_gt(PG_FUNCTION_ARGS)
{
    auto *a = (S2CellId *) PG_GETARG_POINTER(0);
    auto *b = (S2CellId *) PG_GETARG_POINTER(1);

    PG_RETURN_BOOL(cell_cmp_internal(a, b) > 0);
}

PG_FUNCTION_INFO_V1(cell_cmp);
Datum cell_cmp(PG_FUNCTION_ARGS)
{
    auto *a = (S2CellId *) PG_GETARG_POINTER(0);
    auto *b = (S2CellId *) PG_GETARG_POINTER(1);

    PG_RETURN_INT32(cell_cmp_internal(a, b));
}

PG_FUNCTION_INFO_V1(s2_astext);
Datum s2_astext(PG_FUNCTION_ARGS)
{
    auto *cell = (S2CellId *) PG_GETARG_POINTER(0);
    char *result;
    auto latlng = cell->ToLatLng().Normalized();

    result = psprintf("%f,%f", latlng.lng().degrees(), latlng.lat().degrees());

    PG_RETURN_CSTRING(result);
}

PG_FUNCTION_INFO_V1(s2_range_min);
Datum s2_range_min(PG_FUNCTION_ARGS)
{
    auto *cell = (S2CellId *) PG_GETARG_POINTER(0);
    auto *minCell = new S2CellId(cell->range_min());

    PG_RETURN_POINTER(minCell);
}

PG_FUNCTION_INFO_V1(s2_range_max);
Datum s2_range_max(PG_FUNCTION_ARGS)
{
    auto *cell = (S2CellId *) PG_GETARG_POINTER(0);
    auto *maxCell = new S2CellId(cell->range_max());

    PG_RETURN_POINTER(maxCell);
}
}