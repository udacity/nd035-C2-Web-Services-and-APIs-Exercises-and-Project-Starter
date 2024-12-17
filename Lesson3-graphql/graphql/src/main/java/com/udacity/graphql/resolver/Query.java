package com.udacity.graphql.resolver;

import com.udacity.graphql.entity.Location;
import com.udacity.graphql.repository.LocationRepository;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class Query{
    private LocationRepository locationRepository;

    public Query(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @QueryMapping
    public Iterable<Location> findAllLocations() {
        return locationRepository.findAll();
    }
}
