package app;

import com.jme3.app.SimpleApplication;


import javafx.application.Application;
import javafx.stage.Stage;
import model.Mesh;
import model.geometry.Point3D;
import model.topology.Tetrahedron3D;
import model.topology.Vertex3D;
import presenter.Presenter;
import view.jfx.ViewFX;

/**
 * app for combined fx and jme application
 */
public class App {

	public static int sampleSize = 40;

	public static void main(String[] args) {
		new Thread(() -> Application.launch(AppFX.class)).start();
	}

	/**
	 * app for fx application including jme application
	 */
	public static class AppFX extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			ViewFX viewFX = new ViewFX(primaryStage);

			// start jme thread
			new Thread(() -> {
				AppJME appJME = new AppJME();
				appJME.start();
				Presenter.createConnection(viewFX, appJME, sampleSize);
			}).start();
		}

		/**
		 * app for jme application
		 */
		public static class AppJME extends SimpleApplication {

			@Override
			public void simpleInitApp() {
				
				Mesh mesh = new Mesh();
				
				
				// a square of tetrahedrons
				mesh.addTet(new Tetrahedron3D(new Vertex3D(new Point3D(0, 0, 0)), new Vertex3D(new Point3D(0, 10, 0)),
						new Vertex3D(new Point3D(10, 10, 0)), new Vertex3D(new Point3D(0, 0, 10))));
				
				mesh.addTet(new Tetrahedron3D(new Vertex3D(new Point3D(0, 0, 0)), new Vertex3D(new Point3D(10, 0, 0)),
						new Vertex3D(new Point3D(10, 10, 0)), new Vertex3D(new Point3D(0, 0, 10))));
				
				mesh.addTet(new Tetrahedron3D(new Vertex3D(new Point3D(0, 10, 0)), new Vertex3D(new Point3D(0, 0, 10)),
						new Vertex3D(new Point3D(0, 10, 10)), new Vertex3D(new Point3D(10, 10, 10))));
				
				mesh.addTet(new Tetrahedron3D(new Vertex3D(new Point3D(0, 10, 0)), new Vertex3D(new Point3D(0, 0, 10)),
						new Vertex3D(new Point3D(10, 10, 0)), new Vertex3D(new Point3D(10, 10, 10))));
				
				mesh.addTet(new Tetrahedron3D(new Vertex3D(new Point3D(0, 0, 10)), new Vertex3D(new Point3D(10, 0, 10)),
						new Vertex3D(new Point3D(10, 10, 0)), new Vertex3D(new Point3D(10, 10, 10))));
				
				mesh.addTet(new Tetrahedron3D(new Vertex3D(new Point3D(0, 0, 10)), new Vertex3D(new Point3D(10, 0, 0)),
						new Vertex3D(new Point3D(10, 10, 0)), new Vertex3D(new Point3D(10, 0, 10))));
				

				// set Scene while selecting sample size in each direction
				// true -> example A
				// false -> example B
				Presenter.setScene(this, mesh);
			}
		}
	}
}