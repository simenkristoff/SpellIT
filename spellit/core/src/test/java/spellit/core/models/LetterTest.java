package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class LetterTest {

  private Letter letter;

  @Test
  @Order(1)
  @DisplayName("Should instantiate a Letter")
  public void constructorTest() {
    letter = new Letter('A', 3);
    assertEquals(letter.getCharacter(), 'A');
    assertEquals(letter.getPoints(), 3);
  }

  @Test
  @Order(2)
  @DisplayName("Should set new Character")
  public void setCharacterTest() {
    letter = new Letter('A', 3);
    letter.setCharacter('B');
    assertEquals(letter.getCharacter(), 'B');
  }

  @Test
  @Order(3)
  @DisplayName("Should set new Points")
  public void setPointsTest() {
    letter = new Letter('A', 3);
    letter.setPoints(8);
    assertEquals(letter.getPoints(), 8);
  }

  @Test
  @Order(4)
  @DisplayName("Should return toString")
  public void toStringTest() {
    letter = new Letter('A', 3);
    String expectedString = "Letter [character=A, points=3]";
    assertEquals(letter.toString(), expectedString);
  }

}
