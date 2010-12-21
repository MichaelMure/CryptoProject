package keytool;

import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
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
	
	public ArrayList<String> getCertificates() {
		String alias;
		ArrayList<String> certificates = new ArrayList<String>();
		try {
			Enumeration<String> aliases = model.getKeyStore().aliases();
			while(aliases.hasMoreElements()) {
				alias = aliases.nextElement();
				if(model.getKeyStore().isCertificateEntry(alias))
				{
					certificates.add(alias);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return certificates;
	}
	
	public Key getSelectedKey() {
		if(model.getSelectedElement().getClass().equals(Key.class))
			return (Key) model.getSelectedElement();
		else
			return null;
	}
	
	public void notifyKeyStoreChanged(String path) {
		model.setKeyStore(path);
	}

	public void notifyTabChanged(ChangeEvent e) {
		JTabbedPane panel = (JTabbedPane) e.getSource();
		model.setSelectedTab(panel.getSelectedIndex());
	}

	public void notifyElementSelected(MouseEvent e) {
		JList KeyList = (JList) e.getSource();
		if(KeyList.getSelectedValue() == null) return;
		
		model.setSelectedElement(KeyList.getSelectedValue().toString());
	}
}
