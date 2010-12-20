package keytool;

import java.awt.event.FocusEvent;

import javax.swing.JList;

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

	public void notifyElementSelected(FocusEvent e) {
		JList KeyList = (JList) e.getSource();
		model.setSelectedKey(KeyList.getSelectedValue().toString());
		System.out.println(e.getSource().getClass().getName()+
				KeyList.getSelectedValue().toString());
	}
}
