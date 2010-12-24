package keytool.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;


public class MTKeyStore {
	private KeyStore keystore;
	private char[] password;
	
	/**
	 * Open a KeyStore from a file and its password
	 * @param path
	 * @param password
	 */
	public MTKeyStore(String path, char[] password) {
		keystore = openKeyStore(path, password);
		this.password = password;
	}
	
	/**
	 * Create a new KeyStore
	 * @param password of the new KeyStore
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 */
	public MTKeyStore(char[] password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		this.keystore = KeyStore.getInstance("JCEKS");
		this.keystore.load(null, password);
		this.password = password;
	}
	
	public static KeyStore openKeyStore(String path, char[] password) {
	    try {
			KeyStore ks = KeyStore.getInstance("JCEKS");
		    java.io.FileInputStream fis = new java.io.FileInputStream("store.ks");
		    ks.load(fis, password);
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
				return new MTKey(keystore.getKey(alias, password));
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
				keys.add(new MTKey(keystore.getKey(alias, Model.DEFAULT_PASSWORD)));
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
		// FIXME : le dernier argument doit etre une chaine de certificat
		// Je n'ai pas compris le fonctionnement
		// T.H. 24/12/10
		keystore.setKeyEntry(alias, key.getKey(), this.password, null);
	}
	
	public void addCertificate(String alias, MTCertificate cert) throws KeyStoreException {
		keystore.setCertificateEntry(alias, cert.getCertificate());
	}
	
	public void delEntry(String alias) throws KeyStoreException {
		this.keystore.deleteEntry(alias);
	}
	
	public void saveTo(String path) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		FileOutputStream fos = new FileOutputStream(new File(path));
		this.keystore.store(fos, password);
	}
}
