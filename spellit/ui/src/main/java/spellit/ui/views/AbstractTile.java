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

public abstract class AbstractTile extends BorderPane {

  public static final DataFormat LETTER_LIST = new DataFormat("LetterList");

  protected Text character;
  protected Text points;
  protected ObjectProperty<Letter> letterProperty = new SimpleObjectProperty<Letter>(null);

  private final GameController controller;
  private final double size;

  public AbstractTile(GameController controller, double size) {
    this.controller = controller;
    this.size = size;
    setupLayout();
    setupDragListeners();
    getStyleClass().add("tile");
  }

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

  public boolean hasLetter() {
    return letterProperty.get() != null;
  }

  public Letter getLetter() {
    return letterProperty.get();
  }

  public void setLetter(Letter letter) {
    if (letter != null) {
      this.letterProperty.set(letter);
      addLetterStyle();
      this.setCharacter(letter.character);
      this.setPoints(letter.points);
    }
  }

  protected void removeLetter() {
    if (!hasLetter()) {
      return;
    }
    letterProperty.set(null);
    this.getStyleClass().remove("letter");
    this.setCharacter(' ');
    this.setPoints(null);
  }

  protected abstract void setDragTransferState();

  protected abstract void resetDragTransferState(Letter letter);

  protected void addLetterStyle() {
    if (this.getStyleClass().indexOf("letter") < 0) {
      this.getStyleClass().add("letter");
    }
  }

  protected void setCharacter(char character) {
    if (this.character == null) {
      this.character = new Text(Character.toString(character));
    } else {
      this.character.setText(Character.toString(character));
    }
  }

  protected void setPoints(Integer points) {
    if (this.points == null) {
      this.points = new Text(String.valueOf(points));
    } else {
      String p = points != null ? String.valueOf(points) : "";
      this.points.setText(p);
    }
  }

  private WritableImage createSnapshot() {
    SnapshotParameters parameters = new SnapshotParameters();
    parameters.setFill(Color.TRANSPARENT);
    parameters.setTransform(Translate.scale(1.3, 1.3));
    return snapshot(parameters, null);
  }

  private void setupDragListeners() {

    this.addEventFilter(MouseDragEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        if (hasLetter() && !isDisable()) {

          Dragboard db = startDragAndDrop(TransferMode.COPY_OR_MOVE);
          ClipboardContent content = new ClipboardContent();
          content.put(LETTER_LIST, getLetter());
          db.setContent(content);

          db.setDragView(createSnapshot());
          db.setDragViewOffsetX(getWidth() / 2);
          db.setDragViewOffsetY(getHeight() / 2);
          setDragTransferState();
          event.consume();
        }
      }
    });

    /**
     * Handles Drag over event
     */
    this.addEventFilter(DragEvent.DRAG_OVER, new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

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

    this.addEventFilter(DragEvent.DRAG_EXITED, new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {

        if (event.getTarget() instanceof AbstractTile) {
          AbstractTile tile = (AbstractTile) event.getTarget();
          tile.setOpacity(1.0);
        }

        event.consume();
      }
    });

    /**
     * Handles event where drag is dropped upon this tile
     */
    this.addEventFilter(DragEvent.DRAG_DROPPED, new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        AbstractTile target = null;
        boolean dragCompleted = false;
        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();
        target = controller.getIntersection(event);

        if (dragboard.hasContent(LETTER_LIST) && !target.hasLetter()) {
          if (target instanceof GridTile) {
            target.getStyleClass().add("recently-placed");
            if (((GridTile) target).getTileType() == TileType.STAR) {
              target.setCenter(target.character);
            }
          } else {
            target.getStyleClass().remove("recently-placed");
          }

          setLetter((Letter) dragboard.getContent(LETTER_LIST));
          dragCompleted = true;
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
      }
    });

    /**
     * What to do after drag is completed
     */
    this.addEventFilter(DragEvent.DRAG_DONE, new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        TransferMode tm = event.getTransferMode();
        AbstractTile source = (AbstractTile) event.getSource();
        if (tm == TransferMode.MOVE) {
          if (source instanceof GridTile) {
            source.getStyleClass().addAll("tile", ((GridTile) source).getTileType().getClassName());
          }
          event.consume();
        } else {
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
