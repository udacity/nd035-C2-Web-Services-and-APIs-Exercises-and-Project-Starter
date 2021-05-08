package com.udacity.pricing.web;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetPriceByVehicleID_200_OK() throws Exception{
        ResponseEntity<Price> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" +port+ "/services/price?vehicleId=1", Price.class);
        assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPriceByVehicleID_404_NOTFOUND() throws Exception{
        ResponseEntity<Price> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" +port+ "/service/price?vehicleId=1", Price.class);
        assertEquals( HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
