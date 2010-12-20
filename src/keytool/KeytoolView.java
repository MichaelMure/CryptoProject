package keytool;

public abstract class KeytoolView implements KeytoolListener {

	private KeytoolController controller = null;
	
	public KeytoolView(KeytoolController controller) {
		super();
		
		this.controller = controller;
	}
	
	public final KeytoolController getController() {
		return controller;
	}
	
	public abstract void display();
	public abstract void close();
}
