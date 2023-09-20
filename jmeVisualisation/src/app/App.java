package app;

import com.jme3.app.SimpleApplication;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Mesh;
import model.geometry.Point3D;
import model.topology.Tetrahedron3D;
import model.topology.Vertex3D;
import presenter.Presenter;
import view.fx.ViewFX;
import viewmodel.InitialView;

/**
 * app for combined fx and jme application
 */
public class App {

	private static Mesh createMeshExample() {
		/*
		 * example
		 */

		Mesh mesh = new Mesh();

		Vertex3D[] cube = new Vertex3D[] { new Vertex3D(new Point3D(0, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 0), false), new Vertex3D(new Point3D(10, 10, 0), false),
				new Vertex3D(new Point3D(0, 0, 10), false), new Vertex3D(new Point3D(10, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 10), false), new Vertex3D(new Point3D(10, 10, 10), false),
				new Vertex3D(new Point3D(10, 0, 10), false) };

		// a square of tetrahedrons
		Tetrahedron3D tet0 = new Tetrahedron3D(cube[0], cube[1], cube[2], cube[3]);
		mesh.addTet(tet0, 100);
		Tetrahedron3D tet1 = new Tetrahedron3D(cube[0], cube[4], cube[2], cube[3]);
		mesh.addTet(tet1, 0);
		Tetrahedron3D tet2 = new Tetrahedron3D(cube[1], cube[3], cube[5], cube[6]);
		mesh.addTet(tet2, 1);
		Tetrahedron3D tet3 = new Tetrahedron3D(cube[1], cube[3], cube[2], cube[6]);
		mesh.addTet(tet3, 1);
		Tetrahedron3D tet4 = new Tetrahedron3D(cube[3], cube[7], cube[2], cube[6]);
		mesh.addTet(tet4, 2);
		Tetrahedron3D tet5 = new Tetrahedron3D(cube[3], cube[4], cube[2], cube[7]);
		mesh.addTet(tet5, 100);

		// mark triangles that are boundary
		mesh.markBoundary();

		/*
		 * end of example
		 */

		return mesh;
	}

	public static void main(String[] args) {

		new Thread(() -> Application.launch(AppFX.class)).start();
	}

	/**
	 * app for fx application including jme application
	 */
	public static class AppFX extends Application {

		private static final Mesh mesh = App.createMeshExample();

		public AppFX() {
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			ViewFX viewFX = new ViewFX(primaryStage, mesh);

			// start jme thread
			new Thread(() -> {
				AppJME appJME = new AppJME();
				appJME.start();
				Presenter.createConnection(viewFX, appJME, mesh);
			}).start();
		}

		/**
		 * app for jme application
		 */
		public static class AppJME extends SimpleApplication {

			@Override
			public void simpleInitApp() {

				// set Scene -> choose initial view
				Presenter.setScene(this, mesh, InitialView.NOELEMENTS);
			}
		}
	}
}