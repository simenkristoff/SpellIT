package spellit.ui.views;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import spellit.core.events.NextTurnListener;
import spellit.core.models.Game;
import spellit.ui.controllers.GameController;

public class Sidebar extends VBox implements NextTurnListener {

	private final Game game;
	private final Text currentPlayer;
	private final VBox tilesLeft;
	private final JFXButton finishRoundBtn, passRoundBtn, saveGameBtn, loadGameBtn, newGameBtn;

	public Sidebar(GameController controller) {
		this.setId("sidebar");
		this.game = controller.getGame();
		this.currentPlayer = new Text();
		this.tilesLeft = new VBox();
		this.finishRoundBtn = new JFXButton("Ferdig");
		this.passRoundBtn = new JFXButton("Stå over");
		this.saveGameBtn = new JFXButton("Lagre spill");
		this.loadGameBtn = new JFXButton("Last inn spill");
		this.newGameBtn = new JFXButton("Nytt spill");
		this.setupLayout();
	}

	private void setupLayout() {
		setPadding(new Insets(5.0));
		this.setSpacing(10.0);
		setAlignment(Pos.TOP_CENTER);

		// Setup current Player
		currentPlayer.setText(game.getCurrentPlayer().getName());
		currentPlayer.getStyleClass().add("player-label");
		getChildren().add(currentPlayer);
		setMargin(currentPlayer, new Insets(0, 0, 10, 0));

		// Setup Tiles left
		Text tilesLeftText = new Text();
		tilesLeftText.textProperty().bind(game.letterCollection.textProperty());
		tilesLeftText.getStyleClass().add("tiles-left");
		Label tilesLeftLabel = new Label("Brikker igjen");
		tilesLeft.getChildren().addAll(tilesLeftText, tilesLeftLabel);
		tilesLeft.setAlignment(Pos.CENTER);
		getChildren().add(tilesLeft);

		// Setup Buttons;
		finishRoundBtn.prefWidthProperty().bind(this.widthProperty());
		passRoundBtn.prefWidthProperty().bind(this.widthProperty());
		saveGameBtn.prefWidthProperty().bind(this.widthProperty());
		loadGameBtn.prefWidthProperty().bind(this.widthProperty());
		newGameBtn.prefWidthProperty().bind(this.widthProperty());
		getChildren().addAll(finishRoundBtn, passRoundBtn, saveGameBtn, loadGameBtn, newGameBtn);
		setMargin(saveGameBtn, new Insets(20, 0, 0, 0));
		this.setAlignment(Pos.CENTER);

	}

	public JFXButton getFinishRoundButton() {
		return this.finishRoundBtn;
	}

	public JFXButton getPassRoundButton() {
		return this.passRoundBtn;
	}

	public JFXButton getSaveGameButton() {
		return this.saveGameBtn;
	}

	public JFXButton getLoadGameButton() {
		return this.loadGameBtn;
	}

	public JFXButton getNewGameButton() {
		return this.newGameBtn;
	}

	@Override
	public void onNextTurn() {
		currentPlayer.setText(game.getCurrentPlayer().getName());
	}
}
