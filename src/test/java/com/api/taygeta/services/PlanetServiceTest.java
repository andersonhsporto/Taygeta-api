package com.api.taygeta.services;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
class PlanetServiceTest {

  @Autowired
  private PlanetService planetService;

  @Test
  @DisplayName("Planet string is invalid when it has less than 3 characters")
  public void itShouldCheckIfPlanetStringIsInvalidWhenItHasLessThan3Characters() {
    var planetString = "5x";
    var isValid = planetService.commandInPlanetSizeFormat(planetString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("Planet string is invalid when x is a negative number")
  public void itShouldCheckIfPlanetStringIsInvalidWhenXIsANegativeNumber() {
    var planetString = "-5x5";
    var isValid = planetService.commandInPlanetSizeFormat(planetString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("Planet string is invalid when y is a negative number")
  public void itShouldCheckIfPlanetStringIsInvalidWhenYIsANegativeNumber() {
    var planetString = "5x-5";
    var isValid = planetService.commandInPlanetSizeFormat(planetString);

    assertFalse(isValid);
  }

  @Test
  @DisplayName("Planet string is invalid when x is not a number")
  public void itShouldCheckIfPlanetStringIsInvalidWhenXIsNotANumber() {
    var planetString = "x5";
    var isValid = planetService.commandInPlanetSizeFormat(planetString);

    assertFalse(isValid);
  }
}