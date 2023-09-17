package presenter;

import javax.swing.event.ChangeListener;

import com.jme3.math.ColorRGBA;

import app.App.AppFX.AppJME;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import model.Mesh;
import model.geometry.Point3D;
import model.topology.Tetrahedron3D;
import model.topology.Triangle3D;
import model.topology.Vertex3D;
import view.jfx.ViewFX;
import view.jme.TetrahedronJME;
import viewmodel.ViewModel;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * presenter according to mvpvm-pattern
 */
public abstract class Presenter {

	// Application starts with exampleA
	private static boolean exampleA = true;

	/**
	 * create controls on fx-Buttons
	 * 
	 * @param viewJFX
	 * @param appJME
	 * @param sampleSize
	 */
	public static void createConnection(ViewFX viewJFX, AppJME appJME, int sampleSize) {

		viewJFX.getListView().getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
			@Override
			public void onChanged(Change<? extends Integer> c) {
				for (int i = 0; i < viewJFX.getListView().getItems().size(); i++) {
					if (viewJFX.getListView().getSelectionModel().getSelectedIndices().contains(i)) {
						System.out.println("Element " + i + " ausgewählt\n");
						appJME.enqueue(() -> ViewModel.showBoundary());
					} else {
						System.out.println("Element " + i + " nicht ausgewählt\n");
						appJME.enqueue(() -> ViewModel.hideBoundary());
					}
				}
			}
		});

	}

	/**
	 * initialize jme view with drawn elements
	 * 
	 * @param app
	 * @param sampleSize
	 */
	public static void setScene(AppJME app, Mesh mesh) {

		ViewModel.initializeView(app, exampleA);
		Presenter.drawElements(app, mesh);
	}

	/**
	 * draw all elements in jme view
	 * 
	 * @param app
	 * @param mesh
	 * @param firstMesh
	 */
	private static void drawElements(AppJME app, Mesh mesh) {

		// color for tetrahedron
		ColorRGBA color = ColorRGBA.Red;

		for (int i = 0; i < mesh.getMapTet().size(); i++) {
			// draw tetrahedron
			ViewModel.drawTetrahedron(app, mesh.getMapTet().get(i), color);

		}

		// color for triangle
		color = ColorRGBA.Blue;

		for (int i = 0; i < mesh.getMapTri().size(); i++) {
			for (Triangle3D triangle : mesh.getMapTri().get(i)) {

				ViewModel.drawTriangle(app, triangle, color);

			}
		}
	}
}
