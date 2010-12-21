package keytool;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.swing.event.EventListenerList;

public class KeytoolModel {

	private KeyStore keystore;
	private Object selectedElement;
	//private Certificate selectedCertificate;
	private int selectedTab;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	private static char[] DEFAULT_PASSWORD = { 'k', 'e', 'y', 't', 'o', 'o', 'l' };
	
	private EventListenerList listeners;
	
	public KeytoolModel() {
		this(KEYSTORE_DEFAULT_PATH);
	}
	
	public KeytoolModel(String path) {
		super();
	    try {
			keystore = KeyStore.getInstance("JCEKS");
			openKeyStore(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listeners = new EventListenerList();
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
		fireKeyStoreChanged();

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
		fireElementSelected();
	}

	public void fireElementSelected() {
		KeytoolListener[] listenerList = (KeytoolListener[])listeners.getListeners(KeytoolListener.class);
		
		for(KeytoolListener listener : listenerList){
			listener.elementSelected(new ElementSelectedEvent(this, getSelectedElement()));
		}		
	}

	public Object getSelectedElement() {
		return selectedElement;
	}

/*
	public Certificate getSelectedCertificate() {
		return selectedCertificate;
	}
*/
	public void addKeytoolListener(KeytoolListener listener){
		listeners.add(KeytoolListener.class, listener);
	}
	
	public void removeKeytoolListener(KeytoolListener l){
		 listeners.remove(KeytoolListener.class, l);
	}
	
	public void fireKeyStoreChanged(){
		KeytoolListener[] listenerList = (KeytoolListener[])listeners.getListeners(KeytoolListener.class);
		
		for(KeytoolListener listener : listenerList){
			listener.keystoreChanged(new KeyStoreChangedEvent(this, getKeyStore()));
		}
	}
	
	public void openKeyStore(String path) throws NoSuchAlgorithmException, CertificateException, IOException {

	    String password = "keytool";
	    java.io.FileInputStream fis = new java.io.FileInputStream("store.ks");
	    keystore.load(fis, password.toCharArray());
	}

	public void setSelectedTab(int selectedIndex) {
		this.selectedTab = selectedIndex;
		fireSelectedTab();
	}
	
	private void fireSelectedTab() {
		KeytoolListener[] listenerList = (KeytoolListener[]) listeners.getListeners(KeytoolListener.class);
		
		for(KeytoolListener listener : listenerList){
			listener.selectedTabChanged(new TabChangedEvent(this, getSelectedTab()));
		}
	}

	public int getSelectedTab() {
		return this.selectedTab;
	}
}
