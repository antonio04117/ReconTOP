package presenter;

import java.util.Iterator;
import java.util.Map.Entry;

import app.App.AppFX.AppJME;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import model.Mesh;
import view.fx.ViewFX;
import viewmodel.ViewModel;

public abstract class EventManager {

	public static void coreEvents(ViewFX viewFX, Mesh mesh) {

		// cell function
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
								appJME.enqueue(() -> ViewModel.showTetrahedron(Integer.valueOf(index)));
							} else {
								appJME.enqueue(() -> ViewModel.hideTetrahedron(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every tetrahedron is selected
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(0)) {
							for (int j = 2; j < viewFX.getListViewTetrahedron().getItems().size(); j++) {
								viewFX.getListViewTetrahedron().getSelectionModel().select(j);
							}
						}

						/*
						 * if settings are selected
						 */

						// show edges with tet
						if (viewFX.getCheckBoxSettings()[0].isSelected()) {

							for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
								final int index = i - 2; // Make 'i' effectively final
								final int startingValue = 6 * index + 2;
								if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices()
										.contains(i)) {
									System.out.println("Tetrahedron " + index + " selected\n");
									viewFX.getListViewEdge().getSelectionModel().selectIndices(startingValue,
											startingValue + 1, startingValue + 2, startingValue + 3, startingValue + 4,
											startingValue + 5);
								} else {
									System.out.println("Tetrahedron " + index + " not selected\n");
									viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue);
									viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 1);
									viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 2);
									viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 3);
									viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 4);
									viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 5);
								}
							}
						}

						// show edges with tet
						if (viewFX.getCheckBoxSettings()[1].isSelected()) {

							for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
								final int index = i - 2; // Make 'i' effectively final
								final int startingValue = 4 * index + 2;
								if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices()
										.contains(i)) {
									System.out.println("Tetrahedron " + index + " selected\n");
									viewFX.getListViewVertex().getSelectionModel().selectIndices(startingValue,
											startingValue + 1, startingValue + 2, startingValue + 3);
								} else {
									System.out.println("Tetrahedron " + index + " not selected\n");
									viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue);
									viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 1);
									viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 2);
									viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 3);
								}
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
								appJME.enqueue(() -> ViewModel.showTriangle(Integer.valueOf(index)));
							} else {
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
								appJME.enqueue(() -> ViewModel.showEdge(Integer.valueOf(index)));
							} else {
								appJME.enqueue(() -> ViewModel.hideEdge(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every edge is selected
						if (viewFX.getListViewEdge().getSelectionModel().getSelectedIndices().contains(0)) {
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
								appJME.enqueue(() -> ViewModel.showVertex(Integer.valueOf(index)));
							} else {
								appJME.enqueue(() -> ViewModel.hideVertex(Integer.valueOf(index)));
							}
						}
						// if "select all" is selected, every vertex is selected
						if (viewFX.getListViewVertex().getSelectionModel().getSelectedIndices().contains(0)) {
							for (int j = 2; j < viewFX.getListViewVertex().getItems().size(); j++) {
								viewFX.getListViewVertex().getSelectionModel().select(j);
							}
						}
					}
				});

		// trigger event for deselection of all objects
		viewFX.getButtonDeselectAllElements().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// deselect tets
				viewFX.getListViewTetrahedron().getSelectionModel().clearAndSelect(1);
				// deselect tris
				viewFX.getListViewTriangle().getSelectionModel().clearAndSelect(1);
				// deselect edges
				viewFX.getListViewEdge().getSelectionModel().clearAndSelect(1);
				// deselect vertices
				viewFX.getListViewVertex().getSelectionModel().clearAndSelect(1);
			}
		});

		/*
		 * settings function
		 */

		// checkbox edges with tets
		viewFX.getCheckBoxSettings()[0].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				// action if checkbox is newly checked -> select edges of selected tets
				if (viewFX.getCheckBoxSettings()[0].isSelected()) {

					for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						final int startingValue = 6 * index + 2;
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
							viewFX.getListViewEdge().getSelectionModel().selectIndices(startingValue, startingValue + 1,
									startingValue + 2, startingValue + 3, startingValue + 4, startingValue + 5);
						} else {
//							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue);
//							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 1);
//							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 2);
//							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 3);
//							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 4);
//							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 5);
						}
					}
				}
				// action if checkbox is newly unchecked -> deselect edges of selected tets
				else {

					for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						final int startingValue = 6 * index + 2;
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue);
							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 1);
							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 2);
							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 3);
							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 4);
							viewFX.getListViewEdge().getSelectionModel().clearSelection(startingValue + 5);
						}
					}

				}
			}

		});

		// checkbox vertex with tets
		viewFX.getCheckBoxSettings()[1].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				// action if checkbox is newly checked -> select vertices of selected tets
				if (viewFX.getCheckBoxSettings()[1].isSelected()) {

					for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						final int startingValue = 4 * index + 2;
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
							viewFX.getListViewVertex().getSelectionModel().selectIndices(startingValue,
									startingValue + 1, startingValue + 2, startingValue + 3);
						} else {
//							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue);
//							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 1);
//							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 2);
//							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 3);
						}
					}
				}
				// action if checkbox is newly unchecked -> deselect vertices of selected tets
				else {

					for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						final int startingValue = 4 * index + 2;
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue);
							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 1);
							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 2);
							viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 3);
						}
					}

				}
			}

		});

	}

}
