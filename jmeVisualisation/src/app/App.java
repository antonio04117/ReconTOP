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

		Vertex3D[] square = new Vertex3D[] { new Vertex3D(new Point3D(0, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 0), false), new Vertex3D(new Point3D(10, 10, 0), false),
				new Vertex3D(new Point3D(0, 0, 10), false), new Vertex3D(new Point3D(10, 0, 0), false),
				new Vertex3D(new Point3D(0, 10, 10), false), new Vertex3D(new Point3D(10, 10, 10), false),
				new Vertex3D(new Point3D(10, 0, 10), false) };

		// a square of tetrahedrons
		mesh.addTet(new Tetrahedron3D(square[0], square[1], square[2], square[3]));

		mesh.addTet(new Tetrahedron3D(square[0], square[4], square[2], square[3]));

		mesh.addTet(new Tetrahedron3D(square[1], square[3], square[5], square[6]));

		mesh.addTet(new Tetrahedron3D(square[1], square[3], square[2], square[6]));

		mesh.addTet(new Tetrahedron3D(square[3], square[7], square[2], square[6]));

		mesh.addTet(new Tetrahedron3D(square[3], square[4], square[2], square[7]));

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
				Presenter.createConnection(viewFX, appJME);
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