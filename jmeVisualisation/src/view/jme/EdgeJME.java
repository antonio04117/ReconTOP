package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class EdgeJME {

	AssetManager assetManager;
	Node parentNode;
	// start point
	Vector3f startPoint;
	// end point
	Vector3f endPoint;

	Geometry lineGeo;

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
		this.assetManager = assetManager;
		this.parentNode = parentNode;

		startPoint = new Vector3f(xS, yS, zS);
		endPoint = new Vector3f(xE, yE, zE);

		// line in jme
		com.jme3.scene.shape.Line lineMesh = new com.jme3.scene.shape.Line(startPoint, endPoint);

		lineGeo = new Geometry("Line", lineMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		lineGeo.setMaterial(mat);

		parentNode.attachChild(lineGeo);
	}

	public void move(float y1, float y2, float y3) {
		this.lineGeo.move(y1, y2, y3);
	}

}
