package com.udacity.test.service;

import com.udacity.test.entity.Location;

import java.util.List;

public interface LocationService {
    public List<Location> retrieveLocations();
    public Location retrieveLocation(long id);
}
