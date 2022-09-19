package com.api.taygeta.services;

import com.api.taygeta.dto.PlanetDTO;
import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.repositories.PlanetRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
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

  public ResponseEntity<?> getAllPlanets() {
    var planets = planetRepository.findAll();

    if (planets.isEmpty()) {
      return new ResponseEntity<>("No planets found", HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(convertListEntityToDTO(planets), HttpStatus.OK);
    }
  }

  private List<PlanetDTO> convertListEntityToDTO(List<PlanetEntity> planets) {
    return planets.stream().map(PlanetDTO::fromEntity).toList();
  }

  public ResponseEntity<Object> getPlanetById(Long id) {
    var planet = planetRepository.findById(id);

    if (planet.isEmpty()) {
      return new ResponseEntity<>("Planet not found", HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(PlanetDTO.fromEntity(planet.get()), HttpStatus.OK);
    }
  }

  public ResponseEntity<Object> getPlanetProbeById(Long id) {
    var planet = planetRepository.findById(id);

    if (planet.isEmpty()) {
      return new ResponseEntity<>("Planet not found", HttpStatus.NO_CONTENT);
    } else if (planet.get().getProbes().isEmpty()) {
      return new ResponseEntity<>("No probes found", HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(ProbeService.convertListEntityToDto(planet.get().getProbes()),
          HttpStatus.OK);
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

  public ResponseEntity<String> updatePlanet(Long id, String string) {
    var planet = planetRepository.findById(id);

    if (planet.isPresent()) {
      return updatePlanetEntity(planet.get(), string);
    } else {
      return new ResponseEntity<>("Planet not found", HttpStatus.NO_CONTENT);
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

  public ResponseEntity<Object> deletePlanet(Long id) {
    var planet = planetRepository.findById(id);

    if (planet.isPresent()) {
      planetRepository.delete(planet.get());
      return ResponseEntity.ok("Planet deleted");
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Planet not found");
    }
  }

  @Transactional
  public ResponseEntity<Object> deletePlanetProbes(Long id) {
    var planet = planetRepository.findById(id);

    if (planet.isPresent()) {
      return deleteProbesList(planet);
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Planet not found");
    }
  }

  public ResponseEntity<Object> deleteProbesList(Optional<PlanetEntity> planet) {
    if (planet.get().getProbesCount() > 0) {
      planet.get().getProbes().removeAll(planet.get().getProbes());
      planetRepository.save(planet.get());
      return ResponseEntity.ok("Probes deleted");
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No probes found");
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
