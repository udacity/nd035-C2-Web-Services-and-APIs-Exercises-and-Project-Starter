package com.udacity.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 * TODO: Convert the application from a REST API to a microservice.
 */
@SpringBootApplication
@EnableEurekaClient
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    /**
     * Initializes the Prices for the vehicles.
     */
    @Bean
    CommandLineRunner initDB(PriceRepository repository) {
        return args -> {
            List<Price> priceList = LongStream
                    .range(1, 21)
                    .mapToObj(
                            i -> new Price("USD", generateRandomPrice(new BigDecimal(10000), new BigDecimal(30000)), i))
                    .collect(Collectors.toList());

            repository.saveAll(priceList);
        };
    }

    public static BigDecimal generateRandomPrice(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

}
