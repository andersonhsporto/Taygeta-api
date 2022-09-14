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

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long width;
  private Long height;

  @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL)
  private List<ProbeEntity> probes = new ArrayList<>();

  public PlanetEntity(Long width, Long height) {
    this.width = width;
    this.height = height;
  }

  public PlanetEntity() { }

  public Long getId() {
    return id;
  }

  public Long getWidth() {
    return width;
  }

  public Long getHeight() {
    return height;
  }

  public Long getArea() {
    return width * height;
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
