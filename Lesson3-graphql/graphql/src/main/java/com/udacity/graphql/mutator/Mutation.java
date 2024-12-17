package com.udacity.graphql.mutator;

import com.udacity.graphql.entity.Location;
import com.udacity.graphql.exception.LocationNotFoundException;
import com.udacity.graphql.repository.LocationRepository;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class Mutation{
    private LocationRepository locationRepository;

    public Mutation(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @MutationMapping
    public Location newLocation(@Argument String name,@Argument String address) {
        Location location = new Location(name, address);
        locationRepository.save(location);
        return location;
    }

    @MutationMapping
    public boolean deleteLocation(@Argument Long id) {
        locationRepository.deleteById(id);
        return true;
    }

    @MutationMapping
    public Location updateLocationName(@Argument String newName,@Argument Long id) {
        Optional<Location> optionalLocation =
                locationRepository.findById(id);

        if(optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setName(newName);
            locationRepository.save(location);
            return location;
        } else {
            throw new LocationNotFoundException("Location Not Found", id);
        }
    }
}
