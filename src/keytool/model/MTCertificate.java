package keytool.model;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.x509.X509V3CertificateGenerator;

public class MTCertificate {
	private Certificate certificate;

	public MTCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	
	public MTCertificate() throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
		generateNewCertificate();
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Certificate getCertificate() {
		return certificate;
	}
	
	public PublicKey getPublicKey() {
        // Get public key
		PublicKey publicKey = this.certificate.getPublicKey();
		return publicKey;
	}
	
	public String getDetails() {
		X509Certificate cert = (X509Certificate) this.certificate;
		StringBuilder sb = new StringBuilder();
		if(cert != null) {
				
			sb.append("IssuerDN:"+cert.getIssuerDN().toString());
			sb.append("\nNotBefore:"+cert.getNotBefore().toString());
			sb.append("\nNotAfter:"+cert.getNotAfter().toString());
	
		    // récupération de la description X500 de l'émetteur
			X500Principal issuer = cert.getIssuerX500Principal();
			// édition de la description selon la RFC2253 (mode par défaut)
			sb.append("émetteur\n").append(issuer.getName("RFC2253")).append("\n________________\n\n");
			// récupération de la description X500 du sujet
			X500Principal subject = cert.getSubjectX500Principal();
			// édition de la description selon la RFC1779
			sb.append("sujet\n").append(subject.getName("RFC1779")).append("\n________________\n\n");

		} else {
			sb.append("Pas de certificat X509");
		}
		return sb.toString();
	}
	
	public String toString() {
		return getDetails();
	}
	
	public void generateNewCertificate() throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException  {

		/* Génération d'une paire de clé privée/publique */
	    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
	    kpGen.initialize(1024, new SecureRandom());
	    KeyPair keyPair = kpGen.generateKeyPair();

	    
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
}
