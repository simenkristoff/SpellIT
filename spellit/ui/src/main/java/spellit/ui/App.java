package spellit.ui;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spellit.ui.controllers.GameController;

public class App extends Application {

	private GameController tileController = new GameController();

	@Override
	public void start(Stage stage) throws Exception {
		loadFonts();
		stage.setTitle("Spell It");
		stage.setScene(this.setupScene());
		stage.setWidth(720);
		stage.setHeight(664);
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
	}

	private Scene setupScene() {
		Scene scene = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setController(tileController);
		InputStream inputStream = null;
		try {
			inputStream = getClass().getResourceAsStream("views/app.fxml");
			scene = new Scene(loader.load(inputStream));
			scene.getStylesheets().setAll(this.getClass().getResource("css/styles.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scene;
	}

	private void loadFonts() {
		String[] robotoFonts = { "Black", "Bold", "Italic", "Light", "Medium", "Regular", "Thin" };
		for (String font : robotoFonts) {
			String fontStr = String.format("fonts/Roboto/Roboto-%s.ttf", font);
			Font.loadFont(this.getClass().getResource(fontStr).toExternalForm(), 10);
		}
		Font font = Font.loadFont(this.getClass().getResource("fonts/Digital-7/Digital-7.ttf").toExternalForm(), 10);
	}

	/**
	 * The main method. Starts the preloader and launches the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
