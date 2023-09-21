package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class TriangleJME {

	private Node parentNode;

	private Geometry triangleGeo;

	/**
	 * creates a triangle in jme between point 1, 2 and 3 and visible from both
	 * sides
	 * 
	 * @param assetManager
	 * @param parentNode
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public TriangleJME(AssetManager assetManager, Node parentNode, float x0, float y0, float z0, float x1, float y1,
			float z1, float x2, float y2, float z2, ColorRGBA color) {

		this.parentNode = parentNode;

		// triangle in jme
		Mesh triangleMesh = new Mesh();

		float[] vertices = { x0, y0, z0, // point A
				x1, y1, z1, // point B
				x2, y2, z2 // point C
		};

		int[] indexes = { 0, 1, 2 };

		triangleMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		triangleMesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indexes));
		triangleMesh.updateBound();

		triangleGeo = new Geometry("Triangle", triangleMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		// initialize invisible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		triangleGeo.setMaterial(mat);

//		parentNode.attachChild(triangleGeo);
	}

	public void move(float y0, float y1, float y2) {
		this.triangleGeo.move(y0, y1, y2);
	}

	public void setVisibility(boolean visible) {
		if (visible) {
			// visible
			parentNode.attachChild(triangleGeo);
		} else {
			// not visible
			parentNode.detachChild(triangleGeo);
		}
	}

	public Geometry getTriangleGeo() {
		return triangleGeo;
	}

}
