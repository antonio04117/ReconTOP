package app;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;

import javafx.stage.Stage;
import view.jme.Tetrahedron3DVM;

public class AppJME extends SimpleApplication {

	public static void main(String[] args) throws Exception {
		AppJME app = new AppJME();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		jmeInitializer();

		Tetrahedron3DVM tet = new Tetrahedron3DVM(assetManager, rootNode, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1,
				ColorRGBA.Red);

	}

	/**
	 * sets initial settings of jme project
	 */
	private void jmeInitializer() {
//		initInput();

		hideSettings();

		configureCamera();

		createCoordSys(rootNode);
	}

	/**
	 * Hides all informations concerning the application like frames per second
	 */
	private void hideSettings() {

		// hide the settings
		showSettings = false;

		// hide display of frames per second
		setDisplayFps(false);

		// hide the status of the application
		setDisplayStatView(false);

	}

	/**
	 * Configuration of the camera: Position and axis of the screen are defined and
	 * settings for navigation (e.g. speed for scrolling) are set
	 * 
	 */
	private void configureCamera() {

		/*
		 * jMonkey creates the coordinate system in the camera's view direction. First
		 * the axis is specified which operates as the camera view direction. Then the
		 * up-direction must be specified (z-direction). The left axis is computed
		 * automatically from the other two.
		 * 
		 * In the lecture a screen coordinate system was introduced. Screen and camera
		 * are facing each other. The screen plane and camera plane are parallel.
		 */
		cam.lookAtDirection(Vector3f.UNIT_X.negate(), Vector3f.UNIT_Z);

		// origin of the camera
		cam.setLocation(new Vector3f(10f, 0f, 0f));
//		cam.setLocation(new Vector3f(45.077415f, 54.299328f, 62.707615f));

		/*
		 * The distance between screen and camera is 100f by default.
		 * 
		 * The class "FlyByCamera" enables us to control the camera by implementing the
		 * interfaces "AnalogListener" and "ActionListener".
		 * 
		 * Moving the camera results in moving the screen.
		 */

		// enabling the camera to move
		flyCam.setEnabled(true);

		/*
		 * The up-axis of the screen must be assigned to the flyCamera to enable a
		 * rotation around this axis.
		 */
		flyCam.setUpVector(cam.getUp());

		/*
		 * Keys for moving: Up: Q, Down: Z Left: A, Right: D Zoom in: W, Zoom out: S
		 * 
		 */
		flyCam.setMoveSpeed(15);

		/*
		 * Actions to do with the mouse:
		 */
		flyCam.setZoomSpeed(5);
		flyCam.setRotationSpeed(1);
		flyCam.setDragToRotate(true);

	}

	/**
	 * Creates the coordinate system and assigns them to a node
	 * 
	 * @param n : the node
	 */
	private void createCoordSys(Node n) {

		createAxis(n, new Arrow(new Vector3f(2f, 0f, 0f)), ColorRGBA.Red); // x
		createAxis(n, new Arrow(new Vector3f(0f, 2f, 0f)), ColorRGBA.Green); // y
		createAxis(n, new Arrow(new Vector3f(0f, 0f, 2f)), ColorRGBA.Blue); // z
	}

	/**
	 * A method to create an axis
	 * 
	 * @param n     : nodes
	 * @param mesh  : mesh of the axis
	 * @param color : color of the axis
	 */
	private void createAxis(Node n, Mesh mesh, ColorRGBA color) {
		Geometry g = new Geometry("Coordinate axis", mesh);

		Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.getAdditionalRenderState().setLineWidth(4); // virtual line width
		mat.setColor("Color", color);
		g.setMaterial(mat);

		n.attachChild(g);
	}

}
