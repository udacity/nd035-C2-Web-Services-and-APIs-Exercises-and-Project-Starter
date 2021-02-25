package com.udacity.vehicles.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;

	private Manufacturer defaultManufacturer;
	
	@Before
	public void setup() {
		defaultManufacturer = manufacturerRepository.save(new Manufacturer(1, "manufacturer"));
	}
	
	@Test
	public void contextLoads() {
		assertThat(carService, is(notNullValue()));
	}

	@Test
	public void testList() {
		given_threeCarsInDatabase();
		List<Car> list = when_carServiceListIsInvoked();
		then_aNonEmptyListWithThreeEntriesIsReturned(list);
	}

	@Test
	public void testFindById() {
		Car car = given_aCar();
		Car carFromService = when_carServiceFindByIdIsInvokedWithCarsId(car);
		then_aCarObjectWithTheSameIdIsReturned(car, carFromService);
	}

	@Test
	public void testFindByIdException() {
		Car car = given_aCarWithAnIdUnknownInDatabase();
		thrown.expect(CarNotFoundException.class);
		when_carServiceFindByIdIsInvokedWithCarsId(car);
		// informative: then_thrownExpectSucceeds
	}

	@Test
	public void testSave() {
		Car car = createCar(Condition.USED, 44d, 32d, "147.85");
		Car carSaved = carService.save(car);
		assertThat(carSaved, is(notNullValue()));
		assertThat(carSaved.getId(), is(notNullValue()));
	}

	@Test
	public void testDelete() {
		Car car = given_aCar();
		long countBefore = given_currentRowCount();
		when_carServiceDeleteIsInvoked(car);
		then_theRowCountIsDecreasedByOne(countBefore);
	}

	private void then_theRowCountIsDecreasedByOne(long countBefore) {
		assertThat(carRepository.count(), is(equalTo(countBefore - 1)));
	}

	private void when_carServiceDeleteIsInvoked(Car car) {
		carService.delete(car.getId());
	}

	private long given_currentRowCount() {
		long countBefore = carRepository.count();
		assertThat(countBefore, is(greaterThan(0L)));
		return countBefore;
	}

	private Car given_aCarWithAnIdUnknownInDatabase() {
		Car car = given_aCar();
		long newId = 666;
		car.setId(newId);
		return car;
	}

	private void then_aNonEmptyListWithThreeEntriesIsReturned(List<Car> list) {
		assertThat(list, is(notNullValue()));
		assertThat(list, not(empty()));
		assertThat(list, hasSize(3));
	}

	private void then_aCarObjectWithTheSameIdIsReturned(Car car, Car carFromService) {
		assertThat(carFromService, is(notNullValue()));
		assertThat(carFromService.getId(), is(equalTo(car.getId())));
	}

	private List<Car> when_carServiceListIsInvoked() {
		List<Car> list = carService.list();
		return list;
	}

	private Car when_carServiceFindByIdIsInvokedWithCarsId(Car car) {
		Car carFromService = carService.findById(car.getId());
		return carFromService;
	}

	private void given_threeCarsInDatabase() {
		createAndSaveCar(Condition.NEW, 53.0, 8.3, "123.45");
		createAndSaveCar(Condition.NEW, 53.1, 8.4, "123.45");
		createAndSaveCar(Condition.NEW, 53.2, 8.5, "123.45");
	}
	
	private Car given_aCar() {
		Car car = createAndSaveCar(Condition.NEW, 88d, 10d, "234.56");
		return car;
	}

	private Car createAndSaveCar(Condition condition, double lat, double lon, String price) {
		return carRepository.save(createCar(condition, lat, lon, price));
	}

	private Car createCar(Condition condition, double lat, double lon, String price) {
		Car car = new Car();
		car.setCondition(condition);
		Details details = new Details();
		details.setBody("body");
		details.setEngine("engine");
		details.setExternalColor("externalColor");
		details.setFuelType("fuelType");
		details.setManufacturer(defaultManufacturer);
		details.setMileage(1000);
		details.setModel("model");
		details.setModelYear(2020);
		details.setNumberOfDoors(4);
		details.setProductionYear(2020);
		car.setDetails(details);
		car.setLocation(new Location(lat, lon));
		car.setPrice(price);
		return car;
	}

}
