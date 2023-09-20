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
	public static void createConnection(ViewFX viewFX, AppJME appJME, Mesh mesh) {

		// all events happening in surface only, no calculation in model needed
		EventManager.viewModelEvents(viewFX, appJME);

		// all events needing calculation of the model
		EventManager.coreEvents(viewFX, mesh);

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
