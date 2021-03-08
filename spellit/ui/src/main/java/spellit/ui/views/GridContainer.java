package spellit.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import spellit.core.events.NextTurnListener;
import spellit.core.models.Board;
import spellit.ui.App;
import spellit.ui.controllers.GameController;
import spellit.ui.interfaces.TileContainerInterface;

/**
 * The Class GridContainer. Manages the UI-board with support of drag and drop functionality.
 */
public class GridContainer extends GridPane implements NextTurnListener, TileContainerInterface {

  private final GameController controller;
  private final ObservableList<GridTile> tiles = FXCollections.observableArrayList();

  /**
   * Instantiates a new grid container.
   *
   * @param controller the controller
   */
  public GridContainer(GameController controller) {
    this.setId("gridContainer");
    this.controller = controller;
    setupLayout();
    setupGrid();
  }

  /**
   * Setup layout.
   */
  private void setupLayout() {
    setPrefWidth(Board.BOARD_WIDTH);
    maxWidthProperty().bind(prefWidthProperty());
    setPrefHeight(Board.BOARD_HEIGHT);
    maxHeightProperty().bind(prefHeightProperty());
    setAlignment(Pos.BOTTOM_CENTER);
  }

  /**
   * Setup grid.
   */
  private void setupGrid() {
    // Setup row constraints
    for (int j = 0; j < Board.COLS; j++) {
      getColumnConstraints().add(new ColumnConstraints(Board.COL_SIZE, Board.COL_SIZE,
          Board.COL_SIZE, Priority.ALWAYS, HPos.CENTER, true));
      getRowConstraints().add(new RowConstraints(Board.COL_SIZE, Board.COL_SIZE, Board.COL_SIZE,
          Priority.ALWAYS, VPos.CENTER, true));
    }

    // Add GridTiles
    for (int i = 0; i < Board.ROWS; i++) {
      for (int j = 0; j < Board.COLS; j++) {
        GridTile gridTile = new GridTile(this.controller, Board.COL_SIZE, i, j);
        tiles.add(gridTile);
        add(gridTile, j, i, 1, 1);
      }
    }
  }

  /**
   * On next turn.
   */
  @Override
  public void onNextTurn() {
    for (GridTile tile : tiles) {
      // Remove style class from newly processed classes, and set them undraggable
      if (tile.hasLetter()) {
        tile.getStyleClass().remove("recently-placed");
        tile.setDisable(true);
      }
    }
  }

  /**
   * Gets the stylesheet.
   *
   * @return the user agent stylesheet
   */
  public String getUserAgentStylesheet() {
    return App.class.getResource("css/tiles.css").toExternalForm();
  }

  /**
   * Mouse intersection.
   *
   * @param mouseX the mouse X-coordinate
   * @param mouseY the mouse Y-coordinate
   * @return true, if successful
   */
  @Override
  public boolean mouseIntersection(double mouseX, double mouseY) {
    return this.getBoundsInParent().contains(mouseX, mouseY);
  }

  /**
   * Gets the intersecting tile.
   *
   * @param mouseX the mouse X-coordinate
   * @param mouseY the mouse Y-coordinate
   * @return the intersecting tile
   */
  @Override
  public AbstractTile getIntersectingTile(double mouseX, double mouseY) {
    int col = (int) Math.floor((mouseX - this.getBoundsInParent().getMinX()) / Board.COL_SIZE);
    int row = (int) Math.floor((mouseY - this.getBoundsInParent().getMinY()) / Board.COL_SIZE);
    return (AbstractTile) this.getChildren().get((Board.COLS * row) + col);
  }

}
