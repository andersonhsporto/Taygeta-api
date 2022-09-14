package com.api.taygeta.repositories;

import com.api.taygeta.entities.PlanetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<PlanetEntity, Long> {

}
