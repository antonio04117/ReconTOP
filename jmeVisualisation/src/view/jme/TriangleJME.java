package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.Technique;
import com.jme3.material.TechniqueDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Triangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import com.jme3.util.TangentBinormalGenerator;

public class TriangleJME {

	private Node parentNode;

	private Geometry triangleGeo;

	/**
	 * creates a triangle in jme between point 1, 2 and 3 and visible from both
	 * sides after it has been set visible outside of this method
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

		setBuffer(triangleMesh, x0, y0, z0, x1, y1, z1, x2, y2, z2);

		triangleMesh.updateBound();

		Vector3f normal = new Vector3f();
		Triangle.computeTriangleNormal(new Vector3f(x0, y0, z0), new Vector3f(x1, y1, z1), new Vector3f(x2, y2, z2),
				normal);

		float[] normals = { normal.x, normal.y, normal.z, normal.x, normal.y, normal.z, normal.x, normal.y, normal.z };

		triangleMesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));

//generate texture coordinates
		Vector2f[] texCoord = { new Vector2f(0, 0), // texcoord for point A
				new Vector2f(1, 0), // texcoord for point B
				new Vector2f(0, 1) // texcoord for point C
		};

		triangleMesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));

//		TangentBinormalGenerator.generate(triangleMesh);

		triangleGeo = new Geometry("Triangle", triangleMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat.setColor("Ambient", color);
		mat.setColor("Diffuse", color);
		mat.setColor("Specular", ColorRGBA.Green);
		mat.setBoolean("UseMaterialColors", true);
//		mat.setBoolean("BackfaceShadows", true);
		mat.setFloat("Shininess", 10);

		// initialize invisible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		triangleGeo.setMaterial(mat);
	}

	private void setBuffer(Mesh triangleMesh, float x0, float y0, float z0, float x1, float y1, float z1, float x2,
			float y2, float z2) {

		float[] vertices = { x0, y0, z0, // Punkt A
				x1, y1, z1, // Punkt B
				x2, y2, z2, // Punkt C
//				(x0 + x1) / 2, (y0 + y1) / 2, (z0 + z1) / 2, // Punkt D
//				(x0 + x2) / 2, (y0 + y2) / 2, (z0 + z2) / 2, // Punkt E
//				(x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2 // Punkt F
		};

		int[] indexes = { 0, 1, 2
//				0, 3, 4, // Erstes Dreieck (ABC)
//				1, 3, 5, // Zweites Dreieck (DEF)
//				2, 4, 5, // Drittes Dreieck (BDE)
//				3, 4, 5 // Viertes Dreieck (CEF)
		};

		triangleMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		triangleMesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indexes));

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
