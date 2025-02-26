package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

@WebMvcTest(CityController.class)  // Replace with your actual controller
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    // Test the GET request for fetching all cities
    @Test
    void testGetAllCities() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())  // Verifies the response is an array
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").isString());  // Check a property in the response
    }

    // Test the POST request for creating a new city
    @Test
    void testCreateCity() throws Exception {
        String cityJson = "{\"name\":\"TestCity\", \"state\":\"TestState\", \"population\":50000}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cityJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Expect a 201 Created response
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestCity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("TestState"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.population").value(50000));
    }

    // Test the PUT request for updating an existing city
    @Test
    void testUpdateCity() throws Exception {
        String updatedCityJson = "{\"name\":\"UpdatedCity\", \"state\":\"UpdatedState\", \"population\":60000}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cities/1")  // Assuming the ID of the city is 1
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCityJson))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Expect a 200 OK response
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UpdatedCity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("UpdatedState"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.population").value(60000));
    }

    // Test the DELETE request for deleting a city
    @Test
    void testDeleteCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cities/1"))  // Assuming the ID of the city is 1
                .andExpect(MockMvcResultMatchers.status().isNoContent());  // Expect a 204 No Content response (successful delete)
    }

    // Test for invalid data in the POST request
    @Test
    void testCreateCityWithInvalidData() throws Exception {
        String invalidCityJson = "{\"name\":\"\", \"state\":\"TestState\", \"population\":-100}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCityJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());  // Expect a 400 Bad Request response due to invalid data
    }

    // Test the GET request for a single city by ID
    @Test
    void testGetCityById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities/1"))  // Assuming city with ID 1 exists
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString());
    }

    // Test for a non-existing city ID (should return 404)
    @Test
    void testGetCityByInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cities/999"))  // Assuming city with ID 999 does not exist
                .andExpect(MockMvcResultMatchers.status().isNotFound());  // Expect a 404 Not Found response
    }
}
