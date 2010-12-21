package keytool.mvc;

import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.swing.event.EventListenerList;


import keytool.view.ElementSelectedEvent;
import keytool.view.KeyStoreChangedEvent;
import keytool.view.TabChangedEvent;
 
public class Model {

	private KeyStore keystore;
	private Object selectedElement;
	//private Certificate selectedCertificate;
	private int selectedTab;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	private static char[] DEFAULT_PASSWORD = { 'k', 'e', 'y', 't', 'o', 'o', 'l' };
	
	private EventListenerList listeners;
	
    public Model(){
	    try {
			keystore = KeyStore.getInstance("JCEKS");
			openKeyStore(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	

	
	public KeyStore getKeyStore() {
		return keystore;
	}
	
	public void setKeyStore(String path) {
		try {
			openKeyStore(path);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public void setSelectedElement(String alias) {
		try {
			if(keystore.isKeyEntry(alias))
				this.selectedElement = keystore.getKey(alias, DEFAULT_PASSWORD);
			else if (keystore.isCertificateEntry(alias))
				this.selectedElement = keystore.getCertificate(alias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void openKeyStore(String path) throws NoSuchAlgorithmException, CertificateException, IOException {

	    String password = "keytool";
	    java.io.FileInputStream fis = new java.io.FileInputStream("store.ks");
	    keystore.load(fis, password.toCharArray());
	}

	public void setSelectedTab(int selectedIndex) {
		this.selectedTab = selectedIndex;
	}
	
	public int getSelectedTab() {
		return this.selectedTab;
	}

 
}