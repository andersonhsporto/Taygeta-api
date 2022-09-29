package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardinalTest {


  @Test
  @DisplayName("rotateLeft south to east")
  void shouldRotateEnumLeftSouthToWest() {
    Cardinal cardinal = Cardinal.SOUTH;

    Cardinal rotatedCardinal = cardinal.rotateLeft();

    assertEquals(Cardinal.EAST, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateLeft north to west")
  void shouldRotateEnumLeftNorthToWest() {
    Cardinal cardinal = Cardinal.NORTH;

    Cardinal rotatedCardinal = cardinal.rotateLeft();

    assertEquals(Cardinal.WEST, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateLeft east to north")
  void shouldRotateEnumLeftEastToNorth() {
    Cardinal cardinal = Cardinal.EAST;

    Cardinal rotatedCardinal = cardinal.rotateLeft();

    assertEquals(Cardinal.NORTH, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateLeft west to south")
  void shouldRotateEnumLeftWestToSouth() {
    Cardinal cardinal = Cardinal.WEST;

    Cardinal rotatedCardinal = cardinal.rotateLeft();

    assertEquals(Cardinal.SOUTH, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateRight south to west")
  void shouldRotateEnumRightSouthToWest() {
    Cardinal cardinal = Cardinal.SOUTH;

    Cardinal rotatedCardinal = cardinal.rotateRight();

    assertEquals(Cardinal.WEST, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateRight north to east")
  void shouldRotateEnumRightNorthToEast() {
    Cardinal cardinal = Cardinal.NORTH;

    Cardinal rotatedCardinal = cardinal.rotateRight();

    assertEquals(Cardinal.EAST, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateRight east to south")
  void shouldRotateEnumRightEastToSouth() {
    Cardinal cardinal = Cardinal.EAST;

    Cardinal rotatedCardinal = cardinal.rotateRight();

    assertEquals(Cardinal.SOUTH, rotatedCardinal);
  }

  @Test
  @DisplayName("rotateRight west to north")
  void shouldRotateEnumRightWestToNorth() {
    Cardinal cardinal = Cardinal.WEST;

    Cardinal rotatedCardinal = cardinal.rotateRight();

    assertEquals(Cardinal.NORTH, rotatedCardinal);
  }

}