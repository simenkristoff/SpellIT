package spellit.ui.views.dialogs;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;

/**
 * The Class AbstractDialog. Wrapper for all dialogs used in this application.
 *
 * @param <T> the generic type
 */
public abstract class AbstractDialog<T> {

  protected final JFXAlert<T> dialog;
  protected final StackPane root;
  protected final JFXDialogLayout layout;
  private final VBox contentWrapper;
  private final Text title;
  private final Text errorMsg;
  private JFXButton cancelBtn;
  private JFXButton okBtn;

  /**
   * Instantiates a new abstract dialog.
   *
   * @param root the root pane
   * @param okBtnText the Ok-button text
   * @param displayErrors whether to render error messages or not
   * @param hasCancelButton whether the dialog has a Cancel-button or not
   */
  public AbstractDialog(StackPane root, String okBtnText, boolean displayErrors,
      boolean hasCancelButton) {
    this.root = root;
    this.dialog = new JFXAlert<T>(root.getScene().getWindow());
    this.layout = new JFXDialogLayout();
    this.contentWrapper = new VBox();
    this.title = new Text();
    this.errorMsg = new Text();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setOverlayClose(false);
    this.setupLayout(displayErrors);
    this.setupButtons(okBtnText, hasCancelButton);
    dialog.setContent(layout);
  }

  /**
   * Sets up the layout.
   *
   * @param displayErrors whether to render error messages or not
   */
  private void setupLayout(boolean displayErrors) {
    this.layout.setMinWidth((root.getWidth() / 3) * 2);
    this.layout.setMinHeight(root.getHeight() / 2);
    this.layout.maxWidthProperty().bind(this.layout.minWidthProperty());
    this.layout.maxHeightProperty().bind(this.layout.minHeightProperty());
    title.setFill(Color.WHITE);
    errorMsg.getStyleClass().add("danger");
    layout.setHeading(title);
    layout.getStyleClass().add("dialog-dark");
    layout.setBody(contentWrapper);
    if (displayErrors) {
      this.contentWrapper.getChildren().add(errorMsg);
    }
  }

  /**
   * Setup buttons.
   *
   * @param okBtnText the Ok-button text
   * @param hasCancelButton whether the dialog has a Cancel-button or not
   */
  private void setupButtons(String okBtnText, boolean hasCancelButton) {
    if (hasCancelButton) {
      this.cancelBtn = new JFXButton("Avbryt");
      cancelBtn.setCancelButton(true);
      cancelBtn.getStyleClass().add("danger");
      cancelBtn.setOnAction(event -> dialog.close());
      layout.getActions().add(cancelBtn);
    }
    this.okBtn = new JFXButton(okBtnText);
    okBtn.setDefaultButton(true);
    layout.getActions().add(okBtn);
  }

  /**
   * Sets the title of the dialog.
   *
   * @param title the title
   */
  protected void setTitle(String title) {
    this.title.setText(title);
  }

  /**
   * Gets the Ok-button.
   *
   * @return the Ok-button
   */
  protected JFXButton getOkButton() {
    return this.okBtn;
  }

  /**
   * Sets the error message.
   *
   * @param error the new error message
   */
  protected void setError(String error) {
    this.errorMsg.setText(error);
  }

  /**
   * Sets the content of the dialog.
   *
   * @param elements the new content
   */
  protected void setContent(Node... elements) {
    this.contentWrapper.getChildren().addAll(elements);
  }

  /**
   * Setup dialog.
   */
  protected abstract void setupDialog();

  /**
   * Show dialog.
   *
   * @return the t
   */
  public abstract T showDialog();
}
