package com.api.taygeta.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.taygeta.enums.Cardinal;
import com.api.taygeta.services.PlanetService;
import com.api.taygeta.services.ProbeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProbeControllerTest {

  @Autowired
  private PlanetService planetService;

  @Autowired
  private ProbeService probeService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Get all probes return http status 204")
  public void shouldReturnNoProbesFound() throws Exception {
    mockMvc.perform(get("/api/v1/probes"))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Get all probes return http status 200")
  public void shouldReturnOkIfProbesFound() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 1, 1, "NORTH");

    mockMvc.perform(get("/api/v1/probes"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$[0].id").exists(),
            MockMvcResultMatchers.jsonPath("$[0].position.x").value(1),
            MockMvcResultMatchers.jsonPath("$[0].position.y").value(1),
            MockMvcResultMatchers.jsonPath("$[0].cardinal").value(Cardinal.NORTH.toString()),
            MockMvcResultMatchers.jsonPath("$[0].PlanetId").value(1)
        );
  }

  @Test
  @DisplayName("Get probe by id return http status 204")
  public void shouldReturnNoProbeFound() throws Exception {
    mockMvc.perform(get("/api/v1/probes/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Get probe by id return Probe not found")
  public void shouldReturnProbeNotFound() throws Exception {
    mockMvc.perform(get("/api/v1/probes/1"))
        .andExpect(content().string("Probe not found"));
  }

  @Test
  @DisplayName("Get probe by id return http status 200 when probe exists")
  public void shouldReturnOkIfProbeFound() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 1, 1, "NORTH");

    mockMvc.perform(get("/api/v1/probes/2")).andDo(print())
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$.id").exists(),
            MockMvcResultMatchers.jsonPath("$.position.x").value(1),
            MockMvcResultMatchers.jsonPath("$.position.y").value(1),
            MockMvcResultMatchers.jsonPath("$.cardinal").value(Cardinal.NORTH.toString()),
            MockMvcResultMatchers.jsonPath("$.PlanetId").value(1)
        );
  }

  @Test
  @DisplayName("Post probe return http status 204 when there is no planet")
  public void shouldReturnNotFoundWhenPostProbe() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=40&x=0&y=2&direction=NORTH"))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Post probe return Planet not found when there is no planet")
  public void shouldReturnPlanetNotFoundWhenPostProbe() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=40&x=0&y=2&direction=NORTH"))
        .andExpect(content().string("Planet not found"));
  }

  @Test
  @DisplayName("Post probe return http status 404 when direction is invalid")
  public void shouldReturnBadRequestWhenPostProbe() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "LESTE");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=0&y=2&direction=NORTH"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Post probe return Probe already exists in coordinates")
  public void shouldReturnProbeAlreadyExistsWhenPostProbe() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "LESTE");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=0&y=2&direction=NORTH"))
        .andExpect(content().string("Probe already exists in coordinates"));
  }

  @Test
  @DisplayName("Probe return http status 404 when invalid direction")
  public void shouldReturnBadRequestWhenPostProbeWithInvalidDirection() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=0&y=2&direction=INVALID"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Probe return Invalid cardinal when invalid direction")
  public void shouldReturnInvalidCardinalWhenPostProbeWithInvalidDirection() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=0&y=2&direction=INVALID"))
        .andExpect(content().string("Invalid cardinal"));
  }

  @Test
  @DisplayName("Probe return http status 404 and Invalid parameters y=44444")
  public void shouldReturnBadRequestWhenPostProbeWithInvalidParameters() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=0&y=44444&direction=Oeste"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Invalid parameters"));
  }

  @Test
  @DisplayName("Probe return http status 404 and Invalid parameters x=44444")
  public void shouldReturnBadRequestWhenPostProbeWithInvalidParameters2() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=44444&y=0&direction=Oeste"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Invalid parameters"));
  }

  @Test
  @DisplayName("Probe return http status 200 and Probe created")
  public void shouldReturnOkWhenPostProbe() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/probes?planetId=1&x=0&y=2&direction=Oeste"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe created"));
  }

  @Test
  @DisplayName("Put probe return http status 204 when there is no probe")
  public void shouldReturnNotFoundWhenNotFoundProbe() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=43&movements=L"))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Put probe return probe not found when there is no probe")
  public void shouldReturnProbeNotFoundWhenPutProbe() throws Exception {
    planetService.makePlanet("42x42");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=43&movements=L"))
        .andExpect(content().string("Probe not found"));
  }

  @Test
  @DisplayName("Put probe return http status 404 when invalid movement")
  public void shouldReturnBadRequestWhenPutProbeWithInvalidMovement() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "LESTE");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=INVALID"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Put probe return \"Invalid movement sequence\" when invalid movement string")
  public void shouldReturnInvalidMovementWhenPutProbeWithInvalidMovement() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "SUL");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=INVALID"))
        .andExpect(content().string("Invalid movements sequence"));
  }

  @Test
  @DisplayName("Put probe return http status 200 and Probe moved successfully")
  public void shouldReturnOkWhenPutProbe() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "LESTE");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=LL"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe moved successfully"));
  }

  @Test
  @DisplayName("Put probe rotate probe to left")
  public void shouldReturnOkWhenPutProbeRotateLeft() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "LESTE");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=L"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe moved successfully"));

    mockMvc.perform(get("/api/v1/probes/2"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.cardinal").value(Cardinal.NORTH.toString())
        );
  }

  @Test
  @DisplayName("Put probe rotate probe to right")
  public void shouldReturnOkWhenPutProbeRotateRight() throws Exception {
    planetService.makePlanet("42x42");
    probeService.makeProbe(1L, 0, 2, "LESTE");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=R"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe moved successfully"));

    mockMvc.perform(get("/api/v1/probes/2"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.cardinal").value(Cardinal.SOUTH.toString())
        );
  }

  @Test
  @DisplayName("Put probe move probe to north")
  public void shouldReturnOkWhenPutProbeMoveNorth() throws Exception {
    planetService.makePlanet("5x5");
    probeService.makeProbe(1L, 0, 2, "Oeste");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=RM"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe moved successfully"));

    mockMvc.perform(get("/api/v1/probes/2"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.position.x").value(0),
            MockMvcResultMatchers.jsonPath("$.position.y").value(3),
            MockMvcResultMatchers.jsonPath("$.cardinal").value(Cardinal.NORTH.toString())
        );
  }

  @Test
  @DisplayName("Put probe default edge case")
  public void shouldMoveProbeTo1x3NorthWhenLMLMLMLMMh() throws Exception {
    planetService.makePlanet("5x5");
    probeService.makeProbe(1L, 1, 2, "Norte");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=LMLMLMLMM"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe moved successfully"));

    mockMvc.perform(get("/api/v1/probes/2"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.position.x").value(1),
            MockMvcResultMatchers.jsonPath("$.position.y").value(3),
            MockMvcResultMatchers.jsonPath("$.cardinal").value(Cardinal.NORTH.toString())
        );
  }

  @Test
  @DisplayName("Put probe default edge case 2")
  public void shouldMoveProbeTo5x1EastWhenMMRMMRMRRM() throws Exception {
    planetService.makePlanet("5x5");
    probeService.makeProbe(1L, 3, 3, "Leste");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=MMRMMRMRRM"))
        .andExpect(status().isOk())
        .andExpect(content().string("Probe moved successfully"));

    mockMvc.perform(get("/api/v1/probes/2"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.position.x").value(5),
            MockMvcResultMatchers.jsonPath("$.position.y").value(1),
            MockMvcResultMatchers.jsonPath("$.cardinal").value(Cardinal.EAST.toString())
        );
  }

  @Test
  @DisplayName("Put probe collision")
  public void shouldReturnCollisionWhenPutProbe() throws Exception {
    planetService.makePlanet("5x5");
    probeService.makeProbe(1L, 1, 2, "Norte");
    probeService.makeProbe(1L, 1, 3, "North");

    mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/probes?probeId=2&movements=M"))
        .andExpect(status().isConflict())
        .andExpect(content().string("Collision detected"));
  }


}
