package keytool.model;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.x509.X509V3CertificateGenerator;


public class MTKey {
	private Key key;
	private Certificate certificate;
	
	public MTKey(String subject) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
		generateNewKey(subject);
	}
	
	public MTKey(Key key2) {
		this.key = key2;
	}

	public Key getKey() {
		return this.key;
	}

	public Certificate getCertificate() {
		return this.certificate;
	}
	
	public String toString() {
		return "clé "+key.getAlgorithm()+", "+key.getFormat()+", "+key.getClass().getName();
	}
	
	public String getDetails() {
		return this.key.getClass().getName();
	}
	
	public void generateNewKey(String subject) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException  {

		/* Génération d'une paire de clé privée/publique */
	    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
	    kpGen.initialize(1024, new SecureRandom());
	    KeyPair keyPair = kpGen.generateKeyPair();
	    /* Save the key */
	    this.key = keyPair.getPrivate();
	    
	    /* Génération d'un certificat qui encapsule la paire de clé */
	    X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

	    SecureRandom random = new SecureRandom();

	    // FIXME: seems can be negative
	    certGen.setSerialNumber(BigInteger.valueOf(random.nextInt()));
	    certGen.setIssuerDN(new X500Principal(subject));
	    certGen.setNotBefore(new Date(System.currentTimeMillis() - 10000));
	    certGen.setNotAfter(new Date(System.currentTimeMillis() + 10000));
	    certGen.setSubjectDN(new X500Principal(subject));
	    certGen.setPublicKey(keyPair.getPublic());
	    certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

	    /* Sauvegarde du certificat */
	    this.certificate = certGen.generate(keyPair.getPrivate(), "BC");
	    
	  }
	
	public String getPrivateBase64() {
		String string = new String(org.bouncycastle.util.encoders.Base64.encode(this.key.getEncoded()));
		
		return string;
	}
	
	public String getPublicBase64() {
		String string = new String(org.bouncycastle.util.encoders.Base64.encode(this.certificate.getPublicKey().getEncoded()));
		
		return "-----BEGIN CERTIFICATE-----\n"+string+"\n-----END CERTIFICATE-----\n";
	}
}
