package com.udacity.graphql.service;

import com.udacity.graphql.entity.Location;

import java.util.List;

public interface LocationService {
    List<Location> retrieveLocations();
}
