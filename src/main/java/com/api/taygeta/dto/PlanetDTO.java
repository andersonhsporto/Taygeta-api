package com.api.taygeta.dto;

import com.api.taygeta.entities.PlanetEntity;

public record PlanetDTO(Long id, Integer width, Integer height, Integer area) {

  public static PlanetDTO fromEntity(PlanetEntity planet) {
    return new PlanetDTO(
        planet.getId(),
        planet.getWidth(),
        planet.getHeight(),
        planet.getArea());
  }
}
