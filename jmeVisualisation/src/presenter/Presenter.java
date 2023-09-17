package presenter;

import com.jme3.math.ColorRGBA;

import app.App.AppFX.AppJME;
import model.Mesh;
import model.topology.Edge3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;
import view.fx.ViewFX;
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

		// trigger event for selection/deselection of traingles
		viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 0; i < viewFX.getListViewTetrahedron().getItems().size(); i++) {
							final int index = i; // Make 'i' effectively final
							if (viewFX.getListViewTetrahedron().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Tetrahedron " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showTetrahedron(Integer.valueOf(index)));
							} else {
								System.out.println("Tetrahedron " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideTetrahedron(Integer.valueOf(index)));
							}
						}
					}
				});

		// trigger event for selection/deselection of traingles
		viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 0; i < viewFX.getListViewTriangle().getItems().size(); i++) {
							final int index = i; // Make 'i' effectively final
							if (viewFX.getListViewTriangle().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Triangle " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showTriangle(Integer.valueOf(index)));
							} else {
								System.out.println("Triangle " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideTriangle(Integer.valueOf(index)));
							}
						}
					}
				});

		// trigger event for selection/deselection of traingles
		viewFX.getListViewEdges().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 0; i < viewFX.getListViewEdges().getItems().size(); i++) {
							final int index = i; // Make 'i' effectively final
							if (viewFX.getListViewEdges().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Edge " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showEdge(Integer.valueOf(index)));
							} else {
								System.out.println("Triangle " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideEdge(Integer.valueOf(index)));
							}
						}
					}
				});

		// trigger event for selection/deselection of traingles
		viewFX.getListViewVertices().getSelectionModel().getSelectedIndices()
				.addListener(new ListChangeListener<Integer>() {
					@Override
					public void onChanged(Change<? extends Integer> c) {
						for (int i = 0; i < viewFX.getListViewVertices().getItems().size(); i++) {
							final int index = i; // Make 'i' effectively final
							if (viewFX.getListViewVertices().getSelectionModel().getSelectedIndices().contains(i)) {
								System.out.println("Vertex " + i + " selected\n");
								appJME.enqueue(() -> ViewModel.showVertex(Integer.valueOf(index)));
							} else {
								System.out.println("Vertex " + i + " not selected\n");
								appJME.enqueue(() -> ViewModel.hideVertex(Integer.valueOf(index)));
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
	public static void setScene(AppJME app, Mesh mesh) {

		ViewModel.initializeView(app);
		Presenter.drawElements(app, mesh);
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
		color = ColorRGBA.Yellow;

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
