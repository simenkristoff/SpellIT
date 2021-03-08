package spellit.ui.views;

import spellit.core.models.Letter;
import spellit.ui.controllers.GameController;

/**
 * The Class PlayerTile. Class for tiles representing each letter a player has drawn and is
 * currently available in the player rack.
 *
 * @see Player
 */
public class PlayerTile extends AbstractTile {

  /**
   * Instantiates a new player tile.
   *
   * @param controller the controller
   * @param size the tile size
   */
  public PlayerTile(GameController controller, double size) {
    super(controller, size);
  }

  /**
   * Sets the drag transfer state. Displays an empty tile while it's being dragged.
   */
  @Override
  protected void setDragTransferState() {
    this.getStyleClass().remove("letter");
    removeLetter();
    this.character.setText("");
    this.points.setText("");
  }

  /**
   * Reset drag transfer state. Resets tile display.
   *
   * @param letter the letter
   */
  @Override
  protected void resetDragTransferState(Letter letter) {
    addLetterStyle();
    setLetter(letter);
    this.setCharacter(letter.getCharacter());
    this.setPoints(letter.getPoints());
  }

}
