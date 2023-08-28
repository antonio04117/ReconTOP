package presenter;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import app.AppJME;
import view.jme.Tetrahedron3DVM;
import viewmodel.ViewModel;

public class Presenter {
	

	public Node rootForJME;
	public AppJME jmeApplication;

	private ViewModel viewModel;
	// TODO also knows model

	public Presenter() {
	}

	public void init() {

		// create root node for JME
		rootForJME = new Node("model in JME");

		getJmeApp().enqueue(() -> {
			jmeApplication.getRootNode().detachAllChildren();
		});
		mapToJME();
		getJmeApp().enqueue(() -> {
			jmeApplication.getRootNode().attachChild(rootForJME);
		});

	}

	public AppJME getJmeApp() {
		if (jmeApplication == null) {
			jmeApplication = new AppJME();
		}

		return jmeApplication;
	}

	private void mapToJME() {
		rootForJME = new Node("model in JME");
		
//		Tetrahedron3DVM tet = new Tetrahedron3DVM(jmeApplication.getAssetManager(), rootForJME, 0, 0, 0, 1, 0, 0, 1, 1,
//				0, 0, 0, 1, ColorRGBA.Red);
	}

}
