//
// Created by lrj on 2021/11/10.
//

#include "s2point.h"
#include "s2latlng.h"
#include <string>

extern "C" {
#include "postgres.h"
#include "fmgr.h"

//PG_MODULE_MAGIC;

PG_FUNCTION_INFO_V1(s2point_in);
PG_FUNCTION_INFO_V1(s2point_out);
}

extern "C" Datum s2point_in(PG_FUNCTION_ARGS) {
    char *char_ptr = PG_GETARG_CSTRING(0);
    S2Point *point = nullptr;
    int deli;
    double lat, lng;

    /* Empty string. */
    if (char_ptr[0] == '\0')
        ereport(ERROR, (errmsg("parse error - invalid geometry")));

//    deli = str.find(",");
//    lng = std::stod(str.substr(0, deli));
//    lat = std::stod(str.substr(deli + 1));
    point = new S2Point(S2LatLng::FromDegrees(10, 0));
    PG_RETURN_POINTER(point);
}

extern "C" Datum s2point_out(PG_FUNCTION_ARGS) {
    S2Point *point = (S2Point *) PG_GETARG_DATUM(0);
    S2LatLng latLng(*point);
    std::string str;
    char *buffer;
    double lat, lng;

    lat = latLng.lat().degrees();
    lng = latLng.lng().degrees();
    str = std::to_string(lng) + "," + std::to_string(lat);
    buffer = (char *) malloc(20);
    strcpy(buffer, str.c_str());
    PG_RETURN_CSTRING(buffer);
}