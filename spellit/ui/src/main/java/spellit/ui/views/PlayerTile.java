package spellit.ui.views;

import spellit.core.models.Letter;
import spellit.ui.controllers.GameController;

public class PlayerTile extends AbstractTile {

	public PlayerTile(GameController controller, double size) {
		super(controller, size);
	}

	@Override
	protected void setDragTransferState() {
		this.getStyleClass().remove("letter");
		removeLetter();
		this.character.setText("");
		this.points.setText("");
	}

	@Override
	protected void resetDragTransferState(Letter letter) {
		addLetterStyle();
		setLetter(letter);
		this.setCharacter(letter.character);
		this.setPoints(letter.points);
	}

}
