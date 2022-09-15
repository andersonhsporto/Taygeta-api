package com.api.taygeta.services;

import com.api.taygeta.dto.ProbeDTO;
import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.enums.Cardinal;
import com.api.taygeta.exceptions.InvalidCardinalException;
import com.api.taygeta.repositories.PlanetRepository;
import com.api.taygeta.repositories.ProbeRepository;
import java.awt.Point;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProbeService {

  private final ProbeRepository probeRepository;
  private final PlanetRepository planetRepository;

  @Autowired
  public ProbeService(ProbeRepository probeRepository, PlanetRepository planetRepository) {
    this.probeRepository = probeRepository;
    this.planetRepository = planetRepository;
  }

  public ResponseEntity<Object> getProbes() {
    var probes = probeRepository.findAll();

    if (probes.isEmpty()) {
      return new ResponseEntity<>("No probes found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(probes, HttpStatus.OK);
    }
  }

  public ResponseEntity<Object> getProbeById(Long id) {
    var probe = probeRepository.findById(id);

    if (probe.isPresent()) {
      return ResponseEntity.ok(ProbeDTO.fromEntity(probe.get()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found");
    }
  }

  public ResponseEntity<Object> makeProbe(Long planetId, Integer x, Integer y, String direction) {
    var planet = planetRepository.findById(planetId);
    var point = new Point(x, y);

    if (!isValidParameters(planet, point, direction)) {
      return new ResponseEntity<>("Invalid parameters", HttpStatus.BAD_REQUEST);
    } else {
      var cardinal = parseCardinal(direction);
      var probeEntity = new ProbeEntity(point, cardinal, planet.get());
      planet.get().addProbe(probeEntity);
      planetRepository.save(planet.get());
      return ResponseEntity.ok("Probe created");
    }

  }

  public boolean isValidParameters(Optional<PlanetEntity> planet, Point coordinates, String direction) {
    if (planet.isEmpty()) {
      return false;
    } else if (existProbeInCoordinates(planet, coordinates)) {
      return false;
    } else
      return isValidCardinal(direction) && isValidCoordinate(planet, coordinates);
  }

  public boolean existProbeInCoordinates(Optional<PlanetEntity> planet, Point coordinates) {
    var probes = planet.get().getProbes();

    return probes.stream().anyMatch(probe -> probe.getPosition().equals(coordinates));
  }
  public boolean isValidCoordinate(Optional<PlanetEntity> planet, Point coordinates) {
    var planetWidth = planet.get().getWidth();
    var planetHeight = planet.get().getHeight();

    return coordinates.x >= 0 &&
        coordinates.x < planetWidth &&
        coordinates.y >= 0 &&
        coordinates.y < planetHeight;
  }

  public static Cardinal parseCardinal(String command) throws InvalidCardinalException {
    String lowerCaseCommand = command.toLowerCase();

    return switch (lowerCaseCommand) {
      case "north", "norte", "n" -> Cardinal.NORTH;
      case "south", "sul", "s" -> Cardinal.SOUTH;
      case "east", "leste", "e", "l" -> Cardinal.EAST;
      case "west", "oeste", "w", "o" -> Cardinal.WEST;
      default -> throw new InvalidCardinalException("Invalid Cardinal");
    };
  }

  public boolean isValidCardinal(String command) {
    String lowerCaseCommand = command.toLowerCase();

    return switch (lowerCaseCommand) {
      case "north", "south", "east", "west", "norte", "sul", "leste", "oeste" -> true;
      case "n", "s", "e", "l", "w", "o" -> true;
      default -> false;
    };
  }
}
