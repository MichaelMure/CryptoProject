package keytool;

import java.awt.List;
import java.awt.event.FocusEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

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
	
	public ArrayList<String> getKeys() {
		String alias;
		ArrayList<String> keys = new ArrayList<String>();
		try {
			Enumeration<String> aliases = model.getKeyStore().aliases();
			while(aliases.hasMoreElements()) {
				alias = aliases.nextElement();
				if(model.getKeyStore().isKeyEntry(alias))
				{
					keys.add(alias);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return keys;
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

	public void notifyTabChanged(ChangeEvent e) {
		JTabbedPane panel = (JTabbedPane) e.getSource();
		model.setSelectedTab(panel.getSelectedIndex());
	}
}
