//
// Created by lrj on 2022/2/8.
//

#ifndef S2GEOMETRY_S2COVER_H
#define S2GEOMETRY_S2COVER_H
#include "s2/s2earth.h"
#include "s2/s2closest_point_query.h"

typedef struct {
    int32 length;
    S2CellId cells[1];
} S2Cover;
#endif //S2GEOMETRY_S2COVER_H
