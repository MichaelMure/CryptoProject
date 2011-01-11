package keytool.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.KeyStore.ProtectionParameter;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.crypto.SecretKey;
import javax.swing.DefaultListModel;

public class Model {
	private KeyStore keystore;
	private char[] password;
	private String currentPath;

	public Model() throws ModelException {

	}
	
	public Model(char[] password) throws ModelException {
		this.newKeyStore(password);
	}

	
	/**
	 * Create a new KeyStore in the memory
	 * @param password of the new KeyStore
	 * @throws ModelException 
	 */
	public void newKeyStore(char[] password) throws ModelException {
		try {
			this.keystore = KeyStore.getInstance("JCEKS");
			this.keystore.load(null, password);
		} catch (KeyStoreException e) {
			throw new ModelException("Erreur lors de la création du keystore : "+e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Erreur lors de la création du keystore : "+e.getMessage());
		} catch (CertificateException e) {
			//cannot happen
			e.printStackTrace();
		} catch (IOException e) {
			// cannot happen
			e.printStackTrace();
		}
		this.password = password;
		this.currentPath = null;
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
			throw new ModelException("Erreur de l'ouverture du keystore "+path+": "+e.getMessage());
		} catch (CertificateException e) {
			throw new ModelException("Erreur de l'ouverture du keystore "+path+": "+e.getMessage());
		} catch (IOException e) {
			throw new ModelException("Erreur de l'ouverture du keystore "+path+": Mauvais mot de passe ? "+e.getMessage());
		} catch (KeyStoreException e) {
			throw new ModelException("Erreur de l'ouverture du keystore "+path+": "+e.getMessage());
		}
		this.password = password;
		this.currentPath = path;
	}
	
	/**
	 * Get a key from the alias. It can be a private or secret key
	 * @param alias
	 * @return a key
	 * @throws ModelException 
	 */
	public MTPSKey getKey(String alias) throws ModelException {
		try {
			if(keystore.getKey(alias, password) != null) {
				if(keystore.getCertificate(alias) != null) {
					// La clé est associée à un certificat, c'est une clé privée
					return new MTPrivateKey(keystore.getKey(alias, this.password), keystore.getCertificate(alias));
				} else {
					//throw new KeyStoreException("pas de certificat associé à la clé");
					// FIXME : ajouter le mot de passe de la clé secrète
					return new MTSecretKey(keystore.getKey(alias, this.password), this.password);
				}
			} else
				throw new KeyStoreException("pas de clé "+alias+" à ce nom");
		} catch (UnrecoverableKeyException e) {
			throw new ModelException("Problème avec la clé irrécupérable :"+e.getMessage());

		} catch (KeyStoreException e) {
			throw new ModelException("Problème de KeyStore :"+e.getMessage());

		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Algorithme non connu :"+e.getMessage());

		}
	}
	
	/**
	 * Get a Certificate from its alias
	 * @param alias of the certificate
	 * @return a Certificate
	 * @throws ModelException 
	 */
	public MTCertificate getCertificate(String alias) throws ModelException {
		try {
			if(this.keystore.containsAlias(alias))
				return new MTCertificate(keystore.getCertificate(alias));
			else
				throw new KeyStoreException("Pas de certificat "+alias);
		} catch (KeyStoreException e) {
			throw new ModelException("Pas de certificat pour l'alias "+alias+" : "+e.getMessage());

		}
		
	}
	
	/**
	 * Add a Key in the KeyStore
	 * @param alias
	 * @param key
	 * @param certificate
	 * @throws ModelException
	 */
	public void addPrivateKey(String alias, Key key, Certificate certificate) throws ModelException {
		try {
			keystore.setKeyEntry(alias, key, this.password, new Certificate[] { certificate });
		} catch (KeyStoreException e) {
			throw new ModelException("Impossible d'ajouter la clé avec l'alias "+alias+" :"+e.getMessage());

		}
	}
	
	/**
	 * Add a certificate to the keystore
	 * @param alias : alias of new certificate in the keystore
	 * @param cert : Certificate to add
	 * @throws ModelException
	 */
	public void addCertificate(String alias, Certificate cert) throws ModelException {
		try {
			keystore.setCertificateEntry(alias, cert);
		} catch (KeyStoreException e) {
			throw new ModelException("Problème d'ajout du certificat :"+e.getMessage());

		}
	}
	
	/**
	 * Add a secretKey to the keystore
	 * @param alias of the key to insert
	 * @param key to insert
	 * @param password to protect the key
	 * @throws ModelException
	 */
	public void addSecretKey(String alias, SecretKey key, char[] password ) throws ModelException {
		try {

			KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);

			keystore.setEntry("secretKeyAlias", skEntry, new KeyStore.PasswordProtection(password));
		} catch (KeyStoreException e) {
			throw new ModelException("Problème d'ajout du certificat :"+e.getMessage());
		}
	}
	
	/**
	 * Delete an entry
	 * @param alias to be deleted
	 * @throws ModelException 
	 */
	public void delEntry(String alias) throws ModelException {
		try {
			if(alias == null) return;
			if(! this.keystore.containsAlias(alias))
				throw new ModelException("Pas d'alias "+alias);
			this.keystore.deleteEntry(alias);
		} catch (KeyStoreException e) {
			throw new ModelException("Pas d'entrée "+alias+"à supprimer :"+e.getMessage());
		}
	}
	
	/**
	 * Save the KeyStore to a path
	 * @param path of the file
	 * @throws ModelException
	 */
	public void saveAs(String path) throws ModelException {
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));
			this.keystore.store(fos, this.password);
			this.currentPath = path;
		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Erreur lors de l'enregistrement du keystore "+path+": "+e.getMessage());
		} catch (CertificateException e) {
			throw new ModelException("Erreur lors de l'enregistrement du keystore "+path+": "+e.getMessage());
		} catch (KeyStoreException e) {
			throw new ModelException("Erreur lors de l'enregistrement du keystore "+path+": "+e.getMessage());
		} catch (IOException e) {
			throw new ModelException("Erreur lors de l'enregistrement du keystore "+path+": "+e.getMessage());
		}
		System.out.println("Save the keystore to: "+path);
	}
	
	/**
	 * Save the KeyStore where is has been opened
	 * @throws ModelException 
	 */
	public void save() throws ModelException {
		saveAs(this.currentPath);
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
					list.addElement(alias);
				}
			}
		} catch (KeyStoreException e) {
			throw new ModelException("Erreur lors de l'accès à la liste des clés "+e.getMessage());
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
				// FIXME Il faut afficher les certificats des clés privées
				// qui ne sont pas du type "CertificateEntry"
				// On teste d'abord si c'est un vrai certificat.
				// Sinon, si c'est une clé privée (pas l'inverse !)
				if(keystore.isCertificateEntry(alias) || isPrivateKey(alias)) {
					list.addElement(alias);
				}
			}
		} catch (KeyStoreException e) {
			throw new ModelException("Erreur lors de l'accès à la liste des certificats "+e.getMessage());
		}
        return list;
    }

    /**
     * Détermine si le keystore est initialisé
     * @return
     */
	public boolean isInitialized() {
		if(this.keystore == null) return false;
		
	    // Attention : keystore peut-être différent de null, mais non initialisé !
		try {
			this.keystore.size();
			return true;
		} catch (KeyStoreException e) {
			return false;
		}
	}

	public String getCurrentPath() {
		return this.currentPath;
	}
	
	protected boolean isPrivateKey(String alias) throws ModelException {
		return (getKey(alias) instanceof MTPrivateKey);
		//return true;
		
	}

}