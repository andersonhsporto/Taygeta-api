package com.api.taygeta.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "planets")
public class PlanetEntity {

  @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<ProbeEntity> probes = new ArrayList<>();
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Integer width;
  private Integer height;

  public PlanetEntity(Integer width, Integer height) {
    this.width = width;
    this.height = height;
  }

  public PlanetEntity() {
  }

  public static PlanetEntity fromString(String string) {
    String[] sides = string.split("x");

    return new PlanetEntity(Integer.parseInt(sides[0]), Integer.parseInt(sides[1]));
  }

  public Long getId() {
    return id;
  }

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }

  public List<ProbeEntity> getProbes() {
    return probes;
  }

  public Integer getArea() {
    return width * height;
  }

  public void updateSizes(String string) {
    String[] sides = string.split("x");

    this.width = Integer.parseInt(sides[0]);
    this.height = Integer.parseInt(sides[1]);
  }

  public Integer getProbesCount() {
    return probes.size();
  }

  public boolean isFull() {
    return Objects.equals(getProbesCount(), getArea());
  }

  public void addProbe(ProbeEntity probe) {
    probes.add(probe);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlanetEntity that = (PlanetEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(width, that.width)
        && Objects.equals(height, that.height) && Objects.equals(probes,
        that.probes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, width, height, probes);
  }

  @Override
  public String toString() {
    return "PlanetEntity{" +
        "id=" + id +
        ", width=" + width +
        ", height=" + height +
        ", probes=" + probes +
        '}';
  }
}
