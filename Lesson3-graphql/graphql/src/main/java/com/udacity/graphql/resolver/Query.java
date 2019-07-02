package com.udacity.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.udacity.graphql.entity.Location;
import com.udacity.graphql.repository.LocationRepository;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
    private LocationRepository locationRepository;

    public Query(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<Location> findAllLocations() {
        return locationRepository.findAll();
    }
}
