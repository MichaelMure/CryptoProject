package keytool.view;

import java.security.KeyStore;
import java.util.EventObject;

public class KeyStoreChangedEvent extends EventObject {

	private static final long serialVersionUID = -498517649858096994L;

	private KeyStore newKeyStore;
	
	public KeyStoreChangedEvent(Object source, KeyStore newKeyStore) {
		super(source);
		
		this.newKeyStore = newKeyStore;
	}
	
	public KeyStore getNewKeyStore() {
		return newKeyStore;
	}

}
