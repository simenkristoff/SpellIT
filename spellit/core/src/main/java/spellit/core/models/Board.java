package spellit.core.models;

import java.util.ArrayList;
import java.util.List;
import spellit.core.exceptions.TurnException;
import spellit.core.utils.BoardParser;

/**
 * The Class Board. This class represents the structure of the game board and watches the placed
 * tiles.
 */
public class Board {

  public static final int BOARD_WIDTH = 540;
  public static final int BOARD_HEIGHT = BOARD_WIDTH;
  public static final int COLS = 15;
  public static final int ROWS = COLS;
  public static final double COL_SIZE = BOARD_WIDTH / COLS;
  private final Game game;
  private final TileMap tileMap;
  private final Dictionary dictionary;
  private final BoardParser parser;
  private ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>();
  private ArrayList<Tile> placedTiles = new ArrayList<Tile>();

  /**
   * Instantiates a new board.
   *
   * @param game the game instance
   */
  public Board(Game game) {
    this.game = game;
    this.dictionary = new Dictionary();
    this.tileMap = new TileMap();
    this.parser = new BoardParser(this.game, this.tileMap, this.dictionary);
  }

  /**
   * Sets the tile.
   *
   * @param row the row
   * @param col the column
   * @param letter the letter
   */
  public void setTile(int row, int col, Letter letter) {
    tileMap.tiles[row][col].setLetter(letter);
    unprocessedTiles.add(new Tile(row, col, letter));
  }

  /**
   * Removes the tile.
   *
   * @param row the row
   * @param col the column
   */
  public void removeTile(int row, int col) {
    tileMap.tiles[row][col].setLetter(null);
    for (Tile tile : unprocessedTiles) {
      if (tile.getRow() == row && tile.getCol() == col) {
        unprocessedTiles.remove(tile);
        break;
      }
    }
  }

  /**
   * Gets the tile map.
   *
   * @return the tile map
   */
  public TileMap getTileMap() {
    return this.tileMap;
  }

  /**
   * Returns the tiles placed on the board.
   *
   * @return the placed tiles
   */
  public ArrayList<Tile> getPlacedTiles() {
    return this.placedTiles;
  }

  /**
   * Returns the unprocessed tiles placed on the board.
   *
   * @return the unprocessed tiles
   */
  public ArrayList<Tile> getUnprocessedTiles() {
    return this.unprocessedTiles;
  }

  /**
   * Loads a list of tiles to the board.
   *
   * @param placedTiles the the tiles to load
   */
  public void loadTiles(List<Tile> placedTiles) {
    for (Tile tile : placedTiles) {
      this.game.letterCollection.removeLetter(tile.getLetter());
      this.tileMap.setTile(tile);
    }
    this.placedTiles = new ArrayList<Tile>(placedTiles);
  }

  /**
   * Gets the tile type.
   *
   * @param row the row
   * @param col the column
   * @return the tile type
   */
  public TileType getTileType(int row, int col) {
    return tileMap.tiles[row][col].getTileType();
  }

  /**
   * Process turn. Validates and calculates the tiles placed during the turn. If the placement and
   * word is valid the current player will be awarded points, else an error is thrown.
   *
   * @throws TurnException the turn exception
   */
  public void processTurn() throws TurnException {
    parser.parseBoard(unprocessedTiles);
    cleanUp();
  }

  /**
   * Clean up. Clears the board logic before next turn.
   */
  private void cleanUp() {
    this.placedTiles.addAll(unprocessedTiles);
    unprocessedTiles = new ArrayList<Tile>();
  }

}
