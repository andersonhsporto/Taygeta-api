package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import enums.Cardinal;
import exceptions.UndoCommandException;
import java.awt.Point;
import java.util.Optional;
import models.Planet;
import models.Probe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MissionControlServiceTest {

  @Test
  @DisplayName("Test add Planet of size 5x5")
  void add5x5Planet() {
    MissionControlService missionControlService = new MissionControlService();
    missionControlService.addPlanet("5x5");
    assertEquals(1, missionControlService.getPlanets().size());
  }

  @Test
  @DisplayName("Add one probe to planet")
  void addOneProbeToPlanet() {
    MissionControlService missionControlService = new MissionControlService();
    missionControlService.addPlanet("10x10");
    missionControlService.addProbeToPlanet(new Probe(0, 0, Cardinal.NORTH), 0);
    assertEquals(1, missionControlService.getPlanets().size());
  }

  @Test
  @DisplayName("Size of planets list is 5")
  void sizeOfPlanetsListIs5() {
    MissionControlService missionControlService = new MissionControlService();
    missionControlService.addPlanet("5x5");
    missionControlService.addPlanet("7x1");
    missionControlService.addPlanet("1x3");
    missionControlService.addPlanet("4x9");
    missionControlService.addPlanet("42x42");
    assertEquals(5, missionControlService.getPlanets().size());
  }

  @Test
  @DisplayName("Get planet by id 0")
  void getPlanetById() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    missionControlService.addPlanet("7x1");
    missionControlService.addPlanet("1x3");
    missionControlService.addPlanet("4x9");
    missionControlService.addPlanet("42x42");

    Planet planet = missionControlService.getPlanetById(0).get();

    assertEquals(0, planet.getId());
    assertEquals(5, planet.getWidth());
    assertEquals(5, planet.getHeight());
  }

  @Test
  @DisplayName("Get planet by id 4")
  void getPlanetById4() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    missionControlService.addPlanet("7x1");

    Optional<Planet> planet = missionControlService.getPlanetById(4);

    Assertions.assertTrue(planet.isEmpty());
  }

  @Test
  @DisplayName("Size of planets list is 0")
  void sizeOfPlanetsListIs0() {
    MissionControlService missionControlService = new MissionControlService();

    assertEquals(0, missionControlService.getPlanets().size());
  }

  @Test
  @DisplayName("Probe exists in coordinates true")
  void probeExistsInCoordinatesTrue() {
    MissionControlService missionControlService = new MissionControlService();
    Probe probe = new Probe(1, 1, Cardinal.NORTH);

    missionControlService.addPlanet("5x5");
    missionControlService.addProbeToPlanet(probe, 0);
    Assertions.assertTrue(missionControlService.existProbeInCoordinates(probe, 0));
  }

  @Test
  @DisplayName("Probe exists in coordinates false")
  void probeExistsInCoordinatesFalse() {
    MissionControlService missionControlService = new MissionControlService();
    Probe probe = new Probe(1, 1, Cardinal.NORTH);

    missionControlService.addPlanet("5x5");
    Assertions.assertFalse(missionControlService.existProbeInCoordinates(probe, 0));
  }



  @Test
  @DisplayName("Size of planets list is 1")
  void sizeOfPlanetsListIs1() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    assertEquals(1, missionControlService.getPlanets().size());
  }


  @Test
  @DisplayName("Move probe using LMLMLMLMM sequence")
  void moveProveUsingLMLMLMLMMSequence() throws UndoCommandException {
    MissionControlService missionControlService = new MissionControlService();
    Probe tempProbe = new Probe(1, 2, Cardinal.NORTH);
    Planet planet = new Planet(0, 5, 5);
    Probe cloneProbe = missionControlService.cloneUpdateProbe(tempProbe, planet, "LMLMLMLMM");

    assertEquals(new Point(1, 3), cloneProbe.getPoint());
    assertEquals(Cardinal.NORTH, cloneProbe.getCardinal());
  }

  @Test
  @DisplayName("Move probe using MMRMMRMRRML sequence")
  void moveProveUsingMMRMMRMRRMLSequence() throws UndoCommandException {
    MissionControlService missionControlService = new MissionControlService();
    Probe tempProbe = new Probe(3, 3, Cardinal.EAST);
    Planet planet = new Planet(0, 5, 5);
    Probe cloneProbe = missionControlService.cloneUpdateProbe(tempProbe, planet, "MMRMMRMRRML");

    assertEquals(new Point(5, 1), cloneProbe.getPoint());
    assertEquals(Cardinal.NORTH, cloneProbe.getCardinal());
  }

  @Test
  @DisplayName("Probe exist in coordinates")
  void collisionBetweenProbes() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    missionControlService.addProbeToPlanet(new Probe(1, 2, Cardinal.NORTH), 0);
    missionControlService.addProbeToPlanet(new Probe(1, 2, Cardinal.NORTH), 0);
    assertEquals(1, missionControlService.getPlanets().size());
  }

  @Test
  @DisplayName("Probe simple collision")
  void probeSimpleCollisionTheProbeIsNotAdded() throws UndoCommandException {
    MissionControlService missionControlService = new MissionControlService();
    Probe collision = new Probe(4, 3, Cardinal.NORTH);
    Probe tempProbe = new Probe(3, 3, Cardinal.EAST);
    Planet planet = new Planet(0, 5, 5);

    planet.addProbe(collision);
    planet.addProbe(tempProbe);
    try {
      Probe clone = missionControlService.cloneUpdateProbe(tempProbe, planet, "M");
      planet.putProbe(1, clone);
    } catch (UndoCommandException e) {
      assertEquals("Collision detected, the probe is not moved", e.getMessage());
      assertEquals(tempProbe, planet.getProbes().get(1));
    }
  }

}