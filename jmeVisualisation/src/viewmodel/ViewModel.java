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
	private static HashSet<EdgeJME> edgBoundary = new HashSet<EdgeJME>();
	private static LinkedList<EdgeJME> edges = new LinkedList<EdgeJME>();
	private static HashSet<VertexJME> verBoundary = new HashSet<VertexJME>();
	private static LinkedList<VertexJME> vertices = new LinkedList<VertexJME>();

	/**
	 * initialize jme view without drawn elements
	 * 
	 * @param app
	 * @param exampleA
	 */
	public static void initializeView(AppJME app) {
		JmeConfigurations.jmeInitializer(app);
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

		// list of boundary triangles
		if (t.isBoundary()) {
			ViewModel.triBoundary.add(tri);
		}

		// list of all triangles
		ViewModel.triangles.add(tri);

	}

	/**
	 * create line in jme
	 * 
	 * @param app
	 * @param e
	 * @param color
	 */
	public static void drawEdge(AppJME app, Edge3D e, ColorRGBA color) {
		EdgeJME edg = new EdgeJME(app.getAssetManager(), app.getRootNode(), (float) e.getStart().getP().getX(),
				(float) e.getStart().getP().getY(), (float) e.getStart().getP().getZ(),
				(float) e.getEnd().getP().getX(), (float) e.getEnd().getP().getY(), (float) e.getEnd().getP().getZ(),
				color);

		// list of boundary triangles
		if (e.isBoundary()) {
			ViewModel.edgBoundary.add(edg);
		}

		// list of all edges
		ViewModel.edges.add(edg);
	}

	/**
	 * create vertex in jme
	 *
	 * @param app
	 * @param v
	 * @param color
	 */
	public static void drawVertex(AppJME app, Vertex3D v, ColorRGBA color) {

		VertexJME ver = new VertexJME(app.getAssetManager(), app.getRootNode(), (float) v.getP().getX(),
				(float) v.getP().getY(), (float) v.getP().getZ(), color);

		// list of boundary triangles
		if (v.isBoundary()) {
			ViewModel.verBoundary.add(ver);
		}

		// list of all vertices
		ViewModel.vertices.add(ver);
	}

	public static void drawBoundaryElements(boolean triangles, boolean edges, boolean vertices) {
		if (triangles) {
			for (TriangleJME triangleJME : triBoundary) {
				triangleJME.setVisibility(true);
			}
		}
		if (edges) {
			for (EdgeJME edgeJME : edgBoundary) {
				edgeJME.setVisibility(true);
			}
		}
		if (vertices) {
			for (VertexJME vertexJME : verBoundary) {
				vertexJME.setVisibility(true);
			}
		}
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

	public static void showEdge(int i) {
		edges.get(i).setVisibility(true);
	}

	public static void hideEdge(int i) {
		edges.get(i).setVisibility(false);
	}

	public static void showVertex(int i) {
		vertices.get(i).setVisibility(true);
	}

	public static void hideVertex(int i) {
		vertices.get(i).setVisibility(false);
	}

}
