package keytool.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class MTCertificate {
	private Certificate certificate;
	private static String DEFAULT_SUBJECT = "CN=Alice-Subject, O=ENSISA, L=Mulhouse, ST=68, C=FR";
	private static String DEFAULT_ISSUER = "CN=Bob-Issuer, O=DataPower, L=Cambridge, ST=MA, C=US";


	public MTCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	
	public MTCertificate() throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
		this(DEFAULT_SUBJECT, DEFAULT_ISSUER);
	}

	public MTCertificate(String subject) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
		this(subject, subject);
	}
	
	public MTCertificate(String subject, String issuer) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException {

		/* Génération d'une paire de clé privée/publique */
	    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
	    kpGen.initialize(1024, new SecureRandom());
	    KeyPair keyPair = kpGen.generateKeyPair();

	    
	    /* Génération d'un certificat qui encapsule la paire de clé */
	    X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

	    certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
	    certGen.setIssuerDN(new X500Principal(issuer));
	    // One day before now
	    certGen.setNotBefore(new Date(System.currentTimeMillis() - (60 * 60 * 24)* 1000 ));
	    // One year after today
	    certGen.setNotAfter(new Date(System.currentTimeMillis() + (365 * 60 * 60 * 24)* 1000 ));
	    certGen.setSubjectDN(new X500Principal(subject));
	    certGen.setPublicKey(keyPair.getPublic());
	    certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

	    /* Sauvegarde du certificat */
	    this.certificate = certGen.generate(keyPair.getPrivate(), "BC");
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Certificate getCertificate() {
		return certificate;
	}
	
	public MTKey getPublicKey() {
		MTKey mtKey = new MTKey(this.certificate.getPublicKey());
		return mtKey;
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

	
	public String toBase64() throws CertificateEncodingException, IOException {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    
		PEMWriter pemWrt = new PEMWriter(new OutputStreamWriter(bOut));
    
		pemWrt.writeObject(this.certificate);
		pemWrt.close();
		bOut.close();
    
		return bOut.toString();
	}
}
