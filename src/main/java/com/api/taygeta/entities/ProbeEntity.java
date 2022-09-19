package com.api.taygeta.entities;

import com.api.taygeta.enums.Cardinal;
import java.awt.Point;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "probes")
public class ProbeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Point position;

  @Enumerated(EnumType.STRING)
  private Cardinal cardinal;

  @ManyToOne
  private PlanetEntity planet;

  public ProbeEntity(Point position, Cardinal cardinal, PlanetEntity planet) {
    this.position = position;
    this.cardinal = cardinal;
    this.planet = planet;
  }

  public ProbeEntity() {
  }

  public Long getId() {
    return id;
  }

  public Point getPosition() {
    return position;
  }

  public void setPosition(Point position) {
    this.position = position;
  }

  public Cardinal getCardinal() {
    return cardinal;
  }

  public PlanetEntity getPlanet() {
    return planet;
  }

  public void rotateLeft() {
    this.cardinal = this.cardinal.rotateLeft();
  }

  public void rotateRight() {
    this.cardinal = this.cardinal.rotateRight();
  }

  public void moveForward() {
    switch (this.cardinal) {
      case NORTH -> this.position.translate(0, 1);
      case SOUTH -> this.position.translate(0, -1);
      case EAST -> this.position.translate(1, 0);
      case WEST -> this.position.translate(-1, 0);
    }
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProbeEntity that = (ProbeEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(position, that.position)
        && cardinal == that.cardinal && Objects.equals(planet, that.planet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, position, cardinal, planet);
  }

  @Override
  public String toString() {
    return "ProbeEntity { " +
        "id = " + id +
        ", position = " + position +
        ", cardinal = " + cardinal +
        ", planet = " + planet +
        '}';
  }
}
