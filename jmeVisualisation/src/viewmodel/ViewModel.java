package viewmodel;

import com.jme3.math.ColorRGBA;

import app.App.AppFX.AppJME;
import model.topology.Edge3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;
import view.jme.Edge3DVM;
import view.jme.JmeConfigurations;
import view.jme.Triangle3DVM;
import view.jme.Vertex3DVM;


/**
 * viewmodel according to mvpvm-pattern
 */
public abstract class ViewModel {

	/**
	 * initialize jme view without drawn elements
	 * 
	 * @param app
	 * @param exampleA
	 */
	public static void initializeView(AppJME app, boolean exampleA) {
		JmeConfigurations.jmeInitializer(app, exampleA);
	}

	/**
	 * create triangle in jme
	 *
	 * @param assetManager
	 * @param parentNode
	 * @param p0
	 * @param p1
	 * @param p2
	 * @param color
	 * @return
	 */
	public static void drawTriangle(AppJME app, Triangle3D t, ColorRGBA color) {

		new Triangle3DVM(app.getAssetManager(), app.getRootNode(), (float) t.getP0().getP().getX(),
				(float) t.getP0().getP().getY(), (float) t.getP0().getP().getZ(), (float) t.getP1().getP().getX(),
				(float) t.getP1().getP().getY(), (float) t.getP1().getP().getZ(), (float) t.getP2().getP().getX(),
				(float) t.getP2().getP().getY(), (float) t.getP2().getP().getZ(), color);
	}

	/**
	 * create vertex in jme
	 *
	 * @param app
	 * @param v
	 * @param color
	 */
	public static void drawPoint(AppJME app, Vertex3D v, ColorRGBA color) {

		new Vertex3DVM(app.getAssetManager(), app.getRootNode(), (float) v.getP().getX(), (float) v.getP().getY(),
				(float) v.getP().getZ(), color);

	}

	/**
	 * create line in jme
	 * 
	 * @param app
	 * @param cp
	 * @param color
	 */
	public static void drawLine(AppJME app, Edge3D cp, ColorRGBA color) {
		new Edge3DVM(app.getAssetManager(), app.getRootNode(), (float) cp.getStart().getP().getX(),
				(float) cp.getStart().getP().getY(), (float) cp.getStart().getP().getZ(),
				(float) cp.getEnd().getP().getX(), (float) cp.getEnd().getP().getY(), (float) cp.getEnd().getP().getZ(),
				color);
	}

	/**
	 * adapt camera according to the chosen example
	 *
	 * @param appJME
	 * @param exampleA
	 */
	public static void adaptView(AppJME appJME, boolean exampleA) {
		JmeConfigurations.adaptView(appJME, exampleA);
	}

}
