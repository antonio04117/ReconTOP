package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class VertexJME {

	private AssetManager assetManager;
	private Node parentNode;
	// point
	private Vector3f point;

	private float radius = 0.1f;

	private Geometry pointGeo;

	/**
	 * creates a point in jme
	 * 
	 * @param assetManager
	 * @param parentNode
	 * @param x
	 * @param y
	 * @param z
	 * @param color
	 */
	public VertexJME(AssetManager assetManager, Node parentNode, float x, float y, float z, ColorRGBA color) {
		this.assetManager = assetManager;
		this.parentNode = parentNode;

		point = new Vector3f(x, y, z);

		// line in jme
		Sphere pointMesh = new Sphere(30, 30, radius);

		pointGeo = new Geometry("Point", pointMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		// initialize invisible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
		pointGeo.setMaterial(mat);
		// set location
		move(x, y, z);

		parentNode.attachChild(pointGeo);
	}

	public void move(float y1, float y2, float y3) {
		this.pointGeo.move(y1, y2, y3);
	}

	public Geometry getPointGeo() {
		return pointGeo;
	}

	public void setVisibility(boolean visible) {
		if (visible) {
			// visible
			pointGeo.getMaterial().getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		} else {
			// not visible
			pointGeo.getMaterial().getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);

		}
	}

}
