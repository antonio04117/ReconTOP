package presenter;

import java.util.Iterator;
import java.util.Map.Entry;

import app.App.AppFX.AppJME;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import model.Mesh;
import view.fx.ViewFX;
import viewmodel.ViewModel;

public abstract class EventManager {

	private EventManager() {
	}

	public static void coreEvents(ViewFX viewFX, Mesh mesh) {

		// cell function
		if (viewFX.getCheckBoxCells().size() > 0) {
			Iterator<Entry<CheckBox, Integer>> it = viewFX.getCheckBoxCells().entrySet().iterator();

			while (it.hasNext()) {
				Entry<CheckBox, Integer> entry = it.next();

				entry.getKey().selectedProperty().addListener((observable, oldValue, newValue) -> {
					final int selectedTab = viewFX.getSelectedTab();

					// clear selection -> do not display any element
					viewFX.getListViewTetrahedron().getSelectionModel().clearSelection();
					viewFX.getListViewTriangle().getSelectionModel().clearSelection();
					viewFX.getListViewEdge().getSelectionModel().clearSelection();
					viewFX.getListViewVertex().getSelectionModel().clearSelection();
					// create new tabPane
					viewFX.createTabPane(mesh, selectedTab);

				});
			}
		}
	}

	public static void viewModelEvents(ViewFX viewFX, AppJME appJME) {

		selectionTet(viewFX, appJME);

		selectionTri(viewFX, appJME);

		selectionEdg(viewFX, appJME);

		selectionVer(viewFX, appJME);

		deselectAll(viewFX);

		/*
		 * settings function
		 */

		settingsTicked(viewFX);

		// put function on button for initial camera perspective
		viewFX.getButtonInitialCamera().setOnAction((ActionEvent event) -> ViewModel.setInitialCameraPosition(appJME));

		// switches between opaque and transparent material
		opaqueTransparent(viewFX, appJME);

		// change alpha value of material colors
		alphaValue(viewFX, appJME);

	}

