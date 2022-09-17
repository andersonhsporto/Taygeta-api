package com.api.taygeta.controller;

import com.api.taygeta.services.ProbeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/probes")
public class ProbeController {

  private final ProbeService probeService;

  public ProbeController(ProbeService probeService) {
    this.probeService = probeService;
  }

  @GetMapping
  public ResponseEntity<Object> getAllProbes() {
    return probeService.getAllProbes();
  }

  @GetMapping("/{probeId}")
  public ResponseEntity<Object> getByProbeId(@PathVariable Long probeId) {
    return probeService.getProbeById(probeId);
  }

  @PostMapping
  public ResponseEntity<Object> makeProbe(
      @RequestParam Long planetId,
      @RequestParam Integer x,
      @RequestParam Integer y,
      @RequestParam String direction) {

    return probeService.makeProbe(planetId, x, y, direction);
  }

  @PutMapping
  public ResponseEntity<?> moveProbe(@RequestParam Long probeId, @RequestParam String movements) {
    return probeService.moveProbe(probeId, movements);
  }

}
