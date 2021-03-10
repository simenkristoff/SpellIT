package spellit.ui.views;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * The Class MainMenu. Renders the main menu.
 */
public class MainMenu extends VBox {

  private final JFXButton newGameBtn;
  private final JFXButton loadGameBtn;
  private final JFXButton quitBtn;

  /**
   * Instantiates a new main menu.
   */
  public MainMenu() {
    this.setId("main-menu");
    this.newGameBtn = new JFXButton("Nytt spill");
    newGameBtn.getStyleClass().add("new_game-button");
    this.loadGameBtn = new JFXButton("Last inn spill");
    loadGameBtn.getStyleClass().add("load_game-button");
    this.quitBtn = new JFXButton("Avslutt spillet");
    quitBtn.getStyleClass().add("quit-button");
    this.setupLayout();
  }

  /**
   * Setup layout.
   */
  private void setupLayout() {
    setPadding(new Insets(5.0));
    this.setSpacing(10.0);
    setAlignment(Pos.TOP_CENTER);

    // Setup title
    Text gameTitle = new Text("SpellIT");
    gameTitle.getStyleClass().add("game-title");
    gameTitle.setTextAlignment(TextAlignment.CENTER);
    getChildren().add(gameTitle);
    VBox.setMargin(gameTitle, new Insets(20, 0, 20, 0));

    // Setup buttons
    newGameBtn.setMaxWidth(250);
    newGameBtn.prefWidthProperty().bind(this.widthProperty());
    loadGameBtn.setMaxWidth(250);
    loadGameBtn.prefWidthProperty().bind(this.widthProperty());
    quitBtn.setMaxWidth(250);
    quitBtn.prefWidthProperty().bind(this.widthProperty());
    getChildren().addAll(newGameBtn, loadGameBtn, quitBtn);
    this.setAlignment(Pos.CENTER);
  }

  /**
   * Gets the new game button.
   *
   * @return the new game button
   */
  public JFXButton getNewGameButton() {
    return this.newGameBtn;
  }

  /**
   * Gets the load game button.
   *
   * @return the load game button
   */
  public JFXButton getLoadGameButton() {
    return this.loadGameBtn;
  }

  /**
   * Gets the quit button.
   *
   * @return the quit button
   */
  public JFXButton getQuitButton() {
    return this.quitBtn;
  }
}
