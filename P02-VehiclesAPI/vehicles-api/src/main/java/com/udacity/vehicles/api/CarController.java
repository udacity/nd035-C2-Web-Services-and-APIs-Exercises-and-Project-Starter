package com.udacity.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
@Tag(name = "Vehicle Controller", description = "Create and retrieve vehicles")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * 
     * @return list of vehicles
     */
    @Operation(summary = "Retrieve all vehicles", description = "Provides a list of all vehicles")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of contacts", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Car.class))))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * 
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @Operation(summary = "Get Car by Id", description = "Returns a car based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Car doesn't exist", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of car", content = @Content(schema = @Schema(implementation = Car.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Resource<Car> get(@PathVariable Long id) {
        Car car = carService.findById(id);
        return assembler.toResource(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     * 
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */

    @Operation(summary = "Create Car", description = "Creates a car from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of car", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        Car savedCar = carService.save(car);
        Resource<Car> resource = assembler.toResource(savedCar);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     * 
     * @param id  The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */

    @Operation(summary = "Create Car", description = "Creates a car from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of car", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        car.setId(id);
        Car updatedCar = carService.save(car);
        Resource<Car> resource = assembler.toResource(updatedCar);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * 
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @Operation(summary = "Delete contact by Id", description = "Deletes a car based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Car doesn't exist", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "200", description = "Successful deletion of car"),
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        /**
         * Use the Car Service to delete the requested vehicle.
         */
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
