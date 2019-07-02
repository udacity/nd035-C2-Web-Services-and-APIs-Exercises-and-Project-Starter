package com.udacity.test.web;

import com.udacity.test.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LocationService locationService;

    @Test
    public void getAllLocations() throws Exception {
        mockMvc.perform(get("/location/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));

        verify(locationService, times(1)).retrieveLocations();
    }

    @Test
    public void getLocation() throws Exception {
        mockMvc.perform(get("/location/1"))
                .andExpect(status().isOk());

        verify(locationService, times(1)).retrieveLocation(1);
    }
}