	private static void alphaValue(ViewFX viewFX, AppJME appJME) {
		// put function on alpha slider
		viewFX.getSliderTransparency().setOnMouseReleased((MouseEvent event) -> {
			// set alpha for tetrahedrons
			for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
				final int index = i - 2; // Make 'i' effectively final
				appJME.enqueue(
						() -> ViewModel.setAlphaTetrahedron(index, (int) viewFX.getSliderTransparency().getValue()));
			}
			// set alpha for triangles
			for (int i = 2; i < viewFX.getListViewTriangle().getItems().size(); i++) {
				final int index = i - 2; // Make 'i' effectively final
				appJME.enqueue(
						() -> ViewModel.setAlphaTriangle(index, (int) viewFX.getSliderTransparency().getValue()));
			}
			// set alpha for edges
			for (int i = 2; i < viewFX.getListViewEdge().getItems().size(); i++) {
				final int index = i - 2; // Make 'i' effectively final
				appJME.enqueue(() -> ViewModel.setAlphaEdge(index, (int) viewFX.getSliderTransparency().getValue()));
			}
			// set alpha for vertices
			for (int i = 2; i < viewFX.getListViewVertex().getItems().size(); i++) {
				final int index = i - 2; // Make 'i' effectively final
				appJME.enqueue(() -> ViewModel.setAlphaVertex(index, (int) viewFX.getSliderTransparency().getValue()));
			}

		});
	}

	private static void opaqueTransparent(ViewFX viewFX, AppJME appJME) {
		// put switch function on Button transparent/opaque
		viewFX.getButtonTransparentOpaque().setOnAction((ActionEvent event) -> {

			// switch value of transparent boolean
			ViewModel.setTransparent(!ViewModel.isTransparent());

			// set tetrahedrons opaque/transparent
			EventManager.tetOpaqueTransparent(viewFX, appJME);

			// set triangles opaque/transparent
			EventManager.triOpaqueTransparent(viewFX, appJME);

			// set edges opaque/transparent
			EventManager.edgOpaqueTransparent(viewFX, appJME);

			// set vertices opaque/transparent
			EventManager.verOpaqueTransparent(viewFX, appJME);

		});
	}

	private static void tetOpaqueTransparent(ViewFX viewFX, AppJME appJME) {
		for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
			final int index = i - 2; // Make 'i' effectively final
			if (ViewModel.isTransparent()) {
				appJME.enqueue(() -> ViewModel.transparentTetrahedron(index));
			} else {
				appJME.enqueue(() -> ViewModel.opaqueTetrahedron(index));
			}
		}
	}

	private static void triOpaqueTransparent(ViewFX viewFX, AppJME appJME) {
		for (int i = 2; i < viewFX.getListViewTriangle().getItems().size(); i++) {
			final int index = i - 2; // Make 'i' effectively final
			if (ViewModel.isTransparent()) {
				appJME.enqueue(() -> ViewModel.transparentTriangle(index));
			} else {
				appJME.enqueue(() -> ViewModel.opaqueTriangle(index));
			}
		}
	}

	private static void edgOpaqueTransparent(ViewFX viewFX, AppJME appJME) {
		for (int i = 2; i < viewFX.getListViewEdge().getItems().size(); i++) {
			final int index = i - 2; // Make 'i' effectively final
			if (ViewModel.isTransparent()) {
				appJME.enqueue(() -> ViewModel.transparentEdge(index));
			} else {
				appJME.enqueue(() -> ViewModel.opaqueEdge(index));
			}
		}
	}

	private static void verOpaqueTransparent(ViewFX viewFX, AppJME appJME) {
		for (int i = 2; i < viewFX.getListViewVertex().getItems().size(); i++) {
			final int index = i - 2; // Make 'i' effectively final
			if (ViewModel.isTransparent()) {
				appJME.enqueue(() -> ViewModel.transparentVertex(index));
			} else {
				appJME.enqueue(() -> ViewModel.opaqueVertex(index));
			}
		}
	}

	/**
	 * manages action if settings checkbox is ticked/unticked
	 * 
	 * @param viewFX
	 * @param appJME
	 */
	private static void settingsTicked(ViewFX viewFX) {
		// checkbox edges with tets
		viewFX.getCheckBoxSettings()[0].selectedProperty().addListener((observable, oldValue, newValue) -> {

			// action if checkbox is newly checked -> select edges of selected tets
			if (viewFX.getCheckBoxSettings()[0].isSelected()) {

				EventManager.edgesSettingsTicked(viewFX);

			}
			// action if checkbox is newly unchecked -> deselect edges of selected tets
			else {

				EventManager.edgesSettingsNotTicked(viewFX);

			}

		});

		// checkbox vertex with tets
		viewFX.getCheckBoxSettings()[1].selectedProperty().addListener((observable, oldValue, newValue) -> {

			// action if checkbox is newly checked -> select vertices of selected tets
			if (viewFX.getCheckBoxSettings()[1].isSelected()) {

				EventManager.vertexSettingsTicked(viewFX);

			}
			// action if checkbox is newly unchecked -> deselect vertices of selected tets
			else {

				EventManager.vertexSettingsNotTicked(viewFX);

			}

		});
	}

	private static void edgesSettingsTicked(ViewFX viewFX) {
		for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
			final int index = i - 2; // Make 'i' effectively final
			final int startingValue = 6 * index + 2;
			if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
				viewFX.getListViewEdge().getSelectionModel().selectIndices(startingValue, startingValue + 1,
						startingValue + 2, startingValue + 3, startingValue + 4, startingValue + 5);
			}
		}
	}

	private static void edgesSettingsNotTicked(ViewFX viewFX) {
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

	private static void vertexSettingsTicked(ViewFX viewFX) {
		for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
			final int index = i - 2; // Make 'i' effectively final
			final int startingValue = 4 * index + 2;
			if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
				viewFX.getListViewVertex().getSelectionModel().selectIndices(startingValue, startingValue + 1,
						startingValue + 2, startingValue + 3);
			}
		}
	}

	private static void vertexSettingsNotTicked(ViewFX viewFX) {
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

	private static void deselectAll(ViewFX viewFX) {
		// trigger event for deselection of all objects
		viewFX.getButtonDeselectAllElements().setOnAction((ActionEvent event) -> {
			// deselect tets
			viewFX.getListViewTetrahedron().getSelectionModel().clearAndSelect(1);
			// deselect tris
			viewFX.getListViewTriangle().getSelectionModel().clearAndSelect(1);
			// deselect edges
			viewFX.getListViewEdge().getSelectionModel().clearAndSelect(1);
			// deselect vertices
			viewFX.getListViewVertex().getSelectionModel().clearAndSelect(1);

		});
	}

	/**
	 * shows and hides tetrahedron, includes action if one of the setting checkboxes
	 * is ticked
	 * 
	 * @param viewFX
	 * @param appJME
	 */
	private static void selectionTet(ViewFX viewFX, AppJME appJME) {

		// trigger event for selection/deselection of tetrahedrons
		viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices()
				.addListener((ListChangeListener<Integer>) c -> {
					for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
							appJME.enqueue(() -> ViewModel.showTetrahedron(index));
						} else {
							appJME.enqueue(() -> ViewModel.hideTetrahedron(index));
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

					EventManager.showEdgesWithTets(viewFX);

					EventManager.showVerticesWithTets(viewFX);

				});
	}

	/**
	 * selects and hides triangles
	 * 
	 * @param viewFX
	 * @param appJME
	 */
	private static void selectionTri(ViewFX viewFX, AppJME appJME) {
		// trigger event for selection/deselection of triangles
		viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices()
				.addListener((ListChangeListener<Integer>) c -> {
					for (int i = 2; i < viewFX.getListViewTriangle().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						if (viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices().contains(i)) {
							appJME.enqueue(() -> ViewModel.showTriangle(index));
						} else {
							appJME.enqueue(() -> ViewModel.hideTriangle(index));
						}
					}
					// if "select all" is selected, every triangle is selected
					if (viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices().contains(0)) {
						for (int j = 2; j < viewFX.getListViewTriangle().getItems().size(); j++) {
							viewFX.getListViewTriangle().getSelectionModel().select(j);
						}
					}

				});
	}

	/**
	 * selects and hides edges
	 * 
	 * @param viewFX
	 * @param appJME
	 */
	private static void selectionEdg(ViewFX viewFX, AppJME appJME) {
		// trigger event for selection/deselection of edges
		viewFX.getListViewEdge().getSelectionModel().getSelectedIndices()
				.addListener((ListChangeListener<Integer>) c -> {
					for (int i = 2; i < viewFX.getListViewEdge().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						if (viewFX.getListViewEdge().getSelectionModel().getSelectedIndices().contains(i)) {
							appJME.enqueue(() -> ViewModel.showEdge(index));
						} else {
							appJME.enqueue(() -> ViewModel.hideEdge(index));
						}
					}
					// if "select all" is selected, every edge is selected
					if (viewFX.getListViewEdge().getSelectionModel().getSelectedIndices().contains(0)) {
						for (int j = 2; j < viewFX.getListViewEdge().getItems().size(); j++) {
							viewFX.getListViewEdge().getSelectionModel().select(j);
						}
					}

				});
	}

	/**
	 * selects and hides vertices
	 * 
	 * @param viewFX
	 * @param appJME
	 */
	private static void selectionVer(ViewFX viewFX, AppJME appJME) {
		// trigger event for selection/deselection of vertices
		viewFX.getListViewVertex().getSelectionModel().getSelectedIndices()
				.addListener((ListChangeListener<Integer>) c -> {
					for (int i = 2; i < viewFX.getListViewVertex().getItems().size(); i++) {
						final int index = i - 2; // Make 'i' effectively final
						if (viewFX.getListViewVertex().getSelectionModel().getSelectedIndices().contains(i)) {
							appJME.enqueue(() -> ViewModel.showVertex(index));
						} else {
							appJME.enqueue(() -> ViewModel.hideVertex(index));
						}
					}
					// if "select all" is selected, every vertex is selected
					if (viewFX.getListViewVertex().getSelectionModel().getSelectedIndices().contains(0)) {
						for (int j = 2; j < viewFX.getListViewVertex().getItems().size(); j++) {
							viewFX.getListViewVertex().getSelectionModel().select(j);
						}

					}
				});
	}

	private static void showEdgesWithTets(ViewFX viewFX) {
		// show edges with tet
		if (viewFX.getCheckBoxSettings()[0].isSelected()) {

			for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
				final int index = i - 2; // Make 'i' effectively final
				final int startingValue = 6 * index + 2;
				if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
					viewFX.getListViewEdge().getSelectionModel().selectIndices(startingValue, startingValue + 1,
							startingValue + 2, startingValue + 3, startingValue + 4, startingValue + 5);
				} else {
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

	private static void showVerticesWithTets(ViewFX viewFX) {
		// show vertices with tet
		if (viewFX.getCheckBoxSettings()[1].isSelected()) {

			for (int i = 2; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
				final int index = i - 2; // Make 'i' effectively final
				final int startingValue = 4 * index + 2;
				if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
					viewFX.getListViewVertex().getSelectionModel().selectIndices(startingValue, startingValue + 1,
							startingValue + 2, startingValue + 3);
				} else {
					viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue);
					viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 1);
					viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 2);
					viewFX.getListViewVertex().getSelectionModel().clearSelection(startingValue + 3);
				}
			}
		}
	}

}
