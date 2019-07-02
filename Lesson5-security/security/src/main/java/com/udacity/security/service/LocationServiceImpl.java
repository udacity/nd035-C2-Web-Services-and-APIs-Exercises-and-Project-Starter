package com.udacity.security.service;

import com.udacity.security.entity.Location;
import com.udacity.security.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<Location> getLocations() {
        return (List<Location>) locationRepository.findAll();
    }
}
