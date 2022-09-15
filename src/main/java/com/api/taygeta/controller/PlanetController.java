package com.api.taygeta.controller;

import com.api.taygeta.services.PlanetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

  @GetMapping()
  public ResponseEntity<Object> getPlanets() {
    return planetService.getPlanets();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getPlanetById(@PathVariable Long id) {
    return planetService.getPlanetById(id);
  }

  @PostMapping
  public ResponseEntity<String> createPlanet(@RequestParam String area) {
    return planetService.makePlanet(area);
  }

  @PutMapping
  public ResponseEntity<String> updatePlanet(@RequestParam Long id, @RequestParam String area) {
    return planetService.updatePlanet(id, area);
  }

}
