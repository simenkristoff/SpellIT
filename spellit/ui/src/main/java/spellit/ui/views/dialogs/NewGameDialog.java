package spellit.ui.views.dialogs;

import com.jfoenix.controls.JFXTextField;
import java.util.Optional;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.StackPane;
import spellit.core.models.Game;

/**
 * The Class NewGameDialog. Renders a dialog for starting a new game instance.
 */
public class NewGameDialog extends AbstractDialog<Game> {

  /**
   * Instantiates a new new game dialog.
   *
   * @param root the root pane
   */
  public NewGameDialog(StackPane root) {
    super(root, "Start nytt spill", true, true);
    this.setTitle("Start nytt spill");
    this.setupDialog();
  }

  /**
   * Setup dialog.
   */
  @Override
  protected void setupDialog() {
    JFXTextField player1Name = new JFXTextField();
    player1Name.setPromptText("Spiller 1");
    JFXTextField player2Name = new JFXTextField();
    player2Name.setPromptText("Spiller 2");

    this.setContent(player1Name, player2Name);

    this.getOkButton().setOnAction(event -> {
      if (player1Name.getText().length() < 1 || player2Name.getText().length() < 1) {
        this.setError("Vennligst fyll inn navn på begge spillerne");
        dialog.setResult(null);
      } else {
        dialog.setResult(new Game(player1Name.getText(), player2Name.getText()));
        dialog.hide();
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
