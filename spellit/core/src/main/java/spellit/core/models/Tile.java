package spellit.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The Class Tile. Represents any tile placed on the board. A tile can have a tile type which refers
 * to the bonus added to the points given for the tile's letter. Tiles also have a state called
 * processed which is False if the tile is recently placed by a player, and the tile's contribution
 * to the player's score haven't yet been calculated.
 *
 * @see TileType
 */
@JsonIgnoreProperties(value = { "tileType" })
public class Tile {

  private final int row;
  private final int col;
  private TileType tileType;
  private ObjectProperty<Letter> letterProperty = new SimpleObjectProperty<Letter>(null);
  private BooleanProperty processedProperty = new SimpleBooleanProperty(false);

  /**
   * Instantiates a new tile.
   *
   * @param row the row
   * @param col the column
   * @param tileType the tile type
   */
  public Tile(int row, int col, TileType tileType) {
    this.row = row;
    this.col = col;
    this.tileType = tileType;
  }

  /**
   * Instantiates a new tile.
   *
   * @param row the row
   * @param col the column
   * @param letter the letter
   */
  @JsonCreator
  public Tile(@JsonProperty("row") int row, @JsonProperty("col") int col,
      @JsonProperty("letter") Letter letter) {
    this.row = row;
    this.col = col;
    this.letterProperty.set(letter);
  }

  /**
   * Checks if the tile has a letter.
   *
   * @return true, if successful
   */
  public boolean hasLetter() {
    return letterProperty.get() != null;
  }

  /**
   * Gets the tile's letter.
   *
   * @return the letter
   */
  public Letter getLetter() {
    return this.letterProperty.get();
  }

  /**
   * Sets the tile's letter.
   *
   * @param letter the new letter
   */
  public void setLetter(Letter letter) {
    this.letterProperty.set(letter);
  }

  /**
   * Removes the letter.
   */
  public void removeLetter() {
    this.letterProperty.set(null);
  }

  /**
   * Checks if the tile is processed by the board i.e, already placed and the score is calculated.
   *
   * @return true, if is processed
   */
  public boolean isProcessed() {
    return this.processedProperty.get();
  }

  /**
   * Sets the tile's processed status.
   *
   * @param value the new processed
   */
  public void setProcessed(boolean value) {
    this.processedProperty.set(value);
  }

  /**
   * Gets the row of the tile.
   *
   * @return the row
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Gets the column of the tile.
   *
   * @return the column
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Gets the tile type.
   *
   * @return the tile type
   */
  public TileType getTileType() {
    return this.tileType;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Tile [row=" + row + ", col=" + col + ", tileType=" + tileType + ", letter="
        + letterProperty.get() + ", isProcessed=" + processedProperty.get() + "]";
  }

}
