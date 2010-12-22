package keytool.model;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;


public class MTKey {
	private Key key;
	private char[] password;
	private Certificate certificate;
	
	public MTKey() throws NoSuchAlgorithmException {
		this.key = newKey();
		this.password = null;
	}
	
	public MTKey(Key key2, char[] password2) {
		this.key = key2;
		this.password = password2;
	}


	public MTKey(Key key2, char[] password2, Certificate certificate2) {
		this.key = key2;
		this.password = password2;
		this.certificate = certificate2;
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
	
	public Key newKey() throws NoSuchAlgorithmException {

		String keyAlgorithm = "DSA"; // can be : DSA, RSA, DH
		
        // Get the public/private key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        return privateKey;
	}
	
	public Certificate getCertificate() {
		return this.certificate;
	}

	public Certificate[] getCertificatesChain() {
		Certificate[] chain = { this.certificate };
		return chain;
	}
}
