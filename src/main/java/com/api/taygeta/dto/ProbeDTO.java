package com.api.taygeta.dto;

import com.api.taygeta.entities.ProbeEntity;
import com.api.taygeta.enums.Cardinal;
import java.awt.Point;

public record ProbeDTO(Long id,
                       Point position,
                       Cardinal cardinal,
                       Long PlanetId) {

  public static ProbeDTO fromEntity(ProbeEntity probe) {
    return new ProbeDTO(
        probe.getId(),
        probe.getPosition(),
        probe.getCardinal(),
        probe.getPlanet().getId());
  }
}
