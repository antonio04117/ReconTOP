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
import javafx.scene.control.TabPane.TabClosingPolicy;
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

	private ListView<Text> listViewTetrahedron = new ListView<Text>();
	private ListView<Text> listViewTriangle = new ListView<Text>();

	public ViewFX(Stage stage, Mesh mesh) {
		canvas = new Canvas();
		this.stage = stage;

		setScene(mesh);
	}

	/**
	 * set scene of the gui depending on different selected handlers
	 */
	public void setScene(Mesh mesh) {

		Tab tabTetrahedrons = new Tab("Tetrahedrons", createTetrahedronTab(mesh));
		Tab tabTriangles = new Tab("Triangles", createTriangleTab(mesh));

		TabPane tabPane = new TabPane(tabTetrahedrons, tabTriangles);
		// tabs can not be closed by the user
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		scene = new Scene(tabPane, 300, 300);

		stage.setScene(scene);

		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		// Window title
		stage.setTitle("Control displayed elements in jMonkey");
		stage.show();
	}

	/**
	 * creates Tab for Tetrahedrons
	 * 
	 * @param mesh
	 * @return
	 */
	private BorderPane createTetrahedronTab(Mesh mesh) {
		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(" List of all Tetrahedrons");

		// enable multiple selections
		listViewTetrahedron.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createTetrahedronList(mesh);

		listViewTetrahedron.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewTetrahedron);

		return borderPane;
	}

	/**
	 * create List of all triangles depending on given mesh
	 * 
	 * @param mesh
	 * @return
	 */
	private ObservableList<Text> createTetrahedronList(Mesh mesh) {

		ObservableList<Text> items = FXCollections.observableArrayList();

		for (int i = 0; i < mesh.getMapTet().size(); i++) {

			items.add(new Text("Tet: " + i));
		}

		return items;
	}

	/**
	 * creates Tab for Triangles
	 * 
	 * @param mesh
	 * @return
	 */
	private BorderPane createTriangleTab(Mesh mesh) {
		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(" List of all Traingles");

		// enable multiple selections
		listViewTriangle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createTriangleList(mesh);

		listViewTriangle.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewTriangle);

		return borderPane;
	}

	/**
	 * create List of all triangles depending on given mesh
	 * 
	 * @param mesh
	 * @return
	 */
	private ObservableList<Text> createTriangleList(Mesh mesh) {

		ObservableList<Text> items = FXCollections.observableArrayList();

		for (int i = 0; i < mesh.getMapTri().size(); i++) {
			for (int j = 0; j < mesh.getMapTri().get(i).size(); j++) {

				items.add(new Text("Tet: " + i + " ; Tri: " + j));
			}
		}

		return items;
	}

	public ListView<Text> getListViewTetrahedron() {
		return listViewTetrahedron;
	}

	public ListView<Text> getListViewTriangle() {
		return listViewTriangle;
	}

}
