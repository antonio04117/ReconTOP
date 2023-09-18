package presenter;

import com.jme3.math.ColorRGBA;

import app.App.AppFX.AppJME;
import model.Mesh;
import model.topology.Edge3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;
import view.fx.ViewFX;
import viewmodel.InitialView;
import viewmodel.ViewModel;
import javafx.collections.ListChangeListener;

/**
 * presenter according to mvpvm-pattern
 */
public abstract class Presenter {

	/**
	 * create controls on selection of input in fx-tables
	 * 
	 * @param viewFX
	 * @param appJME
	 */
	public static void createConnection(ViewFX viewFX, AppJME appJME) {

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

	/**
	 * initialize jme view with drawn elements
	 * 
	 * @param app
	 * @param sampleSize
	 */
	public static void setScene(AppJME app, Mesh mesh, InitialView initView) {

		ViewModel.initializeView(app);
		Presenter.drawElements(app, mesh);

		if (initView == InitialView.ALLBOUNDARYELEMENTS) {
			ViewModel.drawBoundaryElements(true, true, true);
		} else if (initView == InitialView.TRIANGLEBOUNDARY) {
			ViewModel.drawBoundaryElements(true, false, false);
		} else if (initView == InitialView.EDGEBOUNDARY) {
			ViewModel.drawBoundaryElements(false, true, false);
		} else if (initView == InitialView.VERTEXBOUNDARY) {
			ViewModel.drawBoundaryElements(false, false, true);
		} else if (initView == InitialView.EDGEVERTEXBOUNDARY) {
			ViewModel.drawBoundaryElements(false, true, true);
		} else if (initView == InitialView.NOELEMENTS) {
		}
	}

	/**
	 * draw all elements in jme view
	 * 
	 * @param app
	 * @param mesh
	 * @param firstMesh
	 */
	private static void drawElements(AppJME app, Mesh mesh) {

		// color for tetrahedron
		ColorRGBA color = ColorRGBA.Red;

		for (int i = 0; i < mesh.getMapTet().size(); i++) {
			// draw tetrahedron
			ViewModel.drawTetrahedron(app, mesh.getMapTet().get(i), color);

		}

		// color for triangle
		color = ColorRGBA.Blue;

		for (int i = 0; i < mesh.getMapTri().size(); i++) {
			for (Triangle3D triangle : mesh.getMapTri().get(i)) {

				ViewModel.drawTriangle(app, triangle, color);

			}
		}

		// color for edge
		color = ColorRGBA.DarkGray;

		for (int i = 0; i < mesh.getMapEdg().size(); i++) {
			for (Edge3D edge : mesh.getMapEdg().get(i)) {

				ViewModel.drawEdge(app, edge, color);

			}
		}

		// color for vertex
		color = ColorRGBA.Green;

		for (int i = 0; i < mesh.getMapVer().size(); i++) {
			for (Vertex3D vertex : mesh.getMapVer().get(i)) {

				ViewModel.drawVertex(app, vertex, color);

			}
		}
	}
}
