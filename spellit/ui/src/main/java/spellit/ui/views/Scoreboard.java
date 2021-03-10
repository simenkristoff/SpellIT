package spellit.ui.views;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import spellit.core.models.Board;
import spellit.core.models.Game;
import spellit.core.models.Player;
import spellit.ui.controllers.GameController;

/**
 * The Class Scoreboard. Renders each players name and score. Each player /w score is bound to
 * properties in the core-module's Player class, and will update on changes in any of the player
 * instances.
 *
 * @see Player
 */
public class Scoreboard extends AnchorPane {

  private final Game game;
  private final Player player1;
  private final Player player2;
  private final Text player1Score;
  private final Text player2Score;

  /**
   * Instantiates a new scoreboard.
   *
   * @param controller the controller
   */
  public Scoreboard(GameController controller) {
    this.setId("scoreboard");
    this.game = controller.getGame();
    List<Player> players = game.getPlayers();
    this.player1 = players.get(0);
    this.player2 = players.get(1);
    this.player1Score = new Text();
    this.player2Score = new Text();
    setupLayout();
  }

  /**
   * Setup layout.
   */
  private void setupLayout() {
    setPrefWidth(Board.BOARD_WIDTH);
    maxWidthProperty().bind(prefWidthProperty());
    setPrefHeight(50.0);
    maxHeightProperty().bind(prefHeightProperty());
    setPadding(new Insets(5.0));

    // Player1
    player1Score.setFill(Color.WHITE);
    player1Score.textProperty().bind(player1.scoreProperty());

    // Player2
    player2Score.setFill(Color.WHITE);
    player2Score.textProperty().bind(player2.scoreProperty());

    // tilesLeft = new Text();
    // tilesLeft.textProperty().bind(game.letterCollection.textProperty);
    getChildren().addAll(player1Score, player2Score);
    AnchorPane.setLeftAnchor(player1Score, 0.0);
    AnchorPane.setRightAnchor(player2Score, 0.0);
    AnchorPane.setBottomAnchor(player1Score, 0.0);
    AnchorPane.setBottomAnchor(player2Score, 0.0);
  }
}
