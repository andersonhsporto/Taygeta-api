package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import enums.Cardinal;
import exceptions.UndoCommandException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ParseServiceTest {

  private final ParseService parseService = new ParseService();

  @Test
  @DisplayName("Parse North Direction Test")
  void parseNorthDirection() throws UndoCommandException {
    String command = "N";
    InputStream inputStream = new ByteArrayInputStream(command.getBytes());

    System.setIn(inputStream);
    try {
      var compass = parseService.probeDirection();
      assertEquals(Cardinal.NORTH, compass);
    } catch (UndoCommandException e) {
      assertTrue(true);
    }
  }

  @Test
  @DisplayName("Parse South Direction Test")
  void parseSouthDirection() throws UndoCommandException {
    String command = "Sul";
    InputStream inputStream = new ByteArrayInputStream(command.getBytes());

    System.setIn(inputStream);
    try {
      Cardinal compass = parseService.probeDirection();
      assertEquals(Cardinal.SOUTH, compass);
    } catch (UndoCommandException e) {
      assertTrue(true);
    }
  }

  @Test
  @DisplayName("Parse East Direction Test")
  void parseEastDirection() throws UndoCommandException {
    String command = "leste";
    InputStream inputStream = new ByteArrayInputStream(command.getBytes());

    System.setIn(inputStream);
    try {
      Cardinal compass = parseService.probeDirection();
      assertEquals(Cardinal.EAST, compass);
    } catch (UndoCommandException e) {
      assertTrue(true);
    }
  }

  @Test
  @DisplayName("Parse West Direction Test")
  void parseWestDirection() throws UndoCommandException {
    String command = "W";
    InputStream inputStream = new ByteArrayInputStream(command.getBytes());

    System.setIn(inputStream);
    try {
      Cardinal compass = parseService.probeDirection();
      assertEquals(Cardinal.WEST, compass);
    } catch (UndoCommandException e) {
      assertTrue(true);
    }
  }


  @Test
  @DisplayName("Parse valid string to int")
  void parseStringToInt() throws UndoCommandException {
    String command = "5";
    int number = ParseService.id(command);

    assertEquals(5, number);
  }

  @Test
  @DisplayName("Parse invalid string to int")
  void parseInvalidStringToInt() throws UndoCommandException {
    String command = "111Invalid2";

    assertEquals(-1, parseService.id(command));
  }

  @Test
  @DisplayName("Parse invalid string to int")
  void parseInvalidStringToInt2() throws UndoCommandException {
    String command = "12a";

    assertEquals(-1, parseService.id(command));
  }

  @Test
  @DisplayName("Parse invalid cadinal")
  void parseInvalidCadinal() throws UndoCommandException {
    String command = "12a";

    Assertions.assertThrows(UndoCommandException.class, () -> parseService.cardinal(command));
  }

  @Test
  @DisplayName("undo probe direction")
  void parseInvalidProbeDirection() throws UndoCommandException {
    String command = "undo";
    InputStream inputStream = new ByteArrayInputStream(command.getBytes());

    System.setIn(inputStream);
    try {
      Cardinal compass = parseService.probeDirection();
      assertEquals(Cardinal.SOUTH, compass);
    } catch (UndoCommandException e) {
      assertTrue(true);
    }
  }
}

