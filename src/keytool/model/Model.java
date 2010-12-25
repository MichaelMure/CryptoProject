package keytool.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;

public class Model {
	private KeyStore keystore;
	private char[] password;
	private String currentPath;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	private static char[] DEFAULT_PASSWORD = "keytool".toCharArray();
	
	public Model() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		this(Model.DEFAULT_PASSWORD);
	}
	
	/**
	 * Create a new KeyStore
	 * @param password of the new KeyStore
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 */
	public Model(char[] password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		this.keystore = KeyStore.getInstance("JCEKS");
		this.keystore.load(null, password);
		this.password = password;
		this.currentPath = Model.KEYSTORE_DEFAULT_PATH;
	}
	
	/**
	 * Open a KeyStore from a file and its password
	 * @param path
	 * @param password
	 * @throws IOException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 */
	public Model(String path, char[] password) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		openKeyStore(path, password);
	}
	
	/**
	 * Open a Keytore with its path and its password
	 * @param path
	 * @param password
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyStoreException
	 */
	public void openKeyStore(String path, char[] password) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		this.keystore = KeyStore.getInstance("JCEKS");
		java.io.FileInputStream fis = new java.io.FileInputStream(path);
		this.keystore.load(fis, password);
		this.password = password;
		this.currentPath = path;
	}
	
	/**
	 * Change the KeyStore
	 * @param path : path of the new Keystore
	 * @param password : password of the new KeyStore
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public void setKeystore(String path, char[] password) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		openKeyStore(path, password);
	}
	
	/**
	 * Get a key from the alias
	 * @param alias
	 * @return a key
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 */
	private MTKey getKey(String alias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		if(keystore.getKey(alias, password) != null) {
			if(keystore.getCertificate(alias) != null) {
				return new MTKey(keystore.getKey(alias, this.password));
			} else
				throw new KeyStoreException("pas de certificat associé à la clé");
		} else
			throw new KeyStoreException("pas de clé à ce nom");
	}
	
	/**
	 * Get a Certificate from its alias
	 * @param alias of the certificate
	 * @return a Certificate
	 * @throws KeyStoreException
	 */
	private MTCertificate getCertificate(String alias) throws KeyStoreException  {
		return new MTCertificate(keystore.getCertificate(alias));
	}
	
	/**
	 * Add a (private) Key in the KeyStore. The MUST have a Certificate
	 * @param alias
	 * @param key
	 * @throws KeyStoreException
	 */
	public void addKey(String alias, MTKey key) throws KeyStoreException {
		keystore.setKeyEntry(alias, key.getKey(), this.password, new Certificate[] { key.getCertificate() });
	}
	
	/**
	 * Add a certificate to the keystore
	 * @param alias : alias of new certificate in the keystore
	 * @param cert : Certificate to add
	 * @throws KeyStoreException
	 */
	public void addCertificate(String alias, MTCertificate cert) throws KeyStoreException {
		keystore.setCertificateEntry(alias, cert.getCertificate());
	}
	
	/**
	 * Delete an entry
	 * @param alias to be deleted
	 * @throws KeyStoreException
	 */
	public void delEntry(String alias) throws KeyStoreException {
		this.keystore.deleteEntry(alias);
	}
	
	/**
	 * Save the KeyStore to a path
	 * @param path of the file
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public void saveTo(String path) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		FileOutputStream fos = new FileOutputStream(new File(path));
		this.keystore.store(fos, this.password);
		System.out.println("Save the keystore to"+path);
	}
	
	/**
	 * Save the KeyStore where is has been opened
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 */
	public void save() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		saveTo(this.currentPath);
	}
	
    public DefaultListModel getKeys() throws KeyStoreException {
    	DefaultListModel list = new DefaultListModel();
    	Enumeration<String> aliases = keystore.aliases();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(keystore.isKeyEntry(alias)) {
				list.addElement(alias.toString());
			}
		}
        return list;
    }
    
    public DefaultListModel getCertificates() throws KeyStoreException {
    	DefaultListModel list = new DefaultListModel();
    	Enumeration<String> aliases = keystore.aliases();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(keystore.isCertificateEntry(alias)) {
				list.addElement(keystore.getCertificate(alias));
			}
		}
        return list;
    }

}

/*
try {
this.keystore = new MTKeyStore(KEYSTORE_DEFAULT_PATH, DEFAULT_PASSWORD);

MTKey key = new MTKey("CN=Jean Dupont, OU=HEI, O=UCL, L=Lille, ST=France, C=FR");
keystore.addKey("jd", key);
System.out.println(key.getPrivateBase64());
System.out.println(key.getPublicBase64());

MTCertificate cert = new MTCertificate();
keystore.addCertificate("toto", cert);
System.out.println(cert.getBase64());

// keystore.save();

} catch (Exception e) {
e.printStackTrace();
}*/