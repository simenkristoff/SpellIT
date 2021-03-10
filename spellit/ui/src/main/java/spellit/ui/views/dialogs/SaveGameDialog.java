package spellit.ui.views.dialogs;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.StackPane;
import spellit.core.models.Game;
import spellit.core.persistence.FileHandler;

/**
 * The Class SaveGameDialog. Renders a dialog for saving the current game instance.
 */
public class SaveGameDialog extends AbstractDialog<Boolean> {

  private final Game game;

  /**
   * Instantiates a new save game dialog.
   *
   * @param root the root pane
   * @param game the game
   */
  public SaveGameDialog(StackPane root, Game game) {
    super("save_game-dialog", root, "Lagre", true, true);
    this.game = game;
    this.setTitle("Lagre spill");
    this.setupDialog();
  }

  /**
   * Setup dialog.
   */
  @Override
  protected void setupDialog() {
    JFXTextField filenameInput = new JFXTextField();
    filenameInput.setPromptText("Lagre spillet som");

    this.setContent(filenameInput);

    this.getOkButton().setOnAction(event -> {
      String filename = filenameInput.getText();
      if (filename.length() < 1) {
        this.setError("Tast inn et gyldig navn");
      } else {
        FileHandler.saveGame(game, filename);
        dialog.close();
      }
    });
  }

  /**
   * Show dialog.
   *
   * @return True, if successful
   */
  @Override
  public Boolean showDialog() {
    dialog.show();
    return true;
  }
}
