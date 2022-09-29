package models;

import java.util.HashMap;
import java.util.Map;
import services.MessageService;

public class Planet {

  private final int id;
  private final int width;
  private final int height;
  private final Map<Integer, Probe> probes;
  private boolean full;

  public Planet(int id, int width, int height) {
    this.id = id;
    this.width = width;
    this.height = height;
    this.full = false;
    this.probes = new HashMap<Integer, Probe>();
  }

  public static Planet createDefault(int id, String command) {
    var commandArray = command.split("x");
    var width = Integer.parseInt(commandArray[0]);
    var height = Integer.parseInt(commandArray[1]);

    return new Planet(id, width, height);
  }

  public Long getArea() {
    return (long) width * height;
  }

  public void printProbes() {
    for (int i = 0; i < probes.size(); i++) {
      MessageService.blueMessage("Planet id: " + id + ", ");
      MessageService.blueMessage("Probe id: " + i + " = ");
      System.out.println(probes.get(i).toString());
    }
  }

  public void addProbe(Probe probe) {
    probes.put(probes.size(), probe);
    if (probes.size() == getArea()) {
      full = true;
    }
  }

  public Integer getId() {
    return id;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Map<Integer, Probe> getProbes() {
    return probes;
  }

  public Probe getProbeById(int id) {
    return probes.get(id);
  }

  public int getProbesCount() {
    return probes.size();
  }

  public boolean isFull() {
    return full;
  }

  public void putProbe(Integer id, Probe probe) {
    this.probes.put(id, probe);
  }

  @Override
  public String toString() {
    return MessageService.blue(
        " Planet id: " + id +
            " [ Width = " + width +
            ", Height = " + height +
            ", Probes = " + probes.size() +
            "]");
  }
}
