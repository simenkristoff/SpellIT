package spellit.ui.views.dialogs;

import java.util.List;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import spellit.core.models.Board;
import spellit.core.models.Letter;
import spellit.ui.App;
import spellit.ui.controllers.GameController;
import spellit.ui.views.PlayerTile;

/**
 * The Class LetterDialog. Renders a dialog for picking a character for blank letter-tiles.
 */
public class LetterDialog extends AbstractDialog<Character> {

  private final GameController controller;
  private final GridPane grid;
  private final int cols;
  private final int rows;
  private final ObjectProperty<PlayerTile> selectedTile = new SimpleObjectProperty<PlayerTile>();
  private List<Character> chars = List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
      'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Æ', 'Ø', 'Å');

  /**
   * Instantiates a new letter dialog.
   *
   * @param root the root pane
   * @param controller the controller
   */
  public LetterDialog(StackPane root, GameController controller) {
    super(root, "Velg", true, true);
    this.controller = controller;
    this.setTitle("Last inn spill");
    this.grid = new GridPane();
    this.selectedTile.set(null);
    this.cols = Math.floorDiv((int) this.layout.getMaxWidth(), (int) Board.COL_SIZE) - 1;
    this.rows = Math.floorDiv(chars.size(), cols);
    this.setupDialog();
  }

  /**
   * Setup dialog.
   */
  @Override
  protected void setupDialog() {

    this.setupGrid();

    this.selectedTile.addListener(new ChangeListener<PlayerTile>() {

      @Override
      public void changed(ObservableValue<? extends PlayerTile> observable, PlayerTile oldValue,
          PlayerTile newValue) {
        if (oldValue != newValue) {
          if (oldValue != null) {
            oldValue.getStyleClass().remove("selected");
          }

          newValue.getStyleClass().add("selected");
        }
      }
    });

    for (int k = 0; k < chars.size(); k++) {
      PlayerTile tile = new PlayerTile(this.controller, Board.COL_SIZE);
      tile.setLetter(new Letter(chars.get(k), 0));
      tile.setDisable(true);
      int rowIndex = Math.floorDiv(k, cols);
      int colIndex = k - (cols * rowIndex);
      grid.add(tile, colIndex, rowIndex);
    }

    this.setContent(grid);

    this.getOkButton().setOnAction(event -> {
      if (selectedTile.get() == null) {
        this.setError("Du må velge en bokstav");
        dialog.setResult(null);
      } else {
        dialog.setResult(selectedTile.get().getLetter().getCharacter());
        dialog.hide();
      }
    });

    dialog.setResultConverter(buttonType -> {
      if (buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
        dialog.setResult(null);
      }
      return dialog.getResult();
    });
  }

  /**
   * Setup grid.
   */
  private void setupGrid() {
    for (int i = 0; i <= cols; i++) {
      grid.getColumnConstraints().add(new ColumnConstraints(Board.COL_SIZE, Board.COL_SIZE,
          Board.COL_SIZE, Priority.ALWAYS, HPos.CENTER, true));
    }
    for (int j = 0; j <= rows; j++) {
      grid.getRowConstraints().add(new RowConstraints(Board.COL_SIZE, Board.COL_SIZE,
          Board.COL_SIZE, Priority.ALWAYS, VPos.CENTER, true));
    }
    grid.setOnMouseClicked(event -> {
      Bounds wrapperBounds = grid.getParent().getBoundsInParent();
      double offsetX = (App.WIDTH - wrapperBounds.getWidth() - wrapperBounds.getMinX()) / 2
          + Board.COL_SIZE;
      double offsetY = (App.HEIGHT / 2 - grid.getBoundsInParent().getHeight() / 2) - 14;
      int col = (int) Math.floor((event.getSceneX() - offsetX) / Board.COL_SIZE);
      int row = (int) Math.floor((event.getSceneY() - offsetY) / Board.COL_SIZE);
      int index = (cols * row) + col;
      if (index < chars.size()) {
        selectedTile.set((PlayerTile) grid.getChildren().get(index));
      }
    });
  }

  /**
   * Show dialog.
   *
   * @return the character
   */
  @Override
  public Character showDialog() {
    Optional<Character> result = dialog.showAndWait();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

}
