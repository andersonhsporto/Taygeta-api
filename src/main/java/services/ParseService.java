package services;

import enums.Cardinal;
import exceptions.UndoCommandException;
import java.util.Optional;
import java.util.Scanner;
import models.Probe;

public class ParseService {

  private final MessageService messageService;

  public ParseService() {
    this.messageService = new MessageService();
  }

  public static int id(String string) {
    try {
      return Integer.parseInt(string);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public static Cardinal cardinal(String command) {
    String lowerCaseCommand = command.toLowerCase();

    return switch (lowerCaseCommand) {
      case "north", "norte", "n" -> Cardinal.NORTH;
      case "south", "sul", "s" -> Cardinal.SOUTH;
      case "east", "leste", "e", "l" -> Cardinal.EAST;
      case "west", "oeste", "w", "o" -> Cardinal.WEST;
      default -> throw new UndoCommandException("Invalid direction");
    };
  }

  public Probe probe() throws UndoCommandException {
    var x = probeParameter("x coordinate");
    var y = probeParameter("y coordinate");
    var direction = probeDirection();

    return Probe.createDefault(x, y, direction);
  }

  public Cardinal probeDirection() throws UndoCommandException {
    Scanner scanner = new Scanner(System.in);
    String command;

    messageService.defaultMessage("Enter probe initial direction: > ");
    command = scanner.nextLine();
    if (ValidationService.isValidCardinal(command)) {
      return cardinal(command);
    } else if (command.equals("undo")) {
      throw new UndoCommandException("Undo command add-probe");
    } else {
      messageService.error("Invalid direction");
      return probeDirection();
    }
  }

  private int probeParameter(String message) throws UndoCommandException {
    Scanner scanner = new Scanner(System.in);
    String command;
    int value;

    messageService.defaultMessage("Enter probe " + message + ": > ");
    command = scanner.next();
    value = id(command);
    if (command.equals("undo")) {
      throw new UndoCommandException("Undo command " + message);
    }
    if (value != -1) {
      return value;
    } else {
      messageService.error("invalid " + message);
      return probeParameter(message);
    }
  }

  public Optional<Integer> planetID(
      MissionControlService missionControlService) throws UndoCommandException {

    Scanner scanner = new Scanner(System.in);
    String command;

    messageService.defaultMessage("Enter planet id number: > ");
    command = scanner.next();
    if ("undo".equals(command)) {
      throw new UndoCommandException("Undo command add-probe");
    }
    if (ValidationService.planetIsValid(command, missionControlService)) {
      return Optional.of(Integer.parseInt(command));
    } else {
      messageService.error("Invalid planet id");
      return Optional.empty();
    }
  }

  public String parseSequenceCommands() throws UndoCommandException {
    Scanner scanner = new Scanner(System.in);
    String command;

    messageService.defaultMessage("Enter sequence of commands: > ");
    command = scanner.next();
    if ("undo".equals(command)) {
      throw new UndoCommandException("Undo command add-probe");
    }
    if (ValidationService.isValidSequence(command)) {
      return command;
    } else {
      messageService.error("Invalid movement sequence");
      return parseSequenceCommands();
    }
  }

  public int parseProbeId(
      Integer planetId, MissionControlService missionControlService) throws UndoCommandException {

    int probeId = probeParameter("id");

    if (ValidationService.probeExists(planetId, probeId, missionControlService)) {
      return probeId;
    } else {
      messageService.error("Invalid probe id");
      throw new UndoCommandException("Undo command move-probe");
    }
  }

}
