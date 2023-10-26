package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class TriangleJME {

	private Node parentNode;

	private Geometry triangleGeo;

	private ColorRGBA color;

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
	public TriangleJME(AssetManager assetManager, Node parentNode, float[] coords0, float[] coords1, float[] coords2,
			ColorRGBA color) {

		this.parentNode = parentNode;
		this.color = color;

		// triangle in jme
		Mesh triangleMesh = new Mesh();

		defineMesh(triangleMesh, new float[] { coords0[0], coords0[1], coords0[2] },
				new float[] { coords1[0], coords1[1], coords1[2] }, new float[] { coords2[0], coords2[1], coords2[2] });

		triangleGeo = new Geometry("Triangle", triangleMesh);

		// define material -> Lighting material renders according to light sources
		Material mat = JmeConfigurations.createLightingMaterial(assetManager, color);

		// activate transparency
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

		triangleGeo.setMaterial(mat);

		// sorts the objects so the furthest object gets rendered first
		triangleGeo.setQueueBucket(Bucket.Transparent);
	}

	/**
	 * define mesh of triangles
	 * 
	 * @param triangleMesh
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 */
	private void defineMesh(Mesh triangleMesh, float[] coords0, float[] coords1, float[] coords2) {

		// set bound of mesh
		float[] vertices = { coords0[0], coords0[1], coords0[2], // point A
				coords1[0], coords1[1], coords1[2], // point B
				coords2[0], coords2[1], coords2[2], // point C
		};

		int[] indexes = { 0, 1, 2 };

		triangleMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		triangleMesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indexes));

		// generate normals to give mesh direction -> needed for light
		Vector3f normal = new Vector3f();
		Triangle.computeTriangleNormal(new Vector3f(coords0[0], coords0[1], coords0[2]),
				new Vector3f(coords1[0], coords1[1], coords1[2]), new Vector3f(coords2[0], coords2[1], coords2[2]),
				normal);
		float[] normals = { normal.x, normal.y, normal.z, normal.x, normal.y, normal.z, normal.x, normal.y, normal.z };

		triangleMesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));

		// prevent mesh to disappear while still in the view frustum
		triangleMesh.updateBound();
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

	public void setTransparency(boolean transparent) {
		if (transparent) {
			triangleGeo.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			triangleGeo.setQueueBucket(Bucket.Transparent);
		} else {
			triangleGeo.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Off);
			triangleGeo.setQueueBucket(Bucket.Opaque);
		}
	}

	public void move(float y0, float y1, float y2) {
		this.triangleGeo.move(y0, y1, y2);
	}

	public Geometry getTriangleGeo() {
		return triangleGeo;
	}

	public ColorRGBA getColor() {
		return color;
	}

}
