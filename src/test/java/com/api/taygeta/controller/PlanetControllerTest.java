package com.api.taygeta.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.taygeta.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  public PlanetService planetService;

  @Test
  @DisplayName("Test if get all planets return not found")
  void shouldReturnNoPlanetFound() throws Exception {
    mockMvc.perform(get("/api/v1/planets"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No planets found"));
  }

  @Test
  @DisplayName("Test if get all planet return http 200")
  void shouldReturnPlanetList() throws Exception {
    planetService.makePlanet("5x5");

    mockMvc.perform(get("/api/v1/planets"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if get all planet return json")
  void shouldReturnPlanetListJson() throws Exception {
    planetService.makePlanet("5x5");

    mockMvc.perform(get("/api/v1/planets"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$[0].id").exists(),
            MockMvcResultMatchers.jsonPath("$[0].width").exists(),
            MockMvcResultMatchers.jsonPath("$[0].height").exists(),
            MockMvcResultMatchers.jsonPath("$[0].area").exists(),
            MockMvcResultMatchers.jsonPath("$[0].numberOfProbes").exists()
        );
  }
}