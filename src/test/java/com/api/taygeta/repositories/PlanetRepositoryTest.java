package com.api.taygeta.repositories;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.api.taygeta.entities.PlanetEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class PlanetRepositoryTest {

  @Autowired
  private PlanetRepository planetRepository;

  @Test
  @DisplayName("Planet is saved")
  public void itShouldCheckIfPlanetExists() {
    var planet = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);

    boolean exists = planetRepository.existsById(planet.getId());

    assertTrue(exists);
  }

  @Test
  @DisplayName("findAll returns a list of planets")
  public void itShouldCheckIfFindAllReturnsAListOfPlanets() {
    var planet = PlanetEntity.fromString("5x5");
    var planet2 = PlanetEntity.fromString("5x5");

    planetRepository.save(planet);
    planetRepository.save(planet2);

    var planetsList = planetRepository.findAll();

    assertArrayEquals(new PlanetEntity[]{planet, planet2}, planetsList.toArray());
    assertEquals(2, planetsList.size());
  }

  @Test
  @DisplayName("findById returns a planet")
  public void itShouldCheckIfFindByIdReturnsAPlanet() {
    var planet = PlanetEntity.fromString("6x6");
    planetRepository.save(planet);

    var planetFound = planetRepository.findById(planet.getId());

    assertTrue(planetFound.isPresent());
    assertEquals(planet, planetFound.get());
    assertEquals(6, planetFound.get().getWidth());
  }

  @Test
  @DisplayName("deleteById deletes a planet")
  public void itShouldCheckIfDeleteByIdDeletesAPlanet() {
    var planet = PlanetEntity.fromString("7x7");

    planetRepository.save(planet);

    var planetFound = planetRepository.findById(planet.getId());

    assertTrue(planetFound.isPresent());
    assertEquals(planet, planetFound.get());
    assertEquals(7, planetFound.get().getWidth());

    planetRepository.deleteById(planet.getId());

    var planetFoundAfterDelete = planetRepository.findById(planet.getId());

    assertTrue(planetFoundAfterDelete.isEmpty());
  }

  @Test
  @DisplayName("deleteAll deletes all planets")
  public void itShouldCheckIfDeleteAllDeletesAllPlanets() {
    var planet = PlanetEntity.fromString("8x8");
    var planet2 = PlanetEntity.fromString("8x8");

    planetRepository.save(planet);
    planetRepository.save(planet2);

    var planetsList = planetRepository.findAll();

    assertArrayEquals(new PlanetEntity[]{planet, planet2}, planetsList.toArray());
    assertEquals(2, planetsList.size());

    planetRepository.deleteAll();

    var planetsListAfterDelete = planetRepository.findAll();

    assertEquals(0, planetsListAfterDelete.size());
  }


}
