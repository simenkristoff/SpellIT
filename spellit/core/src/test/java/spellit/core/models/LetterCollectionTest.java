package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class LetterCollectionTest {

  private static LetterCollection letterCollection;

  @BeforeAll
  public static void setup() {
    letterCollection = new LetterCollection();
  }

  @Test
  @Order(1)
  @DisplayName("Should contain letters")
  public void initLetterCollectionTest() {
    assertTrue(letterCollection.size() > 0);
    assertTrue(!letterCollection.isEmpty());
  }

  @Test
  @Order(2)
  @DisplayName("Should draw initial letters")
  public void drawInitialLettersTest() {
    int size = letterCollection.size();
    ArrayList<Letter> letters = letterCollection.drawInitialLetters();
    assertEquals(letters.size(), 7);
    assertEquals(letterCollection.size(), size - 7);
  }

  @Test
  @Order(3)
  @DisplayName("Should return collection size as string")
  public void textPropertyTest() {
    int size = letterCollection.size();
    assertEquals(letterCollection.textProperty().get(), String.valueOf(size));
  }

  @Test
  @Order(4)
  @DisplayName("Should draw random letter")
  public void drawRandomLetterTest() {
    int size = letterCollection.size();
    Letter letter = letterCollection.drawRandomLetter();
    assertTrue(letter != null);
    assertEquals(letterCollection.size(), size - 1);
  }

  @Test
  @Order(5)
  @DisplayName("Should put back letter")
  public void putLetterTest() {
    Letter letter = letterCollection.drawRandomLetter();
    assertTrue(letter != null);
    int size = letterCollection.size();
    letterCollection.put(letter);
    assertEquals(letterCollection.size(), size + 1);
  }

  @Test
  @Order(6)
  @DisplayName("Should remove one letter")
  public void removeLetterTest() {
    Letter letter = new Letter('E', 1);
    int size = letterCollection.size();
    letterCollection.removeLetter(letter);
    assertEquals(letterCollection.size(), size - 1);
  }

  @Test
  @Order(7)
  @DisplayName("Should remove list of letters")
  public void removeLetterListTest() {
    List<Letter> letters = List.of(new Letter('A', 1), new Letter('E', 1));
    int size = letterCollection.size();
    letterCollection.removeLetters(new ArrayList<Letter>(letters));
    assertEquals(letterCollection.size(), size - letters.size());
  }

  @Test
  @Order(8)
  @DisplayName("Should return null when collection is empty")
  public void returnNullTest() {
    int size = letterCollection.size();
    for (int i = 0; i < size; i++) {
      letterCollection.drawRandomLetter();
    }
    assertTrue(letterCollection.isEmpty());
    assertEquals(letterCollection.drawRandomLetter(), null);
  }

}
