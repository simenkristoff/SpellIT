package spellit.ui.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jfoenix.controls.JFXTextField;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import spellit.ui.App;

@TestMethodOrder(OrderAnnotation.class)
public class MenuControllerTest extends ApplicationTest {

  // Component selectors
  private String gridContainerSelector = "#gridContainer";

  // Input selectors
  private String player1InputSelector = "#player1-inputfield";
  private String player2InputSelector = "#player2-inputfield";

  // Dialog selectors
  private String newGameDialogSelector = "#new_game-dialog";
  private String loadGameDialogSelector = "#load_game-dialog";

  // Button selectors
  private String newGameBtnSelector = ".new_game-button";
  private String loadGameBtnSelector = ".load_game-button";
  private String quitBtnSelector = ".quit-button";
  private String closeBtnSelector = ".close-button";
  private String submitBtnSelector = ".submit-button";

  // String selectors
  private String errorMessageSelector = ".error-message";

  /**
   * Runs the App for testing environment.
   *
   * @throws Exception the exception
   */
  @BeforeEach
  public void runAppToTests() throws Exception {
    FxToolkit.registerPrimaryStage();
    FxToolkit.setupApplication(App::new);
    FxToolkit.showStage();
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  /**
   * Start the app.
   *
   * @param primaryStage the primary stage
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.toFront();
  }

  /**
   * Stop the app.
   *
   * @throws TimeoutException the timeout exception
   */
  @AfterEach
  public void stopApp() throws TimeoutException {
    FxToolkit.cleanupStages();
  }

  @Test
  @Order(1)
  @DisplayName("Should render all buttons")
  public void renderTest() {
    assertNotNull(lookup(newGameBtnSelector).query());
    assertNotNull(lookup(loadGameBtnSelector).query());
    assertNotNull(lookup(quitBtnSelector).query());
  }

  @Test
  @Order(2)
  @DisplayName("Should open new game dialog")
  public void newGameDialogTest() {
    Button btn = lookup(newGameBtnSelector).query();
    clickOn(btn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node dialog = lookup(newGameDialogSelector).query();
    assertNotNull(dialog);
    assertNotNull(dialog.lookup(submitBtnSelector));

    // Close dialog
    Button closeBtn = (Button) dialog.lookup(closeBtnSelector);
    clickOn(closeBtn);
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  @Test
  @Order(3)
  @DisplayName("Should open load game dialog and display error on loading 'null'-file")
  public void loadGameDialogTest() {
    Button btn = lookup(loadGameBtnSelector).query();
    clickOn(btn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node dialog = lookup(loadGameDialogSelector).query();
    assertNotNull(dialog);
    assertNotNull(dialog.lookup(submitBtnSelector));

    // Load game with no selected file
    Button submitBtn = (Button) dialog.lookup(submitBtnSelector);
    clickOn(submitBtn);

    assertNotNull(dialog.lookup(errorMessageSelector));

    // Close dialog
    Button closeBtn = (Button) dialog.lookup(closeBtnSelector);
    clickOn(closeBtn);
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  @Test
  @Order(4)
  @DisplayName("Should start a new game")
  public void startNewGameTest() {
    Button btn = lookup(newGameBtnSelector).query();
    clickOn(btn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node dialog = lookup(newGameDialogSelector).query();
    JFXTextField input1 = (JFXTextField) dialog.lookup(player1InputSelector);
    JFXTextField input2 = (JFXTextField) dialog.lookup(player2InputSelector);

    // Type player 1 name
    clickOn(input1).write("Player1");
    WaitForAsyncUtils.waitForFxEvents(100);

    // Type player 2 name
    clickOn(input2).write("Player2");
    WaitForAsyncUtils.waitForFxEvents(100);

    // Instatiate new game
    Button submitBtn = (Button) dialog.lookup(submitBtnSelector);
    clickOn(submitBtn);
    WaitForAsyncUtils.waitForFxEvents(100);

    assertNotNull(lookup(gridContainerSelector).query());
  }

}
