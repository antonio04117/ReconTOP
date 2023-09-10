package view.jfx;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * class for java fx view
 */
public class ViewFX {

	private Canvas canvas;
	private Stage stage;
	private Scene scene;

	private Button exampleA;
	private Button exampleB;

	public ViewFX(Stage stage) {
		canvas = new Canvas();
		this.stage = stage;

		setScene();
	}

	/**
	 * set scene of the gui depending on different selected handlers
	 */
	public void setScene() {
		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(
				"  Steuerung:\n   W: vorwärts\n   S: rückwärts\n   A: links\n   D: rechts\n   Q: hoch\n   Y, Z: runter\n   Maus: rotieren\n\n  Beispiel auswählen:");

		// add Button
		exampleA = new Button("Beispiel A");
		exampleB = new Button("Beispiel B");

		HBox hBox = new HBox();
		hBox.getChildren().add(exampleA);
		hBox.getChildren().add(exampleB);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(hBox);

		scene = new Scene(borderPane, 180, 210);

		stage.setScene(scene);

		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		// Window title
		stage.setTitle("NURBS Visualisierung");
		stage.show();
	}

	public Button getExampleA() {
		return exampleA;
	}

	public Button getExampleB() {
		return exampleB;
	}

}
