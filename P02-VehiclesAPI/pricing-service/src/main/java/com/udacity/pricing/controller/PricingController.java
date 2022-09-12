package com.udacity.pricing.controller;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {

    /**
     * Gets the price for a requested vehicle.
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping
    public Price get(@RequestParam Long vehicleId) {
        try {
            return PricingService.getPrice(vehicleId);
        } catch (PriceException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Price Not Found", ex);
        }

    }
}
