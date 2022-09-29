package services;

public class MessageService {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_BLUE = "\u001B[94m";
  public static final String ANSI_CYAN = "\u001B[36m";

  public static String blue(String string) {
    StringBuilder sb = new StringBuilder();

    sb.append(ANSI_BLUE);
    sb.append(string);
    sb.append(ANSI_RESET);
    return sb.toString();
  }

  public static void blueMessage(String message) {
    System.out.print(blue(message));
  }

  public String red(String string) {
    StringBuilder sb = new StringBuilder();

    sb.append(ANSI_RED);
    sb.append(string);
    sb.append(ANSI_RESET);
    return sb.toString();
  }

  public String green(String string) {
    StringBuilder sb = new StringBuilder();

    sb.append(ANSI_GREEN);
    sb.append(string);
    sb.append(ANSI_RESET);
    return sb.toString();
  }

  public String cyan(String string) {
    StringBuilder sb = new StringBuilder();

    sb.append(ANSI_CYAN);
    sb.append(string);
    sb.append(ANSI_RESET);
    return sb.toString();
  }

  public void greetings() {
    System.out.println(blue("Welcome to Taygeta! CLI version"));
  }

  public void error(String message) {
    System.out.println(red("Error:\t" + message));
  }

  public void success(String message) {
    System.out.println(green(message));
  }

  public void defaultMessage(String message) {
    System.out.print(cyan(message));
  }

  public void displayHelp() {

    System.out.println(blue("The available commands are:"));
    System.out.println(blue("\tadd-planet: add a planet to the system"));
    System.out.println(blue("\tadd-probe: add a probe to the system"));
    System.out.println(blue("\tmove-probe: move a probe to a new planet"));
    System.out.println(blue("\tlist all: list all planets and probes"));
    System.out.println(blue("\tlist planets: list all planets"));
    System.out.println(blue("\tlist probes: list all probes"));
    System.out.println(blue("\tundo: undo the last command"));
    System.out.println(blue("\texit: exit the program"));
  }

}
