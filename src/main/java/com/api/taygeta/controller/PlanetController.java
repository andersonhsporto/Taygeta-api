package com.api.taygeta.controller;

import com.api.taygeta.services.PlanetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @PostMapping("/{string}")
  public ResponseEntity<String> createPlanet(@PathVariable String string) {
    return planetService.makePlanet(string);
  }

  @PutMapping("/{id}/{string}")
  public ResponseEntity<String> updatePlanet(@PathVariable Long id, @PathVariable String string) {
    return planetService.updatePlanet(id, string);
  }

}
