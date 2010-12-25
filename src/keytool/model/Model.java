package keytool.model;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

@SuppressWarnings("unused")
public class Model {

	private MTKeyStore keystore;

	private static String KEYSTORE_DEFAULT_PATH = "store.ks";
	public static char[] DEFAULT_PASSWORD = "keytool".toCharArray();
	
    public Model(){
        try {
        	this.keystore = new MTKeyStore(KEYSTORE_DEFAULT_PATH, DEFAULT_PASSWORD);

    		MTKey key = new MTKey("CN=Jean Dupont, OU=HEI, O=UCL, L=Lille, ST=France, C=FR");
    		keystore.addKey("jd", key);
    		
        	MTCertificate cert = new MTCertificate();
        	keystore.addCertificate("toto", cert);
        	
        	// keystore.save();
        	
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