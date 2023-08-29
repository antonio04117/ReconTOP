package view.jfx;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewJFX {

	private Canvas canvas;
	private Stage stage;
	private Scene scene;

	private Button coordsys;

	public ViewJFX(Stage stage) {
		canvas = new Canvas(500,500);
		this.stage = stage;

		setScene();
	}

	/**
	 * set scene of the gui depending on different selected handlers
	 */
	public void setScene() {
		BorderPane borderPane = new BorderPane(canvas);

		// add Button
		coordsys = new Button("Koordsys");

		HBox hBox = new HBox();
		hBox.getChildren().add(coordsys);
		borderPane.setTop(hBox);

		scene = new Scene(borderPane, 1024, 768);

		stage.setScene(scene);

		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		// Window title
		stage.setTitle("control jme view");
		stage.show();
	}

	public Button getCoordsys() {
		return coordsys;
	}

}
