package com.udacity.pricing;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {
	
	private int validVehicleId = 5;
	private int notValidVehicleId = 50;

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void checkValidVehicleId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/services/price?vehicleId=" + validVehicleId)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkNotValidVehicleId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/services/price?vehicleId=" + notValidVehicleId)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkCurrency() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/services/price?vehicleId=" + validVehicleId)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect((jsonPath("$.currency", is("USD"))))
				.andExpect((jsonPath("$.vehicleId", is(validVehicleId))));
	}
	

}
