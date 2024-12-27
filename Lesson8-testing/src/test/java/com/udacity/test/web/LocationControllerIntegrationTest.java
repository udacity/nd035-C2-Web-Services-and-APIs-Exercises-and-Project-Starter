package com.udacity.test.web;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.udacity.test.entity.Location;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LocationControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllLocations() {
        ResponseEntity<List> response =
                  this.restTemplate.getForEntity("http://localhost:" + port + "/location", List.class);

          assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getLocation() {
        ResponseEntity<Location> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/location/1", Location.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
