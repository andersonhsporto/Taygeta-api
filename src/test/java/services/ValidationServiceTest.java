package services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import enums.Cardinal;
import models.Probe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationServiceTest {

  @Test
  @DisplayName("commandInPlanetSizeFormat with valid command")
  void commandInPlanetSizeFormat_validCommand() {
    assertTrue(ValidationService.commandInPlanetSizeFormat("5x5"));
  }

  @Test
  @DisplayName("commandInPlanetSizeFormat with invalid command")
  void commandInPlanetSizeFormat_invalidCommand() {
    assertFalse(ValidationService.commandInPlanetSizeFormat("5x"));
  }

  @Test
  @DisplayName("commandInPlanetSizeFormat with invalid command")
  void commandInPlanetSizeFormat_invalidCommand2() {
    assertFalse(ValidationService.commandInPlanetSizeFormat("5x5x5"));
  }

  @Test
  @DisplayName("commandInPlanetSizeFormat with invalid command")
  void commandInPlanetSizeFormat_invalidCommand3() {
    assertFalse(ValidationService.commandInPlanetSizeFormat("-5x5"));
  }

  @Test
  @DisplayName("commandInPlanetSizeFormat with invalid command")
  void commandInPlanetSizeFormat_invalidCommand4() {
    assertFalse(ValidationService.commandInPlanetSizeFormat("5x-5"));
  }

  @Test
  @DisplayName("commandInPlanetSizeFormat with invalid command")
  void commandInPlanetSizeFormat_invalidCommand5() {
    assertFalse(ValidationService.commandInPlanetSizeFormat("ax5"));
  }

  @Test
  @DisplayName("Planet exists")
  void planetExists() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    assertTrue(ValidationService.planetExists(missionControlService));
  }

  @Test
  @DisplayName("Planet does not exist")
  void planetDoesNotExist() {
    MissionControlService missionControlService = new MissionControlService();

    assertFalse(ValidationService.planetExists(missionControlService));
  }

  @Test
  @DisplayName("Planet is valid")
  void planetIsValid() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    assertTrue(ValidationService.planetIsValid("0", missionControlService));
  }

  @Test
  @DisplayName("Planet is not valid")
  void planetIsNotValid() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("5x5");
    assertFalse(ValidationService.planetIsValid("1", missionControlService));
  }

  @Test
  @DisplayName("Planet is full")
  void planetIsFull() {
    MissionControlService missionControlService = new MissionControlService();
    Probe probe = Probe.createDefault(1, 1, Cardinal.NORTH);

    missionControlService.addPlanet("1x1");
    missionControlService.addProbeToPlanet(probe, 0);
    missionControlService.addProbeToPlanet(probe, 0);
    assertFalse(ValidationService.planetIsValid("0", missionControlService));
    Assertions.assertEquals(1, missionControlService.getPlanets().size());
  }

  @Test
  @DisplayName("Planet exist using a valid planet id")
  void truePlaneExistByid() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("10x10");
    assertTrue(ValidationService.planetExistsById("0", missionControlService));
  }


  @Test
  @DisplayName("Planet exist using id that's not in the list")
  void falsePlaneExistByid() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("55x10");
    assertFalse(ValidationService.planetExistsById("1", missionControlService));
  }

  @Test
  @DisplayName("Planet exist using valid id")
  void falsePlaneExistByidMultipleTrue() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("55x10");
    missionControlService.addPlanet("1x1");
    assertTrue(ValidationService.planetExistsById("1", missionControlService));
  }

  @Test
  @DisplayName("Planet exist using id that's not in the list")
  void falsePlaneExistByidMultipleFalse() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("55x10");
    missionControlService.addPlanet("1x1");
    assertFalse(ValidationService.planetExistsById("2", missionControlService));
  }

  @Test
  @DisplayName("Planet negative id is invalid")
  void falsePlanetExistNegativeId() {
    MissionControlService missionControlService = new MissionControlService();

    missionControlService.addPlanet("55x10");
    missionControlService.addPlanet("1x1");
    assertFalse(ValidationService.planetExistsById("-1", missionControlService));
  }

  @Test
  @DisplayName("Probe exists")
  void probeExists() {
    MissionControlService missionControlService = new MissionControlService();
    Probe probe = Probe.createDefault(1, 1, Cardinal.NORTH);

    missionControlService.addPlanet("1x1");
    missionControlService.addProbeToPlanet(probe, 0);
    assertTrue(ValidationService.probeExists(0, 0, missionControlService));
  }

  @Test
  @DisplayName("Probe does not exist")
  void probeDoesNotExist() {
    MissionControlService missionControlService = new MissionControlService();
    Probe probe = Probe.createDefault(1, 1, Cardinal.NORTH);

    missionControlService.addPlanet("1x1");
    missionControlService.addProbeToPlanet(probe, 0);
    assertFalse(ValidationService.probeExists(0, 1, missionControlService));
  }

  @Test
  @DisplayName("Sequence string MLR is valid")
  void trueStringOnlyContainsCharactersMLR() {
    ValidationService validationService = new ValidationService();

    assertTrue(validationService.isValidSequence("MLR"));
  }

  @Test
  @DisplayName("Sequence string LMLMLMLMM is valid")
  void trueDefaultStringLMLMLMLMM() {
    ValidationService validationService = new ValidationService();

    assertTrue(validationService.isValidSequence("LMLMLMLMM"));
  }

  @Test
  @DisplayName("Big sequence string is valid")
  void bigStringIsValid() {
    ValidationService validationService = new ValidationService();

    assertTrue(validationService.isValidSequence(
        "LMLMLMLMMMMMMMMMMMMMMLMLMLMLMMMMMMMMMMMMMMLMLMLMLMMMMMMMMMMMMMM"));
  }

  @Test
  @DisplayName("Using invalid sequence string norte")
  void invalidSequenceNorte() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence("norte"));
  }

  @Test
  @DisplayName("Empty sequence string is invalid")
  void emptyString() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence(""));
  }

  @Test
  @DisplayName("Using invalid sequence string 123")
  void invalidString123() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence("123"));
  }

  @Test
  @DisplayName("Lowercase sequence string is invalid")
  void lowercaseString() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence("mlr"));
  }

  @Test
  @DisplayName("Sequence string with space is invalid")
  void stringWithSpace() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence("M L R"));
  }

  @Test
  @DisplayName("Sequence string with tab is invalid")
  void stringWithTab() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence("M\tL\tR"));
  }

  @Test
  @DisplayName("Sequence string with new line is invalid")
  void stringWithNewLine() {
    ValidationService validationService = new ValidationService();

    assertFalse(validationService.isValidSequence("M \n L \n R"));
  }

}