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

public class Scoreboard extends AnchorPane {

  private final Game game;
  private final Player player1;
  private final Player player2;
  private final Text player1Score;
  private final Text player2Score;

  public Scoreboard(GameController controller) {
    this.game = controller.getGame();
    List<Player> players = game.getPlayers();
    this.player1 = players.get(0);
    this.player2 = players.get(1);
    this.player1Score = new Text();
    this.player2Score = new Text();
    setupLayout();
  }

  private void setupLayout() {
    this.setId("scoreboard");
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
