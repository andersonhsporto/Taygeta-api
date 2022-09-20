package com.api.taygeta.services;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.exceptions.CollisionException;
import com.api.taygeta.repositories.ProbeRepository;
import java.awt.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovementService {

  ProbeRepository probeRepository;

  @Autowired
  public MovementService(ProbeRepository probeRepository) {
    this.probeRepository = probeRepository;
  }

  public ResponseEntity<Object> moveProbe(ProbeEntity probe, String movement) {
    try {
      updateProbeData(probe, movement);
      return ResponseEntity.status(HttpStatus.OK).body("Probe moved successfully");
    } catch (CollisionException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Collision detected");
    }
  }

  private void updateProbeData(ProbeEntity probe, String movement) throws CollisionException {
    for (int i = 0; i < movement.length(); i++) {
      switch (movement.charAt(i)) {
        case 'L' -> probe.rotateLeft();
        case 'R' -> probe.rotateRight();
        case 'M' -> moveForward(probe);
      }
    }
    probeRepository.save(probe);
  }

  private void moveForward(ProbeEntity probe) throws CollisionException {
    var planet = probe.getPlanet();

    probe.moveForward();
    roundPlanet(probe, planet);
    collisionCheck(probe, planet);
  }

  private void roundPlanet(ProbeEntity probe, PlanetEntity planet) {
    if (probe.getPosition().getX() > planet.getWidth()) {
      probe.setPosition(new Point(1, probe.getPosition().y));
    } else if (probe.getPosition().getX() < 0) {
      probe.setPosition(new Point(planet.getWidth(), probe.getPosition().y));
    } else if (probe.getPosition().getY() > planet.getHeight()) {
      probe.setPosition(new Point(probe.getPosition().x, 1));
    } else if (probe.getPosition().getY() < 0) {
      probe.setPosition(new Point(probe.getPosition().x, planet.getHeight()));
    }
  }

  private void collisionCheck(ProbeEntity probe, PlanetEntity planet) throws CollisionException {
    var probePosition = probe.getPosition();
    var planetProbes = planet.getProbes();

    for (var planetProbe : planetProbes) {
      if (planetProbe.getPosition().equals(probePosition) && !planetProbe.equals(probe)) {
        throw new CollisionException("Collision detected");
      }
    }
  }

}


