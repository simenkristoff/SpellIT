package spellit.ui.controllers;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
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
import spellit.core.models.Letter;
import spellit.ui.App;
import spellit.ui.views.GridTile;
import spellit.ui.views.PlayerTile;

@TestMethodOrder(OrderAnnotation.class)
public class GameControllerTest extends ApplicationTest {

  private final String[] args = { "game", "test" };

  // Component selectors
  private String gridContainerSelector = "#gridContainer";
  private String playerRackSelector = "#rackWrapper";
  private String scoreboardSelector = "#scoreboard";
  private String sidebarSelector = "#sidebar";

  // Dialog selectors
  private String letterDialogSelector = "#letter-dialog";
  private String saveGameDialogSelector = "#save_game-dialog";
  private String newGameDialogSelector = "#new_game-dialog";
  private String loadGameDialogSelector = "#load_game-dialog";
  private String warningDialogSelector = "#warning-dialog";

  // Button selectors
  private String finishRoundBtnSelector = ".finish_round-button";
  private String passRoundBtnSelector = ".pass_round-button";
  private String saveGameBtnSelector = ".save_game-button";
  private String loadGameBtnSelector = ".load_game-button";
  private String newGameBtnSelector = ".new_game-button";
  private String swapTilesBtnSelector = ".swap_tiles-button";
  private String closeBtnSelector = ".close-button";
  private String submitBtnSelector = ".submit-button";

  // String selectors
  private String currentPlayerTextSelector = ".current_player-label";

  /**
   * Runs the App for testing environment.
   *
   * @throws Exception the exception
   */
  @BeforeEach
  public void runAppToTests() throws Exception {
    FxToolkit.registerPrimaryStage();
    FxToolkit.setupApplication(App.class, args);
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
  @DisplayName("Should render correct components")
  public void renderTest() {
    WaitForAsyncUtils.waitForFxEvents(100);
    assertNotNull(lookup(gridContainerSelector).query());
    assertNotNull(lookup(playerRackSelector).query());
    assertNotNull(lookup(scoreboardSelector).query());
    assertNotNull(lookup(sidebarSelector).query());
  }

  @Test
  @Order(2)
  @DisplayName("Should open correct dialogs with sidebar buttons")
  public void sidebarDialogTest() {
    WaitForAsyncUtils.waitForFxEvents(100);
    // Save game dialog
    Button saveGameBtn = lookup(saveGameBtnSelector).query();
    clickOn(saveGameBtn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node saveGameDialog = lookup(saveGameDialogSelector).query();
    assertNotNull(saveGameDialog);
    clickOn(saveGameDialog.lookup(closeBtnSelector));
    WaitForAsyncUtils.waitForFxEvents(100);

    // Load game dialog
    Button loadGameBtn = lookup(loadGameBtnSelector).query();
    clickOn(loadGameBtn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node loadGameDialog = lookup(loadGameDialogSelector).query();
    assertNotNull(loadGameDialog);
    clickOn(loadGameDialog.lookup(closeBtnSelector));
    WaitForAsyncUtils.waitForFxEvents(100);

    // New game dialog
    Button newGameBtn = lookup(newGameBtnSelector).query();
    clickOn(newGameBtn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node newGameDialog = lookup(newGameDialogSelector).query();
    assertNotNull(newGameDialog);
    clickOn(newGameDialog.lookup(closeBtnSelector));
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  @Test
  @Order(3)
  @DisplayName("Should open warning dialog when finishing round with no placed tiles")
  public void emptyTilesWarningDialogTest() {
    WaitForAsyncUtils.waitForFxEvents(100);
    Button finishRoundBtn = lookup(finishRoundBtnSelector).query();
    clickOn(finishRoundBtn);
    WaitForAsyncUtils.waitForFxEvents(100);
    Node warningDialog = lookup(warningDialogSelector).query();
    assertNotNull(warningDialog);
    clickOn(warningDialog.lookup(submitBtnSelector));
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  @Test
  @Order(4)
  @DisplayName("Should pass the round")
  public void passRoundTest() {
    String currentPlayer1 = ((Text) lookup(sidebarSelector + " " + currentPlayerTextSelector)
        .query()).getText();

    Button passRoundBtn = lookup(passRoundBtnSelector).query();
    clickOn(passRoundBtn);
    WaitForAsyncUtils.waitForFxEvents(100);

    String currentPlayer2 = ((Text) lookup(sidebarSelector + " " + currentPlayerTextSelector)
        .query()).getText();
    assertNotEquals(currentPlayer1, currentPlayer2);
  }

  @Test
  @Order(5)
  @DisplayName("Should swap player tiles")
  public void swapPlayerTilesTest() {
    Set<Node> playerTileQuery1 = lookup(playerRackSelector + " .tile").queryAll();
    ArrayList<Letter> letters1 = new ArrayList<Letter>();
    playerTileQuery1.forEach(tile -> {
      letters1.add(((PlayerTile) tile).getLetter());
    });

    WaitForAsyncUtils.waitForFxEvents(100);
    Button swapTilesBtn = lookup(swapTilesBtnSelector).query();
    clickOn(swapTilesBtn);
    WaitForAsyncUtils.waitForFxEvents(100);

    Set<Node> playerTileQuery2 = lookup(playerRackSelector + " .tile").queryAll();
    ArrayList<Letter> letters2 = new ArrayList<Letter>();
    playerTileQuery2.forEach(tile -> {
      if (tile instanceof PlayerTile) {
        letters2.add(((PlayerTile) tile).getLetter());
      }
    });

    assertNotEquals(letters1, letters2);

  }

  @Test
  @Order(6)
  @DisplayName("Should place tiles and proceed to next round")
  public void dragAndDropTest() {
    String currentPlayer1 = ((Text) lookup(sidebarSelector + " " + currentPlayerTextSelector)
        .query()).getText();

    // GridTiles
    Set<Node> gridTileQuery = lookup(gridContainerSelector + " .tile").queryAll();
    ArrayList<GridTile> gridTiles = new ArrayList<GridTile>();
    gridTileQuery.forEach(tile -> {
      if (tile instanceof GridTile) {
        gridTiles.add((GridTile) tile);
      }
    });

    // Player tiles
    Set<Node> playerTileQuery = lookup(playerRackSelector + " .tile").queryAll();
    ArrayList<PlayerTile> playerTiles = new ArrayList<PlayerTile>();
    playerTileQuery.forEach(tile -> {
      if (tile instanceof PlayerTile) {
        playerTiles.add((PlayerTile) tile);
      }
    });
    for (int i = 0; i < 3; i++) {
      Node target = gridTiles.get(112 + i);
      drag(playerTiles.get(i), MouseButton.PRIMARY).interact(() -> dropTo(target));
      if (i == 1) {
        WaitForAsyncUtils.waitForFxEvents(100);
        Node letterDialog = lookup(letterDialogSelector).query();
        assertNotNull(letterDialog);

        // Select character A
        Node node = lookup(letterDialogSelector + " .tile").query();
        clickOn(node);
        WaitForAsyncUtils.waitForFxEvents(100);

        // Close letter dialog
        clickOn(letterDialog.lookup(submitBtnSelector));
        WaitForAsyncUtils.waitForFxEvents(100);
      }
    }

    Button finishRoundBtn = lookup(finishRoundBtnSelector).query();
    clickOn(finishRoundBtn);
    WaitForAsyncUtils.waitForFxEvents(100);

    String currentPlayer2 = ((Text) lookup(sidebarSelector + " " + currentPlayerTextSelector)
        .query()).getText();
    assertNotEquals(currentPlayer1, currentPlayer2);

  }

}
