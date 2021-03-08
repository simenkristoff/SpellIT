package spellit.core.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import spellit.core.exceptions.TurnException;
import spellit.core.models.Dictionary;
import spellit.core.models.Game;
import spellit.core.models.Letter;
import spellit.core.models.Tile;
import spellit.core.models.TileMap;

@TestMethodOrder(OrderAnnotation.class)
public class BoardParserTest {

  private static Game game;
  private static TileMap tileMap;
  private static Dictionary dictionary;
  private static ArrayList<Tile> placedTiles;
  private static BoardParser parser;

  /**
   * Setup dependencies.
   */
  @BeforeAll
  public static void setup() {
    game = new Game();
    tileMap = new TileMap();
    dictionary = new Dictionary();
    placedTiles = new ArrayList<Tile>();
    parser = new BoardParser(game, tileMap, dictionary, placedTiles);
  }

  @Test
  @Order(1)
  @DisplayName("Should throw exception when unprocessed tiles are empty")
  public void emptyUnprocessedTilesTest() {
    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>();
    assertThrows(TurnException.class, () -> parser.parseBoard(unprocessedTiles));
  }

  @Test
  @Order(2)
  @DisplayName("Should throw exception on wrong initial letter placement")
  public void initialTurnPlacementExceptionTest() {
    Tile tileD = new Tile(8, 6, new Letter('D', 1));
    Tile tileA = new Tile(8, 7, new Letter('A', 1));
    Tile tileG = new Tile(8, 8, new Letter('G', 2));

    tileMap.setTile(tileD);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(List.of(tileD, tileA, tileG));
    assertThrows(TurnException.class, () -> parser.parseBoard(unprocessedTiles));

    tileMap.removeTile(tileD);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);
  }

  @Test
  @Order(3)
  @DisplayName("Should throw exception if any letter is placed alone")
  public void singleLetterPlacementTest() {
    Tile tileA = new Tile(7, 7, new Letter('A', 1)); // Center

    tileMap.setTile(tileA);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(List.of(tileA));
    assertThrows(TurnException.class, () -> parser.parseBoard(unprocessedTiles));
  }

  @Test
  @Order(4)
  @DisplayName("Should throw exception if letters are placed in both directions")
  public void adsTest() throws TurnException {
    Tile tileD = new Tile(7, 6, new Letter('D', 1));
    Tile tileA = new Tile(7, 7, new Letter('A', 1));
    Tile tileG = new Tile(7, 8, new Letter('G', 2));
    Tile tileR = new Tile(8, 6, new Letter('R', 1));
    Tile tileA2 = new Tile(9, 6, new Letter('A', 1));
    Tile tileG2 = new Tile(10, 6, new Letter('G', 2));
    Tile tileE = new Tile(11, 6, new Letter('E', 1));

    tileMap.setTile(tileD);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);
    tileMap.setTile(tileR);
    tileMap.setTile(tileA2);
    tileMap.setTile(tileG2);
    tileMap.setTile(tileE);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(
        List.of(tileD, tileA, tileG, tileR, tileA2, tileG2, tileE));
    assertThrows(TurnException.class, () -> parser.parseBoard(unprocessedTiles));

    tileMap.removeTile(tileD);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);
    tileMap.removeTile(tileR);
    tileMap.removeTile(tileA2);
    tileMap.removeTile(tileG2);
    tileMap.removeTile(tileE);

  }

  @Test
  @Order(5)
  @DisplayName("Should throw exception on invalid word lookup")
  public void invalidWordLookupTest() {
    Tile tileD = new Tile(7, 6, new Letter('D', 1));
    Tile tileG = new Tile(7, 7, new Letter('G', 2));
    Tile tileD2 = new Tile(7, 8, new Letter('D', 1));

    tileMap.setTile(tileD);
    tileMap.setTile(tileG);
    tileMap.setTile(tileD2);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(List.of(tileD, tileG, tileD2));
    assertThrows(TurnException.class, () -> parser.parseBoard(unprocessedTiles));

    tileMap.removeTile(tileD);
    tileMap.removeTile(tileG);
    tileMap.removeTile(tileD2);

  }

  @Test
  @Order(6)
  @DisplayName("Should throw exception on invalid subword lookup")
  public void invalidSubwordLookupTest() {
    Tile tileD = new Tile(7, 6, new Letter('D', 1));
    tileD.setProcessed(true);
    Tile tileA = new Tile(7, 7, new Letter('A', 1));
    tileA.setProcessed(true);
    Tile tileG = new Tile(7, 8, new Letter('G', 2));
    tileG.setProcessed(true);
    Tile tileD2 = new Tile(8, 6, new Letter('D', 1));
    Tile tileA2 = new Tile(8, 7, new Letter('A', 1));
    Tile tileG2 = new Tile(8, 8, new Letter('G', 2));

    game.setTurnCount(1);
    
    tileMap.setTile(tileD);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);
    tileMap.setTile(tileD2);
    tileMap.setTile(tileA2);
    tileMap.setTile(tileG2);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(List.of(tileG2, tileA2, tileD2));
    assertThrows(TurnException.class, () -> parser.parseBoard(unprocessedTiles));

    tileMap.removeTile(tileD);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);
    tileMap.removeTile(tileD2);
    tileMap.removeTile(tileA2);
    tileMap.removeTile(tileG2);

  }

  @Test
  @Order(7)
  @DisplayName("Should successfully calculate score in horizontal dir")
  public void validHorizontalWordTest() throws TurnException {
    Tile tileD = new Tile(7, 6, new Letter('D', 1));
    Tile tileA = new Tile(7, 7, new Letter('A', 1));
    Tile tileG = new Tile(7, 8, new Letter('G', 2));

    tileMap.setTile(tileD);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(List.of(tileG, tileA, tileD));
    parser.parseBoard(unprocessedTiles);

    tileMap.removeTile(tileD);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);

  }

  @Test
  @Order(8)
  @DisplayName("Should successfully calculate score in vertical dir")
  public void validVerticalWordTest() throws TurnException {
    Tile tileD = new Tile(6, 7, new Letter('D', 1));
    Tile tileA = new Tile(7, 7, new Letter('A', 1));
    Tile tileG = new Tile(8, 7, new Letter('G', 2));

    tileMap.setTile(tileD);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(List.of(tileG, tileA, tileD));
    parser.parseBoard(unprocessedTiles);

    tileMap.removeTile(tileD);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);

  }

  @Test
  @Order(9)
  @DisplayName("Should successfully word with subword downwards")
  public void validWordWithSubwordDownTest() throws TurnException {
    Tile tileL = new Tile(7, 7, new Letter('L', 1));
    tileL.setProcessed(true);
    Tile tileA = new Tile(8, 7, new Letter('A', 1));
    tileA.setProcessed(true);
    Tile tileG = new Tile(9, 7, new Letter('G', 2));
    tileG.setProcessed(true);

    Tile tileS = new Tile(6, 7, new Letter('S', 1));
    Tile tileY = new Tile(6, 8, new Letter('Y', 6));
    Tile tileN = new Tile(6, 9, new Letter('N', 1));
    Tile tileO = new Tile(6, 10, new Letter('O', 2));
    Tile tileN2 = new Tile(6, 11, new Letter('N', 1));
    Tile tileY2 = new Tile(6, 12, new Letter('Y', 6));
    Tile tileM = new Tile(6, 13, new Letter('M', 2));

    tileMap.setTile(tileL);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);
    tileMap.setTile(tileS);
    tileMap.setTile(tileY);
    tileMap.setTile(tileN);
    tileMap.setTile(tileO);
    tileMap.setTile(tileN2);
    tileMap.setTile(tileY2);
    tileMap.setTile(tileM);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(
        List.of(tileM, tileY2, tileN2, tileO, tileN, tileY, tileS));
    parser.parseBoard(unprocessedTiles);

    tileMap.removeTile(tileL);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);
    tileMap.removeTile(tileS);
    tileMap.removeTile(tileY);
    tileMap.removeTile(tileN);
    tileMap.removeTile(tileO);
    tileMap.removeTile(tileN2);
    tileMap.removeTile(tileY2);
    tileMap.removeTile(tileM);

  }

  @Test
  @Order(10)
  @DisplayName("Should successfully word with subword upwards")
  public void validWordWithSubwordUpTest() throws TurnException {
    Tile tileG = new Tile(7, 7, new Letter('G', 1));
    tileG.setProcessed(true);
    Tile tileR = new Tile(8, 7, new Letter('R', 1));
    tileR.setProcessed(true);
    Tile tileU = new Tile(9, 7, new Letter('U', 2));
    tileU.setProcessed(true);

    Tile tileS = new Tile(10, 7, new Letter('S', 1));
    Tile tileY = new Tile(10, 8, new Letter('Y', 6));
    Tile tileN = new Tile(10, 9, new Letter('N', 1));
    Tile tileO = new Tile(10, 10, new Letter('O', 2));
    Tile tileN2 = new Tile(10, 11, new Letter('N', 1));
    Tile tileY2 = new Tile(10, 12, new Letter('Y', 6));
    Tile tileM = new Tile(10, 13, new Letter('M', 2));

    tileMap.setTile(tileG);
    tileMap.setTile(tileR);
    tileMap.setTile(tileU);
    tileMap.setTile(tileS);
    tileMap.setTile(tileY);
    tileMap.setTile(tileN);
    tileMap.setTile(tileO);
    tileMap.setTile(tileN2);
    tileMap.setTile(tileY2);
    tileMap.setTile(tileM);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(
        List.of(tileM, tileY2, tileN2, tileO, tileN, tileY, tileS));
    parser.parseBoard(unprocessedTiles);

    tileMap.removeTile(tileG);
    tileMap.removeTile(tileR);
    tileMap.removeTile(tileU);
    tileMap.removeTile(tileS);
    tileMap.removeTile(tileY);
    tileMap.removeTile(tileN);
    tileMap.removeTile(tileO);
    tileMap.removeTile(tileN2);
    tileMap.removeTile(tileY2);
    tileMap.removeTile(tileM);

  }

  @Test
  @Order(11)
  @DisplayName("Should successfully word with subword to the right")
  public void validWordWithSubwordRightTest() throws TurnException {
    Tile tileL = new Tile(7, 7, new Letter('L', 1));
    tileL.setProcessed(true);
    Tile tileA = new Tile(7, 8, new Letter('A', 1));
    tileA.setProcessed(true);
    Tile tileG = new Tile(7, 9, new Letter('G', 2));
    tileG.setProcessed(true);

    Tile tileS = new Tile(7, 6, new Letter('S', 1));
    Tile tileY = new Tile(8, 6, new Letter('Y', 6));
    Tile tileN = new Tile(9, 6, new Letter('N', 1));
    Tile tileO = new Tile(10, 6, new Letter('O', 2));
    Tile tileN2 = new Tile(11, 6, new Letter('N', 1));
    Tile tileY2 = new Tile(12, 6, new Letter('Y', 6));
    Tile tileM = new Tile(13, 6, new Letter('M', 2));

    tileMap.setTile(tileL);
    tileMap.setTile(tileA);
    tileMap.setTile(tileG);
    tileMap.setTile(tileS);
    tileMap.setTile(tileY);
    tileMap.setTile(tileN);
    tileMap.setTile(tileO);
    tileMap.setTile(tileN2);
    tileMap.setTile(tileY2);
    tileMap.setTile(tileM);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(
        List.of(tileM, tileY2, tileN2, tileO, tileN, tileY, tileS));
    parser.parseBoard(unprocessedTiles);

    tileMap.removeTile(tileL);
    tileMap.removeTile(tileA);
    tileMap.removeTile(tileG);
    tileMap.removeTile(tileS);
    tileMap.removeTile(tileY);
    tileMap.removeTile(tileN);
    tileMap.removeTile(tileO);
    tileMap.removeTile(tileN2);
    tileMap.removeTile(tileY2);
    tileMap.removeTile(tileM);

  }

  @Test
  @Order(12)
  @DisplayName("Should successfully word with subword to the left")
  public void validWordWithSubwordLeftTest() throws TurnException {
    Tile tileG = new Tile(7, 7, new Letter('G', 1));
    tileG.setProcessed(true);
    Tile tileR = new Tile(7, 8, new Letter('R', 1));
    tileR.setProcessed(true);
    Tile tileU = new Tile(7, 9, new Letter('U', 2));
    tileU.setProcessed(true);

    Tile tileS = new Tile(7, 10, new Letter('S', 1));
    Tile tileY = new Tile(8, 10, new Letter('Y', 6));
    Tile tileN = new Tile(9, 10, new Letter('N', 1));
    Tile tileO = new Tile(10, 10, new Letter('O', 2));
    Tile tileN2 = new Tile(11, 10, new Letter('N', 1));
    Tile tileY2 = new Tile(12, 10, new Letter('Y', 6));
    Tile tileM = new Tile(13, 10, new Letter('M', 2));

    tileMap.setTile(tileG);
    tileMap.setTile(tileR);
    tileMap.setTile(tileU);
    tileMap.setTile(tileS);
    tileMap.setTile(tileY);
    tileMap.setTile(tileN);
    tileMap.setTile(tileO);
    tileMap.setTile(tileN2);
    tileMap.setTile(tileY2);
    tileMap.setTile(tileM);

    ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>(
        List.of(tileM, tileY2, tileN2, tileO, tileN, tileY, tileS));
    parser.parseBoard(unprocessedTiles);

    tileMap.removeTile(tileG);
    tileMap.removeTile(tileR);
    tileMap.removeTile(tileU);
    tileMap.removeTile(tileS);
    tileMap.removeTile(tileY);
    tileMap.removeTile(tileN);
    tileMap.removeTile(tileO);
    tileMap.removeTile(tileN2);
    tileMap.removeTile(tileY2);
    tileMap.removeTile(tileM);

  }
}
