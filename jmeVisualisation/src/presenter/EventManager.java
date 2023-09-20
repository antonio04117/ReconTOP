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

			if (it.hasNext()) {
				Entry<CheckBox, Integer> entry = it.next();

				entry.getKey().selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						viewFX.createTabPane(mesh, viewFX.getRootPane());
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
						for (int i = 1; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
							final int index = i - 1; // Make 'i' effectively final
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
							for (int j = 1; j < viewFX.getListViewTetrahedron().getItems().size() + 1; j++) {
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
						for (int i = 1; i < viewFX.getListViewTriangle().getItems().size(); i++) {
							final int index = i - 1; // Make 'i' effectively final
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
							for (int j = 1; j < viewFX.getListViewTriangle().getItems().size() + 1; j++) {
								viewFX.getListViewTriangle().getSelectionModel().select(j);
							}
						}

					}
				});

		// trigger event for selection/deselection of edges
		viewFX.getListViewEdges().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 1; i < viewFX.getListViewEdges().getItems().size(); i++) {
							final int index = i - 1; // Make 'i' effectively final
							if (viewFX.getListViewEdges().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Edge " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showEdge(Integer.valueOf(index)));
							} else {
								System.out.println("Triangle " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideEdge(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every edge is selected
						if (viewFX.getListViewEdges().getSelectionModel().getSelectedIndices().contains(0)) {
							System.out.println("Every Edge selected\n");
							for (int j = 1; j < viewFX.getListViewEdges().getItems().size() + 1; j++) {
								viewFX.getListViewEdges().getSelectionModel().select(j);
							}
						}
					}
				});

		// trigger event for selection/deselection of vertices
		viewFX.getListViewVertices().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 1; i < viewFX.getListViewVertices().getItems().size(); i++) {
							final int index = i - 1; // Make 'i' effectively final
							if (viewFX.getListViewVertices().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Vertex " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showVertex(Integer.valueOf(index)));
							} else {
								System.out.println("Vertex " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideVertex(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every vertex is selected
						if (viewFX.getListViewVertices().getSelectionModel().getSelectedIndices().contains(0)) {
							System.out.println("Every Vertex selected\n");
							for (int j = 1; j < viewFX.getListViewVertices().getItems().size() + 1; j++) {
								viewFX.getListViewVertices().getSelectionModel().select(j);
							}
						}
					}
				});
	}

}
