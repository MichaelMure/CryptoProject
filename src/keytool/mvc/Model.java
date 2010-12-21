package keytool.mvc;


import javax.swing.event.EventListenerList;

import keytool.model.MTKeyStore;

public class Model {

	private MTKeyStore keystore;
	private int selectedTab;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	private static char[] DEFAULT_PASSWORD = { 'k', 'e', 'y', 't', 'o', 'o', 'l' };
	
	private EventListenerList listeners;
	
    public Model(){
    	this.keystore = new MTKeyStore("store.ks", "keytool".toCharArray());
    }
	

 
}