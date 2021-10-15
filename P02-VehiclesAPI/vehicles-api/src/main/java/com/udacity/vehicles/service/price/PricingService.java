package com.udacity.vehicles.service.price;

import com.udacity.vehicles.client.prices.PriceClient;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private final PriceClient priceClient;

    public PricingService(PriceClient priceClient) {
        this.priceClient = priceClient;
    }

    public String getCarPrice(Long vehicleId){
        return priceClient.getPrice(vehicleId);
    }
}
