package view.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Mesh;

/**
 * class for java fx view
 */
public class ViewFX {

	private Canvas canvas;
	private Stage stage;
	private Scene scene;

	private ListView<Text> listView = new ListView<Text>();

	public ViewFX(Stage stage, Mesh mesh) {
		canvas = new Canvas();
		this.stage = stage;

		setScene(mesh);
	}

	/**
	 * set scene of the gui depending on different selected handlers
	 */
	public void setScene(Mesh mesh) {

		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(" List of all Tetrahedrons");

		// enable multiple selections
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		
		ObservableList<Text> items = createTriangleList(mesh);

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

	private ObservableList<Text> createTriangleList(Mesh mesh) {

		ObservableList<Text> items = FXCollections.observableArrayList();

		for (int i = 0; i < mesh.getMapTri().size(); i++) {
			for (int j = 0; j < mesh.getMapTri().get(i).size(); j++) {

				items.add(new Text("Tet: " + i + " ; Tri: " + j));
//				ObservableList<Text> triangleList = FXCollections.observableArrayList(new Text("show boundary"), new Text("Test A"),
//						new Text("Test B"));
			}
		}

		return items;
	}

	public ListView<Text> getListView() {
		return listView;
	}

}
