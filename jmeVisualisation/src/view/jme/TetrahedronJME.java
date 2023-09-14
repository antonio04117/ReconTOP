package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class TetrahedronJME {

	private AssetManager assetManager;
	private Node parentNode;
	// points
	private Vector3f p0;
	private Vector3f p1;
	private Vector3f p2;
	private Vector3f p3;

	private TriangleJME[] triangles;

	public TetrahedronJME(AssetManager assetManager, Node parentNode, float x0, float y0, float z0, float x1, float y1,
			float z1, float x2, float y2, float z2, float x3, float y3, float z3, ColorRGBA color) {
		this.assetManager = assetManager;
		this.parentNode = parentNode;

		p0 = new Vector3f(x0, y0, z0);
		p1 = new Vector3f(x1, y1, z1);
		p2 = new Vector3f(x2, y2, z2);
		p3 = new Vector3f(x3, y3, z3);

		// tetrahedron in jme
		TriangleJME t0 = new TriangleJME(assetManager, parentNode, x0, y0, z0, x1, y1, z1, x2, y2, z2, color);
		TriangleJME t1 = new TriangleJME(assetManager, parentNode, x0, y0, z0, x1, y1, z1, x3, y3, z3, color);
		TriangleJME t2 = new TriangleJME(assetManager, parentNode, x0, y0, z0, x2, y2, z2, x3, y3, z3, color);
		TriangleJME t3 = new TriangleJME(assetManager, parentNode, x1, y1, z1, x2, y2, z2, x3, y3, z3, color);

		// TODO adapt visibility
		t0.setVisibility(true);
		t1.setVisibility(true);
		t2.setVisibility(true);
		t3.setVisibility(true);
	}

}
