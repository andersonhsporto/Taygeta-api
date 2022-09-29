package command;

import exceptions.UndoCommandException;
import java.util.Scanner;
import services.MessageService;
import services.MissionControlService;
import services.ParseService;
import services.ValidationService;

public class Console {

  private final MessageService messageService;
  private final ParseService parseService;

  public Console() {
    this.messageService = new MessageService();
    this.parseService = new ParseService();
  }

  public void init() {
    var scanner = new Scanner(System.in);
    var missionControl = new MissionControlService();

    messageService.greetings();
    while (true) {
      messageService.defaultMessage("Enter command: > ");
      try {
        receiveCommand(scanner.nextLine(), missionControl);
      } catch (UndoCommandException ignored) {
      }
    }
  }

  private void receiveCommand(
      String command, MissionControlService missionControlService) throws UndoCommandException {

    String firstWord = getFirstWord(command);

    switch (firstWord) {
      case "add-planet" -> makePlanet(missionControlService);
      case "add-probe", "move-probe" -> planetExists(command, missionControlService);
      case "list" -> list(command, missionControlService);
      case "exit" -> System.exit(0);
      case "help", "?" -> messageService.displayHelp();
      default -> messageService.error("Invalid command (type help or ? to see the commands)");
    }
  }

  private String getFirstWord(String command) {
    int index = command.indexOf(" ");

    if (index == -1) {
      return command;
    } else {
      return command.substring(0, index).trim();
    }
  }

  private void makePlanet(
      MissionControlService missionControlService) throws UndoCommandException {

    messageService.defaultMessage("Enter planet area width and height: (example: 5x5) > ");
    Scanner scanner = new Scanner(System.in);
    String command = scanner.next();

    if (ValidationService.commandInPlanetSizeFormat(command)) {
      missionControlService.addPlanet(command);
    } else if (command.equals("undo")) {
      throw new UndoCommandException("Undo command add-planet");
    } else {
      messageService.error("Invalid planet area");
      makePlanet(missionControlService);
    }
  }

  private void makeProbe(MissionControlService missionControlService) throws UndoCommandException {
    var command = parseService.planetID(missionControlService);

    command.ifPresent(integer -> addProbeToPlanet(integer, missionControlService));
  }

  private void list(String command, MissionControlService missionControlService) {
    String[] commandArray;

    if (validateListCommand(command)) {
      commandArray = command.split(" ");

      switch (commandArray[1]) {
        case "planets", "planet" -> missionControlService.listPlanets();
        case "probes", "probe" -> missionControlService.listProbes();
        case "all", "total" -> missionControlService.listAll();
      }
    } else {
      messageService.error("Invalid command (type help or ? to see the commands)");
    }
  }

  private boolean validateListCommand(String command) {
    String[] commandArray = command.split(" ");

    if (commandArray.length != 2) {
      return false;
    }
    return switch (commandArray[1]) {
      case "planets", "planet", "probes", "probe", "all", "total" -> true;
      default -> false;
    };
  }

  private void planetExists(
      String command, MissionControlService missionControlService) throws UndoCommandException {

    if (ValidationService.planetExists(missionControlService)) {
      switch (command) {
        case "add-probe" -> makeProbe(missionControlService);
        case "move-probe" -> moveProbe(missionControlService);
        default -> messageService.error("Invalid command");
      }
    } else {
      messageService.error("There is no planets in the system");
    }
  }

  public void moveProbe(MissionControlService missionControlService) throws UndoCommandException {
    var planetId = parseService.planetID(missionControlService);

    if (planetId.isPresent()) {
      int probeId = parseService.parseProbeId(planetId.get(), missionControlService);
      String sequenceCommands = parseService.parseSequenceCommands();

      missionControlService.moveProbe(planetId.get(), probeId, sequenceCommands);
    }
  }

  private void addProbeToPlanet(
      Integer planetId, MissionControlService missionControlService) throws UndoCommandException {

    var probe = parseService.probe();

    missionControlService.addProbeToPlanet(probe, planetId);
  }

}
