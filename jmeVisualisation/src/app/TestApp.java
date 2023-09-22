package app;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class TestApp extends SimpleApplication {

	public static void main(String[] args) {
		TestApp app = new TestApp();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		Sphere sphereMesh = new Sphere(32, 32, 2f);
		Geometry sphereGeo = new Geometry("sphere", sphereMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat.setColor("Ambient", ColorRGBA.Blue);
		mat.setColor("Diffuse", ColorRGBA.Blue);
		mat.setColor("Specular", ColorRGBA.Green);
		mat.setBoolean("UseMaterialColors", true);
		mat.setFloat("Shininess", 5);

		sphereGeo.setMaterial(mat);

		rootNode.attachChild(sphereGeo);

		/** Must add a light to make the lit object visible! */
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
		sun.setColor(ColorRGBA.White);
		rootNode.addLight(sun);



	}

}
