package com.udacity.document.service;

import com.udacity.document.entity.Location;
import com.udacity.document.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationRepository locationRepository;

    public List<Location> retrieveLocations() {
        return (List<Location>) locationRepository.findAll();
    }
    public Location retrieveLocation(long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);

        if(optionalLocation.isPresent())
            return optionalLocation.get();
        else
            throw new RuntimeException();
    }
}
