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

	private ViewModel() {
	}

	private static LinkedList<TetrahedronJME> tetrahedrons = new LinkedList<>();
	private static HashSet<TriangleJME> triBoundary = new HashSet<>();
	private static LinkedList<TriangleJME> triangles = new LinkedList<>();
	private static HashSet<EdgeJME> edgBoundary = new HashSet<>();
	private static LinkedList<EdgeJME> edges = new LinkedList<>();
	private static HashSet<VertexJME> verBoundary = new HashSet<>();
	private static LinkedList<VertexJME> vertices = new LinkedList<>();

	private static boolean transparent = true;

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
				new float[] { tet.getP0().getP().getX(), tet.getP0().getP().getY(), tet.getP0().getP().getZ() },
				new float[] { tet.getP1().getP().getX(), tet.getP1().getP().getY(), tet.getP1().getP().getZ() },
				new float[] { tet.getP2().getP().getX(), tet.getP2().getP().getY(), tet.getP2().getP().getZ() },
				new float[] { tet.getP3().getP().getX(), tet.getP3().getP().getY(), tet.getP3().getP().getZ() }, color);

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

		TriangleJME tri = new TriangleJME(app.getAssetManager(), app.getRootNode(),
				new float[] { t.getP0().getP().getX(), t.getP0().getP().getY(), t.getP0().getP().getZ() },
				new float[] { t.getP1().getP().getX(), t.getP1().getP().getY(), t.getP1().getP().getZ() },
				new float[] { t.getP2().getP().getX(), t.getP2().getP().getY(), t.getP2().getP().getZ() }, color);

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
		EdgeJME edg = new EdgeJME(app.getAssetManager(), app.getRootNode(),
				new float[] { e.getStart().getP().getX(), e.getStart().getP().getY(), e.getStart().getP().getZ() },
				new float[] { e.getEnd().getP().getX(), e.getEnd().getP().getY(), e.getEnd().getP().getZ() }, color);

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

		VertexJME ver = new VertexJME(app.getAssetManager(), app.getRootNode(), v.getP().getX(), v.getP().getY(),
				v.getP().getZ(), color);

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
	 * sets the initial camer position of the jme view
	 * 
	 * @param app
	 */
	public static void setInitialCameraPosition(AppJME app) {
		JmeConfigurations.setInitialCameraPosition(app);
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

	public static void transparentTetrahedron(int i) {
		tetrahedrons.get(i).setTransparency(true);
	}

	public static void opaqueTetrahedron(int i) {
		tetrahedrons.get(i).setTransparency(false);
	}

	public static void showTriangle(int i) {
		triangles.get(i).setVisibility(true);
	}

	public static void hideTriangle(int i) {
		triangles.get(i).setVisibility(false);
	}

	public static void transparentTriangle(int i) {
		triangles.get(i).setTransparency(true);
	}

	public static void opaqueTriangle(int i) {
		triangles.get(i).setTransparency(false);
	}

	public static void showEdge(int i) {
		edges.get(i).setVisibility(true);
	}

	public static void hideEdge(int i) {
		edges.get(i).setVisibility(false);
	}

	public static void transparentEdge(int i) {
		edges.get(i).setTransparency(true);
	}

	public static void opaqueEdge(int i) {
		edges.get(i).setTransparency(false);
	}

	public static void showVertex(int i) {
		vertices.get(i).setVisibility(true);
	}

	public static void hideVertex(int i) {
		vertices.get(i).setVisibility(false);
	}

	public static void transparentVertex(int i) {
		vertices.get(i).setTransparency(true);
	}

	public static void opaqueVertex(int i) {
		vertices.get(i).setTransparency(false);
	}

	public static void setAlphaTetrahedron(int i, int deltaAlpha) {
		JmeConfigurations.changeAlpha(tetrahedrons.get(i).getTetrahedronGeo(), tetrahedrons.get(i).getColor(),
				deltaAlpha);
	}

	public static void setAlphaTriangle(int i, int deltaAlpha) {
		JmeConfigurations.changeAlpha(triangles.get(i).getTriangleGeo(), triangles.get(i).getColor(), deltaAlpha);
	}

	public static void setAlphaEdge(int i, int deltaAlpha) {
		JmeConfigurations.changeAlpha(edges.get(i).getEdgeGeo(), edges.get(i).getColor(), deltaAlpha);
	}

	public static void setAlphaVertex(int i, int deltaAlpha) {
		JmeConfigurations.changeAlpha(vertices.get(i).getPointGeo(), vertices.get(i).getColor(), deltaAlpha);
	}

	public static boolean isTransparent() {
		return transparent;
	}

	public static void setTransparent(boolean transparent) {
		ViewModel.transparent = transparent;
	}

}
