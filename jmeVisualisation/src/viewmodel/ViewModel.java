package viewmodel;

import java.util.HashSet;
import java.util.LinkedList;

import com.jme3.math.ColorRGBA;

import app.App.AppFX.AppJME;
import model.topology.Edge3D;
import model.topology.Tetrahedron3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;
import view.jme.EdgeJME;
import view.jme.JmeConfigurations;
import view.jme.TetrahedronJME;
import view.jme.TriangleJME;
import view.jme.VertexJME;

/**
 * viewmodel according to mvpvm-pattern
 */
public abstract class ViewModel {

	private static LinkedList<TetrahedronJME> tetrahedrons = new LinkedList<TetrahedronJME>();
	private static HashSet<TriangleJME> triBoundary = new HashSet<TriangleJME>();
	private static LinkedList<TriangleJME> triangles = new LinkedList<TriangleJME>();

	/**
	 * initialize jme view without drawn elements
	 * 
	 * @param app
	 * @param exampleA
	 */
	public static void initializeView(AppJME app, boolean exampleA) {
		JmeConfigurations.jmeInitializer(app, exampleA);
	}

	/**
	 * create tetrahedron in jme
	 * 
	 * @param app
	 * @param tet
	 * @param color
	 */
	public static void drawTetrahedron(AppJME app, Tetrahedron3D tet, ColorRGBA color) {

		TetrahedronJME tetra = new TetrahedronJME(app.getAssetManager(), app.getRootNode(),
				(float) tet.getP0().getP().getX(), (float) tet.getP0().getP().getY(), (float) tet.getP0().getP().getZ(),
				(float) tet.getP1().getP().getX(), (float) tet.getP1().getP().getY(), (float) tet.getP1().getP().getZ(),
				(float) tet.getP2().getP().getX(), (float) tet.getP2().getP().getY(), (float) tet.getP2().getP().getZ(),
				(float) tet.getP3().getP().getX(), (float) tet.getP3().getP().getY(), (float) tet.getP3().getP().getZ(),
				color);

		// set of all tetrahedrons
		ViewModel.tetrahedrons.add(tetra);

	}

	/**
	 * create triangle in jme -> boundary triangles are shown
	 *
	 * @param assetManager
	 * @param parentNode
	 * @param p0
	 * @param p1
	 * @param p2
	 * @param color
	 * @return
	 */
	public static void drawTriangle(AppJME app, Triangle3D t, ColorRGBA color) {

		TriangleJME tri = new TriangleJME(app.getAssetManager(), app.getRootNode(), (float) t.getP0().getP().getX(),
				(float) t.getP0().getP().getY(), (float) t.getP0().getP().getZ(), (float) t.getP1().getP().getX(),
				(float) t.getP1().getP().getY(), (float) t.getP1().getP().getZ(), (float) t.getP2().getP().getX(),
				(float) t.getP2().getP().getY(), (float) t.getP2().getP().getZ(), color);

		// set of boundary triangles
		if (t.isBoundary()) {
			tri.setVisibility(true);
			ViewModel.triBoundary.add(tri);
		}

		// set of all triangles
		ViewModel.triangles.add(tri);

	}

	/**
	 * create vertex in jme
	 *
	 * @param app
	 * @param v
	 * @param color
	 */
	public static void drawPoint(AppJME app, Vertex3D v, ColorRGBA color) {

		new VertexJME(app.getAssetManager(), app.getRootNode(), (float) v.getP().getX(), (float) v.getP().getY(),
				(float) v.getP().getZ(), color);

	}

	/**
	 * create line in jme
	 * 
	 * @param app
	 * @param cp
	 * @param color
	 */
	public static void drawLine(AppJME app, Edge3D cp, ColorRGBA color) {
		new EdgeJME(app.getAssetManager(), app.getRootNode(), (float) cp.getStart().getP().getX(),
				(float) cp.getStart().getP().getY(), (float) cp.getStart().getP().getZ(),
				(float) cp.getEnd().getP().getX(), (float) cp.getEnd().getP().getY(), (float) cp.getEnd().getP().getZ(),
				color);
	}

	/**
	 * adapt camera according to the chosen example
	 *
	 * @param appJME
	 * @param exampleA
	 */
	public static void adaptView(AppJME appJME, boolean exampleA) {
		JmeConfigurations.adaptView(appJME, exampleA);
	}

	/**
	 * hides boundary elements
	 */
	public static void hideBoundary() {
		for (TriangleJME triangleJME : triBoundary) {
			triangleJME.setVisibility(false);
		}
	}

	/**
	 * shows boundary elements
	 */
	public static void showBoundary() {
		for (TriangleJME triangleJME : triBoundary) {
			triangleJME.setVisibility(true);
		}
	}

	public static void showTetrahedron(int i) {
		tetrahedrons.get(i).setVisibility(true);
	}

	public static void hideTetrahedron(int i) {
		tetrahedrons.get(i).setVisibility(false);
	}

	public static void showTriangle(int i) {
		triangles.get(i).setVisibility(true);
	}

	public static void hideTriangle(int i) {
		triangles.get(i).setVisibility(false);
	}

}
