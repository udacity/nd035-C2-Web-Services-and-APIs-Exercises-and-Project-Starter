package com.udacity.pricing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PricingServiceApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testListPrices() throws Exception {
        mvc.perform(get(new URI("/prices/")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$..prices.length()").value(6));
    }

    @Test
    public void testGetPrice() throws Exception {
        mvc.perform(get(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(22547.98))
                .andExpect(jsonPath("$.currency").value("USD"));
    }
}
