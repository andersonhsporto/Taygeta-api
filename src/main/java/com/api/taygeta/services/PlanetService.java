package com.api.taygeta.services;

import com.api.taygeta.dto.PlanetDTO;
import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;

  @Autowired
  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public ResponseEntity<Object> getPlanets() {
    var planets = planetRepository.findAll();

    if (planets.isEmpty()) {
      return new ResponseEntity<>("No planets found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(planets, HttpStatus.OK);
    }
  }

  public ResponseEntity<String> makePlanet(String string) {
    if (commandInPlanetSizeFormat(string)) {
      var planetEntity = PlanetEntity.fromString(string);
      planetRepository.save(planetEntity);
      return ResponseEntity.ok("Planet created");
    } else {
      return new ResponseEntity<>("Invalid planet size", HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<Object> getPlanetById(Long id) {
    var planet = planetRepository.findById(id);

    if (planet.isPresent()) {
      return ResponseEntity.ok(PlanetDTO.fromEntity(planet.get()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found");
    }
  }

  public ResponseEntity<String> updatePlanet(Long id, String string) {
    var planet = planetRepository.findById(id);

    if (planet.isPresent()) {
      return updatePlanetEntity(planet.get(), string);
    } else {
      return new ResponseEntity<>("Planet not found", HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<String> updatePlanetEntity(PlanetEntity planetEntity, String string) {
    if (commandInPlanetSizeFormat(string)) {
      planetEntity.updateSizes(string);
      planetRepository.save(planetEntity);
      return ResponseEntity.ok("Planet updated");
    } else {
      return new ResponseEntity<>("Invalid planet size", HttpStatus.BAD_REQUEST);
    }
  }

  public boolean commandInPlanetSizeFormat(String command) {
    var commandArray = command.split("x");

    if (commandArray.length != 2) {
      return false;
    }
    try {
      int width = Integer.parseInt(commandArray[0]);
      int height = Integer.parseInt(commandArray[1]);

      if (width < 1 || height < 1) {
        return false;
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }


}
