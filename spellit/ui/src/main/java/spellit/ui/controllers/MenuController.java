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
import spellit.ui.views.PopupDialog;

public class MenuController extends AbstractStateController {

	@FXML
	StackPane menuPane;

	@FXML
	AnchorPane menuWrapper;

	@FXML
	HBox innerWrapper;

	private MainMenu mainMenu;
	private PopupDialog popup;

	public MenuController(App app) {
		super(app);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.popup = new PopupDialog(this.menuPane);
		this.initializeComponents();
		this.connectLogic();
	}

	private void initializeComponents() {
		mainMenu = new MainMenu();
		innerWrapper.getChildren().add(mainMenu);
		HBox.setHgrow(mainMenu, Priority.SOMETIMES);
	}

	private void connectLogic() {
		// New game
		mainMenu.getNewGameButton().setOnAction(event -> {
			Game newGame = popup.showNewGameDialog();
			if (newGame != null) {
				app.setState(app.GAME);
				((GameController) this.app.GAME.getController()).setGame(newGame);
			}
		});

		// Load game
		mainMenu.getLoadGameButton().setOnAction(event -> {
			Game loadedGame = popup.showLoadGameDialog();
			if (loadedGame != null) {
				app.setState(app.GAME);
				((GameController) this.app.GAME.getController()).setGame(loadedGame);
			}
		});

		// Quit game
		mainMenu.getQuitButton().setOnAction(event -> app.close());
	}

}
