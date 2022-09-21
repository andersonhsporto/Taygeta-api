package com.api.taygeta.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.api.taygeta.entities.PlanetEntity;
import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.enums.Cardinal;
import java.awt.Point;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
class ProbeServiceTest {

  @Autowired
  private ProbeService probeService;

  @Test
  @DisplayName("Invalid Cardinal string")
  public void itShouldCheckIfCardinalStringIsInvalid() {
    var cardinalString = "INVALID";
    var isValid = probeService.isValidCardinal(cardinalString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("Cardinal string valid  -> north south east west")
  public void itShouldCheckIfCardinalStringIsValid() {
    var north = probeService.isValidCardinal("North");
    var south = probeService.isValidCardinal("South");
    var east = probeService.isValidCardinal("East");
    var west = probeService.isValidCardinal("West");

    assertTrue(north);
    assertTrue(south);
    assertTrue(east);
    assertTrue(west);
  }

  @Test
  @DisplayName("Cardinal string valid  -> norte sul leste oeste")
  public void itShouldCheckIfCardinalStringIsValid2() {
    var norte = probeService.isValidCardinal("Norte");
    var sul = probeService.isValidCardinal("Sul");
    var leste = probeService.isValidCardinal("Leste");
    var oeste = probeService.isValidCardinal("Oeste");

    assertTrue(norte);
    assertTrue(sul);
    assertTrue(leste);
    assertTrue(oeste);
  }

  @Test
  @DisplayName("Cardinal string valid upper case -> north south east west")
  public void itShouldCheckIfCardinalStringIsValidUpperCase() {
    var north = probeService.isValidCardinal("NORTH");
    var south = probeService.isValidCardinal("SOUTH");
    var east = probeService.isValidCardinal("EAST");
    var west = probeService.isValidCardinal("WEST");

    assertTrue(north);
    assertTrue(south);
    assertTrue(east);
    assertTrue(west);
  }

  @Test
  @DisplayName("Cardinal string valid upper case -> NORTE SUL LESTE OESTE")
  public void itShouldCheckIfCardinalStringIsValid3() {
    var norte = probeService.isValidCardinal("NORTE");
    var sul = probeService.isValidCardinal("SUL");
    var leste = probeService.isValidCardinal("LESTE");
    var oeste = probeService.isValidCardinal("OESTE");

    assertTrue(norte);
    assertTrue(sul);
    assertTrue(leste);
    assertTrue(oeste);
  }

  @Test
  @DisplayName("Cardinal string valid first letter of cardinal")
  public void itShouldCheckIfCardinalStringIsValidFirstLetterOfCardinal() {
    var north = probeService.isValidCardinal("N");
    var south = probeService.isValidCardinal("S");
    var east = probeService.isValidCardinal("E");
    var west = probeService.isValidCardinal("W");

    assertTrue(north);
    assertTrue(south);
    assertTrue(east);
    assertTrue(west);
  }

  @Test
  @DisplayName("Cardinal string valid first portuguese letter of cardinal")
  public void itShouldCheckIfCardinalStringIsValidFirstLetterBROfCardinal() {
    var norte = probeService.isValidCardinal("N");
    var sul = probeService.isValidCardinal("S");
    var leste = probeService.isValidCardinal("L");
    var oeste = probeService.isValidCardinal("O");

    assertTrue(norte);
    assertTrue(sul);
    assertTrue(leste);
    assertTrue(oeste);
  }

  @Test
  @DisplayName("Parse cardinal invalid string")
  public void itShouldParseCardinalInvalidString() {
    var cardinalString = "INVALID";

    try {
      var cardinal = probeService.parseCardinal(cardinalString);
    } catch (Exception e) {
      assertEquals("Invalid cardinal", e.getMessage());
    }
  }

  @Test
  @DisplayName("Parse cardinal valid english strings")
  public void itShouldParseCardinalValidString() {
    var north = probeService.parseCardinal("NORTH");
    var south = probeService.parseCardinal("SOUTH");
    var east = probeService.parseCardinal("EAST");
    var west = probeService.parseCardinal("WEST");

    assertEquals(Cardinal.NORTH, north);
    assertEquals(Cardinal.SOUTH, south);
    assertEquals(Cardinal.EAST, east);
    assertEquals(Cardinal.WEST, west);
  }

  @Test
  @DisplayName("Parse cardinal valid portuguese strings")
  public void itShouldParseCardinalValidString2() {
    var norte = probeService.parseCardinal("NORTE");
    var sul = probeService.parseCardinal("SUL");
    var leste = probeService.parseCardinal("LESTE");
    var oeste = probeService.parseCardinal("OESTE");

    assertEquals(Cardinal.NORTH, norte);
    assertEquals(Cardinal.SOUTH, sul);
    assertEquals(Cardinal.EAST, leste);
    assertEquals(Cardinal.WEST, oeste);
  }

  @Test
  @DisplayName("Parse cardinal valid first letter of english cardinal")
  public void itShouldParseCardinalValidFirstLetterOfCardinal() {
    var north = probeService.parseCardinal("N");
    var south = probeService.parseCardinal("S");
    var east = probeService.parseCardinal("E");
    var west = probeService.parseCardinal("W");

    assertEquals(Cardinal.NORTH, north);
    assertEquals(Cardinal.SOUTH, south);
    assertEquals(Cardinal.EAST, east);
    assertEquals(Cardinal.WEST, west);
  }

  @Test
  @DisplayName("Parse cardinal valid first letter of portuguese cardinal")
  public void itShouldParseCardinalValidFirstLetterOfCardinal2() {
    var norte = probeService.parseCardinal("N");
    var sul = probeService.parseCardinal("S");
    var leste = probeService.parseCardinal("L");
    var oeste = probeService.parseCardinal("O");

    assertEquals(Cardinal.NORTH, norte);
    assertEquals(Cardinal.SOUTH, sul);
    assertEquals(Cardinal.EAST, leste);
    assertEquals(Cardinal.WEST, oeste);
  }

  @Test
  @DisplayName("Movements string is invalid")
  public void itShouldCheckIfMovementStringIsValid() {
    var movementString = "INVALID";
    var isValid = probeService.isValidMovements(movementString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("Movements string is a valid string")
  public void itShouldCheckIfMovementStringIsValid2() {
    var movementString = "LMLMLMLMMRR";
    var isValid = probeService.isValidMovements(movementString);

    assertTrue(isValid);
  }

  @Test
  @DisplayName("Probe exists in a given point of a planet")
  public void itShouldCheckIfProbeExistsInPosition() {
    var point = new Point(1, 2);
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);

    planet.addProbe(probe);
    var exists = probeService.existProbeInCoordinates(planet, point);

    assertTrue(exists);
  }

  @Test
  @DisplayName("Probe does not exist in a given point of a planet")
  public void itShouldCheckIfProbeDoesNotExistInPosition() {
    var point = new Point(1, 2);
    var planet = PlanetEntity.fromString("5x5");
    var probe = new ProbeEntity(point, Cardinal.NORTH, planet);

    planet.addProbe(probe);
    var exists = probeService.existProbeInCoordinates(planet, new Point(2, 2));

    assertFalse(exists);
  }

  @Test
  @DisplayName("Is a valid coordinate point")
  public void itShouldCheckIfPointIsValid() {
    var point = new Point(1, 2);
    var planet = PlanetEntity.fromString("5x5");
    var isValid = probeService.isValidCoordinate(planet, point);

    assertTrue(isValid);
  }

  @Test
  @DisplayName("Coordinate point is exactly the planet's limit")
  public void itShouldCheckIfPointIsPlanetLimit() {
    var point = new Point(5, 5);
    var planet = PlanetEntity.fromString("5x5");
    var isValid = probeService.isValidCoordinate(planet, point);

    assertTrue(isValid);
  }

  @Test
  @DisplayName("Coordinate point is zero")
  public void itShouldCheckIfPointIsZero() {
    var point = new Point(0, 0);
    var planet = PlanetEntity.fromString("5x5");
    var isValid = probeService.isValidCoordinate(planet, point);

    assertTrue(isValid);
  }

  @Test
  @DisplayName("Coordinate point is a negative value")
  public void itShouldCheckIfPointIsNegative() {
    var point = new Point(-1, 2);
    var planet = PlanetEntity.fromString("5x5");
    var isValid = probeService.isValidCoordinate(planet, point);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("Coordinate point is greater than planet dimensions")
  public void itShouldCheckIfPointIsGreaterThanPlanetDimensions() {
    var point = new Point(6, 2);
    var planet = PlanetEntity.fromString("5x5");
    var isValid = probeService.isValidCoordinate(planet, point);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("validParameters empty planet")
  public void itShouldCheckIfParametersAreValidWhenEmptyPlanet() {
    var point = new Point(1, 2);
    var cardinalString = "Norte";

    var isValid = probeService.isValidParameters(
        Optional.empty(), point, cardinalString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("validParameters invalid point")
  public void itShouldCheckIfParametersAreValidWhenInvalidPoint() {
    var point = new Point(42, 42);
    var cardinalString = "Norte";
    var planet = PlanetEntity.fromString("5x5");

    var isValid = probeService.isValidParameters(
        Optional.of(planet), point, cardinalString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("validParameters invalid movement string")
  public void itShouldCheckIfParametersAreValidWhenInvalidMoveString() {
    var point = new Point(1, 2);
    var cardinalString = "INVALID";
    var planet = PlanetEntity.fromString("5x5");

    var isValid = probeService.isValidParameters(
        Optional.of(planet), point, cardinalString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("validParameters using valid parameters")
  public void itShouldCheckIfParametersAreValidWhenValidParameters() {
    var point = new Point(1, 2);
    var cardinalString = "NORTH";
    var planet = PlanetEntity.fromString("5x5");

    var isValid = probeService.isValidParameters(
        Optional.of(planet), point, cardinalString);

    assertTrue(isValid);
  }
}