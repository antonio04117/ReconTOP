package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

public class EdgeJME {

	private Node parentNode;
	// start point
	private Vector3f startPoint;
	// end point
	private Vector3f endPoint;

	private Geometry edgeGeo;

	private ColorRGBA color;

	/**
	 * creates a line in jme
	 * 
	 * @param assetManager
	 * @param parentNode
	 * @param xS
	 * @param yS
	 * @param zS
	 * @param xE
	 * @param yE
	 * @param zE
	 */
	public EdgeJME(AssetManager assetManager, Node parentNode, float[] coordsS, float[] coordsE, ColorRGBA color) {
		this.parentNode = parentNode;
		this.color = color;

		startPoint = new Vector3f(coordsS[0], coordsS[1], coordsS[2]);
		endPoint = new Vector3f(coordsE[0], coordsE[1], coordsE[2]);

		// line in jme
		Line lineMesh = new Line(startPoint, endPoint);

		// prevent mesh to disappear while still in the view frustum
		lineMesh.updateBound();

		edgeGeo = new Geometry("Line", lineMesh);

		// define material -> Lighting material renders according to light sources
		Material mat = JmeConfigurations.createLightingMaterial(assetManager, color);

		// activate transparency
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

		// set line width
		mat.getAdditionalRenderState().setLineWidth(5f);
		// initialize visible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
		edgeGeo.setMaterial(mat);

	}

	public void setVisibility(boolean visible) {
		if (visible) {
			// visible
			parentNode.attachChild(edgeGeo);
		} else {
			// not visible
			parentNode.detachChild(edgeGeo);
		}
	}

	public void setTransparency(boolean transparent) {
		if (transparent) {
			edgeGeo.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			edgeGeo.setQueueBucket(Bucket.Transparent);

		} else {
			edgeGeo.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Off);
			edgeGeo.setQueueBucket(Bucket.Opaque);
		}
	}

	public void move(float y1, float y2, float y3) {
		this.edgeGeo.move(y1, y2, y3);
	}

	public Geometry getEdgeGeo() {
		return edgeGeo;
	}

	public ColorRGBA getColor() {
		return color;
	}

}
