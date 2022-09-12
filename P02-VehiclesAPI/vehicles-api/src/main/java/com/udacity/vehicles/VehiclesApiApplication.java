package com.udacity.vehicles;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Launches a Spring Boot application for the Vehicles API,
 * initializes the car manufacturers in the database,
 * and launches web clients to communicate with maps and pricing.
 */
@EnableSwagger2
@SpringBootApplication
@EnableJpaAuditing
public class VehiclesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

    /**
     * Initializes the car manufacturers available to the Vehicle API.
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */
    @Bean
    CommandLineRunner initDatabase(ManufacturerRepository repository) {
        return args -> {
            repository.save(new Manufacturer(100, "Audi"));
            repository.save(new Manufacturer(101, "Chevrolet"));
            repository.save(new Manufacturer(102, "Ford"));
            repository.save(new Manufacturer(103, "BMW"));
            repository.save(new Manufacturer(104, "Dodge"));
        };
    }
    
    @Bean
    CommandLineRunner createCar(CarRepository repository, ManufacturerRepository manuRepository) {
        return args -> {
            repository.save(new Car(Condition.NEW, new Details("body", "model", manuRepository.getOne(100))));
            repository.save(new Car(Condition.USED, new Details("test", "old model", manuRepository.getOne(101))));
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Web Client for the maps (location) API
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name="maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    /**
     * Web Client for the pricing API
     * @param endpoint where to communicate for the pricing API
     * @return created pricing endpoint
     */
    @Bean(name="pricing")
    public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

}
