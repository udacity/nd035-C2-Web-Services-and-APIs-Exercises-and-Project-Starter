package com.udacity.test.web;

import com.udacity.test.entity.Location;
import com.udacity.test.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/location/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable("id") long id) {
        return new ResponseEntity<Location>(locationService.retrieveLocation(id), HttpStatus.OK);
    }

}
