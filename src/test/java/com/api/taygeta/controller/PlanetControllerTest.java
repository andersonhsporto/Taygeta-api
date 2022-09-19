package com.api.taygeta.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.repositories.PlanetRepository;
import com.api.taygeta.services.PlanetService;
import com.api.taygeta.services.ProbeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class PlanetControllerTest {

  @Autowired
  private PlanetService planetService;

  @Autowired
  private ProbeService probeService;

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Test if get all planets return http status 204")
  void shouldReturnNoPlanetFound() throws Exception {
    mockMvc.perform(get("/api/v1/planets"))
        .andExpect(status().isNoContent())
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

  @Test
  @DisplayName("Test if get planet by id return http 204")
  void shouldReturnPlanetNotFound() throws Exception {
    mockMvc.perform(get("/api/v1/planets/1"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Test if get planet by id return http 200")
  void shouldReturnPlanet() throws Exception {
    var planet = PlanetEntity.fromString("5x5");
    planetRepository.save(planet);

    mockMvc.perform(get("/api/v1/planets/" + planet.getId()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if get planet by id return json")
  void shouldReturnPlanetJson() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);

    mockMvc.perform(get("/api/v1/planets/" + planet.getId()))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$.id").exists(),
            MockMvcResultMatchers.jsonPath("$.width").exists(),
            MockMvcResultMatchers.jsonPath("$.height").exists(),
            MockMvcResultMatchers.jsonPath("$.area").exists(),
            MockMvcResultMatchers.jsonPath("$.numberOfProbes").exists()
        );
  }

  @Test
  @DisplayName("Test if get planet probes return http 204")
  void shouldReturnPlanetNotFoundIfPlanetIsInvalid() throws Exception {
    mockMvc.perform(get("/api/v1/planets/1/probes"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Test if get planet probes return http 204")
  void shouldReturnPlanetProbesNotFoundIfProbeAreMissing() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);

    mockMvc.perform(get("/api/v1/planets/"+ planet.getId() + "/probes"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("No probes found"));
  }

  @Test
  @DisplayName("Test if get planet probes return http 200")
  void shouldReturnPlanetProbes() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);
    probeService.makeProbe(planet.getId(), 1, 1, "Norte");

    mockMvc.perform(get("/api/v1/planets/" + planet.getId() + "/probes"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if get planet probes return json")
  void shouldReturnPlanetProbesJson() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);
    probeService.makeProbe(planet.getId(), 1, 1, "Norte");

    mockMvc.perform(get("/api/v1/planets/" + planet.getId() + "/probes")).andDo(print())
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$[0].id").exists(),
            MockMvcResultMatchers.jsonPath("$[0].position.x").exists(),
            MockMvcResultMatchers.jsonPath("$[0].position.y").exists(),
            MockMvcResultMatchers.jsonPath("$[0].cardinal").exists(),
            MockMvcResultMatchers.jsonPath("$[0].PlanetId").exists(),
            MockMvcResultMatchers.jsonPath("$.*", hasSize(1))
        );
  }

  @Test
  @DisplayName("Test if get planet probes return json with 2 probes")
  void shouldReturnPlanetProbesIfProbesAreValid() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);
    probeService.makeProbe(planet.getId(), 1, 1, "Norte");
    probeService.makeProbe(planet.getId(), 1, 2, "Leste");

    mockMvc.perform(get("/api/v1/planets/" + planet.getId() + "/probes"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$[0].id").exists(),
            MockMvcResultMatchers.jsonPath("$[0].position.x").exists(),
            MockMvcResultMatchers.jsonPath("$[0].position.y").exists(),
            MockMvcResultMatchers.jsonPath("$[0].cardinal").exists(),
            MockMvcResultMatchers.jsonPath("$[0].PlanetId").exists(),
            MockMvcResultMatchers.jsonPath("$.*", hasSize(2))
        );
  }

  @Test
  @DisplayName("Test if post planet return http 201")
  void shouldReturnPlanetCreated() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/planets?area=5x5"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if post planet return http 404")
  void shouldReturnPlanetNotCreated() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/planets?area=5x"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test if put planet return http 204")
  void shouldReturnPlanetNotFoundIfPlanetIsInvalidPut() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/planets?id=4&area=4x5"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Test if put planet return http 200")
  void shouldReturnPlanetUpdated() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/planets?id=" + planet.getId() + "&area=4x5"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if put planet return http 404")
  void shouldReturnPlanetNotUpdatedIfAreaStringIsInvalid() throws Exception {
    var planet = PlanetEntity.fromString("5x50");

    planetRepository.save(planet);

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/planets?id=" + planet.getId() + "&area=4xINVALID"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test if delete planet return http 204")
  void shouldReturnPlanetNotFoundIfPlanetIsInvalidDelete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/planets/4"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Test if delete planet return http 200")
  void shouldReturnPlanetDeleted() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);

    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/planets/" + planet.getId()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if planet is deleted in delete planet")
  void shouldReturnPlanetDeletedInGetPlanet() throws Exception {
    var planet = PlanetEntity.fromString("7x7");

    planetRepository.save(planet);
    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/planets/"+ planet.getId()))
        .andExpect(status().isOk());

    mockMvc.perform(get("/api/v1/planets/"+ planet.getId()))
        .andExpect(status().isNoContent())
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Test if delete planet probes return http 204")
  void shouldReturnPlanetNotFoundIfPlanetIsInvalidDeleteProbes() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/planets/42/probes"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Test if delete planet probes return http 200")
  void shouldReturnPlanetProbesDeleted() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);
    probeService.makeProbe(planet.getId(), 1, 1, "Norte");

    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/planets/"+ planet.getId() + "/probes"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if delete planet probes only delete planet probes")
  void shouldReturnPlanetProbesDeletedInGetPlanetProbes() throws Exception {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);
    probeService.makeProbe(planet.getId(), 1, 1, "Norte");

    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/planets/"+ planet.getId() + "/probes"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/api/v1/planets/"+ planet.getId() + "/probes"))
        .andExpect(status().isNoContent())
        .andExpect(content().string("No probes found"));
  }
}