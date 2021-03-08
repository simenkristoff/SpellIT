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

/**
 * The Class Sidebar. Renders the in-game sidebar.
 */
public class Sidebar extends VBox implements NextTurnListener {

  private final Game game;
  private final Text currentPlayer;
  private final VBox tilesLeft;
  private final JFXButton finishRoundBtn;
  private final JFXButton passRoundBtn;
  private final JFXButton saveGameBtn;
  private final JFXButton loadGameBtn;
  private final JFXButton newGameBtn;
  private final JFXButton menuBtn;

  /**
   * Instantiates a new sidebar.
   *
   * @param controller the controller
   */
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
    this.menuBtn = new JFXButton("Gå til menyen");
    this.setupLayout();
  }

  /**
   * Setup layout.
   */
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
    menuBtn.prefWidthProperty().bind(this.widthProperty());
    getChildren().addAll(finishRoundBtn, passRoundBtn, saveGameBtn, loadGameBtn, newGameBtn,
        menuBtn);
    setMargin(saveGameBtn, new Insets(20, 0, 0, 0));
    this.setAlignment(Pos.CENTER);

  }

  /**
   * Gets the finish round button.
   *
   * @return the finish round button
   */
  public JFXButton getFinishRoundButton() {
    return this.finishRoundBtn;
  }

  /**
   * Gets the pass round button.
   *
   * @return the pass round button
   */
  public JFXButton getPassRoundButton() {
    return this.passRoundBtn;
  }

  /**
   * Gets the save game button.
   *
   * @return the save game button
   */
  public JFXButton getSaveGameButton() {
    return this.saveGameBtn;
  }

  /**
   * Gets the load game button.
   *
   * @return the load game button
   */
  public JFXButton getLoadGameButton() {
    return this.loadGameBtn;
  }

  /**
   * Gets the new game button.
   *
   * @return the new game button
   */
  public JFXButton getNewGameButton() {
    return this.newGameBtn;
  }

  /**
   * Gets the menu button.
   *
   * @return the menu button
   */
  public JFXButton getMenuButton() {
    return this.menuBtn;
  }

  /**
   * On next turn.
   */
  @Override
  public void onNextTurn() {
    currentPlayer.setText(game.getCurrentPlayer().getName());
  }
}
