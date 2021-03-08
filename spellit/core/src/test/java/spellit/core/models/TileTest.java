package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class TileTest {

  @Test
  @Order(1)
  @DisplayName("Should instantiate a Tile with constructor 1")
  public void constructor1Test() {
    Tile tile = new Tile(1, 1, TileType.DEFAULT);
    assertNotNull(tile);
  }

  @Test
  @Order(2)
  @DisplayName("Should instantiate a Tile with constructor 2")
  public void constructor2Test() {
    Tile tile = new Tile(1, 1, new Letter('A', 1));
    assertNotNull(tile);
  }

  @Test
  @Order(3)
  @DisplayName("Should return true if Tile has a letter")
  public void hasLetterTest() {
    Tile tile1 = new Tile(1, 1, TileType.DEFAULT);
    Tile tile2 = new Tile(1, 1, new Letter('A', 1));
    assertFalse(tile1.hasLetter());
    assertTrue(tile2.hasLetter());
  }

  @Test
  @Order(4)
  @DisplayName("Should get the Tile's letter")
  public void getLetterTest() {
    Letter letter = new Letter('A', 1);
    Tile tile = new Tile(1, 1, letter);
    assertEquals(tile.getLetter(), letter);
  }

  @Test
  @Order(5)
  @DisplayName("Should set the Tile's letter property")
  public void setLetterTest() {
    Letter letter = new Letter('A', 1);
    Tile tile = new Tile(1, 1, TileType.DEFAULT);
    assertFalse(tile.hasLetter());
    tile.setLetter(letter);
    assertEquals(tile.getLetter(), letter);
  }

  @Test
  @Order(6)
  @DisplayName("Should remove the Tile's letter property value")
  public void removeLetterTest() {
    Letter letter = new Letter('A', 1);
    Tile tile = new Tile(1, 1, letter);
    tile.removeLetter();
    assertFalse(tile.hasLetter());
  }

  @Test
  @Order(7)
  @DisplayName("Should get and set the Tile's processed property")
  public void processedTileTest() {
    Tile tile = new Tile(1, 1, new Letter('A', 1));
    assertFalse(tile.isProcessed());
    tile.setProcessed(true);
    assertTrue(tile.isProcessed());
  }

  @Test
  @Order(8)
  @DisplayName("Should get and set the Tile's indexes")
  public void tileIndexTest() {
    Tile tile = new Tile(4, 9, new Letter('A', 1));
    assertEquals(tile.getRow(), 4);
    assertEquals(tile.getCol(), 9);
  }

  @Test
  @Order(9)
  @DisplayName("Should return the Tile's tile type")
  public void tileTypeTest() {
    Tile tile = new Tile(1, 1, TileType.DEFAULT);
    assertEquals(tile.getTileType(), TileType.DEFAULT);
  }

  @Test
  @Order(10)
  @DisplayName("Should return toString")
  public void toStringTest() {
    Letter letter = new Letter('A', 1);
    Tile tile = new Tile(1, 1, TileType.DEFAULT);
    tile.setLetter(letter);
    String expectedString = "Tile [row=1, col=1, tileType=" + TileType.DEFAULT + ", letter="
        + letter + ", isProcessed=false]";
    assertEquals(tile.toString(), expectedString);
  }

}
