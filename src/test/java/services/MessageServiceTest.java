package services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.MessageService;

class MessageServiceTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final MessageService messageService = new MessageService();

  // Reassigns standard output stream to a new stream with ByteArrayOutputStream
  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  // Restore initial standard output stream
  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  @DisplayName("red() should return red string")
  void printRedString() {
    String string = "red";

    assertEquals("\u001B[31mred\u001B[0m", messageService.red(string));
  }

  @Test
  @DisplayName("green() should return green string")
  void printGreenString() {
    String string = "green";

    assertEquals("\u001B[32mgreen\u001B[0m", messageService.green(string));
  }

  @Test
  @DisplayName("red() should not return green string")
  void redNotGreen() {
    String string = "red";

    assertNotEquals("\u001B[32mgreen\u001B[0m", messageService.red(string));
  }

  @Test
  @DisplayName("green() should not return red string")
  void greenNotRed() {
    String string = "green";

    assertNotEquals("\u001B[31mred\u001B[0m", messageService.green(string));
  }

  @Test
  @DisplayName("cyan() should return cyan string")
  void cyan() {
    String string = "cyan";

    assertEquals("\u001B[36mcyan\u001B[0m", messageService.cyan(string));
  }

  @Test
  @DisplayName("cyan() should not return red string")
  void cyanNotRed() {
    String string = "cyan";

    assertNotEquals("\u001B[31mred\u001B[0m", messageService.cyan(string));
  }

  @Test
  @DisplayName("blue() should not return red string")
  void blueNotRed() {
    String string = "blue";

    assertNotEquals("\u001B[31mred\u001B[0m", MessageService.blue(string));
  }

  @Test
  @DisplayName("Test greetings message")
  void testGreetingMessageIsCyanWelcomeToTaygeta() {
    MessageService messageService = new MessageService();
    String defaultCyanGreetings = "[94mWelcome to Taygeta! CLI version\u001B[0m";

    messageService.greetings();
    assertEquals(defaultCyanGreetings, outputStreamCaptor.toString().trim());
  }

  @Test
  @DisplayName("Test Red error message")
  void TestIfErrorIsRedMessage() {
    MessageService messageService = new MessageService();
    String defaultRedError = "[31mError:\tInvalid planet area\u001B[0m";

    messageService.error("Invalid planet area");
    assertEquals(defaultRedError, outputStreamCaptor.toString().trim());
  }

  @Test
  @DisplayName("Test Green success message")
  void success() {
    MessageService messageService = new MessageService();
    String defaultGreenSuccess = "[32mProbe successfully added\u001B[0m";

    messageService.success("Probe successfully added");
    assertEquals(defaultGreenSuccess, outputStreamCaptor.toString().trim());
  }

  @Test
  @DisplayName("Test Cyan default message")
  void defaultMessage() {
    MessageService messageService = new MessageService();
    String defaultCyanMessage = "[36mEnter planet area width and height: (example: 5x5) > \u001B[0m";

    messageService.defaultMessage("Enter planet area width and height: (example: 5x5) > ");
    assertEquals(defaultCyanMessage, outputStreamCaptor.toString().trim());
  }
}