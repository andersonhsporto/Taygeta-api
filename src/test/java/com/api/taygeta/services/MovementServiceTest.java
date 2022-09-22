package com.api.taygeta.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.enums.Cardinal;
import com.api.taygeta.repositories.PlanetRepository;
import java.awt.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.main.banner-mode=off")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
class MovementServiceTest {

  @Autowired
  private MovementService movementService;

  @Autowired
  private PlanetRepository planetRepository;

  @Test
  @DisplayName("Move probe forward")
  void moveProbeForward() {
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(new Point(1, 1), Cardinal.NORTH, planet);

    planet.addProbe(probe);
    planetRepository.save(planet);

    var moveProbe = movementService.movePersistProbe(probe, "M");

    assertEquals("Probe moved successfully", moveProbe.getBody());
    assertEquals(1, probe.getPosition().x);
    assertEquals(2, probe.getPosition().y);
  }

  @Test
  @DisplayName("Move probe left")
  void moveProbeLeft() {
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(new Point(1, 1), Cardinal.NORTH, planet);

    planet.addProbe(probe);
    planetRepository.save(planet);

    var moveProbe = movementService.movePersistProbe(probe, "L");

    assertEquals("Probe moved successfully", moveProbe.getBody());
    assertEquals(1, probe.getPosition().x);
    assertEquals(1, probe.getPosition().y);
    assertEquals(Cardinal.WEST, probe.getCardinal());
  }

  @Test
  @DisplayName("Move probe right")
  void moveProbeRight() {
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(new Point(1, 1), Cardinal.NORTH, planet);

    planet.addProbe(probe);
    planetRepository.save(planet);

    var moveProbe = movementService.movePersistProbe(probe, "R");

    assertEquals("Probe moved successfully", moveProbe.getBody());
    assertEquals(1, probe.getPosition().x);
    assertEquals(1, probe.getPosition().y);
    assertEquals(Cardinal.EAST, probe.getCardinal());
  }

  @Test
  @DisplayName("Rotate probe left")
  void rotateProbeLeft() {
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(new Point(1, 1), Cardinal.NORTH, planet);

    planet.addProbe(probe);
    planetRepository.save(planet);

    var moveProbe = movementService.movePersistProbe(probe, "L");

    assertEquals("Probe moved successfully", moveProbe.getBody());
    assertEquals(1, probe.getPosition().x);
    assertEquals(1, probe.getPosition().y);
    assertEquals(Cardinal.WEST, probe.getCardinal());
  }

  @Test
  @DisplayName("Rotate probe right")
  void rotateProbeRight() {
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(new Point(1, 1), Cardinal.NORTH, planet);

    planet.addProbe(probe);
    planetRepository.save(planet);

    var moveProbe = movementService.movePersistProbe(probe, "R");

    assertEquals("Probe moved successfully", moveProbe.getBody());
    assertEquals(1, probe.getPosition().x);
    assertEquals(1, probe.getPosition().y);
    assertEquals(Cardinal.EAST, probe.getCardinal());
  }

  @Test
  @DisplayName("Colliding probe")
  void collidingProbe() {
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(new Point(1, 1), Cardinal.NORTH, planet);
    var probe2 = new ProbeEntity(new Point(1, 2), Cardinal.NORTH, planet);

    planet.addProbe(probe);
    planet.addProbe(probe2);
    planetRepository.save(planet);

    var moveProbe = movementService.movePersistProbe(probe, "M");

    assertEquals("Collision detected the probe will not move", moveProbe.getBody());
  }
}