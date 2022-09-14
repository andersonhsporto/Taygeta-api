package com.api.taygeta.controller;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.services.PlanetService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<List<PlanetEntity>> getPlanets() {
    return planetService.getPlanets();
  }



}
