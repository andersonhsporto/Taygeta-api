package com.api.taygeta.controller;

import com.api.taygeta.dto.PlanetDTO;
import com.api.taygeta.services.PlanetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/planets")
public class PlanetController {

  private final PlanetService planetService;

  public PlanetController(PlanetService planetService) {
    this.planetService = planetService;
  }

  @ApiOperation(value = "Return a list of all planets")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of all planets",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PlanetDTO.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "No planets found",
            content = @Content)
      })
  @GetMapping
  public ResponseEntity<?> getAllPlanets() {
    return planetService.getAllPlanets();
  }

  @ApiOperation(value = "Return a planet by id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Planet found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PlanetDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Planet not found",
            content = @Content)
      })
  @GetMapping("/{planetId}")
  public ResponseEntity<?> getPlanetById(
      @ApiParam(value = "Planet Id number.", required = true, allowableValues = "range[1, infinity]")
      @PathVariable Long planetId) {

    return planetService.getPlanetById(planetId);
  }


  @ApiOperation(value = "Return all probes by planet id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Probes found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PlanetDTO.class))
            }),
        @ApiResponse(responseCode = "409", description = "No probes found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Planet not found", content = @Content)
      })
  @GetMapping("/{planetId}/probes")
  public ResponseEntity<?> getPlanetProbesById(
      @ApiParam(value = "Planet Id number.", required = true, allowableValues = "range[1, infinity]")
      @PathVariable Long planetId) {

    return planetService.getPlanetProbeById(planetId);
  }


  @ApiOperation(value = "Add a new planet using area string")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Planet created", content = @Content(mediaType = "string")),
        @ApiResponse(responseCode = "400", description = "Invalid planet size", content = @Content)
      })
  @PostMapping
  public ResponseEntity<?> makePlanet(
      @ApiParam(value = "String area of planet (example: 10x10).", required = true)
      @RequestParam String area) {

    return planetService.makePlanet(area);
  }

  @ApiOperation(value = "Update a planet by planet id and area string")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Planet updated", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid planet size", content = @Content),
        @ApiResponse(responseCode = "409", description = "Planet not found", content = @Content)
      })
  @PutMapping
  public ResponseEntity<?> updatePlanet(
      @ApiParam(value = "Planet Id number.", required = true, allowableValues = "range[1, infinity]")
      @RequestParam Long id,
      @ApiParam(value = "String area of planet (example: 10x10).", required = true)
      @RequestParam String area) {

    return planetService.updatePlanet(id, area);
  }

  @ApiOperation(value = "Delete a planet by planet id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Planet deleted", content = @Content),
        @ApiResponse(responseCode = "409", description = "Planet not found", content = @Content)
      })
  @DeleteMapping("/{planetId}")
  public ResponseEntity<?> deletePlanet(
      @ApiParam(value = "Planet Id number.", required = true, allowableValues = "range[1, infinity]")
      @PathVariable Long planetId) {

    return planetService.deletePlanet(planetId);
  }

  @ApiOperation(value = "Delete probes of a planet by planet id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Probes deleted", content = @Content),
        @ApiResponse(responseCode = "409", description = "Planet not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Probes not found", content = @Content)
      })
  @DeleteMapping("/{planetId}/probes")
  public ResponseEntity<?> deletePlanetProbes(
      @ApiParam(value = "Planet Id number.", required = true, allowableValues = "range[1, infinity]")
      @PathVariable Long planetId) {

    return planetService.deletePlanetProbes(planetId);
  }
}

