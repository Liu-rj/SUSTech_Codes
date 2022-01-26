//
// Created by lrj on 2021/11/8.
//

#include <iostream>
#include "s2/s2testing.h"
#include "s2/s2earth.h"
#include "s2/s2latlng.h"
#include "s2/s2point_index.h"
#include "s2/s2closest_point_query.h"

int main()
{
    // Build an index containing random points anywhere on the Earth.
    S2PointIndex<int> index;
    S2Point point(S2LatLng::FromDegrees(20, 140));
    index.Add(point, 0);
    for (int i = 1; i < 1000; ++i)
    {
        index.Add(S2Testing::RandomPoint(), i);
    }

    // Create a query to search within the given radius of a target point.
    S2ClosestPointQuery<int> query(&index);
    query.mutable_options()->set_max_distance(
            S1Angle::Radians(S2Earth::KmToRadians(1)));

    // Repeatedly choose a random target point, and count how many index points
    // are within the given radius of that point.
    unsigned long long num_found = 0;
    S2ClosestPointQuery<int>::PointTarget target(point);
    num_found += query.FindClosestPoints(&target).size();
    for (int i = 0; i < 10; ++i)
    {
        target = S2ClosestPointQuery<int>::PointTarget(S2Testing::RandomPoint());
        num_found += query.FindClosestPoints(&target).size();
    }

    printf("Found %lld points in %d queries\n", num_found, 10);

}