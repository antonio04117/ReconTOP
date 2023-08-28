package viewmodel;

import presenter.Presenter;
import view.jme.ViewJME;

public class ViewModel {

	private Presenter presenter;
	private ViewJME viewJME;
	// TODO also knows viewJFX

	public ViewModel(Presenter presenter, ViewJME viewJME) {
		this.presenter = presenter;
		this.viewJME = viewJME;
	}

}
