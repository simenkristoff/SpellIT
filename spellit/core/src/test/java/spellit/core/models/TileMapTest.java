package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class TileMapTest {

  private static TileMap tileMap;

  @BeforeAll
  public static void setup() {
    tileMap = new TileMap();
  }

  @Test
  @Order(1)
  @DisplayName("Should return tile by row and column")
  public void getTileByRowColTest() {
    Tile tile = tileMap.getTile(7, 7);
    assertNotNull(tile);
  }

  @Test
  @Order(2)
  @DisplayName("Should return tile by Tile")
  public void getTileByTileTest() {
    Tile tile = tileMap.getTile(7, 7);
    assertEquals(tileMap.getTile(tile), tile);
  }

  @Test
  @Order(3)
  @DisplayName("Should set Tile's letter and processed property")
  public void setTileTest() {
    Tile newTile = new Tile(7, 7, TileType.STAR);
    newTile.setLetter(new Letter('A', 0));
    newTile.setProcessed(true);
    tileMap.setTile(newTile);

    Tile tile = tileMap.getTile(7, 7);
    assertEquals(tile.getLetter().getCharacter(), 'A');
    assertTrue(tile.isProcessed());
  }

  @Test
  @Order(4)
  @DisplayName("Should return a tile's bonus")
  public void getTileBonusTest() {
    Tile tile = tileMap.getTile(7, 7);
    assertEquals(tileMap.getBonus(tile), TileType.STAR);
  }

  @Test
  @Order(5)
  @DisplayName("Should check and return left tile-neighbour")
  public void leftNeighbourTest() {
    // Origin
    Tile origin = new Tile(7, 7, TileType.STAR);
    origin.setLetter(new Letter('A', 0));
    tileMap.setTile(origin);
    assertFalse(tileMap.hasLeft(origin));

    // Neighbour tile
    Tile neighbour = new Tile(7, 6, TileType.DEFAULT);
    neighbour.setLetter(new Letter('B', 0));
    tileMap.setTile(neighbour);
    assertTrue(tileMap.hasLeft(origin));
    assertNotNull(tileMap.getLeft(origin));
  }

  @Test
  @Order(6)
  @DisplayName("Should check and return right tile-neighbour")
  public void rightNeighbourTest() {
    // Origin
    Tile origin = new Tile(7, 7, TileType.STAR);
    origin.setLetter(new Letter('A', 0));
    tileMap.setTile(origin);
    assertFalse(tileMap.hasRight(origin));

    // Neighbour tile
    Tile neighbour = new Tile(7, 8, TileType.DEFAULT);
    neighbour.setLetter(new Letter('B', 0));
    tileMap.setTile(neighbour);
    assertTrue(tileMap.hasRight(origin));
    assertNotNull(tileMap.getRight(origin));
  }

  @Test
  @Order(7)
  @DisplayName("Should check and return the tile-neighbour above")
  public void aboveNeighbourTest() {
    // Origin
    Tile origin = new Tile(7, 7, TileType.STAR);
    origin.setLetter(new Letter('A', 0));
    tileMap.setTile(origin);
    assertFalse(tileMap.hasUp(origin));

    // Neighbour tile
    Tile neighbour = new Tile(6, 7, TileType.DEFAULT);
    neighbour.setLetter(new Letter('B', 0));
    tileMap.setTile(neighbour);
    assertTrue(tileMap.hasUp(origin));
    assertNotNull(tileMap.getUp(origin));
  }

  @Test
  @Order(9)
  @DisplayName("Should check and return the tile-neighbour below")
  public void belowNeighbourTest() {
    // Origin
    Tile origin = new Tile(7, 7, TileType.STAR);
    origin.setLetter(new Letter('A', 0));
    tileMap.setTile(origin);
    assertFalse(tileMap.hasDown(origin));

    // Neighbour tile
    Tile neighbour = new Tile(8, 7, TileType.DEFAULT);
    neighbour.setLetter(new Letter('B', 0));
    tileMap.setTile(neighbour);
    assertTrue(tileMap.hasDown(origin));
    assertNotNull(tileMap.getDown(origin));
  }

  @Test
  @Order(10)
  @DisplayName("Should return false for neighbouring edges")
  public void neighbouringEdgesTest() {
    // Left edge
    Tile leftOrigin = new Tile(7, 0, TileType.DEFAULT);
    assertFalse(tileMap.hasLeft(leftOrigin));

    // Right edge
    Tile rightOrigin = new Tile(7, 14, TileType.DEFAULT);
    assertFalse(tileMap.hasRight(rightOrigin));

    // Top edge
    Tile topOrigin = new Tile(0, 7, TileType.DEFAULT);
    assertFalse(tileMap.hasUp(topOrigin));

    // Bottom edge
    Tile bottomOrigin = new Tile(14, 7, TileType.DEFAULT);
    assertFalse(tileMap.hasDown(bottomOrigin));
  }

}
