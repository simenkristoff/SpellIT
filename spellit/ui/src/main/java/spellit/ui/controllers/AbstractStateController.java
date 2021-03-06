package spellit.ui.controllers;

import javafx.fxml.Initializable;
import spellit.ui.App;

/**
 * The Class AbstractViewController. Root-class for all ViewControllers
 */
public abstract class AbstractStateController implements Initializable {

	protected final App app;

	public AbstractStateController(App app) {
		this.app = app;
	}

}
