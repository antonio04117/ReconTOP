package view.jme;

import com.jme3.input.CameraInput;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
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

	private static Vector3f locationA = new Vector3f(-8.776165f, -12.8125305f, 1.8886071f);
	private static Vector3f directionA = new Vector3f(0.60457885f, 0.79543835f, 0.04197961f);
	private static Vector3f locationB = new Vector3f(-6.672361f, -11.4183445f, 3.046562f);
	private static Vector3f directionB = new Vector3f(0.6053308f, 0.794814f, 0.04295534f);

	/**
	 * set initial settings of jme project
	 */
	public static void jmeInitializer(AppJME app, boolean exampleA) {
		JmeConfigurations.initInput(app);

		JmeConfigurations.hideSettings(app);

		// adapt starting position to chosen example
		JmeConfigurations.configureCamera(app, exampleA);

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
	private static void configureCamera(AppJME app, boolean exampleA) {

		// origin of the camera
		if (exampleA) {
			app.getCamera().lookAtDirection(new Vector3f(0.60457885f, 0.79543835f, 0.04197961f), Vector3f.UNIT_Z);
			app.getCamera().setLocation(new Vector3f(-8.776165f, -12.8125305f, 1.8886071f));
		} else {
			app.getCamera().lookAtDirection(new Vector3f(0.6053308f, 0.794814f, 0.04295534f), Vector3f.UNIT_Z);
			app.getCamera().setLocation(new Vector3f(21f, 3f, 5.5f));
		}
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
	 * adapts camera according to the chosen example and re-draws coordinate system
	 *
	 * @param appJME
	 * @param exampleA
	 */
	public static void adaptView(AppJME appJME, boolean exampleA) {
		// origin of the camera

		Vector3f previousDirection = appJME.getCamera().getDirection();

		// save previous location of view
		Vector3f previousLocation = appJME.getCamera().getLocation();

		if (exampleA) {
			// save previous location of other example to reload it later (no conflict,
			// because this method is only called on a switch, not when the same example is
			// chosen)
			JmeConfigurations.locationB = previousLocation.clone();
			JmeConfigurations.directionB = previousDirection.clone();
			// load previous location of this example
			appJME.getCamera().lookAtDirection(directionA, Vector3f.UNIT_Z);
			appJME.getCamera().setLocation(JmeConfigurations.locationA);
		} else {
			// save previous location of other example to reload it later (no conflict,
			// because this method is only called on a switch, not when the same example is
			// chosen)
			JmeConfigurations.locationA = previousLocation.clone();
			JmeConfigurations.directionA = previousDirection.clone();
			// load previous location of this example
			appJME.getCamera().lookAtDirection(directionB, Vector3f.UNIT_Z);
			appJME.getCamera().setLocation(JmeConfigurations.locationB);
		}

		// re-draw coordinate system
		createCoordSys(appJME);

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

}
