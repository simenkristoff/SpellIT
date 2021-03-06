package spellit.ui;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import spellit.ui.controllers.AbstractStateController;

public class State {

  private String fxml;
  private AbstractStateController controller;

  State(String fxml, AbstractStateController controller) {
    this.fxml = fxml;
    this.controller = controller;
  }

  public AbstractStateController getController() {
    return this.controller;
  }

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
