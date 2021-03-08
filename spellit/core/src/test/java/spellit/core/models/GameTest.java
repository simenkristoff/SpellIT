package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class GameTest {

  private static Game game;
  private static TestListener listener1;
  private static TestListener listener2;
  private static TestListener listener3;

  /**
   * Initialize game and listeners.
   */
  @BeforeAll
  public static void setup() {
    game = new Game("Test1", "Test2");
    listener1 = new TestListener();
    listener2 = new TestListener();
    listener3 = new TestListener();
  }

  @Test
  @Order(1)
  @DisplayName("Should get and set turn count")
  public void turnCountTest() {
    assertEquals(game.getTurnCount(), 0);
    game.setTurnCount(1);
    assertEquals(game.getTurnCount(), 1);
    game.setTurnCount(0);
  }

  @Test
  @Order(2)
  @DisplayName("Should return the game's players")
  public void getPlayersTest() {
    List<String> playerNames = List.of("Test1", "Test2");
    List<Player> players = game.getPlayers();
    for (int i = 0; i < players.size(); i++) {
      assertNotNull(players.get(i));
      assertEquals(players.get(i).getName(), playerNames.get(i));
    }
  }

  @Test
  @Order(3)
  @DisplayName("Should set the game's players")
  public void setPlayersTest() {
    List<Letter> playerLetters = List.of(new Letter('Y', 6), new Letter('T', 1), new Letter('X', 8),
        new Letter('D', 1), new Letter('D', 1), new Letter('R', 1), new Letter('R', 1));
    Player player1 = new Player("newPlayer1", playerLetters);
    Player player2 = new Player("newPlayer2", playerLetters);
    List<Player> players = List.of(player1, player2);
    game.setPlayers(players);

    List<Player> newPlayers = game.getPlayers();

    for (int i = 0; i < newPlayers.size(); i++) {
      assertEquals(newPlayers.get(i).getName(), players.get(i).getName());
      assertTrue(newPlayers.get(i).getLetters().size() == 7);
    }
  }

  @Test
  @Order(4)
  @DisplayName("Should change current player based on turn count")
  public void currentPlayerTest() {
    Player player1 = game.getCurrentPlayer();
    game.initiateNextTurn();
    assertNotEquals(player1, game.getCurrentPlayer());
  }

  @Test
  @Order(5)
  @DisplayName("Should set placed tiles")
  public void setPlacedTilesTest() {
    assertEquals(game.getPlacedTiles().size(), 0);
    Tile tile1 = new Tile(7, 7, TileType.STAR);
    tile1.setLetter(new Letter('A', 1));
    Tile tile2 = new Tile(7, 8, TileType.DEFAULT);
    tile2.setLetter(new Letter('B', 4));
    List<Tile> storedTiles = List.of(tile1, tile2);
    game.setPlacedTiles(storedTiles);
    assertEquals(game.getPlacedTiles().size(), storedTiles.size());
  }

  @Test
  @Order(6)
  @DisplayName("Should add turn listeners without any errors")
  public void addTurnListenersTest() {
    game.addListener(listener1);
    game.addListener(listener2, listener3);
  }

  @Test
  @Order(7)
  @DisplayName("Should declare player 1 as winner")
  public void player1WinnerTest() {
    assertNull(game.getWinner());
    Player player1 = game.getPlayers().get(0);
    player1.setPoints(10);

    // Empty the letter collection
    int size = game.letterCollection.size();
    for (int i = 0; i < size; i++) {
      game.letterCollection.drawRandomLetter();
    }
    game.initiateNextTurn();
    assertEquals(game.getWinner(), player1);
  }

  @Test
  @Order(8)
  @DisplayName("Should declare player 2 as winner")
  public void player2WinnerTest() {
    Player player2 = game.getPlayers().get(1);
    player2.setPoints(20);

    game.initiateNextTurn();
    assertEquals(game.getWinner(), player2);
  }

  @Test
  @Order(9)
  @DisplayName("Should remove turn listeners without any errors")
  public void removeTurnListenerTest() {
    game.removeListener(listener1);
    game.removeListener(listener2);
    game.removeListener(listener3);
  }

  @Test
  @Order(10)
  @DisplayName("Should return the winner property")
  public void getWinnerPropertyTest() {
    assertNotNull(game.winnerProperty());
  }
}
