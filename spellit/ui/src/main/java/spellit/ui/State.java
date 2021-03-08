package spellit.ui;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import spellit.ui.controllers.AbstractStateController;

/**
 * The Class State. Wrapper class for managing the view and controller for any game state.
 */
public class State {

  private String fxml;
  private AbstractStateController controller;

  /**
   * Instantiates a new state.
   *
   * @param fxml the fxml
   * @param controller the controller
   */
  State(String fxml, AbstractStateController controller) {
    this.fxml = fxml;
    this.controller = controller;
  }

  /**
   * Gets the controller of the state.
   *
   * @return the controller
   */
  public AbstractStateController getController() {
    return this.controller;
  }

  /**
   * Load the state's view.
   *
   * @return the view
   */
  public Region loadView() {
    FXMLLoader loader = new FXMLLoader();
    loader.setController(this.controller);
    Region region = null;
    try (
        InputStream in = App.class.getResourceAsStream(String.format("views/%s.fxml", this.fxml))) {
      region = loader.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return region;
  }
}
