package com.api.taygeta.repositories;

import com.api.taygeta.entities.ProbeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<ProbeEntity, Long> {

}
