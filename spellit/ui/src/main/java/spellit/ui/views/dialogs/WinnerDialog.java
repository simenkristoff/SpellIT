package spellit.ui.views.dialogs;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import spellit.core.models.Player;
import spellit.ui.App;

/**
 * The Class WinnerDialog. Renders a dialog for displaying the winner of the current game instance.
 */
public class WinnerDialog extends AbstractDialog<Boolean> {

  private final App app;
  private final Player winner;

  /**
   * Instantiates a new winner dialog.
   *
   * @param root the root pane
   * @param app the app
   * @param winner the winner
   */
  public WinnerDialog(StackPane root, App app, Player winner) {
    super(root, "Til hovedmenyen", false, false);
    this.app = app;
    this.winner = winner;
    this.setTitle("Spillet er ferdig");
    this.setupDialog();
  }

  /**
   * Setup dialog.
   */
  @Override
  protected void setupDialog() {
    Text message = new Text(
        String.format("%s vant med %d poeng!", winner.getName(), winner.getPoints()));
    message.setFill(Color.WHITE);

    this.setContent(message);

    this.getOkButton().setOnAction(event -> {
      app.setState(app.menuState);
      dialog.close();
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
