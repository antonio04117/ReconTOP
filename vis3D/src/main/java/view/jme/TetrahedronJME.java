package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class TetrahedronJME {

	private Node parentNode;

	private Node tetrahedronGeo;
	private TriangleJME[] tri = new TriangleJME[4];

	private ColorRGBA color;

	public TetrahedronJME(AssetManager assetManager, Node parentNode, float[] coords0, float[] coords1, float[] coords2,
			float[] coords3, ColorRGBA color) {

		this.parentNode = parentNode;
		this.color = color;

		tri[0] = new TriangleJME(assetManager, parentNode, new float[] { coords0[0], coords0[1], coords0[2] },
				new float[] { coords1[0], coords1[1], coords1[2] }, new float[] { coords2[0], coords2[1], coords2[2] },
				color);
		tri[1] = new TriangleJME(assetManager, parentNode, new float[] { coords0[0], coords0[1], coords0[2] },
				new float[] { coords1[0], coords1[1], coords1[2] }, new float[] { coords3[0], coords3[1], coords3[2] },
				color);
		tri[2] = new TriangleJME(assetManager, parentNode, new float[] { coords0[0], coords0[1], coords0[2] },
				new float[] { coords2[0], coords2[1], coords2[2] }, new float[] { coords3[0], coords3[1], coords3[2] },
				color);
		tri[3] = new TriangleJME(assetManager, parentNode, new float[] { coords1[0], coords1[1], coords1[2] },
				new float[] { coords2[0], coords2[1], coords2[2] }, new float[] { coords3[0], coords3[1], coords3[2] },
				color);

		tetrahedronGeo = new Node();
		tetrahedronGeo.attachChild(tri[0].getTriangleGeo());
		tetrahedronGeo.attachChild(tri[1].getTriangleGeo());
		tetrahedronGeo.attachChild(tri[2].getTriangleGeo());
		tetrahedronGeo.attachChild(tri[3].getTriangleGeo());
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

	/**
	 * set transparency of the triangles of the tetrahedron
	 * 
	 * @param transparent
	 */
	public void setTransparency(boolean transparent) {
		for (TriangleJME triangleJME : tri) {
			triangleJME.setTransparency(transparent);
		}
	}

	public Node getTetrahedronGeo() {
		return tetrahedronGeo;
	}

	public ColorRGBA getColor() {
		return color;
	}

}
