package spellit.ui.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Region;
import spellit.core.models.Game;
import spellit.core.models.Letter;
import spellit.core.models.TileType;
import spellit.ui.controllers.GameController;

public class GridTile extends AbstractTile {

	private final Game game;
	private final int row, col;
	TileType tileType;
	private Region icon;

	public GridTile(GameController controller, double size, int row, int col) {
		super(controller, size);
		this.game = controller.getGame();
		this.row = row;
		this.col = col;
		this.tileType = game.board.getTileType(row, col);
		setupLayout();
		this.letterProperty.addListener(new ChangeListener<Letter>() {

			@Override
			public void changed(ObservableValue<? extends Letter> observable, Letter oldValue, Letter newValue) {
				if (oldValue == newValue) {
					return;
				}
				if (newValue != null) {
					game.board.setTile(row, col, tileType, newValue);
				} else {
					game.board.removeTile(row, col);
				}

			}

		});
	}

	private void setupLayout() {
		this.getStyleClass().add("tile");
		if (this.tileType == TileType.STAR) {
			icon = new Region();
			icon.getStyleClass().add("icon-star");
			icon.setPrefSize(this.getPrefWidth() / 2, this.getPrefHeight() / 2);
		}

		if (game.board.getTileMap().tiles[row][col].getLetter() != null) {
			this.setLetter(game.board.getTileMap().tiles[row][col].getLetter());
			this.setDisable(true);
		} else {
			this.getStyleClass().add(this.tileType.getClassName());
			this.character.setText(this.tileType.getValue());
			if (icon != null) {
				this.setCenter(icon);
			}
		}

	}

	@Override
	protected void setDragTransferState() {
		this.getStyleClass().remove("letter");
		if (this.tileType == TileType.STAR) {
			this.setCenter(icon);
		}
		removeLetter();
		this.character.setText(this.tileType.getValue());
		this.points.setText("");
	}

	@Override
	protected void resetDragTransferState(Letter letter) {
		addLetterStyle();
		setLetter(letter);
		this.setCharacter(letter.character);
		this.setPoints(letter.points);
	}

	@Override
	protected void handleDragTransferSuccess(DragEvent event) {
		this.getStyleClass().addAll("tile", this.tileType.getClassName());
		removeLetter();
	}

	@Override
	public String toString() {
		return "GridTile [tileType=" + tileType + ", letter=" + this.character + "]";
	}

}
