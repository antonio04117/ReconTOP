package presenter;

import com.jme3.scene.Node;

import app.DualWindowApp.JMonkeyApp;
import view.jfx.ViewJFX;

public class Presenter {

	public ViewJFX viewJFX;

	public Node rootForJME;
	public JMonkeyApp jmeApp;

	public Presenter(ViewJFX viewJFX, JMonkeyApp jmeAPP) {
		this.viewJFX = viewJFX;
		this.jmeApp = jmeAPP;
		this.rootForJME = jmeAPP.getRootNode();

		this.viewJFX.getCoordsys().setOnAction(e -> jmeAPP.createCoordSys(rootForJME));
	}

}
