package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import spellit.core.exceptions.TurnException;

@TestMethodOrder(OrderAnnotation.class)
public class BoardTest {

  private static Board board;

  @BeforeAll
  public static void setup() {
    board = new Board(new Game());
  }

  @Test
  @Order(1)
  @DisplayName("Should have instantiated board")
  public void initBoardTest() {
    assertNotNull(board);
  }

  @Test
  @Order(2)
  @DisplayName("Should add and remove tile from the tile map")
  public void setTileTest() {
    Letter letter = new Letter('A', 0);
    board.setTile(7, 7, letter);
    assertEquals(board.getTileMap().getTile(7, 7).getLetter(), letter);
    assertTrue(board.getUnprocessedTiles().size() == 1);

    board.removeTile(7, 7);
    assertNull(board.getTileMap().getTile(7, 7).getLetter());
    assertTrue(board.getUnprocessedTiles().size() == 0);
  }

  @Test
  @Order(3)
  @DisplayName("Should return the tile map")
  public void getTileMapTest() {
    assertNotNull(board.getTileMap());
  }

  @Test
  @Order(4)
  @DisplayName("Should load list of tiles to the map")
  public void loadTilesTest() {
    Tile tile1 = new Tile(7, 7, TileType.STAR);
    tile1.setLetter(new Letter('A', 1));
    Tile tile2 = new Tile(7, 8, TileType.DEFAULT);
    tile2.setLetter(new Letter('B', 4));
    List<Tile> storedTiles = List.of(tile1, tile2);
    board.loadTiles(storedTiles);

    assertEquals(board.getPlacedTiles().size(), storedTiles.size());
    board.removeTile(7, 7);
    board.removeTile(7, 8);
  }

  @Test
  @Order(5)
  @DisplayName("Should return tile type")
  public void getTileType() {
    assertEquals(board.getTileType(7, 7), TileType.STAR);
  }

  @Test
  @Order(6)
  @DisplayName("Should fail on turn processing")
  public void failProcessTurnTest() {
    board.setTile(7, 7, new Letter('D', 1));
    assertThrows(TurnException.class, () -> board.processTurn());
    assertEquals(board.getUnprocessedTiles().size(), 1);
    board.removeTile(7, 7);
  }

  @Test
  @Order(7)
  @DisplayName("Should process turn")
  public void processTurnTest() throws TurnException {
    board.setTile(7, 7, new Letter('D', 1));
    board.setTile(7, 8, new Letter('A', 1));
    board.setTile(7, 9, new Letter('G', 2));
    board.processTurn();
    assertEquals(board.getUnprocessedTiles().size(), 0);
    board.removeTile(7, 7);
    board.removeTile(7, 8);
    board.removeTile(7, 9);
  }
}
