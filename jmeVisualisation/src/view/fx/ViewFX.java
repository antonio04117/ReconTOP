package view.fx;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

	private BorderPane rootPane;
	private Canvas canvas;
	private Stage stage;
	private Scene scene;
	private String defaultAnnotation = " (CTRL + click = deselection)";

	// lists for all objects
	private ListView<Text> listViewTetrahedron = new ListView<Text>();
	private ListView<Text> listViewTriangle = new ListView<Text>();
	private ListView<Text> listViewEdge = new ListView<Text>();
	private ListView<Text> listViewVertex = new ListView<Text>();

	// check box for cells
	private Map<CheckBox, Integer> checkBoxCells;

	// button to deselect all elements
	private Button buttonDeselectAllElements;

	// check box settings
	private CheckBox[] checkBoxSettings;

	public ViewFX(Stage stage, Mesh mesh) {
		rootPane = new BorderPane();
		canvas = new Canvas();
		this.stage = stage;

		setScene(mesh);
	}

	/**
	 * set scene of the gui depending on different selected handlers
	 */
	public void setScene(Mesh mesh) {

		VBox rightSideVBox = new VBox();
		// create checkbox and add to vBox to add right to the rootPane
		checkBoxCells = createCheckbox(mesh);
		// only add checkbox view, if it contains content
		if (checkBoxCells.size() > 0) {
			addCheckboxes(rightSideVBox);
		}

		// add button to deselect all elements of scene graph
		addButtonDeselectAllElements(rightSideVBox);

		// add checkboxes for settings
		addSettingCheckboxes(rightSideVBox);

		// add rightSideVBox to rootPane
		rootPane.setRight(rightSideVBox);

		createTabPane(mesh, 0);

		// TODO final adaption: adapt width to need of visualisation
		scene = new Scene(rootPane, 402, 400);

		stage.setScene(scene);

		canvas.widthProperty().bind(scene.widthProperty());
		canvas.heightProperty().bind(scene.heightProperty());

		// Window title
		stage.setTitle("Control displayed elements in jMonkey");
		stage.show();
	}

	public void createTabPane(Mesh mesh, int selectedTab) {
		Tab tabTetrahedrons = new Tab("Tetrahedrons", createTetrahedronTab(mesh));
		Tab tabTriangles = new Tab("Triangles", createTriangleTab(mesh));
		Tab tabEdges = new Tab("Edges", createEdgeTab(mesh));
		Tab tabVertices = new Tab("Vertices", createVertexTab(mesh));

		TabPane tabPane = new TabPane(tabTetrahedrons, tabTriangles, tabEdges, tabVertices);
		// tabs can not be closed by the user
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getSelectionModel().select(selectedTab);

		rootPane.setCenter(tabPane);
	}

	/**
	 * get selection model of tabPane
	 * 
	 * @return
	 */
	public int getSelectedTab() {

		TabPane tabPane = (TabPane) rootPane.getCenter();
		return tabPane.getSelectionModel().getSelectedIndex();

	}

	/**
	 * create checkbox for cells from mesh
	 * 
	 * @param mesh
	 * @return
	 */
	private LinkedHashMap<CheckBox, Integer> createCheckbox(Mesh mesh) {

		LinkedHashMap<CheckBox, Integer> cbs = new LinkedHashMap<CheckBox, Integer>();

		Iterator<Entry<Integer, Set<Integer>>> it = mesh.getCellIDs().entrySet().iterator();

		while (it.hasNext()) {
			int id = it.next().getKey();
			cbs.put(new CheckBox("Cell " + id), Integer.valueOf(id));
		}
		return cbs;
	}

	private void addSettingCheckboxes(VBox rightSideVBox) {
		// create Label
		Label headline = new Label("settings:");
		headline.setUnderline(true);
		VBox.setMargin(headline, new Insets(50, 0, 5, 0));
		// add label to view
		rightSideVBox.getChildren().add(headline);

		// create Array of Checkboxes
		checkBoxSettings = new CheckBox[] { new CheckBox("show edges with tet"),
				new CheckBox("show vertices with tet") };

		// add Checkboxes to vBox
		for (CheckBox checkBox : checkBoxSettings) {
			rightSideVBox.getChildren().add(checkBox);
		}
	}

	/**
	 * add Checkboxes to given BorderPane
	 * 
	 * @param rootPane
	 */
	@SuppressWarnings("unchecked") // cast to generic type T is not checked
	private <T> VBox addCheckboxes(VBox rightSideVBox) {
		// create vBox
		TreeItem<T> rootItem = new TreeItem<T>((T) new Text("Cells"));
		// add all elements of checkBoxCells
		Iterator<Entry<CheckBox, Integer>> it = checkBoxCells.entrySet().iterator();

		while (it.hasNext()) {
			TreeItem<T> item = new TreeItem<T>((T) it.next().getKey());
			rootItem.getChildren().add(item);
		}

		TreeView<T> tree = new TreeView<T>(rootItem);
		// set width of tree view
		tree.setMaxWidth(110);
		tree.setMaxHeight(200);

		// add Tree to vBox
		rightSideVBox.getChildren().add(tree);
		// align checkboxes
		BorderPane.setMargin(rightSideVBox, new Insets(46, 5, 5, 5));

		return rightSideVBox;
	}

	private void addButtonDeselectAllElements(VBox rightSideVBox) {
		buttonDeselectAllElements = new Button("deselect all elements");
		rightSideVBox.getChildren().add(buttonDeselectAllElements);
		// align checkboxes
		VBox.setMargin(buttonDeselectAllElements, new Insets(5, 0, 5, 0));
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
		items.add(new Text("deselect all"));

		// Iterator for entries
		Iterator<Entry<Integer, Tetrahedron3D>> it = mesh.getMapTet().entrySet().iterator();

		// place to save if no checkbox is selected
		boolean noCheckboxSelected = true;

		// get id of tetrahedron
		while (it.hasNext()) {
			Entry<Integer, Tetrahedron3D> entry = it.next();
			// only add elements to list that have a ticket cell
			if (getAllDisplayableTetsID(mesh).contains(Integer.valueOf(entry.getKey()))) {
				items.add(new Text("Tet: " + entry.getKey()));
				// mark that at least one tet was added
				noCheckboxSelected = false;
			}
		}

		// if no checkbox is selected show every tet
		if (noCheckboxSelected) {
			// Iterator for entries
			Iterator<Entry<Integer, Tetrahedron3D>> sameIt = mesh.getMapTet().entrySet().iterator();
			// get id of tetrahedron
			while (sameIt.hasNext()) {
				Entry<Integer, Tetrahedron3D> entry = sameIt.next();
				items.add(new Text("Tet: " + entry.getKey()));
			}
		}

		return items;
	}

	/**
	 * returns IDs of all tetraeders that can be drawn
	 * 
	 * @param mesh
	 * @return
	 */
	private Set<Integer> getAllDisplayableTetsID(Mesh mesh) {
		Set<Integer> displayableID = new HashSet<Integer>();

		Iterator<Entry<CheckBox, Integer>> it = checkBoxCells.entrySet().iterator();

		while (it.hasNext()) {
			Entry<CheckBox, Integer> entry = it.next();
			// check if checkbox is selected
			if (entry.getKey().isSelected()) {
				// add all ids of tets of cell that is checked
				displayableID.addAll(mesh.getCellIDs().get(entry.getValue()));
			}
		}
		return displayableID;
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
		items.add(new Text("deselect all"));

		// Iterator for entries
		Iterator<Entry<Integer, LinkedList<Triangle3D>>> it = mesh.getMapTri().entrySet().iterator();

		// place to save if no checkbox is selected
		boolean noCheckboxSelected = true;

		// get id of tetrahedron
		while (it.hasNext()) {
			Entry<Integer, LinkedList<Triangle3D>> entry = it.next();
			for (int j = 0; j < entry.getValue().size(); j++) {
				// only add elements to list that have a ticket cell
				if (getAllDisplayableTetsID(mesh).contains(Integer.valueOf(entry.getKey()))) {
					items.add(new Text("Tet: " + entry.getKey() + " ; Tri: " + j));
					// mark that at least one tet was added
					noCheckboxSelected = false;
				}
			}
		}

		// if no checkbox is selected show every tet
		if (noCheckboxSelected) {
			// Iterator for entries
			Iterator<Entry<Integer, LinkedList<Triangle3D>>> sameIt = mesh.getMapTri().entrySet().iterator();
			// get id of tetrahedron
			while (sameIt.hasNext()) {
				Entry<Integer, LinkedList<Triangle3D>> entry = sameIt.next();
				for (int j = 0; j < entry.getValue().size(); j++) {
					items.add(new Text("Tet: " + entry.getKey() + " ; Tri: " + j));
				}
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
		listViewEdge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createEdgeList(mesh);

		listViewEdge.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewEdge);
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
		items.add(new Text("deselect all"));

		// Iterator for entries
		Iterator<Entry<Integer, LinkedList<Edge3D>>> it = mesh.getMapEdg().entrySet().iterator();

		// place to save if no checkbox is selected
		boolean noCheckboxSelected = true;

		// get id of tetrahedron
		while (it.hasNext()) {
			Entry<Integer, LinkedList<Edge3D>> entry = it.next();
			for (int j = 0; j < entry.getValue().size(); j++) {
				// only add elements to list that have a ticket cell
				if (getAllDisplayableTetsID(mesh).contains(Integer.valueOf(entry.getKey()))) {
					items.add(new Text("Tet: " + entry.getKey() + " ; Edg: " + j));
					// mark that at least one tet was added
					noCheckboxSelected = false;
				}
			}
		}

		// if no checkbox is selected show every tet
		if (noCheckboxSelected) {
			// Iterator for entries
			Iterator<Entry<Integer, LinkedList<Edge3D>>> sameIt = mesh.getMapEdg().entrySet().iterator();
			// get id of tetrahedron
			while (sameIt.hasNext()) {
				Entry<Integer, LinkedList<Edge3D>> entry = sameIt.next();
				for (int j = 0; j < entry.getValue().size(); j++) {
					items.add(new Text("Tet: " + entry.getKey() + " ; Edg: " + j));
				}
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
		listViewVertex.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<Text> items = createVertexList(mesh);

		listViewVertex.setItems(items);
		// add elements to root
		borderPane.setTop(label);
		borderPane.setCenter(listViewVertex);
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
		items.add(new Text("deselect all"));

		// Iterator for entries
		Iterator<Entry<Integer, LinkedList<Vertex3D>>> it = mesh.getMapVer().entrySet().iterator();

		// place to save if no checkbox is selected
		boolean noCheckboxSelected = true;

		// get id of tetrahedron
		while (it.hasNext()) {
			Entry<Integer, LinkedList<Vertex3D>> entry = it.next();
			for (int j = 0; j < entry.getValue().size(); j++) {
				// only add elements to list that have a ticket cell
				if (getAllDisplayableTetsID(mesh).contains(Integer.valueOf(entry.getKey()))) {
					items.add(new Text("Tet: " + entry.getKey() + " ; Ver: " + j));
					// mark that at least one tet was added
					noCheckboxSelected = false;
				}
			}
		}

		// if no checkbox is selected show every tet
		if (noCheckboxSelected) {
			// Iterator for entries
			Iterator<Entry<Integer, LinkedList<Vertex3D>>> sameIt = mesh.getMapVer().entrySet().iterator();
			// get id of tetrahedron
			while (sameIt.hasNext()) {
				Entry<Integer, LinkedList<Vertex3D>> entry = sameIt.next();
				for (int j = 0; j < entry.getValue().size(); j++) {
					items.add(new Text("Tet: " + entry.getKey() + " ; Ver: " + j));
				}
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

	public ListView<Text> getListViewEdge() {
		return listViewEdge;
	}

	public ListView<Text> getListViewVertex() {
		return listViewVertex;
	}

	public Map<CheckBox, Integer> getCheckBoxCells() {
		return checkBoxCells;
	}

	public Button getButtonDeselectAllElements() {
		return buttonDeselectAllElements;
	}

	public CheckBox[] getCheckBoxSettings() {
		return checkBoxSettings;
	}

}
