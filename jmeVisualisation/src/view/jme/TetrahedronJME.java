package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class TetrahedronJME {

	private Node parentNode;

	private Node tetrahedronGeo;

	public TetrahedronJME(AssetManager assetManager, Node parentNode, float x0, float y0, float z0, float x1, float y1,
			float z1, float x2, float y2, float z2, float x3, float y3, float z3, ColorRGBA color) {

		this.parentNode = parentNode;

		TriangleJME tri0 = new TriangleJME(assetManager, parentNode, x0, y0, z0, x1, y1, z1, x2, y2, z2, color);
		TriangleJME tri1 = new TriangleJME(assetManager, parentNode, x0, y0, z0, x1, y1, z1, x3, y3, z3, color);
		TriangleJME tri2 = new TriangleJME(assetManager, parentNode, x0, y0, z0, x2, y2, z2, x3, y3, z3, color);
		TriangleJME tri3 = new TriangleJME(assetManager, parentNode, x1, y1, z1, x2, y2, z2, x3, y3, z3, color);

		tetrahedronGeo = new Node();
		tetrahedronGeo.attachChild(tri0.getTriangleGeo());
		tetrahedronGeo.attachChild(tri1.getTriangleGeo());
		tetrahedronGeo.attachChild(tri2.getTriangleGeo());
		tetrahedronGeo.attachChild(tri3.getTriangleGeo());
//		parentNode.attachChild(tetrahedronGeo);
	}

	public void move(float y0, float y1, float y2) {
		this.tetrahedronGeo.move(y0, y1, y2);
	}

	public void setVisibility(boolean visible) {
		if (visible) {
			// visible
			parentNode.attachChild(tetrahedronGeo);
		} else {
			// not visible
			parentNode.detachChild(tetrahedronGeo);

		}
	}

}
