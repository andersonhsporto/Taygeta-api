package com.api.taygeta.controller;

import com.api.taygeta.dto.ProbeDTO;
import com.api.taygeta.services.ProbeService;
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
@RequestMapping("/api/v1/probes")
public class ProbeController {

  private final ProbeService probeService;

  public ProbeController(ProbeService probeService) {
    this.probeService = probeService;
  }

  @ApiOperation(value = "Return a list of all probes")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "List of all probes",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ProbeDTO.class))
              }),
          @ApiResponse(responseCode = "409", description = "Probes not found", content = @Content)
      })
  @GetMapping
  public ResponseEntity<Object> getAllProbes() {
    return probeService.getAllProbes();
  }

  @ApiOperation(value = "Return a probe by id")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Probe found",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ProbeDTO.class))
              }),
          @ApiResponse(responseCode = "409", description = "Probe not found", content = @Content)
      })
  @GetMapping("/{probeId}")
  public ResponseEntity<Object> getByProbeId(
      @ApiParam(value = "Probe Id number.", required = true, allowableValues = "range[1, infinity]")
      @PathVariable Long probeId) {

    return probeService.getProbeById(probeId);
  }


  @ApiOperation(value = "Add a new probe")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Probe created", content = @Content),
          @ApiResponse(responseCode = "409", description = "Not found error", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid parameter error", content = @Content)
      })
  @PostMapping
  public ResponseEntity<Object> makeProbe(
      @ApiParam(value = "Planet Id number.", required = true, allowableValues = "range[1, infinity]")
      @RequestParam Long planetId,
      @ApiParam(value = "Probe X coordinate.", required = true, allowableValues = "range[0, infinity]")
      @RequestParam Integer x,
      @ApiParam(value = "Probe Y coordinate.", required = true, allowableValues = "range[0, infinity]")
      @RequestParam Integer y,
      @ApiParam(value = "Probe cardinal (NORTH, SOUTH, EAST, WEST).", required = true)
      @RequestParam String direction) {

    return probeService.makeProbe(planetId, x, y, direction);
  }

  @ApiOperation(value = "Move a probe")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Probe moved successfully", content = @Content),
          @ApiResponse(responseCode = "409", description = "Probe not found / Collision Error", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid movements sequence", content = @Content)
      })
  @PutMapping
  public ResponseEntity<?> moveProbe(
      @ApiParam(value = "Probe Id number.", required = true, allowableValues = "range[1, infinity]")
      @RequestParam Long probeId,
      @ApiParam(value = "Movement command string using L, R, M characters.", required = true)
      @RequestParam String movements) {

    return probeService.moveProbe(probeId, movements);
  }

  @ApiOperation(value = "Delete a probe by id")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "Probe deleted", content = @Content),
          @ApiResponse(responseCode = "409", description = "Probe not found", content = @Content)
      })
  @DeleteMapping("/{probeId}")
  public ResponseEntity<Object> deleteProbe(
      @ApiParam(value = "Probe Id number.", required = true, allowableValues = "range[1, infinity]")
      @PathVariable Long probeId) {

    return probeService.deleteProbe(probeId);
  }


}
