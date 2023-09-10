package app;

import com.jme3.app.SimpleApplication;


import javafx.application.Application;
import javafx.stage.Stage;
import presenter.Presenter;
import view.jfx.ViewFX;

/**
 * app for combined fx and jme application
 */
public class App {

	public static int sampleSize = 40;

	public static void main(String[] args) {
		new Thread(() -> Application.launch(AppFX.class)).start();
	}

	/**
	 * app for fx application including jme application
	 */
	public static class AppFX extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			ViewFX viewFX = new ViewFX(primaryStage);

			// start jme thread
			new Thread(() -> {
				AppJME appJME = new AppJME();
				appJME.start();
				Presenter.createConnection(viewFX, appJME, sampleSize);
			}).start();
		}

		/**
		 * app for jme application
		 */
		public static class AppJME extends SimpleApplication {

			@Override
			public void simpleInitApp() {

				// set Scene while selecting sample size in each direction
				// true -> example A
				// false -> example B
				Presenter.setScene(this, sampleSize);
			}
		}
	}
}