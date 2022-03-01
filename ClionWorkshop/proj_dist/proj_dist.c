#include "postgres.h"
#include "fmgr.h"
#include <math.h>

PG_MODULE_MAGIC;

PG_FUNCTION_INFO_V1(proj_dist);
Datum proj_dist(PG_FUNCTION_ARGS)
{
	double tolerance = PG_GETARG_FLOAT8(0);
	PG_RETURN_FLOAT8(2 * sin(0.5 * Min(M_PI, (tolerance / 6371010.0))));
}
