package keytool.model;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class Model {

	private MTKeyStore keystore;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	public static char[] DEFAULT_PASSWORD = { 'k', 'e', 'y', 't', 'o', 'o', 'l' };
	
    public Model(){
    	this.keystore = new MTKeyStore(KEYSTORE_DEFAULT_PATH, DEFAULT_PASSWORD);
        try {
        	/*
    	    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
    	    kpGen.initialize(1024, new java.security.SecureRandom());
    	    KeyPair keyPair = kpGen.generateKeyPair();

    		MTKey key = new MTKey(keyPair.getPrivate());
    		keystore.addKey("truc", key);
    		*/
        	MTCertificate cert = new MTCertificate();
        	keystore.addCertificate("toto", cert);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
    public DefaultListModel getKeys() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
    	DefaultListModel list = new DefaultListModel();
    	ArrayList<MTKey> keys = this.keystore.getKeys();
    	for(int i = 0; i < keys.size(); i++) {
    		list.addElement(keys.get(i).toString());
    	}

    	
        return list;
    }
    
    public DefaultListModel getCertificates() throws KeyStoreException {
    	DefaultListModel list = new DefaultListModel();
    	ArrayList<MTCertificate> certificates;
			certificates = this.keystore.getCertificates();

    	for(int i =0; i < certificates.size(); i++)
    		list.addElement(certificates.get(i).toString());
    	
    	list.addElement("Cert test");
        return list;
    }

}