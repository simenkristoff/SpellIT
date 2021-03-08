package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class PlayerTest {

  String name = "Test";
  private static Game game;

  @BeforeAll
  public static void setup() {
    game = new Game();
  }

  @Test
  @Order(1)
  @DisplayName("Should initiate a player instance")
  public void constructorTest() {
    Player player = new Player(game, name);
    assertTrue(player != null);
    assertEquals(player.nameProperty().get(), name);
    assertEquals(player.pointsProperty().get(), 0);
    assertEquals(player.scoreProperty().get(), name + ": " + 0);
    assertEquals(player.getLetters().size(), 0);
  }

  @Test
  @Order(2)
  @DisplayName("Should initiate a player instance from JSON-object")
  public void jsonConstructorTest() {
    List<Letter> letters = List.of(new Letter('E', 0), new Letter('A', 0));
    Player player = new Player(name, letters);
    assertTrue(player != null);
    assertEquals(player.nameProperty().get(), name);
    assertEquals(player.pointsProperty().get(), 0);
    assertEquals(player.getLetters().size(), letters.size());
  }

  @Test
  @Order(3)
  @DisplayName("Should draw initial letters")
  public void initialLettersTest() {
    Player player = new Player(game, name);
    assertEquals(player.getLetters().size(), 0);
    player.drawInitialLetters();
    assertEquals(player.getLetters().size(), 7);
  }

  @Test
  @Order(4)
  @DisplayName("Should draw random letter")
  public void drawLettersTest() {
    Player player = new Player(game, name);
    List<Letter> letters = List.of(new Letter('E', 0), new Letter('A', 0), new Letter('E', 0),
        new Letter('A', 0), new Letter('E', 0), new Letter('A', 0));
    player.setLetters(new ArrayList<Letter>(letters));
    assertEquals(player.getLetters().size(), letters.size());
    player.drawLetter(letters.size() - 1); // test branch
    player.drawLetter(letters.size());
    assertEquals(player.getLetters().size(), letters.size() + 1);
  }

  @Test
  @Order(5)
  @DisplayName("Should throw IndexOutOfBoundsException on DrawLetter")
  public void drawLettersExceptionTest() {
    Player player = new Player(game, name);
    player.drawInitialLetters();
    assertThrows(IndexOutOfBoundsException.class, () -> player.drawLetter(7));
  }

  @Test
  @Order(6)
  @DisplayName("Should swap letters")
  public void swapLettersTest() {
    Player player = new Player(game, name);
    List<Letter> letters = List.of(new Letter('E', 0), new Letter('A', 0), new Letter('E', 0),
        new Letter('A', 0), new Letter('E', 0), new Letter('A', 0), new Letter('Q', 0));
    player.setLetters(new ArrayList<Letter>(letters));
    player.swapLetters();
    assertNotEquals(letters, player.getLetters());
  }

  @Test
  @Order(7)
  @DisplayName("Should return isPlaying false")
  public void isPlayingTest() {
    Player player = new Player(game, name);
    assertTrue(!player.isPlaying());
  }

  @Test
  @Order(8)
  @DisplayName("Should set and get Points")
  public void pointsTest() {
    Player player = new Player(game, name);
    player.setPoints(4);
    assertEquals(player.getPoints(), 4);
    player.addPoints(1);
    assertEquals(player.getPoints(), 5);
  }

  @Test
  @Order(9)
  @DisplayName("Should set and get Name")
  public void nameTest() {
    Player player = new Player(game, name);
    player.setName("Test2");
    assertEquals(player.getName(), "Test2");
  }

  @Test
  @Order(10)
  @DisplayName("Should return toString")
  public void toStringTest() {
    Player player = new Player(game, name);
    String expectedString = "Player [name=" + name + ", playing=" + false + ", points=0]";
    assertEquals(player.toString(), expectedString);
  }
}
