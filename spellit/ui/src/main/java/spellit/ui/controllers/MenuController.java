package spellit.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import spellit.core.models.Game;
import spellit.ui.App;
import spellit.ui.views.MainMenu;
import spellit.ui.views.dialogs.AbstractDialog;
import spellit.ui.views.dialogs.LoadGameDialog;
import spellit.ui.views.dialogs.NewGameDialog;

/**
 * The Class MenuController. Handles the Menu State logic.
 */
public class MenuController extends AbstractStateController {

  @FXML
  StackPane menuPane;

  @FXML
  AnchorPane menuWrapper;

  @FXML
  HBox innerWrapper;

  private MainMenu mainMenu;
  private AbstractDialog<?> popup;

  /**
   * Instantiates a new menu controller.
   *
   * @param app the app
   */
  public MenuController(App app) {
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
    this.initializeComponents();
    this.connectLogic();
  }

  /**
   * Initialize components.
   */
  private void initializeComponents() {
    mainMenu = new MainMenu();
    innerWrapper.getChildren().add(mainMenu);
    HBox.setHgrow(mainMenu, Priority.SOMETIMES);
  }

  /**
   * Connect logic.
   */
  private void connectLogic() {
    // New game
    mainMenu.getNewGameButton().setOnAction(event -> {
      popup = new NewGameDialog(menuPane);
      Game newGame = (Game) popup.showDialog();
      if (newGame != null) {
        app.setState(app.gameState);
        ((GameController) this.app.gameState.getController()).setGame(newGame);
      }
    });

    // Load game
    mainMenu.getLoadGameButton().setOnAction(event -> {
      popup = new LoadGameDialog(menuPane);
      Game loadedGame = (Game) popup.showDialog();
      if (loadedGame != null) {
        app.setState(app.gameState);
        ((GameController) this.app.gameState.getController()).setGame(loadedGame);
      }
    });

    // Quit game
    mainMenu.getQuitButton().setOnAction(event -> app.close());
  }

}
