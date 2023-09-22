package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

public class EdgeJME {

	private Node parentNode;
	// start point
	private Vector3f startPoint;
	// end point
	private Vector3f endPoint;

	public Geometry lineGeo;

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
	public EdgeJME(AssetManager assetManager, Node parentNode, float xS, float yS, float zS, float xE, float yE,
			float zE, ColorRGBA color) {
		this.parentNode = parentNode;

		startPoint = new Vector3f(xS, yS, zS);
		endPoint = new Vector3f(xE, yE, zE);

		// line in jme
		Line lineMesh = new com.jme3.scene.shape.Line(startPoint, endPoint);

		lineGeo = new Geometry("Line", lineMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat.setColor("Ambient", color);
		mat.setColor("Diffuse", color);
		mat.setColor("Specular", ColorRGBA.Green);
		mat.setBoolean("UseMaterialColors", true);
		mat.setFloat("Shininess", 5);
		// set line width
		mat.getAdditionalRenderState().setLineWidth(5f);
		// initialize invisible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
		lineGeo.setMaterial(mat);

		this.parentNode = parentNode;
		// do not attach node now, because edge is ought to be created transparently
//		parentNode.attachChild(lineGeo);
	}

	public void move(float y1, float y2, float y3) {
		this.lineGeo.move(y1, y2, y3);
	}

	public void setVisibility(boolean visible) {
		if (visible) {
			// visible
			parentNode.attachChild(lineGeo);
		} else {
			// not visible
			parentNode.detachChild(lineGeo);
		}
	}

}
