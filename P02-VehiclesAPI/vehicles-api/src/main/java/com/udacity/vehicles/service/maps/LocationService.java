package com.udacity.vehicles.service.maps;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.domain.Location;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final MapsClient mapsClient;

    public LocationService(MapsClient mapsClient) {
        this.mapsClient = mapsClient;
    }

    public Location getLocation(Location location){
        return mapsClient.getAddress(location);
    }
}
