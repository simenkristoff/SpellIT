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

  private String id;
  private AbstractStateController controller;

  /**
   * Instantiates a new state.
   *
   * @param id the state id
   * @param controller the controller
   */
  State(String id, AbstractStateController controller) {
    this.id = id;
    this.controller = controller;
  }

  /**
   * Gets the state id.
   * 
   * @return the id
   */
  public String getId() {
    return this.id;
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
    try (InputStream in = App.class.getResourceAsStream(String.format("views/%s.fxml", this.id))) {
      region = loader.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return region;
  }
}
