package keytool.model;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.*;

public class MTKey {
	private Key key;
	private char[] password;
	private Certificate certificate;
	
	public MTKey() throws NoSuchAlgorithmException, CertificateEncodingException, InvalidKeyException, IllegalStateException, SignatureException, NoSuchProviderException {
		generateNewKey();
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
	
	public void generateNewKey() throws NoSuchAlgorithmException, CertificateEncodingException, InvalidKeyException, IllegalStateException, SignatureException, NoSuchProviderException {

		// FIXME : l'ajouter au bon endroit
		Security.addProvider(new BouncyCastleProvider());

		/* Génération d'une paire de clé privée/publique */
	    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
	    kpGen.initialize(1024, new SecureRandom());
	    KeyPair keyPair = kpGen.generateKeyPair();
	    /* Sauvegarde de la clé privée */
	    this.key = keyPair.getPrivate();

	    /* Génération d'un certificat qui encapsule la paire de clé */
	    X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

	    certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
	    certGen.setIssuerDN(new X500Principal("CN=Test Certificate"));
	    certGen.setNotBefore(new Date(System.currentTimeMillis() - 10000));
	    certGen.setNotAfter(new Date(System.currentTimeMillis() + 10000));
	    certGen.setSubjectDN(new X500Principal("CN=Test Certificate"));
	    certGen.setPublicKey(keyPair.getPublic());
	    certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

	    /* Sauvegarde du certificat */
	    this.certificate = certGen.generate(keyPair.getPrivate(), "BC");
	    
	  }

	
	public Certificate getCertificate() {
		return this.certificate;
	}

	public Certificate[] getCertificatesChain() {
		Certificate[] chain = { this.certificate };
		return chain;
	}
}
