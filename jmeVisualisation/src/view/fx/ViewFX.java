package view.fx;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Mesh;
import model.topology.Edge3D;
import model.topology.Tetrahedron3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;

/**
 * class for java fx view
 */
public class ViewFX {

	private Canvas canvas;
	private Stage stage;
	private Scene scene;
	private String defaultAnnotation = " (CTRL + click = deselection)";

	// lists for all objects
	private ListView<Text> listViewTetrahedron = new ListView<Text>();
	private ListView<Text> listViewTriangle = new ListView<Text>();
	private ListView<Text> listViewEdges = new ListView<Text>();
	private ListView<Text> listViewVertices = new ListView<Text>();

	// check box for cells
	private CheckBox[] checkBoxCells;

	public ViewFX(Stage stage, Mesh mesh) {
		canvas = new Canvas();
		this.stage = stage;

		setScene(mesh);
	}

	/**
	 * set scene of the gui depending on different selected handlers
	 */
	public void setScene(Mesh mesh) {

		// create rootPane
		BorderPane rootPane = new BorderPane();

		// create checkbox and add right to the rootPane
		checkBoxCells = createCheckbox(mesh);
		addCheckbox(rootPane);

		Tab tabTetrahedrons = new Tab("Tetrahedrons", createTetrahedronTab(mesh));
		Tab tabTriangles = new Tab("Triangles", createTriangleTab(mesh));
		Tab tabEdges = new Tab("Edges", createEdgeTab(mesh));
		Tab tabVertices = new Tab("Vertices", createVertexTab(mesh));

		TabPane tabPane = new TabPane(tabTetrahedrons, tabTriangles, tabEdges, tabVertices);
		// tabs can not be closed by the user
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		rootPane.setCenter(tabPane);

		scene = new Scene(rootPane, 400, 400);

		stage.setScene(scene);

		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		// Window title
		stage.setTitle("Control displayed elements in jMonkey");
		stage.show();
	}

	/**
	 * create checkbox for cells from mesh
	 * 
	 * @param mesh
	 * @return
	 */
	private CheckBox[] createCheckbox(Mesh mesh) {
		CheckBox[] cbs = new CheckBox[2];
		cbs[0] = new CheckBox("a");
		cbs[1] = new CheckBox("b");
		return cbs;
	}

	private void addCheckbox(BorderPane borderPane) {
		// create vBox
		VBox vBox = new VBox();
		// add all elements of checkBoxCells
		for (int i = 0; i < checkBoxCells.length; i++) {
			vBox.getChildren().add(checkBoxCells[i]);
		}
		// add vBox to borderPane
		borderPane.setRight(vBox);
		// align checkboxes
		BorderPane.setMargin(borderPane.getRight(), new Insets(50, 5, 5, 5));
	}

	/**
	 * creates Tab for Tetrahedrons
	 * 
	 * @param mesh
	 * @return
	 */
	private BorderPane createTetrahedronTab(Mesh mesh) {
		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(" List of all Tetrahedrons" + defaultAnnotation);

		// enable multiple selections
		listViewTetrahedron.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createTetrahedronList(mesh);

		listViewTetrahedron.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewTetrahedron);
		// add checkbox
//		addCheckbox(borderPane);

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

		// add possibility to select all elements
		items.add(new Text("select all"));

		// Iterator for entries
		Iterator<Entry<Integer, Tetrahedron3D>> it = mesh.getMapTet().entrySet().iterator();

		// get id of tetrahedron
		while (it.hasNext()) {
			items.add(new Text("Tet: " + it.next().getKey()));
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

		Label label = new Label(" List of all Triangles" + defaultAnnotation);

		// enable multiple selections
		listViewTriangle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createTriangleList(mesh);

		listViewTriangle.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewTriangle);
		// add checkbox
//		addCheckbox(borderPane);

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

		// add possibility to select all elements
		items.add(new Text("select all"));

		// Iterator for entries
		Iterator<Entry<Integer, LinkedList<Triangle3D>>> it = mesh.getMapTri().entrySet().iterator();

		// get id of tetrahedron
		while (it.hasNext()) {

			Entry<Integer, LinkedList<Triangle3D>> currentEntry = it.next();

			// get id of triangle
			for (int j = 0; j < currentEntry.getValue().size(); j++) {
				items.add(new Text("Tet: " + currentEntry.getKey() + " ; Tri: " + j));
			}
		}

		return items;
	}

	/**
	 * creates Tab for Edges
	 * 
	 * @param mesh
	 * @return
	 */
	private BorderPane createEdgeTab(Mesh mesh) {
		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(" List of all Edges" + defaultAnnotation);

		// enable multiple selections
		listViewEdges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createEdgeList(mesh);

		listViewEdges.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewEdges);
		// add checkbox
//		addCheckbox(borderPane);

		return borderPane;
	}

	/**
	 * create List of all triangles depending on given mesh
	 * 
	 * @param mesh
	 * @return
	 */
	private ObservableList<Text> createEdgeList(Mesh mesh) {

		ObservableList<Text> items = FXCollections.observableArrayList();

		// add possibility to select all elements
		items.add(new Text("select all"));

		// Iterator for entries
		Iterator<Entry<Integer, LinkedList<Edge3D>>> it = mesh.getMapEdg().entrySet().iterator();

		// get id of tetrahedron
		while (it.hasNext()) {

			Entry<Integer, LinkedList<Edge3D>> currentEntry = it.next();

			// get id of edge
			for (int j = 0; j < currentEntry.getValue().size(); j++) {
				items.add(new Text("Tet: " + currentEntry.getKey() + " ; Edg: " + j));
			}
		}

		return items;
	}

	/**
	 * creates Tab for Vertices
	 * 
	 * @param mesh
	 * @return
	 */
	private BorderPane createVertexTab(Mesh mesh) {
		BorderPane borderPane = new BorderPane(canvas);

		Label label = new Label(" List of all Vertices" + defaultAnnotation);

		// enable multiple selections
		listViewVertices.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createVertexList(mesh);

		listViewVertices.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewVertices);
		// add checkbox
//		addCheckbox(borderPane);

		return borderPane;
	}

	/**
	 * create List of all triangles depending on given mesh
	 * 
	 * @param mesh
	 * @return
	 */
	private ObservableList<Text> createVertexList(Mesh mesh) {

		ObservableList<Text> items = FXCollections.observableArrayList();

		// add possibility to select all elements
		items.add(new Text("select all"));

		// Iterator for entries
		Iterator<Entry<Integer, LinkedList<Vertex3D>>> it = mesh.getMapVer().entrySet().iterator();

		// get id of tetrahedron
		while (it.hasNext()) {

			Entry<Integer, LinkedList<Vertex3D>> currentEntry = it.next();

			// get id of vertex
			for (int j = 0; j < currentEntry.getValue().size(); j++) {
				items.add(new Text("Tet: " + currentEntry.getKey() + " ; Ver: " + j));
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

	public ListView<Text> getListViewEdges() {
		return listViewEdges;
	}

	public ListView<Text> getListViewVertices() {
		return listViewVertices;
	}

}
