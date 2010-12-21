package keytool.mvc;

import javax.swing.DefaultListModel;

import keytool.model.MTKey;
import keytool.model.MTKeyStore;

public class Model {

	private MTKeyStore keystore;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	public static char[] DEFAULT_PASSWORD = { 'k', 'e', 'y', 't', 'o', 'o', 'l' };
	
    public Model(){
    	this.keystore = new MTKeyStore(KEYSTORE_DEFAULT_PATH, DEFAULT_PASSWORD);
    }
	
    public DefaultListModel getKeys() {
    	DefaultListModel list = new DefaultListModel();
    	list.addElement("Item1");
        return list;
    }
    
    public DefaultListModel getCertificates() {
    	DefaultListModel list = new DefaultListModel();
    	
        return list;
    }

}