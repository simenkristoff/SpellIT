package spellit.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import spellit.ui.views.SplashView;

/**
 * The Class SplashController. Handles logic for the splashscreen / loading screen.
 */
public class SplashController implements Initializable {

  @FXML
  BorderPane splashWrapper;

  private static SplashView splashView;

  /**
   * Initialize.
   *
   * @param location the location
   * @param resources the resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.initializeComponents();
  }

  /**
   * Initialize components.
   */
  private void initializeComponents() {
    splashView = new SplashView();
    splashWrapper.setCenter(splashView);
  }

  /**
   * Sets the progress text.
   *
   * @param progress the text
   */
  public static void setProgressText(Double progress) {
    splashView.setProgressText(progress);
  }

  /**
   * Sets the progress value.
   *
   * @param value the progress
   */
  public static void setProgressValue(Double value) {
    splashView.setProgress(value);
  }

}
