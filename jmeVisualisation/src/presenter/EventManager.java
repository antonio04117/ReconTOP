package presenter;

import java.util.Iterator;
import java.util.Map.Entry;

import app.App.AppFX.AppJME;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.CheckBox;
import model.Mesh;
import view.fx.ViewFX;
import viewmodel.ViewModel;

public abstract class EventManager {

	public static void coreEvents(ViewFX viewFX, Mesh mesh) {
		if (viewFX.getCheckBoxCells().size() > 0) {
			Iterator<Entry<CheckBox, Integer>> it = viewFX.getCheckBoxCells().entrySet().iterator();

			while (it.hasNext()) {
				Entry<CheckBox, Integer> entry = it.next();

				entry.getKey().selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						final int selectedTab = viewFX.getSelectedTab();

						// clear selection -> do not display any element
						viewFX.getListViewTetrahedron().getSelectionModel().clearSelection();
						viewFX.getListViewTriangle().getSelectionModel().clearSelection();
						viewFX.getListViewEdge().getSelectionModel().clearSelection();
						viewFX.getListViewVertex().getSelectionModel().clearSelection();
						// create new tabPane
						viewFX.createTabPane(mesh, selectedTab);
					}

				});
			}
		}
	}

	public static void viewModelEvents(ViewFX viewFX, AppJME appJME) {

		// trigger event for selection/deselection of tetrahedrons
		viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {

					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
							final int index = i - 2; // Make 'i' effectively final
							if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Tetrahedron " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showTetrahedron(Integer.valueOf(index)));
							} else {
								System.out.println("Tetrahedron " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideTetrahedron(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every tetrahedron is selected
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(0)) {
							System.out.println("Every Tetrahedron selected\n");
							for (int j = 2; j < viewFX.getListViewTetrahedron().getItems().size(); j++) {
								viewFX.getListViewTetrahedron().getSelectionModel().select(j);
							}
						}
					}
				});

		// trigger event for selection/deselection of triangles
		viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 2; i < viewFX.getListViewTriangle().getItems().size(); i++) {
							final int index = i - 2; // Make 'i' effectively final
							if (viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Triangle " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showTriangle(Integer.valueOf(index)));
							} else {
								System.out.println("Triangle " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideTriangle(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every triangle is selected
						if (viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices().contains(0)) {
							System.out.println("Every Triangle selected\n");
							for (int j = 2; j < viewFX.getListViewTriangle().getItems().size(); j++) {
								viewFX.getListViewTriangle().getSelectionModel().select(j);
							}
						}

					}
				});

		// trigger event for selection/deselection of edges
		viewFX.getListViewEdge().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 2; i < viewFX.getListViewEdge().getItems().size(); i++) {
							final int index = i - 2; // Make 'i' effectively final
							if (viewFX.getListViewEdge().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Edge " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showEdge(Integer.valueOf(index)));
							} else {
								System.out.println("Triangle " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideEdge(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every edge is selected
						if (viewFX.getListViewEdge().getSelectionModel().getSelectedIndices().contains(0)) {
							System.out.println("Every Edge selected\n");
							for (int j = 2; j < viewFX.getListViewEdge().getItems().size(); j++) {
								viewFX.getListViewEdge().getSelectionModel().select(j);
							}
						}
					}
				});

		// trigger event for selection/deselection of vertices
		viewFX.getListViewVertex().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 2; i < viewFX.getListViewVertex().getItems().size(); i++) {
							final int index = i - 2; // Make 'i' effectively final
							if (viewFX.getListViewVertex().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Vertex " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showVertex(Integer.valueOf(index)));
							} else {
								System.out.println("Vertex " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideVertex(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every vertex is selected
						if (viewFX.getListViewVertex().getSelectionModel().getSelectedIndices().contains(0)) {
							System.out.println("Every Vertex selected\n");
							for (int j = 2; j < viewFX.getListViewVertex().getItems().size(); j++) {
								viewFX.getListViewVertex().getSelectionModel().select(j);
							}
						}
					}
				});
	}

}
