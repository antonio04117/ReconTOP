package junit;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class TestApp extends SimpleApplication {

	public static void main(String[] args) {
		TestApp app = new TestApp();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		Sphere sphereMesh = new Sphere(3, 3, 2f);
		Geometry sphereGeo = new Geometry("sphere", sphereMesh);

		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat.setColor("Ambient", ColorRGBA.Blue);
		mat.setColor("Diffuse", ColorRGBA.Blue);
		mat.setColor("Specular", ColorRGBA.Green);
		mat.setBoolean("UseMaterialColors", true);
		mat.setFloat("Shininess", 5);

		sphereGeo.setMaterial(mat);

		rootNode.attachChild(sphereGeo);

		// directional light e.g. the sun
		DirectionalLight sun = new DirectionalLight();
		// white -> neutral lighting
		sun.setColor(ColorRGBA.White);
		sun.setDirection(new Vector3f(-0.5f, -0.5f, -1.0f).normalizeLocal());
		rootNode.addLight(sun);

		// brighten the whole scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);


	}

}
