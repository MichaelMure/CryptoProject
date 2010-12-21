package keytool.model;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MTKeyStore {
	private KeyStore keystore;
	private char[] password;
	
	public MTKeyStore(String path, char[] password) {
		openKeyStore(path, password);
		this.password = password;
	}
	
	public MTKeyStore(KeyStore keystore, char[] password) {
		this.keystore = keystore;
		this.password = password;
	}
	
	public KeyStore openKeyStore(String path, char[] password) {
	    try {
			KeyStore ks = KeyStore.getInstance("JCEKS");
		    java.io.FileInputStream fis = new java.io.FileInputStream("store.ks");
		    ks.load(fis, "keytool".toCharArray());
		    return ks;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setKeystore(KeyStore keystore) {
		this.keystore = keystore;
	}

	public KeyStore getKeystore() {
		return keystore;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public char[] getPassword() {
		return password;
	}
	
	public MTKey getKey(String alias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return new MTKey(this.keystore.getKey(alias, password), password);
	}
	
	public ArrayList<MTKey> getKeys() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		Enumeration<String> aliases = this.keystore.aliases();
		ArrayList<MTKey> keys = new ArrayList<MTKey>();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(this.keystore.isKeyEntry(alias)) {
				keys.add(new MTKey(this.keystore.getKey(alias, "keytool".toCharArray()), "keytool".toCharArray()));
			}
		}
		return keys;
	}
}
