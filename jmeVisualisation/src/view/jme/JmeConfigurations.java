package view.jme;

import com.jme3.asset.AssetManager;
import com.jme3.input.CameraInput;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.material.TechniqueDef.LightMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.debug.Arrow;

import app.App.AppFX.AppJME;

/**
 * configurations of general jme view
 */
public abstract class JmeConfigurations {

	/**
	 * set initial settings of jme project
	 */
	public static void jmeInitializer(AppJME app) {
		JmeConfigurations.initInput(app);

		JmeConfigurations.hideSettings(app);

		// adapt starting position to chosen example
		JmeConfigurations.configureCamera(app);

		JmeConfigurations.createCoordSys(app);
	}

	/**
	 * add custom input
	 *
	 * @param app
	 */
	private static void initInput(AppJME app) {
		// keep rendering if jme is passive window
		app.setPauseOnLostFocus(false);

		// custom key triggers
		FlyByCamera flycam = app.getFlyByCamera();

		// let y-key move the camera down (other downwards key is "z" while upwards key
		// is "q" -> makes sense for English keyboard, German keyboard needs "y" instead
		// of "z")
		flycam.registerWithInput(app.getInputManager());
		app.getInputManager().clearMappings();
		app.getInputManager().addMapping(CameraInput.FLYCAM_LOWER, new KeyTrigger(KeyInput.KEY_Y));

		// set background white
		app.getViewPort().setBackgroundColor(ColorRGBA.White);

		// set light sources
		setLights(app);
	}

	/**
	 * set lights in jme
	 * 
	 * @param app
	 */
	private static void setLights(AppJME app) {
		// directional light e.g. the sun
		DirectionalLight sun = new DirectionalLight();
		// white -> neutral lighting
		sun.setColor(ColorRGBA.White);
		sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());

		// countering directional light e.g. reflection of the surface
		DirectionalLight reflection = new DirectionalLight();
		reflection.setColor(ColorRGBA.White.mult(0.3f));
		reflection.setDirection(new Vector3f(0.5f, 0.5f, 0.5f).normalizeLocal());

		// add lights to rootNode
		app.getRootNode().addLight(sun);
		app.getRootNode().addLight(reflection);

		// set SinglePass as LightMode (only works, if shader of the material includes
		// SinglePass as option)
		app.getRenderManager().setPreferredLightMode(LightMode.SinglePass);
		// set the number of lights to use for each pass -> 2 lights (sun and
		// reflection)
		app.getRenderManager().setSinglePassLightBatchSize(2);

		// brighten the whole scene -> important for the surfaces colinear to the
		// directional lights
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(0.05f));
		app.getRootNode().addLight(al);
	}

	/**
	 * Hides all informations concerning the application like frames per second
	 */
	private static void hideSettings(AppJME app) {

		// hide the settings
		app.setShowSettings(false);

		// hide display of frames per second
		app.setDisplayFps(false);

		// hide the status of the application
		app.setDisplayStatView(false);

	}

	/**
	 * Configuration of the camera: Position and axis of the screen are defined and
	 * settings for navigation (e.g. speed for scrolling) are set
	 *
	 */
	private static void configureCamera(AppJME app) {

		// origin of the camera
		app.getCamera().lookAtDirection(new Vector3f(0.60457885f, 0.79543835f, 0.04197961f), Vector3f.UNIT_Z);
		app.getCamera().setLocation(new Vector3f(-8.776165f, -12.8125305f, 3.1886071f));

		/*
		 * The distance between screen and camera is 100f by default.
		 *
		 * The class "FlyByCamera" enables us to control the camera by implementing the
		 * interfaces "AnalogListener" and "ActionListener".
		 *
		 * Moving the camera results in moving the screen.
		 */

		// enabling the camera to move
		app.getFlyByCamera().setEnabled(true);

		/*
		 * The up-axis of the screen must be assigned to the flyCamera to enable a
		 * rotation around this axis.
		 */
		app.getFlyByCamera().setUpVector(app.getCamera().getUp());

		/*
		 * Keys for moving: Up: Q, Down: Z Left: A, Right: D Zoom in: W, Zoom out: S
		 *
		 */
		app.getFlyByCamera().setMoveSpeed(15);

		/*
		 * Actions to do with the mouse:
		 */
		app.getFlyByCamera().setZoomSpeed(5);
		app.getFlyByCamera().setRotationSpeed(1);
		app.getFlyByCamera().setDragToRotate(true);

	}

	/**
	 * Creates the coordinate system and assigns them to a node
	 *
	 * @param n : the node
	 */
	private static void createCoordSys(AppJME app) {

		JmeConfigurations.createAxis(app, new Arrow(new Vector3f(2f, 0f, 0f)), ColorRGBA.Red); // x
		JmeConfigurations.createAxis(app, new Arrow(new Vector3f(0f, 2f, 0f)), ColorRGBA.Green); // y
		JmeConfigurations.createAxis(app, new Arrow(new Vector3f(0f, 0f, 2f)), ColorRGBA.Blue); // z
	}

	/**
	 * A method to create an axis
	 *
	 * @param n     : nodes
	 * @param mesh  : mesh of the axis
	 * @param color : color of the axis
	 */
	private static void createAxis(AppJME app, Mesh mesh, ColorRGBA color) {
		Geometry g = new Geometry("Coordinate axis", mesh);

		Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.getAdditionalRenderState().setLineWidth(4); // virtual line width
		mat.setColor("Color", color);
		g.setMaterial(mat);

		app.getRootNode().attachChild(g);
	}

	/**
	 * create material that renders according to light sources
	 * 
	 * @param assetManager
	 * @param color
	 * @return
	 */
	public static Material createLightingMaterial(AssetManager assetManager, ColorRGBA color) {
		// define material -> Lighting material renders according to light sources
		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		// settings for material
		mat.setColor("Ambient", color);
		mat.setColor("Diffuse", color);
		// specular color: green with alpha (transparency)
		mat.setColor("Specular", ColorRGBA.fromRGBA255(0, 255, 0, 150));
		mat.setBoolean("UseMaterialColors", true);
		mat.setFloat("Shininess", 10);

		// activate transparency
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

		// initialize visible
		mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

		return mat;
	}

}
