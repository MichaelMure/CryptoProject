package keytool.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.swing.DefaultListModel;

public class Model {
	private KeyStore keystore;
	private char[] password;
	private String currentPath;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	private static char[] DEFAULT_PASSWORD = "keytool".toCharArray();
	
	public Model() throws ModelException {
		this(Model.DEFAULT_PASSWORD);
	}
	
	public Model(char[] password) throws ModelException {
		this.newKeyStore(password);
	}
	
	/**
	 * Open a KeyStore from a file and its password
	 * @param path
	 * @param password
	 * @throws ModelException 
	 */
	public Model(String path, char[] password) throws ModelException {
		openKeyStore(path, password);
	}
	
	/**
	 * Create a new KeyStore with the DEFAULT_PASSWORD, in the KEYSTORE_DEFAULT_PATH
	 * @throws ModelException 
	 */
	public void newKeyStore() throws ModelException {
		newKeyStore(Model.DEFAULT_PASSWORD);
	}
	
	/**
	 * Create a new KeyStore in the KEYSTORE_DEFAULT_PATH
	 * @param password of the new KeyStore
	 * @throws ModelException 
	 */
	public void newKeyStore(char[] password) throws ModelException {
		try {
			this.keystore = KeyStore.getInstance("JCEKS");
			this.keystore.load(null, password);
		} catch (KeyStoreException e) {
			throw new ModelException("Error while creating default keystore : Can not create keystore.");
		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Error while creating default keystore : Integrity checking algorithm not found.");
		} catch (CertificateException e) {
			//cannot happend
			e.printStackTrace();
		} catch (IOException e) {
			// cannot happend
			e.printStackTrace();
		}
		this.password = password;
		this.currentPath = Model.KEYSTORE_DEFAULT_PATH;
	}
	
	/**
	 * Open a Keytore with its path and use the DEFAULT_PASSWORD
	 * @param path
	 * @throws ModelException 
	 */
	public void openKeyStore(String path) throws ModelException {
		this.openKeyStore(path, DEFAULT_PASSWORD);
	}
	
	/**
	 * Open a Keytore with its path and its password
	 * @param path
	 * @param password
	 * @throws ModelException 
	 */
	public void openKeyStore(String path, char[] password) throws ModelException {
		try {
			this.keystore = KeyStore.getInstance("JCEKS");
			java.io.FileInputStream fis = new java.io.FileInputStream(path);
			this.keystore.load(fis, password);
		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Error while opening keystore "+path+": Integrity checking algorithm not found.");
		} catch (CertificateException e) {
			throw new ModelException("Error while opening keystore "+path+": A certificate cannot be loaded.");
		} catch (IOException e) {
			throw new ModelException("Error while opening keystore "+path+": Can not find this file.");
		} catch (KeyStoreException e) {
			throw new ModelException("Error while opening keystore "+path+": Can not create keystore.");
		}
		this.password = password;
		this.currentPath = path;
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
	 * @throws ModelException
	 */
	public void saveTo(String path) throws ModelException {
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));
			this.keystore.store(fos, this.password);
		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Error while saving keystore "+path+": Integrity checking algorithm not found.");
		} catch (CertificateException e) {
			throw new ModelException("Error while saving keystore "+path+": A certificate cannot be saved.");
		} catch (KeyStoreException e) {
			throw new ModelException("Error while saving keystore "+path+": Keystore has not been initialized.");
		} catch (IOException e) {
			throw new ModelException("Error while saving keystore "+path+": Can not save here.");
		}
		System.out.println("Save the keystore to: "+path);
	}
	
	/**
	 * Save the KeyStore where is has been opened
	 * @throws ModelException 
	 */
	public void save() throws ModelException {
		saveTo(this.currentPath);
	}
	
    /**
     * @return the keys list
     * @throws ModelException
     */
    public DefaultListModel getKeys() throws ModelException{
    	DefaultListModel list = new DefaultListModel();
		try {
	    	Enumeration<String> aliases = keystore.aliases();
			String alias;
			while(aliases.hasMoreElements()) {
				alias = aliases.nextElement();
				if(keystore.isKeyEntry(alias)) {
					list.addElement(alias.toString());
				}
			}
		} catch (KeyStoreException e) {
			throw new ModelException("Error while listing keys : Keystore has not been initialized.");
		}
        return list;
    }
    
    /**
     * @return the certificate list
     * @throws ModelException
     */
    public DefaultListModel getCertificates() throws ModelException {
    	DefaultListModel list = new DefaultListModel();
    	try {
    		Enumeration<String> aliases = keystore.aliases();
			String alias;
			while(aliases.hasMoreElements()) {
				alias = aliases.nextElement();
				if(keystore.isCertificateEntry(alias)) {
					list.addElement(keystore.getCertificate(alias));
				}
			}
		} catch (KeyStoreException e) {
			throw new ModelException("Error while listing certificates : Keystore has not been initialized.");
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