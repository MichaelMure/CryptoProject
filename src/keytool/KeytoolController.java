package keytool;

public class KeytoolController {
	public KeytoolView listView = null;
	
	private KeytoolModel model = null;
	
	public KeytoolController(KeytoolModel model) {
		this.model = model;
		
		listView = new JFrameListKeyStore(this, model.getKeyStore());
		
		addListenersToModel();
	}
	
	private void addListenersToModel() {
		model.addKeytoolListener(listView);
	}
	
	public void displayViews() {
		listView.display();
	}
	
	public void closeViews() {
		listView.close();
	}
	
	public void notifyKeyStoreChanged(String path) {
		model.setKeyStore(path);
	}
}
