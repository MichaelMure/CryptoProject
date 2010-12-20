package keytool;
import java.security.KeyStore;

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
		this.keystore = null;
		
		listeners = new EventListenerList();
	}
	
	public KeyStore getKeyStore() {
		return keystore;
	}
	
	public void setKeyStore(String path) {
		
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
}
