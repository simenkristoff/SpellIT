package spellit.ui.views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import spellit.core.models.Letter;
import spellit.core.models.TileType;
import spellit.ui.App;
import spellit.ui.controllers.GameController;
import spellit.ui.views.dialogs.LetterDialog;

/**
 * The Class AbstractTile. Wrapper class for tiles. Each tile is draggable as long as they have a
 * letter, and can drag a letter from one tile to another. Tiles will be undraggable if they have no
 * letter or if they are set to disabled.
 */
public abstract class AbstractTile extends BorderPane {

  public static final DataFormat LETTER_LIST = new DataFormat("LetterList");

  protected Text character;
  protected Text points;
  protected ObjectProperty<Letter> letterProperty = new SimpleObjectProperty<Letter>(null);

  private final GameController controller;
  private final double size;

  /**
   * Instantiates a new abstract tile.
   *
   * @param controller the controller
   * @param size the size
   */
  public AbstractTile(GameController controller, double size) {
    this.controller = controller;
    this.size = size;
    setupLayout();
    setupDragListeners();
    getStyleClass().add("tile");
  }

  /**
   * Setup layout.
   */
  private void setupLayout() {
    setMinSize(size, size);
    setPrefSize(size, size);
    setMaxSize(size, size);
    setPadding(new Insets(2.0));

    // Setup character
    this.character = new Text();
    character.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, size / 3));
    setCenter(character);
    setAlignment(character, Pos.CENTER);

    // Setup points
    this.points = new Text("");
    points.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, size / 4));
    setBottom(this.points);
    setAlignment(points, Pos.CENTER_RIGHT);

    // Setup padder
    HBox padder = new HBox();
    padder.setMinHeight(size / 4);
    padder.setMaxHeight(size / 4);
    setTop(padder);
  }

  /**
   * Checks for letter.
   *
   * @return true, if successful
   */
  public boolean hasLetter() {
    return letterProperty.get() != null;
  }

  /**
   * Gets the letter.
   *
   * @return the letter
   */
  public Letter getLetter() {
    return letterProperty.get();
  }

  /**
   * Sets the letter.
   *
   * @param letter the new letter
   */
  public void setLetter(Letter letter) {
    if (letter != null) {
      this.letterProperty.set(letter);
      addLetterStyle();
      this.setCharacter(letter.getCharacter());
      this.setPoints(letter.getPoints());
    }
  }

  /**
   * Removes the letter.
   */
  protected void removeLetter() {
    if (!hasLetter()) {
      return;
    }
    letterProperty.set(null);
    this.getStyleClass().remove("letter");
    this.setCharacter(' ');
    this.setPoints(null);
  }

  /**
   * Sets the drag transfer state.
   */
  protected abstract void setDragTransferState();

  /**
   * Reset drag transfer state.
   *
   * @param letter the letter
   */
  protected abstract void resetDragTransferState(Letter letter);

  /**
   * Adds the letter style.
   */
  protected void addLetterStyle() {
    if (this.getStyleClass().indexOf("letter") < 0) {
      this.getStyleClass().add("letter");
    }
  }

  /**
   * Sets the character.
   *
   * @param character the new character
   */
  public void setCharacter(char character) {
    if (this.character == null) {
      this.character = new Text(Character.toString(character));
    } else {
      this.character.setText(Character.toString(character));
    }
  }

  /**
   * Sets the points.
   *
   * @param points the new points
   */
  protected void setPoints(Integer points) {
    if (this.points == null) {
      this.points = new Text(String.valueOf(points));
    } else {
      String p = points != null ? String.valueOf(points) : "";
      this.points.setText(p);
    }
  }

  /**
   * Creates a snapshot image of the tile.
   *
   * @return the writable image
   */
  private WritableImage createSnapshot() {
    SnapshotParameters parameters = new SnapshotParameters();
    parameters.setFill(Color.TRANSPARENT);
    parameters.setTransform(Translate.scale(1.3, 1.3));
    return snapshot(parameters, null);
  }

  /**
   * Setup drag listeners.
   */
  private void setupDragListeners() {

    // On drag start
    this.addEventFilter(MouseDragEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        if (hasLetter() && !isDisable()) {

          Dragboard db = startDragAndDrop(TransferMode.COPY_OR_MOVE);
          ClipboardContent content = new ClipboardContent();
          content.put(LETTER_LIST, getLetter());
          db.setContent(content);

          // Create snapshot of the tile being dragged
          db.setDragView(createSnapshot());
          db.setDragViewOffsetX(getWidth() / 2);
          db.setDragViewOffsetY(getHeight() / 2);
          setDragTransferState();
          event.consume();
        }
      }
    });

    // Handle dragover event
    this.addEventFilter(DragEvent.DRAG_OVER, new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

        // Make a hover effect if a tile is being dragged over
        if (dragboard.hasContent(LETTER_LIST)) {
          event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
          if (event.getTarget() instanceof AbstractTile) {
            AbstractTile tile = (AbstractTile) event.getTarget();
            tile.setOpacity(0.8);
          }
        }

        event.consume();
      }
    });

    // Drag exited event
    this.addEventFilter(DragEvent.DRAG_EXITED, new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {

        // Remove hover effect/opacity
        if (event.getTarget() instanceof AbstractTile) {
          AbstractTile tile = (AbstractTile) event.getTarget();
          tile.setOpacity(1.0);
        }

        event.consume();
      }
    });

    // Handles event where drag is dropped upon this tile
    this.addEventFilter(DragEvent.DRAG_DROPPED, new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        AbstractTile target = null;
        boolean dragCompleted = false;
        boolean failed = false;
        Dragboard dragboard = event.getDragboard();
        target = controller.getIntersection(event);

        // If targets letter property isn't occupied
        if (dragboard.hasContent(LETTER_LIST) && !target.hasLetter()) {
          Letter letter = (Letter) dragboard.getContent(LETTER_LIST);

          // If the letter is being placed on the game board
          if (target instanceof GridTile) {
            target.getStyleClass().add("recently-placed");
            event.getDragboard().setDragView(null);

            // If the letter is a blank character, open the picker dialog
            if (letter.getCharacter() == ' ') {
              LetterDialog dialog = new LetterDialog(controller.getRootPane(), controller);
              Character dialogLetter = (Character) dialog.showDialog();
              if (dialogLetter != null) {
                letter.setCharacter(dialogLetter);
              } else {
                failed = true;
                target.getStyleClass().remove("recently-placed");
              }

              dialog = null;
            }

            // Custom handling for starting tile - change the star with text/char
            if (((GridTile) target).getTileType() == TileType.STAR && !failed) {
              target.setCenter(target.character);
            }
          } else {
            target.getStyleClass().remove("recently-placed");
          }

          if (!failed) {
            setLetter(letter);
            dragCompleted = true;
          }
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
      }
    });

    // Cleanup after drag is done
    this.addEventFilter(DragEvent.DRAG_DONE, new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        TransferMode tm = event.getTransferMode();
        AbstractTile source = (AbstractTile) event.getSource();
        if (tm == TransferMode.MOVE) {
          // If tile is dragged from board to rack, reset GridTile type style.
          if (source instanceof GridTile) {
            source.getStyleClass().addAll("tile", ((GridTile) source).getTileType().getClassName());
          }
          event.consume();
        } else {
          // Something went wrong, reset
          Dragboard dragboard = event.getDragboard();
          resetDragTransferState((Letter) dragboard.getContent(LETTER_LIST));
          event.consume();
        }

      }
    });
  }

  /**
   * Gets the stylesheet.
   *
   * @return the user agent stylesheet
   */
  public String getUserAgentStylesheet() {
    return App.class.getResource("css/tiles.css").toExternalForm();
  }
}
