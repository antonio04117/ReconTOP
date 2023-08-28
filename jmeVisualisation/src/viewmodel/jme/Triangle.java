package viewmodel.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class Triangle {

	AssetManager assetManager;
	Node parentNode;
	// points
	Vector3f p1;
	Vector3f p2;
	Vector3f p3;

	Geometry triangleGeo;

	/**
	 * creates a triangle in jme between point 1, 2 and 3 and visible from both
	 * sides
	 * 
	 * @param assetManager
	 * @param parentNode
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param x3
	 * @param y3
	 * @param z3
	 * @param color
	 */
	public Triangle(AssetManager assetManager, Node parentNode, float x1, float y1, float z1, float x2, float y2,
			float z2, float x3, float y3, float z3, ColorRGBA color) {
		this.assetManager = assetManager;
		this.parentNode = parentNode;

		p1 = new Vector3f(x1, y1, z1);
		p2 = new Vector3f(x2, y2, z2);
		p3 = new Vector3f(x3, y3, z3);

		// triangle in jme
		Mesh triangleMesh1 = new Mesh();

		float[] vertices1 = { x1, y1, z1, // Punkt A
				x2, y2, z2, // Punkt B
				x3, y3, z3 // Punkt C
		};
		
		int[] indexes = { 0, 1, 2 };

		triangleMesh1.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices1));
		triangleMesh1.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indexes));
		triangleMesh1.updateBound();

		triangleGeo = new Geometry ("Triangle", triangleMesh1);

		
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		triangleGeo.setMaterial(mat);

		parentNode.attachChild(triangleGeo);
	}

	public void move(float y1, float y2, float y3) {
		this.triangleGeo.move(y1, y2, y3);
	}

}
