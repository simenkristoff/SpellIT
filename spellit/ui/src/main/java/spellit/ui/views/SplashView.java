package spellit.ui.views;

import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import spellit.ui.App;

/**
 * The Class SplashView. Renders the loading screen while the app is booting up.
 */
public class SplashView extends VBox {

  private static final String textFormat = "Laster: %.1f%%";
  private final Text progressText;
  private final ProgressBar progressBar;

  /**
   * Instantiates a new splash view.
   */
  public SplashView() {
    this.setId("splash-view");
    this.progressText = new Text();
    this.progressBar = new ProgressBar();
    this.setupLayout();
  }

  /**
   * Setup layout.
   */
  private void setupLayout() {
    this.setAlignment(Pos.CENTER);
    progressBar.setMinHeight(20.0);
    progressBar.setMinWidth(200.0);
    getChildren().addAll(progressText, progressBar);
  }

  /**
   * Sets the progress text.
   *
   * @param progress the new progress text
   */
  public void setProgressText(Double progress) {
    this.progressText.setText(String.format(Locale.ROOT, textFormat, progress));
  }

  /**
   * Sets the progress value.
   *
   * @param value the new progress
   */
  public void setProgress(Double value) {
    this.progressBar.setProgress(value);
  }

  /**
   * Gets the stylesheet.
   *
   * @return the user agent stylesheet
   */
  public String getUserAgentStylesheet() {
    return App.class.getResource("css/splash.css").toExternalForm();
  }
}
