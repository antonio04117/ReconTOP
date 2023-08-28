package app;

import javafx.stage.Stage;
import presenter.Presenter;

public class AppJFX extends javafx.application.Application {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Presenter pres = new Presenter();

		pres.init();

		stage.show();
	}

}
