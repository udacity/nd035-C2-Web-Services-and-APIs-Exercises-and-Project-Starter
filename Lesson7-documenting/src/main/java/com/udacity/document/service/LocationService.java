package com.udacity.document.service;

import com.udacity.document.entity.Location;

import java.util.List;

public interface LocationService {
    public List<Location> retrieveLocations();
    public Location retrieveLocation(long id);
}
