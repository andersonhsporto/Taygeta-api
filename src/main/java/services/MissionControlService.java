package services;

import enums.Cardinal;
import exceptions.UndoCommandException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import models.Planet;
import models.Probe;

public class MissionControlService {

  private final MessageService messageService;
  private final Collection<Planet> planets;

  public MissionControlService() {
    this.planets = new ArrayList<>();
    this.messageService = new MessageService();
  }

  public Collection<Planet> getPlanets() {
    return planets;
  }

  public void addProbeToPlanet(Probe probe, int planetId) {

    if (existProbeInCoordinates(probe, planetId)) {
      messageService.error("Probe already exists in this coordinates, the probe is not added");
      return;
    }
    if (!coordinatesIsValid(probe, planetId)) {
      messageService.error("Invalid coordinates, the probe is not added");
      return;
    }
    for (Planet planet : planets) {
      if (planet.getId() == planetId) {
        planet.addProbe(probe);
        messageService.success(
            "Probe id: " + planet.getProbesCount() + " added to planet " + planetId);
      }
    }
  }

  public Optional<Planet> getPlanetById(int planetId) {
    for (Planet planet : planets) {
      if (planet.getId() == planetId) {
        return Optional.of(planet);
      }
    }
    return Optional.empty();
  }

  public boolean coordinatesIsValid(Probe probe, int planetId) {
    var planet = getPlanetById(planetId);
    var planetWidth = planet.get().getWidth();
    var planetHeight = planet.get().getHeight();

    if (probe.getPoint().getX() < 1 || probe.getPoint().getX() > planetWidth) {
      return false;
    }
    return !(probe.getPoint().getY() < 1) && !(probe.getPoint().getY() > planetHeight);
  }

  public boolean existProbeInCoordinates(Probe probe, int planetId) {
    var planet = getPlanetById(planetId);

    for (Probe probeInPlanet : planet.get().getProbes().values()) {
      if (probeInPlanet.getPoint().equals(probe.getPoint())) {
        return true;
      }
    }
    return false;
  }

  public void addPlanet(String command) {
    Planet planet = Planet.createDefault(planets.size(), command);

    planets.add(planet);
    messageService.success("Planet id: " + (getPlanets().size() - 1) + " created");
  }

  private int getPlanetsListSize() {
    return this.planets.size();
  }

  public void listPlanets() {
    if (getPlanetsListSize() == 0) {
      messageService.error("Planets not found");
      return;
    }
    for (Planet planet : planets) {
      System.out.println(planet.toString());
    }
  }

  public void listProbes() {
    if (getPlanetsListSize() == 0) {
      messageService.error("Planets not found, no probes to list");
      return;
    }
    for (Planet planet : planets) {
      planet.printProbes();
    }
  }

  public void listAll() {
    if (getPlanetsListSize() == 0) {
      messageService.error("Planets not found");
      return;
    }
    listPlanets();
    listProbes();
  }

  public boolean planetByIdIsFull(int planetId) {
    var planet = getPlanetById(planetId);

    return planet.get().isFull();
  }

  public void moveProbe(
      Integer planetId, Integer probeId, String sequence) throws UndoCommandException {

    Probe probe = getPlanetById(planetId).get().getProbeById(probeId);
    Probe probeCopy;
    String stringOfProbe;

    for (Planet planet : planets) {
      if (Objects.equals(planet.getId(), planetId)) {
        probeCopy = cloneUpdateProbe(probe, planet, sequence);
        planet.putProbe(probeId, probeCopy);
        stringOfProbe = planet.getProbeById(probeId).toString();
        MessageService.blueMessage("Probe id: " + probeId + stringOfProbe + "\n");
      }
    }
  }

  public Probe cloneUpdateProbe(
      Probe probe, Planet planet, String sequence) throws UndoCommandException {

    Probe newProbe;
    var newCardinal = probe.getCardinal();
    var newPoint = probe.getPoint();
    var originPoint = probe.getPoint();

    for (int i = 0; i < sequence.length(); i++) {
      switch (sequence.charAt(i)) {
        case 'L' -> newCardinal = newCardinal.rotateLeft();
        case 'R' -> newCardinal = newCardinal.rotateRight();
        case 'M' -> moveForward(newPoint, newCardinal, planet, originPoint);
      }
    }
    newProbe = new Probe(newPoint, newCardinal);
    return newProbe;
  }

  public void moveForward(
      Point point, Cardinal cardinal, Planet planet, Point origin) throws UndoCommandException {

    switch (cardinal) {
      case NORTH -> moveNorth(point, planet);
      case SOUTH -> moveSouth(point, planet);
      case EAST -> moveEast(point, planet);
      case WEST -> moveWest(point, planet);
    }
    collision(planet, point, origin);
  }

  public void moveNorth(Point point, Planet planet) {
    if (point.getY() < planet.getHeight()) {
      point.translate(0, 1);
    } else if (point.getY() == planet.getHeight()) {
      point.setLocation(point.getX(), 1);
    }
  }

  public void moveSouth(Point point, Planet planet) {
    if (point.getY() > 1) {
      point.translate(0, -1);
    } else if (point.getY() == 1) {
      point.setLocation(point.getX(), planet.getHeight());
    }
  }

  public void moveEast(Point point, Planet planet) {
    if (point.getX() < planet.getWidth()) {
      point.setLocation(point.getX() + 1, point.getY());
    } else if (point.getX() == planet.getWidth()) {
      point.setLocation(1, point.getY());
    }
  }

  public void moveWest(Point point, Planet planet) {
    if (point.getX() > 1) {
      point.setLocation(point.getX() - 1, point.getY());
    } else if (point.getX() == 1) {
      point.setLocation(planet.getWidth(), point.getY());
    }
  }

  public void collision(
      Planet planet, Point point, Point origin) throws UndoCommandException {

    for (Probe probeInPlanet : planet.getProbes().values()) {
      if (probeInPlanet.getPoint().equals(point) && !probeInPlanet.getPoint().equals(origin)) {
        messageService.error("Collision detected, the probe is not moved");
        throw new UndoCommandException("Collision detected");
      }
    }
  }

}

