package com.udacity.pricing;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {
	@Autowired
	PriceRepository priceRepository;

	@Autowired
	MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testFindByVehicleId(){

		Optional<Price> optionalPrice = priceRepository.findById(1L);
		assertTrue(optionalPrice.isPresent());
	}

}