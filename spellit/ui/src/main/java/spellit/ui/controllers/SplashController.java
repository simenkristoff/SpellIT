package spellit.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import spellit.ui.views.SplashView;

public class SplashController implements Initializable {

	@FXML
	BorderPane splashWrapper;

	private static Label label;
	private static ProgressBar progress;
	private static SplashView splashView;

	/**
	 * Initialize.
	 *
	 * @param location  the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initializeComponents();
	}

	private void initializeComponents() {
		splashView = new SplashView();
		splashWrapper.setCenter(splashView);
	}

	/**
	 * Sets the text of label.
	 * 
	 * @param text the label text
	 */
	public static void setLabelText(Double progress) {
		splashView.setProgressText(progress);
	}

	/**
	 * Sets the progress value.
	 * 
	 * @param value the progress
	 */
	public static void setProgressValue(Double value) {
		splashView.setProgress(value);
	}

}
