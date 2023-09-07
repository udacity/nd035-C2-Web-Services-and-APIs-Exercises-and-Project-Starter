package com.udacity.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getVehiclePrice() throws Exception {
        mockMvc.perform(get("/prices"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/prices/5"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/prices/30"))
                .andExpect(status().is(404));
    }

}
