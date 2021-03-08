package spellit.ui.views.dialogs;

import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import spellit.core.models.Game;
import spellit.core.persistence.FileHandler;

/**
 * The Class LoadGameDialog. Renders a dialog for loading previously saved game instances.
 */
public class LoadGameDialog extends AbstractDialog<Game> {

  /**
   * Instantiates a new load game dialog.
   *
   * @param root the root pane
   */
  public LoadGameDialog(StackPane root) {
    super(root, "Last inn", true, true);
    this.setTitle("Last inn spill");
    this.setupDialog();
  }

  /**
   * Setup dialog.
   */
  @Override
  protected void setupDialog() {

    JFXListView<Label> filesList = new JFXListView<Label>();
    try {
      for (File file : FileHandler.getSaveFiles()) {
        filesList.getItems().add(new Label(file.getName()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setContent(filesList);

    this.getOkButton().setOnAction(event -> {
      if (filesList.getSelectionModel().getSelectedItem() == null) {
        this.setError("Du må velge en fil å laste inn");
        dialog.setResult(null);
      } else {
        String filename = filesList.getSelectionModel().getSelectedItem().getText();
        try {
          dialog.setResult(FileHandler.loadGame(filename));
          dialog.hide();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    dialog.setResultConverter(buttonType -> {
      if (buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
        dialog.setResult(null);
      }
      return dialog.getResult();
    });

  }

  /**
   * Show dialog.
   *
   * @return the game
   */
  @Override
  public Game showDialog() {
    Optional<Game> result = dialog.showAndWait();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

}
