package keytool.model;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class MTKey {
	private Key key;
	private char[] password;
	
	public MTKey(Key key, char[] password) {
		this.key = key;
		this.setPassword(password);
	}


	public Key getKey() {
		return this.key;
	}
	public void setKey(Key key) {
		this.key = key;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public char[] getPassword() {
		return password;
	}
	
	public String toString() {
		return "clé "+key.getAlgorithm()+", "+key.getFormat()+", "+key.getClass().getName();
	}
	
	public String getDetails() {
		return this.key.getClass().getName();
	}
	
	public static Key newKey() throws NoSuchAlgorithmException {
		String keyAlgorithm = "DSA"; // can be : DSA, RSA, DH
		
        // Get the public/private key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        return privateKey;
	}
}
