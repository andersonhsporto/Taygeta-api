package com.api.taygeta.services;

import com.api.taygeta.dto.ProbeDTO;
import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.enums.Cardinal;
import com.api.taygeta.repositories.PlanetRepository;
import com.api.taygeta.repositories.ProbeRepository;
import java.awt.Point;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProbeService {

  private final ProbeRepository probeRepository;

  private final PlanetRepository planetRepository;

  private final MovementService movementService;

  @Autowired
  public ProbeService(
      ProbeRepository probeRepository,
      PlanetRepository planetRepository,
      MovementService movementService) {
    this.probeRepository = probeRepository;
    this.planetRepository = planetRepository;
    this.movementService = movementService;
  }

  public static List<ProbeDTO> convertListEntityToDto(List<ProbeEntity> probes) {
    return probes.stream().map(ProbeDTO::fromEntity).toList();
  }

  public ResponseEntity<Object> getAllProbes() {
    var probes = probeRepository.findAll();

    if (probes.isEmpty()) {
      return new ResponseEntity<>("Probes not found", HttpStatus.CONFLICT);
    } else {
      return new ResponseEntity<>(convertListEntityToDto(probes), HttpStatus.OK);
    }
  }

  public ResponseEntity<Object> getProbeById(Long id) {
    var probe = probeRepository.findById(id);

    if (probe.isPresent()) {
      return ResponseEntity.ok(ProbeDTO.fromEntity(probe.get()));
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Probe not found");
    }
  }

  public ResponseEntity<Object> makeProbe(Long planetId, Integer x, Integer y, String direction) {
    var planet = planetRepository.findById(planetId);
    var point = new Point(x, y);

    if (!isValidParameters(planet, point, direction)) {
      return invalidResponseEntity(planet, point, direction);
    } else {
      return saveProbe(planet, point, direction);
    }
  }

  private ResponseEntity<Object> invalidResponseEntity(
      Optional<PlanetEntity> planet, Point coordinates, String direction) {

    if (planet.isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Planet not found");
    } else if (existProbeInCoordinates(planet.get(), coordinates)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Probe already exists in coordinates");
    } else if (!isValidCardinal(direction)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid cardinal");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters");
    }
  }

  private ResponseEntity<Object> saveProbe(
      Optional<PlanetEntity> planet, Point point, String direction) {

    var cardinal = parseCardinal(direction);
    var probeEntity = new ProbeEntity(point, cardinal, planet.get());

    planet.get().addProbe(probeEntity);
    planetRepository.save(planet.get());
    return ResponseEntity.ok("Probe created");
  }

  public ResponseEntity<Object> moveProbe(Long id, String movements) {
    var probe = probeRepository.findById(id);

    if (probe.isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Probe not found");
    } else if (!isValidMovements(movements)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid movements sequence");
    } else {
      return movementService.movePersistProbe(probe.get(), movements);
    }
  }

  public ResponseEntity<Object> deleteProbe(Long id) {
    var probe = probeRepository.findById(id);

    if (probe.isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Probe not found");
    } else {
      probeRepository.delete(probe.get());
      return ResponseEntity.ok("Probe deleted");
    }
  }

  public boolean isValidCardinal(String command) {
    String lowerCaseCommand = command.toLowerCase();

    return switch (lowerCaseCommand) {
      case "north", "south", "east", "west", "norte", "sul", "leste", "oeste" -> true;
      case "n", "s", "e", "l", "w", "o" -> true;
      default -> false;
    };
  }

  public Cardinal parseCardinal(String command) {
    String lowerCaseCommand = command.toLowerCase();

    return switch (lowerCaseCommand) {
      case "north", "norte", "n" -> Cardinal.NORTH;
      case "south", "sul", "s" -> Cardinal.SOUTH;
      case "east", "leste", "e", "l" -> Cardinal.EAST;
      case "west", "oeste", "w", "o" -> Cardinal.WEST;
      default -> throw new IllegalArgumentException("Invalid cardinal");
    };
  }

  public boolean isValidMovements(String movementsSequence) {
    return movementsSequence.matches("^[MLR]+$");
  }

  public boolean existProbeInCoordinates(PlanetEntity planet, Point coordinates) {
    var probes = planet.getProbes();

    return probes.stream().anyMatch(probe -> probe.getPosition().equals(coordinates));
  }

  public boolean isValidCoordinate(PlanetEntity planet, Point coordinates) {
    var planetWidth = planet.getWidth();
    var planetHeight = planet.getHeight();

    return coordinates.x >= 0 &&
        coordinates.x <= planetWidth &&
        coordinates.y >= 0 &&
        coordinates.y <= planetHeight;
  }

  public boolean isValidParameters(
      Optional<PlanetEntity> planet, Point coordinates, String direction) {

    if (planet.isEmpty()) {
      return false;
    } else if (existProbeInCoordinates(planet.get(), coordinates)) {
      return false;
    } else {
      return isValidCardinal(direction) && isValidCoordinate(planet.get(), coordinates);
    }
  }
}
