package spellit.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import spellit.core.exceptions.TurnException;
import spellit.core.models.Game;
import spellit.core.models.Player;
import spellit.ui.App;
import spellit.ui.views.AbstractTile;
import spellit.ui.views.GridContainer;
import spellit.ui.views.PlayerRack;
import spellit.ui.views.Scoreboard;
import spellit.ui.views.Sidebar;
import spellit.ui.views.dialogs.AbstractDialog;
import spellit.ui.views.dialogs.LoadGameDialog;
import spellit.ui.views.dialogs.NewGameDialog;
import spellit.ui.views.dialogs.SaveGameDialog;
import spellit.ui.views.dialogs.WarningDialog;
import spellit.ui.views.dialogs.WinnerDialog;

/**
 * The Class GameController. Handles the Game State logic.
 */
public class GameController extends AbstractStateController {

  @FXML
  StackPane gamePane;

  @FXML
  HBox gameWrapper;

  @FXML
  AnchorPane sidebarWrapper;

  @FXML
  VBox boardWrapper;

  private Game game;
  private Scoreboard scoreboard;
  private Sidebar sidebar;
  private GridContainer gridContainer;
  private PlayerRack playerRack;
  private AbstractDialog<?> popup;

  /**
   * Instantiates a new game controller.
   *
   * @param app the app
   */
  public GameController(App app) {
    super(app);
  }

  /**
   * Initialize.
   *
   * @param location the location
   * @param resources the resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  /**
   * Gets the game.
   *
   * @return the game
   */
  public Game getGame() {
    return this.game;
  }

  /**
   * Sets the game.
   *
   * @param game the new game
   */
  public void setGame(Game game) {
    this.game = game;
    this.clear();
    this.initializeComponents();
    this.setupListeners();
    this.connectLogic();
  }

  /**
   * Initialize components.
   */
  private void initializeComponents() {
    HBox.setHgrow(sidebarWrapper, Priority.SOMETIMES);

    // Setup Scoreboard
    scoreboard = new Scoreboard(this);
    boardWrapper.getChildren().add(scoreboard);
    VBox.setVgrow(scoreboard, Priority.NEVER);

    // Setup Sidebar
    sidebar = new Sidebar(this);
    sidebarWrapper.getChildren().add(sidebar);
    AnchorPane.setLeftAnchor(sidebar, 0.0);
    AnchorPane.setRightAnchor(sidebar, 0.0);
    AnchorPane.setTopAnchor(sidebar, 35.0);

    // Setup Grid Container
    gridContainer = new GridContainer(this);
    boardWrapper.getChildren().add(gridContainer);
    VBox.setVgrow(gridContainer, Priority.ALWAYS);

    // Initialize Player Rack
    playerRack = new PlayerRack(this);
    boardWrapper.getChildren().add(playerRack);
    VBox.setVgrow(playerRack, Priority.NEVER);
  }

  /**
   * Setup listeners.
   */
  private void setupListeners() {
    game.addListener(playerRack, gridContainer, sidebar);
    game.winnerProperty().addListener(new ChangeListener<Player>() {

      @Override
      public void changed(ObservableValue<? extends Player> observable, Player oldValue,
          Player newValue) {
        if (newValue != null) {
          popup = new WinnerDialog(gamePane, app, newValue);
          popup.showDialog();
        }
      }

    });
  }

  /**
   * Clear the rendered components.
   */
  private void clear() {
    boardWrapper.getChildren().removeAll(scoreboard, gridContainer, playerRack);
    sidebarWrapper.getChildren().remove(sidebar);
  }

  /**
   * Gets the intersection of mouse and any draggable tiles.
   *
   * @param event the event
   * @return the intersecting tile
   */
  public AbstractTile getIntersection(DragEvent event) {
    double x = event.getSceneX();
    double y = event.getSceneY();
    if (gridContainer.mouseIntersection(x, y)) {
      return gridContainer.getIntersectingTile(x, y);
    } else if (playerRack.mouseIntersection(x, y)) {
      return playerRack.getIntersectingTile(x, y);
    }
    return null;
  }

  /**
   * Connect logic.
   */
  private void connectLogic() {

    // Finish round
    sidebar.getFinishRoundButton().setOnAction(event -> {
      try {
        game.board.processTurn();
        game.initiateNextTurn();
      } catch (TurnException e) {
        popup = new WarningDialog(gamePane, e.getMessage());
        popup.showDialog();
      }
    });
    // Pass round
    sidebar.getPassRoundButton().setOnAction(event -> {
      game.initiateNextTurn();
    });

    // Save game
    sidebar.getSaveGameButton().setOnAction(event -> {
      popup = new SaveGameDialog(gamePane, this.game);
      popup.showDialog();
    });

    // Load game
    sidebar.getLoadGameButton().setOnAction(event -> {
      popup = new LoadGameDialog(gamePane);
      Game loadedGame = (Game) popup.showDialog();
      if (loadedGame != null) {
        this.setGame(loadedGame);
      }
    });

    // New game
    sidebar.getNewGameButton().setOnAction(event -> {
      popup = new NewGameDialog(gamePane);
      Game newGame = (Game) popup.showDialog();
      if (newGame != null) {
        this.setGame(newGame);
      }
    });

    // Menu
    sidebar.getMenuButton().setOnAction(event -> {
      app.setState(app.menuState);
    });

    // Swap letters
    playerRack.getSwapButton().setOnAction(event -> {
      game.getCurrentPlayer().swapLetters();
      game.initiateNextTurn();
    });
  }

  /**
   * Gets the root pane.
   *
   * @return the root pane
   */
  public StackPane getRootPane() {
    return this.gamePane;
  }
}
