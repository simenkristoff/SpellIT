package spellit.ui.views;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import spellit.core.persistence.FileHandler;
import spellit.ui.controllers.GameController;

public class PopupDialog {

	private final GameController controller;
	private final StackPane root;
	private final JFXDialogLayout layout;
	private final Text title;
	private final JFXDialog dialog;
	private JFXButton cancelBtn, okBtn;

	public PopupDialog(GameController controller) {
		this.controller = controller;
		this.root = controller.getRootPane();
		this.layout = new JFXDialogLayout();
		this.dialog = new JFXDialog(this.root, this.layout, JFXDialog.DialogTransition.CENTER);
		this.title = new Text();
		this.cancelBtn = new JFXButton("Avbryt");
		cancelBtn.setOnAction(event -> {
			dialog.close();
		});
		title.setFill(Color.WHITE);
		layout.setHeading(title);
		layout.getStyleClass().add("dialog-dark");
		cancelBtn.getStyleClass().add("danger");
	}

	public void showSaveGameDialog() {
		title.setText("Lagre spill");

		Text errorMsg = new Text();
		errorMsg.getStyleClass().add("danger");
		JFXTextField filenameInput = new JFXTextField();
		filenameInput.setPromptText("Lagre spillet som");
		VBox contentWrapper = new VBox(errorMsg, filenameInput);
		layout.setBody(contentWrapper);

		okBtn = new JFXButton("Lagre");
		okBtn.setOnAction(event -> {

			String filename = filenameInput.getText();
			if (filename.length() < 1) {
				errorMsg.setText("Tast inn et gyldig navn");
				event.consume();
			} else {
				FileHandler.saveGame(controller.getGame(), filename);
				dialog.close();
			}
		});

		layout.setActions(cancelBtn, okBtn);
		dialog.show();
	}

	public void showLoadGameDialog() {
		title.setText("Last inn spill");
		Text errorMsg = new Text();
		errorMsg.getStyleClass().add("danger");
		JFXListView<Label> filesList = new JFXListView();
		try {
			for (File file : FileHandler.getSaveFiles()) {
				filesList.getItems().add(new Label(file.getName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		VBox contentWrapper = new VBox(errorMsg, filesList);
		layout.setBody(contentWrapper);
		okBtn = new JFXButton("Last inn");
		okBtn.setOnAction(event -> {
			if (filesList.getSelectionModel().getSelectedItem() == null) {
				errorMsg.setText("Du må velge en fil å laste inn");
				event.consume();
			} else {
				String filename = filesList.getSelectionModel().getSelectedItem().getText();
				try {
					this.controller.setGame(FileHandler.loadGame(filename));
					dialog.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		layout.setActions(cancelBtn, okBtn);
		dialog.show();
	}

	public void showDialog(String message) {
		title.setText("Advarsel");
		Text msg = new Text(message);
		msg.setFill(Color.WHITE);
		layout.setBody(msg);
		okBtn = new JFXButton("Ok");
		okBtn.setOnAction(event -> {
			dialog.close();
		});
		layout.setActions(okBtn);
		dialog.show();
	}

	public JFXDialog getDialog() {
		return this.dialog;
	}

	public void toggle() {
		dialog.show();
	}
}
