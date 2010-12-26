package keytool.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

public class MTPrivateKey extends MTKey {
	private Certificate certificate;

	/**
	 * Create a PrivateKey with a subject
	 * @param subject
	 * @throws ModelException 
	 * @throws CertificateEncodingException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 */
	public MTPrivateKey(String subject) throws ModelException  {
		try {
			/* Génération d'une paire de clé privée/publique */
		    KeyPairGenerator kpGen;
		    kpGen = KeyPairGenerator.getInstance("RSA", "BC");

	
		    kpGen.initialize(1024, new SecureRandom());
		    KeyPair keyPair = kpGen.generateKeyPair();
		    /* Save the key */
		    this.key = keyPair.getPrivate();
		    
		    /* Génération d'un certificat qui encapsule la paire de clé */
		    X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
	
		    SecureRandom random = new SecureRandom();
	
		    certGen.setSerialNumber(BigInteger.valueOf(random.nextInt(random.nextInt(Integer.MAX_VALUE))));
		    certGen.setIssuerDN(new X500Principal(subject));
		    certGen.setNotBefore(new Date(System.currentTimeMillis() - 10000));
		    certGen.setNotAfter(new Date(System.currentTimeMillis() + 10000));
		    certGen.setSubjectDN(new X500Principal(subject));
		    certGen.setPublicKey(keyPair.getPublic());
		    certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
	
		    /* Sauvegarde du certificat */
			this.certificate = certGen.generate(keyPair.getPrivate(), "BC");

		} catch (CertificateEncodingException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());

		} catch (InvalidKeyException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());

		} catch (IllegalStateException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());

		} catch (NoSuchProviderException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());

		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());

		} catch (SignatureException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());

		}
	}
	
	/**
	 * Create a PrivateKey from a other PrivateKey and its certificate
	 * @param key2
	 * @param cert2
	 */
	public MTPrivateKey(Key key2, Certificate cert2) {
		this.key = key2;
		this.certificate = cert2;
	}
	
	/**
	 * Add the current PrivateKey to a keystore
	 * @param keystore
	 * @param alias
	 * @throws ModelException 
	 */
	public void addToKeyStore(Model keystore, String alias) throws ModelException {
		keystore.addKey(alias, this.key, this.certificate);
	}

	public void exportTo(String path) throws ModelException {
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(path));
			buf.write(this.toBase64());
			buf.close();
		} catch (CertificateEncodingException e) {
			throw new ModelException("Problème d'encodage de certificat : "+e.getMessage());
		} catch (IOException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());
		}
		
	}

}
