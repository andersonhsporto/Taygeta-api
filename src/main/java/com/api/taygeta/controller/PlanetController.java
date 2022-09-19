package com.api.taygeta.controller;

import com.api.taygeta.services.PlanetService;
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

  @GetMapping
  public ResponseEntity<?> getAllPlanets() {
    return planetService.getAllPlanets();
  }

  @GetMapping("/{planetId}")
  public ResponseEntity<?> getPlanetById(@PathVariable Long planetId) {
    return planetService.getPlanetById(planetId);
  }

  @GetMapping("/{planetId}/probes")
  public ResponseEntity<?> getPlanetProbesById(@PathVariable Long planetId) {
    return planetService.getPlanetProbeById(planetId);
  }

  @PostMapping
  public ResponseEntity<?> makePlanet(@RequestParam String area) {
    return planetService.makePlanet(area);
  }

  @PutMapping
  public ResponseEntity<?> updatePlanet(@RequestParam Long id, @RequestParam String area) {
    return planetService.updatePlanet(id, area);
  }

  @DeleteMapping("/{planetId}")
  public ResponseEntity<?> deletePlanet(@PathVariable Long planetId) {
    return planetService.deletePlanet(planetId);
  }

  @DeleteMapping("/{planetId}/probes")
  public ResponseEntity<?> deletePlanetProbes(@PathVariable Long planetId) {
    return planetService.deletePlanetProbes(planetId);
  }
}

