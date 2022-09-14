package com.api.taygeta.services;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.repositories.PlanetRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public ResponseEntity<List<PlanetEntity>> getPlanets() {

    List<PlanetEntity> planets = planetRepository.findAll();

    if (planets.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(planets);
    }

  }

}
