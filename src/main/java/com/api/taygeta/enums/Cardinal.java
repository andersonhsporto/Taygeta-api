package com.api.taygeta.enums;

public enum Cardinal {
  NORTH,
  WEST,
  SOUTH,
  EAST;

  public Cardinal rotateLeft() {
    return switch (this) {
      case NORTH -> WEST;
      case SOUTH -> EAST;
      case EAST -> NORTH;
      case WEST -> SOUTH;
    };
  }

  public Cardinal rotateRight() {
    return switch (this) {
      case NORTH -> EAST;
      case SOUTH -> WEST;
      case EAST -> SOUTH;
      case WEST -> NORTH;
    };
  }
}