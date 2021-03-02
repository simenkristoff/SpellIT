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
import spellit.core.models.Game;
import spellit.ui.App;
import spellit.ui.controllers.GameController;
import spellit.ui.interfaces.TileContainerInterface;

public class GridContainer extends GridPane implements NextTurnListener, TileContainerInterface {

	private final GameController controller;
	private final Game game;
	private final int NUM_COLS, NUM_ROWS;
	private final double WIDTH, HEIGHT, COL_SIZE;
	private final ObservableList<GridTile> tiles = FXCollections.observableArrayList();

	public GridContainer(GameController controller) {
		this.setId("gridContainer");
		this.controller = controller;
		this.game = controller.getGame();
		this.NUM_COLS = game.board.getCols();
		this.NUM_ROWS = game.board.getRows();
		this.HEIGHT = game.board.getBoardHeight();
		this.WIDTH = game.board.getBoardWidth();
		this.COL_SIZE = game.board.getColSize();
		setupLayout();
		setupGrid();
	}

	private void setupLayout() {
		setPrefWidth(WIDTH);
		maxWidthProperty().bind(prefWidthProperty());
		setPrefHeight(HEIGHT);
		maxHeightProperty().bind(prefHeightProperty());
		setAlignment(Pos.BOTTOM_CENTER);
	}

	private void setupGrid() {
		for (int j = 0; j < NUM_COLS; j++) {
			getColumnConstraints()
					.add(new ColumnConstraints(COL_SIZE, COL_SIZE, COL_SIZE, Priority.ALWAYS, HPos.CENTER, true));
			getRowConstraints()
					.add(new RowConstraints(COL_SIZE, COL_SIZE, COL_SIZE, Priority.ALWAYS, VPos.CENTER, true));
		}
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				GridTile gridTile = new GridTile(this.controller, COL_SIZE, i, j);
				tiles.add(gridTile);
				add(gridTile, j, i, 1, 1);
			}
		}
	}

	@Override
	public void onNextTurn() {
		for (GridTile tile : tiles) {
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

	@Override
	public boolean mouseIntersection(double mouseX, double mouseY) {
		return this.getBoundsInParent().contains(mouseX, mouseY);
	}

	@Override
	public AbstractTile getIntersectingTile(double mouseX, double mouseY) {
		int col = (int) Math.floor((mouseX - this.getBoundsInParent().getMinX()) / this.COL_SIZE);
		int row = (int) Math.floor((mouseY - this.getBoundsInParent().getMinY()) / this.COL_SIZE);
		return (AbstractTile) this.getChildren().get((this.NUM_COLS * row) + col);
	}

}
