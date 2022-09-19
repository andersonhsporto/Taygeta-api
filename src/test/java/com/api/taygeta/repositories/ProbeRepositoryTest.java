package com.api.taygeta.repositories;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.enums.Cardinal;
import java.awt.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class ProbeRepositoryTest {

  @Autowired
  private ProbeRepository probeRepository;

  @Autowired
  private PlanetRepository planetRepository;

  @Test
  @DisplayName("Test if probe is saved")
  public void itShouldCheckIfProbeExists() {
    var planet = PlanetEntity.fromString("5x5");
    planetRepository.save(planet);

    var point = new Point(1, 2);
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);
    probeRepository.save(probe);

    var probeExists = probeRepository.existsById(probe.getId());

    assertTrue(probeExists);
  }

  @Test
  @DisplayName("Test if findAll returns a list of probes")
  public void itShouldCheckIfFindAllReturnsAListOfProbes() {
    var planet = PlanetEntity.fromString("5x5");
    planetRepository.save(planet);

    var point = new Point(1, 2);
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);
    probeRepository.save(probe);

    var point2 = new Point(3, 3);
    var probe2 = new ProbeEntity(point2, Cardinal.SOUTH, planet);
    probeRepository.save(probe2);

    var probesList = probeRepository.findAll();

    assertArrayEquals(new ProbeEntity[]{probe, probe2}, probesList.toArray());
    assertEquals(2, probesList.size());
  }

  @Test
  @DisplayName("Test if findById returns a probe")
  public void itShouldCheckIfFindByIdReturnsAProbe() {
    var planet = PlanetEntity.fromString("5x5");
    planetRepository.save(planet);

    var point = new Point(1, 2);
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);
    probeRepository.save(probe);

    var probeFound = probeRepository.findById(probe.getId());

    assertTrue(probeFound.isPresent());
    assertEquals(probe, probeFound.get());
    assertEquals(1, probeFound.get().getPosition().x);
  }

  @Test
  @DisplayName("Test if deleteById deletes a probe")
  public void itShouldCheckIfDeleteByIdDeletesAProbe() {
    var planet = PlanetEntity.fromString("5x5");
    planetRepository.save(planet);

    var point = new Point(1, 2);
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);
    probeRepository.save(probe);
    probeRepository.deleteById(probe.getId());

    var probeAfterDelete = probeRepository.findById(probe.getId());

    assertTrue(probeAfterDelete.isEmpty());
  }

  @Test
  @DisplayName("Test if deleteAll deletes all probes")
  public void itShouldCheckIfDeleteAllDeletesAllProbes() {
    var planet = PlanetEntity.fromString("5x5");
    planetRepository.save(planet);

    var point = new Point(1, 2);
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);
    probeRepository.save(probe);

    var point2 = new Point(3, 3);
    var probe2 = new ProbeEntity(point2, Cardinal.SOUTH, planet);
    probeRepository.save(probe2);

    probeRepository.deleteAll();

    var probesList = probeRepository.findAll();

    assertFalse(probesList.iterator().hasNext());
  }

}
