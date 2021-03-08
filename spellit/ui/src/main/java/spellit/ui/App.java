package spellit.ui;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import spellit.ui.controllers.GameController;
import spellit.ui.controllers.MenuController;

/**
 * The Class App.
 */
public class App extends Application {

  public static final String TITLE = "Spell IT";
  public static final double WIDTH = 720;
  public static final double HEIGHT = 664;
  private static final int LOAD_COUNTER = 25;
  public final State menuState = new State("menu", new MenuController(this));
  public final State gameState = new State("game", new GameController(this));

  @FXML
  BorderPane container;

  private Scene scene;
  private FXMLLoader loader;

  /**
   * Start the application.
   *
   * @param stage the stage
   * @throws Exception the exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    loadFonts();
    stage.setTitle(TITLE);
    stage.setScene(this.setupScene());
    stage.setWidth(WIDTH);
    stage.setHeight(HEIGHT);
    stage.setMaximized(false);
    stage.setResizable(false);
    stage.centerOnScreen();
    setState(menuState);
    stage.show();

    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        close();
      }
    });
  }

  /**
   * Displays a splashscreen loader while the app is initializing.
   *
   * @throws Exception the exception
   */
  public void init() throws Exception {
    for (int i = 0; i < LOAD_COUNTER; i++) {
      double progress = (double) i / LOAD_COUNTER;
      this.notifyPreloader(new Preloader.ProgressNotification(progress));
      Thread.sleep(100);
    }
  }

  /**
   * Setup the scene.
   *
   * @return the scene
   */
  private Scene setupScene() {
    loader = new FXMLLoader();
    loader.setController(this);
    try (InputStream in = getClass().getResourceAsStream("views/app.fxml")) {
      scene = new Scene(loader.load(in));
      scene.getStylesheets().setAll(getClass().getResource("css/styles.css").toExternalForm());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return scene;
  }

  /**
   * Sets the state.
   *
   * @param state the new state
   */
  public void setState(State state) {
    this.container.setCenter(state.loadView());
  }

  /**
   * Load fonts.
   */
  private void loadFonts() {
    String[] robotoFonts = { "Black", "Bold", "Italic", "Light", "Medium", "Regular", "Thin" };
    for (String font : robotoFonts) {
      String fontStr = String.format("fonts/Roboto/Roboto-%s.ttf", font);
      Font.loadFont(this.getClass().getResource(fontStr).toExternalForm(), 10);
    }
    Font.loadFont(this.getClass().getResource("fonts/Digital-7/Digital-7.ttf").toExternalForm(),
        10);
  }

  /**
   * Closes the application.
   *
   */
  public void close() {
    Platform.exit();
  }

  /**
   * The main method. Starts the preloader and launches the application.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    System.setProperty("javafx.preloader", SplashScreen.class.getCanonicalName());
    Application.launch(args);
  }

}
