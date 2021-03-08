package spellit.ui.views;

import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import spellit.core.events.NextTurnListener;
import spellit.core.models.Board;
import spellit.core.models.Game;
import spellit.core.models.Letter;
import spellit.core.models.Player;
import spellit.ui.controllers.GameController;
import spellit.ui.interfaces.TileContainerInterface;

/**
 * The Class PlayerRack. Renders the a collection of tiles with letters drawn by the player.
 *
 * @see Player
 * @see PlayerTile
 */
public class PlayerRack extends HBox implements NextTurnListener, TileContainerInterface {

  public static final double TILE_SIZE = 40.0;
  private final GameController controller;
  private final Game game;
  private final GridPane rack;
  private final JFXButton swapBtn;
  private ArrayList<PlayerTile> tiles;
  private Player player;

  /**
   * Instantiates a new player rack.
   *
   * @param controller the controller
   */
  public PlayerRack(GameController controller) {
    this.setId("rackWrapper");
    this.controller = controller;
    this.game = controller.getGame();
    this.player = game.getCurrentPlayer();
    this.rack = new GridPane();
    this.swapBtn = new JFXButton();
    setupLayout();
    setupConstraints();
    setupInitialRack();
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
    setAlignment(Pos.CENTER);

    // Setup player rack
    rack.setId("playerRack");
    getChildren().add(rack);

    // Setup swap button
    swapBtn.setMinWidth(TILE_SIZE);
    swapBtn.setMinHeight(TILE_SIZE);
    swapBtn.getStyleClass().add("btn-swap");
    Region icon = new Region();
    icon.getStyleClass().add("swap-icon");
    icon.setPrefSize(TILE_SIZE, TILE_SIZE);
    swapBtn.setGraphic(icon);
    getChildren().add(swapBtn);
    setMargin(swapBtn, new Insets(0, 0, 0, 5));

  }

  /**
   * Setup constraints.
   */
  private void setupConstraints() {
    rack.getRowConstraints().add(
        new RowConstraints(TILE_SIZE, TILE_SIZE, TILE_SIZE, Priority.ALWAYS, VPos.CENTER, true));
    for (int j = 0; j < 7; j++) {
      rack.getColumnConstraints().add(new ColumnConstraints(TILE_SIZE, TILE_SIZE, TILE_SIZE,
          Priority.ALWAYS, HPos.CENTER, true));
    }
  }

  /**
   * Gets the swap button.
   *
   * @return the swap button
   */
  public JFXButton getSwapButton() {
    return this.swapBtn;
  }

  /**
   * Setup initial rack. Draws initial letters from the letter collection, and initiates each player
   * tile.
   */
  private void setupInitialRack() {
    tiles = new ArrayList<PlayerTile>();
    for (Player player : game.getPlayers()) {
      if (player.getLetters().isEmpty()) {
        player.drawInitialLetters();
      }
    }
    for (int i = 0; i < 7; i++) {
      tiles.add(new PlayerTile(this.controller, TILE_SIZE));
      rack.add(tiles.get(i), i, 0);
    }
    setupRackLetters();
  }

  /**
   * Setup rack letters. Changes the letters in the rack based on which player is currently playing.
   */
  private void setupRackLetters() {
    int i = 0;
    for (Letter letter : player.getLetters()) {
      if (letter == null) {
        tiles.get(i).removeLetter();
      } else {
        tiles.get(i).setLetter(letter);
      }

      i++;
    }
  }

  /**
   * On next turn.
   */
  @Override
  public void onNextTurn() {
    int index = 0;
    // Replace used letters
    for (PlayerTile tile : tiles) {
      if (!tile.hasLetter()) {
        player.drawLetter(index);
      }
      index++;
    }
    // Change current player.
    this.player = game.getCurrentPlayer();
    setupRackLetters();
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
    return tiles
        .get((int) Math.floor((mouseX - this.rack.getBoundsInParent().getMinX()) / TILE_SIZE));
  }

}
