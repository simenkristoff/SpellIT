package spellit.ui;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import spellit.ui.controllers.SplashController;

/**
 * The Class Preloader. Displays a loading screen while App is booting up
 */
public class SplashScreen extends Preloader {

  private Stage preloaderStage;
  private Scene scene;

  /**
   * Initializes the Preloader. Loads the .fxml-file and initializes a new Scene.
   *
   * @throws Exception the exception
   */
  public void init() throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("views/splash.fxml"));
    scene = new Scene(root);
  }

  /**
   * Setup the stage and start the preloader.
   *
   * @param stage the stage
   * @throws Exception the exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    this.preloaderStage = stage;
    preloaderStage.setScene(scene);
    preloaderStage.initStyle(StageStyle.UNDECORATED);
    preloaderStage.show();
  }

  /**
   * Handle notifications given to the preloader. Will update the label text and progress bar.
   *
   * @param info the info
   */
  public void handleApplicationNotification(PreloaderNotification info) {
    if (info instanceof ProgressNotification) {
      double progress = ((ProgressNotification) info).getProgress();
      SplashController.setProgressText((progress * 100));
      SplashController.setProgressValue(progress);
    }
  }

  /**
   * Handle state change notification. Hides the preloader if the application is about to start.
   *
   * @param info the info
   */
  public void handleStateChangeNotification(StateChangeNotification info) {

    StateChangeNotification.Type type = info.getType();

    switch (type) {
      case BEFORE_START:
        preloaderStage.hide();
        break;
      default:
        break;
    }
  }

}