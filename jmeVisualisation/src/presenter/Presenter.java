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
//		viewJFX.getExampleA().setOnAction(e -> appJME.enqueue(() -> Presenter.changeToA(appJME, sampleSize)));
//		viewJFX.getExampleB().setOnAction(e -> appJME.enqueue(() -> Presenter.changeToB(appJME, sampleSize)));
	}

	/**
	 * initialize jme view with drawn elements
	 * 
	 * @param app
	 * @param sampleSize
	 */
	public static void setScene(AppJME app, Mesh mesh) {

		ViewModel.initializeView(app, exampleA);

//		if (exampleA) {
//			// first surface
//			NurbsExampleFactory example1 = NurbsExampleFactory.HA_a1;
//			mesh[0] = GeoUtils.createNurbsAreaSample(example1.create(), sampleSize);
//			// second surface
//			NurbsExampleFactory example2 = NurbsExampleFactory.HA_a2;
//			mesh[1] = GeoUtils.createNurbsAreaSample(example2.create(), sampleSize);
//
//		} else {
//
//			// first surface
//			NurbsExampleFactory example1 = NurbsExampleFactory.HA_b1;
//			mesh[0] = GeoUtils.createNurbsAreaSample(example1.create(), sampleSize);
//			// second surface
//			NurbsExampleFactory example2 = NurbsExampleFactory.HA_b2;
//			mesh[1] = GeoUtils.createNurbsAreaSample(example2.create(), sampleSize);
//		}

		// change colors of Meshes
//		boolean firstMesh = true;
//		for (Mesh meshI : mesh) {
		Presenter.drawElements(app, mesh);
//			firstMesh = false;
//		}
	}

	/**
	 * draw all elements in jme view
	 * 
	 * @param app
	 * @param mesh
	 * @param firstMesh
	 */
	private static void drawElements(AppJME app, Mesh mesh) {

		ColorRGBA color = ColorRGBA.Red;

		// draw Tetrahedrons
//		for (int i = 0; i < mesh.getMapTet().size(); i++) {
//			Tetrahedron3D tet = mesh.getMapTet().get(i);
//			ViewModel.drawTetrahedron(app, tet, color);
//		}

		for (int i = 0; i < mesh.getMapTri().size(); i++) {
			for (Triangle3D triangle : mesh.getMapTri().get(i)) {

				ViewModel.drawTriangle(app, triangle, color);
			}

		}

//		// draw triangles
//		if (firstMesh) {
//			color = ColorRGBA.fromRGBA255(255, 204, 50, 255);
//		} else {
//			color = ColorRGBA.fromRGBA255(251, 104, 180, 255);
//		}
//		for (Triangle3D t : mesh.getTriangles()) {
//			ViewModel.drawTriangle(app, t, color);
//		}
//
//		// draw vertices on triangle mesh
//		if (firstMesh) {
//			color = ColorRGBA.fromRGBA255(254, 96, 40, 255);
//		} else {
//			color = ColorRGBA.Magenta;
//		}
//		for (Vertex3D v : mesh.getVertices()) {
//			ViewModel.drawPoint(app, v, color);
//		}
//
//		// draw control points
//		if (firstMesh) {
//			color = ColorRGBA.Red;
//		} else {
//			color = ColorRGBA.Green;
//		}
//		for (Vertex3D cp : mesh.getControlPoints()) {
//			ViewModel.drawControlPoint(app, cp, color);
//		}
//
//		// draw control polygon
//		if (firstMesh) {
//			color = ColorRGBA.Blue;
//		} else {
//			color = ColorRGBA.DarkGray;
//		}
//		for (Edge3D cp : mesh.getControlPolygons()) {
//			ViewModel.drawLine(app, cp, color);
//		}

	}
}
