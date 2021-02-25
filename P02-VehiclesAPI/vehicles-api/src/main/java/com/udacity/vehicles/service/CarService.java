package com.udacity.vehicles.service;

import java.math.BigDecimal;
import java.net.URI;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

/**
 * Implements the car service create, read, update or delete information about
 * vehicles, as well as gather related location and price data when desired.
 */
@Service
public class CarService {

	private final CarRepository repository;
	private WebClient pricingClient;
	private WebClient mapClient;

	public CarService(CarRepository repository, @Qualifier("pricing") WebClient pricingClient,
			@Qualifier("maps") WebClient mapClient) {
		this.repository = repository;
		this.pricingClient = pricingClient;
		this.mapClient = mapClient;
	}

	/**
	 * Gathers a list of all vehicles
	 * 
	 * @return a list of all vehicles in the CarRepository
	 */
	public List<Car> list() {
		return repository.findAll();
	}

	/**
	 * Gets car information by ID (or throws exception if non-existent)
	 * 
	 * @param id the ID number of the car to gather information on
	 * @return the requested car's information, including location and price
	 */
	public Car findById(Long id) {
		return findById(id, false);
	}

	private Car findById(Long id, boolean skipExternalServices) {
		Car car = repository.findById(id).orElseThrow(CarNotFoundException::new);

		if (!skipExternalServices) {
			car.setPrice(getFormattedPriceForVehicle(car));
			car.setLocation(getAddressDataForVehicle(car));
		}
		return car;
	}

	private Location getAddressDataForVehicle(Car car) {
		Assert.notNull(car.getLocation(), "Location data must not be null!");

		// design decision: hide lat/lon-information from the user; thus do not provide
		// this data on a /cars/{id}-request
		// one could easily include this data by setting either the received data in the
		// existing Location-object
		// or adding the lat/lon-information from the original Location-object to the
		// new one below
		Location location = mapClient.get().uri(getUriForMapService(car)).retrieve().bodyToMono(Location.class).onErrorReturn(car.getLocation()).block();
		return location;
	}

	private String getFormattedPriceForVehicle(Car car) {
		PriceFallback fallback = new PriceFallback();
		fallback.setCurrency("USD");
		fallback.setPrice(new BigDecimal(0));
		fallback.setError("Error while trying to get price!");
		Price price = pricingClient.get().uri(getURIForPriceService(car)).retrieve().bodyToMono(Price.class)
				.onErrorReturn(fallback).block();
		NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault());
		currencyInstance.setCurrency(Currency.getInstance(price.getCurrency()));
		String priceFormatted = currencyInstance.format(price.getPrice());
		return priceFormatted;
	}

	private Function<UriBuilder, URI> getUriForMapService(Car car) {
		return uriBuilder -> uriBuilder.path("/maps").queryParam("lat", car.getLocation().getLat())
				.queryParam("lon", car.getLocation().getLon()).build();
	}

	private Function<UriBuilder, URI> getURIForPriceService(Car car) {
		return uriBuilder -> uriBuilder.path("/prices/").path(car.getId().toString()).build();
	}

	/**
	 * Either creates or updates a vehicle, based on prior existence of car
	 * 
	 * @param car A car object, which can be either new or existing
	 * @return the new/updated car is stored in the repository
	 */
	public Car save(Car car) {
		if (car.getId() != null) {
			return repository.findById(car.getId()).map(carToBeUpdated -> {
				carToBeUpdated.setDetails(car.getDetails());
				carToBeUpdated.setLocation(car.getLocation());
				return repository.save(carToBeUpdated);
			}).orElseThrow(CarNotFoundException::new);
		}

		return repository.save(car);
	}

	/**
	 * Deletes a given car by ID
	 * 
	 * @param id the ID number of the car to delete
	 */
	public void delete(Long id) {
		Car car = repository.findById(id)
				.orElseThrow(() -> new CarNotFoundException("Car to be deleted with id [" + id + "] not found!"));
		repository.delete(car);
	}

	class PriceFallback extends Price {
		private String error;

		public PriceFallback() {
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
	}
}
