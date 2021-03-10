package spellit.ui.views.dialogs;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The Class WarningDialog. Renders a warning message.
 */
public class WarningDialog extends AbstractDialog<Boolean> {

  protected Text message;

  /**
   * Instantiates a new warning dialog.
   *
   * @param root the root pane
   * @param message the message
   */
  public WarningDialog(StackPane root, String message) {
    super("warning-dialog", root, "Ok", false, false);
    this.message = new Text(message);
    this.setTitle("Advarsel");
    this.setupDialog();
  }

  /**
   * Setup dialog.
   */
  @Override
  protected void setupDialog() {
    message.setFill(Color.WHITE);

    this.setContent(message);

    this.getOkButton().setOnAction(event -> dialog.close());

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
