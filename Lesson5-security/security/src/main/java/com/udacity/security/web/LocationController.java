package com.udacity.security.web;

import com.udacity.security.entity.Location;
import com.udacity.security.service.LocationService;
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

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> list = locationService.getLocations();
        return new ResponseEntity<List<Location>>(list, HttpStatus.OK);
    }

}
