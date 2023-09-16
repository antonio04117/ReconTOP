package view.jfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * class for java fx view
 */
public class ViewFX {

	private Canvas canvas;
	private Stage stage;
	private Scene scene;

	private Text showBoundary;

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

		Label label = new Label(" List of all Tetrahedrons");

		// add Button
		showBoundary = new Text("show boundary");
		showBoundary.setMouseTransparent(true);

		ListView<Text> listView = new ListView<Text>();
		ObservableList<Text> items = FXCollections.observableArrayList(showBoundary);
		listView.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listView);

		Tab tab1 = new Tab("Start", borderPane);

		TabPane tabPane = new TabPane(tab1);

		scene = new Scene(tabPane, 300, 300);

		stage.setScene(scene);

		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		// Window title
		stage.setTitle("NURBS Visualisierung");
		stage.show();
	}

	public Text getShowBoundary() {
		return showBoundary;
	}

}
