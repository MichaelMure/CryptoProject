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
		keystore = openKeyStore(path, password);
		this.password = password;
	}
	
	public MTKeyStore(KeyStore ks, char[] password) {
		keystore = ks;
		this.password = password;
	}
	
	public static KeyStore openKeyStore(String path, char[] password) {
	    try {
			KeyStore ks = KeyStore.getInstance("JCEKS");
		    java.io.FileInputStream fis = new java.io.FileInputStream("store.ks");
		    ks.load(fis, Model.DEFAULT_PASSWORD);
		    return ks;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setKeystore(KeyStore ks) {
		keystore = ks;
	}
	
	public void setKeystore(String path, char[] password) {
		keystore = openKeyStore(path, password);
		this.password = password;
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
	
	public MTKey getKey(String alias, char[] password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		if(keystore.getKey(alias, password) != null) {
			if(keystore.getCertificate(alias) != null) {
				return new MTKey(keystore.getKey(alias, password), password, keystore.getCertificate(alias));
			} else
				throw new KeyStoreException("pas de certificat associé à la clé");
		} else
			throw new KeyStoreException("pas de clé à ce nom");
	}
	
	public MTCertificate getCertificate(String alias) throws KeyStoreException  {
		return new MTCertificate(keystore.getCertificate(alias));
	}
	
	public ArrayList<MTKey> getKeys() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		Enumeration<String> aliases = keystore.aliases();
		ArrayList<MTKey> keys = new ArrayList<MTKey>();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(keystore.isKeyEntry(alias)) {
				keys.add(new MTKey(keystore.getKey(alias, Model.DEFAULT_PASSWORD), Model.DEFAULT_PASSWORD));
			}
		}
		return keys;
	}
	
	public ArrayList<MTCertificate> getCertificates() throws KeyStoreException {
		Enumeration<String> aliases = keystore.aliases();
		ArrayList<MTCertificate> certificates = new ArrayList<MTCertificate>();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(keystore.isCertificateEntry(alias)) {
				certificates.add(new MTCertificate(keystore.getCertificate(alias)));
			}
		}
		return certificates;
	}
	
	public void addKey(String alias, MTKey key) throws KeyStoreException {
		keystore.setKeyEntry(alias, key.getKey(), Model.DEFAULT_PASSWORD, key.getCertificatesChain());
	}
}
