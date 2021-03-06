package spellit.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The Class TileMap. Controls the board's tiles and layout - i.e what tile type a tile at a given
 * position has, and whether a tile has a placed letter. The layout is loaded from a file and parsed
 * into a two-dimensional list representing the rows and columns of the board.
 */
public class TileMap {

  public Tile[][] tiles;

  /**
   * Instantiates a new tile map.
   */
  TileMap() {
    tiles = new Tile[Board.COLS][Board.ROWS];
    try (InputStream in = this.getClass().getResourceAsStream("tilemap_standard.txt")) {
      Scanner sc = new Scanner(in, "UTF-8");
      int row = 0;
      while (sc.hasNextLine()) {
        String[] list = sc.nextLine().split("\\|");
        int col = 0;
        for (String tile : list) {
          switch (tile.toUpperCase()) {
            case "TW":
              tiles[row][col] = new Tile(row, col, TileType.TW);
              break;
            case "DW":
              tiles[row][col] = new Tile(row, col, TileType.DW);
              break;
            case "TL":
              tiles[row][col] = new Tile(row, col, TileType.TL);
              break;
            case "DL":
              tiles[row][col] = new Tile(row, col, TileType.DL);
              break;
            case "S":
              tiles[row][col] = new Tile(row, col, TileType.STAR);
              break;
            default:
              tiles[row][col] = new Tile(row, col, TileType.DEFAULT);
              break;
          }
          col++;
        }
        row++;
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets a tile from a given position in the tile map.
   *
   * @param row the row
   * @param col the column
   * @return the tile
   */
  public Tile getTile(int row, int col) {
    return tiles[row][col];
  }

  /**
   * Gets a tile from a given position in the tile map.
   *
   * @param tile the tile
   * @return the tile
   */
  public Tile getTile(Tile tile) {
    return tiles[tile.getRow()][tile.getCol()];
  }

  /**
   * Sets the tile at a given position in the tile map.
   *
   * @param tile the new tile
   */
  public void setTile(Tile tile) {
    tiles[tile.getRow()][tile.getCol()].setLetter(tile.getLetter());
    tiles[tile.getRow()][tile.getCol()].setProcessed(tile.isProcessed());
  }

  /**
   * Gets the bonus of a tile in the tile map.
   *
   * @param tile the tile
   * @return the bonus
   */
  public TileType getBonus(Tile tile) {
    return tiles[tile.getRow()][tile.getCol()].getTileType();
  }

  /**
   * Checks if the tile has a neighbor to the left.
   *
   * @param tile the tile to check
   * @return true, if successful
   */
  public boolean hasLeft(Tile tile) {
    return tile.getCol() == 0 ? false : tiles[tile.getRow()][tile.getCol() - 1].hasLetter();
  }

  /**
   * Checks if the tile has a neighbor to the right.
   *
   * @param tile the tile to check
   * @return true, if successful
   */
  public boolean hasRight(Tile tile) {
    return tile.getCol() == Board.COLS - 1 ? false
        : tiles[tile.getRow()][tile.getCol() + 1].hasLetter();
  }

  /**
   * Checks if the tile has a neighbor above.
   *
   * @param tile the tile to check
   * @return true, if successful
   */
  public boolean hasUp(Tile tile) {
    return tile.getRow() == 0 ? false : tiles[tile.getRow() - 1][tile.getCol()].hasLetter();
  }

  /**
   * Checks if the tile has a neighbor below.
   *
   * @param tile the tile to check
   * @return true, if successful
   */
  public boolean hasDown(Tile tile) {
    return tile.getRow() == Board.ROWS - 1 ? false
        : tiles[tile.getRow() + 1][tile.getCol()].hasLetter();
  }

  /**
   * Gets the left neighbor.
   *
   * @param tile the reference tile
   * @return the left
   */
  public Tile getLeft(Tile tile) {
    return tiles[tile.getRow()][tile.getCol() - 1];
  }

  /**
   * Gets the right neighbor.
   *
   * @param tile the reference tile
   * @return the right
   */
  public Tile getRight(Tile tile) {
    return tiles[tile.getRow()][tile.getCol() + 1];
  }

  /**
   * Gets the neighbor above.
   *
   * @param tile the reference tile
   * @return the up
   */
  public Tile getUp(Tile tile) {
    return tiles[tile.getRow() - 1][tile.getCol()];
  }

  /**
   * Gets the neighbor below.
   *
   * @param tile the reference tile
   * @return the down
   */
  public Tile getDown(Tile tile) {
    return tiles[tile.getRow() + 1][tile.getCol()];
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "TileMap [tiles=" + Arrays.toString(tiles) + "]";
  }

}
