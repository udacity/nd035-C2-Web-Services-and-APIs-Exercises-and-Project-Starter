package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PricingServiceApiTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Price> json;

    @Test
    public void testAListPrices() throws Exception {
        mvc.perform(get(new URI("/prices/")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$..prices.length()").value(6));
    }

    @Test
    public void testBGetPrice() throws Exception {
        mvc.perform(get(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(22547.98))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    public void testCSavePrice() throws Exception {
        Price price = new Price("EUR", new BigDecimal(555), 888L);
        mvc.perform(post(new URI("/prices/")).content(json.write(price).getJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currency").value(price.getCurrency()))
                .andExpect(jsonPath("$.price").value(price.getPrice()));
    }

    @Test
    public void testDUpdatePrice() throws Exception {
        // make sure a "get" delivers the values as configured in data.sql
        mvc.perform(get(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(22547.98))
                .andExpect(jsonPath("$.currency").value("USD"));

        // change currency and price, check returned values
        Price price = new Price("EUR", new BigDecimal(5551).divide(new BigDecimal(10)), 1L);
        mvc.perform(put(new URI("/prices/1")).content(json.write(price).getJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value(price.getCurrency()))
                .andExpect(jsonPath("$.price").value(price.getPrice()));

        // make sure another "get" delivers the updated values as well
        mvc.perform(get(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value(price.getCurrency()))
                .andExpect(jsonPath("$.price").value(price.getPrice()));
    }

    @Test
    public void testEDeletePrice() throws Exception {
        // make sure a "get" delivers the values as changed in test D
        Price price = new Price("EUR", new BigDecimal(5551).divide(new BigDecimal(10)), 1L);
        mvc.perform(get(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(price.getPrice()))
                .andExpect(jsonPath("$.currency").value(price.getCurrency()));

        // delete the price
        mvc.perform(delete(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());

        // make sure another "get" results in 404 - not found
        mvc.perform(get(new URI("/prices/1")).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNotFound());
    }
}
