package keytool;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.swing.event.EventListenerList;

public class KeytoolModel {

	private KeyStore keystore;
	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	
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
	    java.io.FileInputStream fis =
	        new java.io.FileInputStream("store.ks");
	    keystore.load(fis, password.toCharArray());
	}
}
