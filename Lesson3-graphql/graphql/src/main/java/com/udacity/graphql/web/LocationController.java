package com.udacity.graphql.web;

import com.udacity.graphql.entity.Location;
import com.udacity.graphql.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> list = locationService.retrieveLocations();
        return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
    }

}
