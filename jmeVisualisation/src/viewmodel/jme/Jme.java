package viewmodel.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import model.topology.Vertex3D;
import view.jme.Vertex3DVM;

public class Jme {

	private AssetManager assetManager;
	private Node parentNode;

	public Jme(AssetManager assetManager, Node parentNode) {
		this.assetManager = assetManager;
		this.parentNode = parentNode;
	}

	// creates red Vertex
	public Vertex3DVM drawVertex(Vertex3D v) {
		Vertex3DVM vertex = new Vertex3DVM(assetManager, parentNode, (float) v.getP().getXDouble(),
				(float) v.getP().getYDouble(), (float) v.getP().getZDouble(), ColorRGBA.Red);
		// vertex not visible
		vertex.getPointGeo().getMaterial().getAdditionalRenderState()
				.setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
		return vertex;
	}
}
