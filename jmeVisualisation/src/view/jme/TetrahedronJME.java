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

public class TetrahedronJME {

	private Node parentNode;

	private Geometry tetrahedronGeo;

	public TetrahedronJME(AssetManager assetManager, Node parentNode, float x0, float y0, float z0, float x1, float y1,
			float z1, float x2, float y2, float z2, float x3, float y3, float z3, ColorRGBA color) {

		this.parentNode = parentNode;

		// triangle in jme
		Mesh tetrahedronMesh = new Mesh();

		float[] vertices = { x0, y0, z0, // point A
				x1, y1, z1, // point B
				x2, y2, z2, // point C
				x3, y3, z3 // point D
		};

		short[] indexes = { 0, 1, 2, // triangle A
				0, 1, 3, // triangle B
				0, 2, 3, // triangle C
				1, 2, 3 }; // triangle D

		tetrahedronMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		tetrahedronMesh.setBuffer(Type.Index, 3, indexes);
		tetrahedronMesh.updateBound();

		tetrahedronGeo = new Geometry("Tetrahedron", tetrahedronMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		// initialize fully visible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		tetrahedronGeo.setMaterial(mat);

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
