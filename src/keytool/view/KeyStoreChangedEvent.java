package keytool.view;

import java.security.KeyStore;
import java.util.EventObject;

public class KeyStoreChangedEvent extends EventObject {
	private KeyStore newKeyStore;
	
	public KeyStoreChangedEvent(Object source, KeyStore newKeyStore) {
		super(source);
		
		this.newKeyStore = newKeyStore;
	}
	
	public KeyStore getNewKeyStore() {
		return newKeyStore;
	}

}
