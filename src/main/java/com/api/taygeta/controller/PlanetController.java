package com.api.taygeta.controller;

import com.api.taygeta.services.MissionService;
import com.api.taygeta.entities.PlanetEntity;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/planets")
public class PlanetController {

  private final MissionService missionService;

  public PlanetController(MissionService missionService) {
    this.missionService = missionService;
  }




}
