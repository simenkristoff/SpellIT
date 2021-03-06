package spellit.ui.views;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import spellit.core.models.Game;
import spellit.core.models.Player;
import spellit.core.persistence.FileHandler;
import spellit.ui.App;

public class PopupDialog {

	private final StackPane root;
	private final JFXDialogLayout layout;
	private final Text title;
	private JFXButton cancelBtn, okBtn;

	public PopupDialog(StackPane root) {
		this.root = root;
		this.layout = new JFXDialogLayout();
		this.title = new Text();
		this.cancelBtn = new JFXButton("Avbryt");
		cancelBtn.setCancelButton(true);
		cancelBtn.getStyleClass().add("danger");
		title.setFill(Color.WHITE);
		layout.setHeading(title);
		layout.getStyleClass().add("dialog-dark");
		cancelBtn.getStyleClass().add("danger");
	}

	public void showSaveGameDialog(Game game) {
		JFXAlert<Boolean> dialog = new JFXAlert<Boolean>(root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setOverlayClose(false);
		title.setText("Lagre spill");

		Text errorMsg = new Text();
		errorMsg.getStyleClass().add("danger");
		JFXTextField filenameInput = new JFXTextField();
		filenameInput.setPromptText("Lagre spillet som");
		VBox contentWrapper = new VBox(errorMsg, filenameInput);
		layout.setBody(contentWrapper);

		this.okBtn = new JFXButton("Lagre");
		okBtn.setDefaultButton(true);
		okBtn.setOnAction(event -> {
			String filename = filenameInput.getText();
			if (filename.length() < 1) {
				errorMsg.setText("Tast inn et gyldig navn");
			} else {
				FileHandler.saveGame(game, filename);
				dialog.close();
			}
		});

		cancelBtn.setOnAction(event -> dialog.close());

		layout.setActions(cancelBtn, okBtn);
		dialog.setContent(layout);
		dialog.show();
	}

	public Game showLoadGameDialog() {
		JFXAlert<Game> dialog = new JFXAlert<Game>(root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setOverlayClose(false);
		title.setText("Last inn spill");
		Text errorMsg = new Text();
		errorMsg.getStyleClass().add("danger");

		JFXListView<Label> filesList = new JFXListView<Label>();
		try {
			for (File file : FileHandler.getSaveFiles()) {
				filesList.getItems().add(new Label(file.getName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		VBox contentWrapper = new VBox(errorMsg, filesList);
		layout.setBody(contentWrapper);

		this.okBtn = new JFXButton("Last inn");
		okBtn.setDefaultButton(true);
		okBtn.setOnAction(event -> {
			if (filesList.getSelectionModel().getSelectedItem() == null) {
				errorMsg.setText("Du må velge en fil å laste inn");
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

		cancelBtn.setOnAction(event -> dialog.close());

		layout.setActions(cancelBtn, okBtn);
		dialog.setContent(layout);
		dialog.setResultConverter(buttonType -> {
			if (buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
				dialog.setResult(null);
			}
			return dialog.getResult();
		});
		Optional<Game> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	public Game showNewGameDialog() {
		JFXAlert<Game> dialog = new JFXAlert<Game>(root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setOverlayClose(false);
		title.setText("Start nytt spill");

		Text errorMsg = new Text();
		errorMsg.getStyleClass().add("danger");
		JFXTextField player1Name = new JFXTextField();
		player1Name.setPromptText("Spiller 1");
		JFXTextField player2Name = new JFXTextField();
		player2Name.setPromptText("Spiller 2");
		VBox contentWrapper = new VBox(errorMsg, player1Name, player2Name);

		layout.setBody(contentWrapper);

		this.okBtn = new JFXButton("Start nytt spill");
		okBtn.setDefaultButton(true);
		okBtn.setOnAction(event -> {
			if (player1Name.getText().length() < 1 || player2Name.getText().length() < 1) {
				errorMsg.setText("Vennligst fyll inn navn på begge spillerne");
				dialog.setResult(null);
			} else {
				dialog.setResult(new Game(player1Name.getText(), player2Name.getText()));
				dialog.hide();
			}

		});

		cancelBtn.setOnAction(event -> dialog.close());

		layout.setActions(cancelBtn, okBtn);
		dialog.setContent(layout);
		dialog.setResultConverter(buttonType -> {
			if (buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
				dialog.setResult(null);
			}
			return dialog.getResult();
		});
		Optional<Game> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	public void showWarningDialog(String message) {
		JFXAlert<Boolean> dialog = new JFXAlert<>(root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setOverlayClose(false);
		title.setText("Advarsel");
		Text msg = new Text(message);
		msg.setFill(Color.WHITE);
		layout.setBody(msg);
		okBtn = new JFXButton("Ok");
		okBtn.setCancelButton(true);
		okBtn.setOnAction(event -> dialog.close());
		layout.setActions(okBtn);
		dialog.setContent(layout);
		dialog.show();
	}

	public void showWinnerDialog(App app, Player winner) {
		JFXAlert<Boolean> dialog = new JFXAlert<>(root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setOverlayClose(false);
		title.setText("Spillet er ferdig");
		Text msg = new Text(String.format("%s vant med %d poeng!", winner.getName(), winner.getPoints()));
		msg.setFill(Color.WHITE);
		layout.setBody(msg);
		okBtn = new JFXButton("Til hovedmenyen");
		okBtn.setCancelButton(true);
		okBtn.setOnAction(event -> {
			app.setState(app.MENU);
			dialog.close();
		});
		layout.setActions(okBtn);
		dialog.setContent(layout);
		dialog.show();
	}

}
