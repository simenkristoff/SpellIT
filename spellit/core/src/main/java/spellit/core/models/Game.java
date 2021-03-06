package spellit.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import spellit.core.events.NextTurnListener;

/**
 * The Class Game. The logic hub for any game instance.
 */
@JsonIgnoreProperties(value = { "board", "currentPlayer", "winner", "letterCollection" })
public class Game {

  public Board board;
  public final LetterCollection letterCollection;
  private Player player1;
  private Player player2;
  private Player currentPlayer;
  private final ObjectProperty<Player> winnerProperty = new SimpleObjectProperty<Player>();
  private int turnCount;

  private List<NextTurnListener> nextTurnListeners = new ArrayList<NextTurnListener>();

  /**
   * Instantiates a new game instance.
   */
  public Game() {
    this.board = new Board(this);
    this.letterCollection = new LetterCollection();
    this.player1 = new Player(this, "player1");
    this.player2 = new Player(this, "player2");
    this.currentPlayer = player1;
    this.winnerProperty.set(null);
    this.turnCount = 0;
  }

  /**
   * Instantiates a new game instance.
   *
   * @param player1Name the player 1 name
   * @param player2Name the player 2 name
   */
  public Game(String player1Name, String player2Name) {
    this.board = new Board(this);
    this.letterCollection = new LetterCollection();
    this.player1 = new Player(this, player1Name);
    this.player2 = new Player(this, player2Name);
    this.currentPlayer = player1;
    this.winnerProperty.set(null);
    this.turnCount = 0;
  }

  /**
   * Adds a listener to the game instance.
   *
   * @param listener the listener
   */
  public void addListener(NextTurnListener listener) {
    nextTurnListeners.add(listener);
  }

  /**
   * Adds listeners to the game instance.
   *
   * @param listeners the listeners
   */
  public void addListener(NextTurnListener... listeners) {
    for (NextTurnListener listener : listeners) {
      nextTurnListeners.add(listener);
    }
  }

  /**
   * Removes a listener from the game instance.
   *
   * @param listener the listener
   */
  public void removeListener(NextTurnListener listener) {
    nextTurnListeners.remove(listener);
  }

  /**
   * Gets the turn count.
   *
   * @return the turn count
   */
  public int getTurnCount() {
    return this.turnCount;
  }

  /**
   * Sets the turn count.
   *
   * @param turnCount the new turn count
   */
  public void setTurnCount(int turnCount) {
    this.turnCount = turnCount;
  }

  /**
   * Gets the players.
   *
   * @return the players
   */
  public List<Player> getPlayers() {
    return List.of(player1, player2);
  }

  /**
   * Sets the players.
   *
   * @param players the new players
   */
  public void setPlayers(List<Player> players) {
    loadPlayer(player1, players.get(0));
    loadPlayer(player2, players.get(1));
    setCurrentPlayer();
  }

  /**
   * Load player. Parses a player from JSON file, and loads the data into an existing instance of a
   * player in the game.
   *
   * @param gamePlayer the game player
   * @param json the JSON-data
   */
  private void loadPlayer(Player gamePlayer, Player json) {
    gamePlayer.setName(json.getName());
    gamePlayer.setPoints(json.getPoints());
    gamePlayer.setLetters(json.getLetters());
  }

  /**
   * Gets the current player.
   *
   * @return the current player
   */
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  /**
   * Sets the current player based on the turn count.
   */
  private void setCurrentPlayer() {
    switch (turnCount % 2) {
      case 1:
        currentPlayer = player2;
        break;
      default:
        currentPlayer = player1;
        break;
    }
  }

  /**
   * Winner property.
   *
   * @return the object property
   */
  public final ObjectProperty<Player> winnerProperty() {
    return this.winnerProperty;
  }

  /**
   * Gets the winner.
   *
   * @return the winner
   */
  public Player getWinner() {
    return this.winnerProperty.get();
  }

  /**
   * Gets the placed tiles.
   *
   * @return the placed tiles
   */
  public ArrayList<Tile> getPlacedTiles() {
    return this.board.getPlacedTiles();
  }

  /**
   * Sets the placed tiles.
   *
   * @param placedTiles the new placed tiles
   */
  public void setPlacedTiles(List<Tile> placedTiles) {
    this.board.loadTiles(placedTiles);
  }

  /**
   * Initiate next turn and notify listeners.
   */
  public void initiateNextTurn() {
    turnCount++;
    if (letterCollection.isEmpty()) {
      if (player1.getPoints() >= player2.getPoints()) {
        this.winnerProperty.set(player1);
      } else {
        this.winnerProperty.set(player2);
      }
    }
    setCurrentPlayer();
    for (NextTurnListener listeners : nextTurnListeners) {
      listeners.onNextTurn();
    }
  }

}
