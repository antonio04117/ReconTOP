package presenter;

import com.jme3.math.ColorRGBA;

import app.App.AppFX.AppJME;
import model.Mesh;
import model.geometry.Point3D;
import model.topology.Tetrahedron3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;
import view.jfx.ViewFX;
import view.jme.TetrahedronJME;
import viewmodel.ViewModel;

/**
 * presenter according to mvpvm-pattern
 */
public abstract class Presenter {

	// Application starts with exampleA
	private static boolean exampleA = true;

	/**
	 * create controls on fx-Buttons
	 * 
	 * @param viewJFX
	 * @param appJME
	 * @param sampleSize
	 */
	public static void createConnection(ViewFX viewJFX, AppJME appJME, int sampleSize) {
		//TODO change to tablePane (siehe Google Verlauf)
		
//		viewJFX.getShowBoundary().selectedTextProperty().addListener((observable, oldValue, newValue) -> {
//			if (newValue.length() > 0) {
//				appJME.enqueue(() -> ViewModel.showBoundary());
//			} else {
//				appJME.enqueue(() -> ViewModel.hideBoundary());
//			}
//		});

	}

	/**
	 * initialize jme view with drawn elements
	 * 
	 * @param app
	 * @param sampleSize
	 */
	public static void setScene(AppJME app, Mesh mesh) {

		ViewModel.initializeView(app, exampleA);
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
	}
}
