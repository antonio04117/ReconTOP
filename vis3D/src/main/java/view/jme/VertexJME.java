package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class VertexJME {

	private Node parentNode;

	private float radius = 0.1f;

	private Geometry vertexGeo;

	private ColorRGBA color;

	/**
	 * creates a vertex in jme
	 * 
	 * @param assetManager
	 * @param parentNode
	 * @param x
	 * @param y
	 * @param z
	 * @param color
	 */
	public VertexJME(AssetManager assetManager, Node parentNode, float x, float y, float z, ColorRGBA color) {

		this.parentNode = parentNode;
		this.color = color;

		// point in jme
		Sphere pointMesh = new Sphere(30, 30, radius);
		vertexGeo = new Geometry("Point", pointMesh);

		// define material -> Lighting material renders according to light sources
		Material mat = JmeConfigurations.createLightingMaterial(assetManager, color);

		// activate transparency
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

		// initialize visible from both sides
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		vertexGeo.setMaterial(mat);
		// set location
		move(x, y, z);

	}

	public void setVisibility(boolean visible) {
		if (visible) {
			// visible
			parentNode.attachChild(vertexGeo);
		} else {
			// not visible
			parentNode.detachChild(vertexGeo);

		}
	}

	public void setTransparency(boolean transparent) {
		if (transparent) {
			vertexGeo.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			vertexGeo.setQueueBucket(Bucket.Transparent);

		} else {
			vertexGeo.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Off);
			vertexGeo.setQueueBucket(Bucket.Opaque);
		}
	}

	public void move(float y1, float y2, float y3) {
		this.vertexGeo.move(y1, y2, y3);
	}

	public Geometry getPointGeo() {
		return vertexGeo;
	}

	public ColorRGBA getColor() {
		return color;
	}

}
