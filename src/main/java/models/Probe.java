package models;

import enums.Cardinal;
import java.awt.Point;
import java.util.Objects;
import services.MessageService;

public class Probe {

  private final Point point;

  private final Cardinal cardinal;

  public Probe(int x, int y, Cardinal cardinal) {
    this.point = new Point(x, y);
    this.cardinal = cardinal;
  }

  public Probe(Point point, Cardinal cardinal) {
    this.point = point;
    this.cardinal = cardinal;
  }

  public static Probe createDefault(int x, int y, Cardinal direction) {
    return new Probe(x, y, direction);
  }

  public Point getPoint() {
    return point;
  }

  public Cardinal getCardinal() {
    return cardinal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Probe probe = (Probe) o;
    return Objects.equals(point, probe.point) && cardinal == probe.cardinal;
  }

  @Override
  public int hashCode() {
    return Objects.hash(point, cardinal);
  }

  @Override
  public String toString() {
    return MessageService.blue(
        " [ coordinates = " + pointToString() +
            ", direction = " + cardinal +
            " ]");
  }

  private String pointToString() {
    return String.format("(x: %d, y: %d)", point.x, point.y);
  }
}
