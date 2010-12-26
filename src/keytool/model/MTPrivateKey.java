package keytool.model;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
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
			throw new ModelException("Problème d'encodage du certificat : "+e.getMessage());

		} catch (InvalidKeyException e) {
			throw new ModelException("Clé invalide : "+e.getMessage());

		} catch (IllegalStateException e) {
			throw new ModelException("État invalide : "+e.getMessage());

		} catch (NoSuchProviderException e) {
			throw new ModelException("Fournisseur de cryptage inconnu : "+e.getMessage());

		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Algorithm utilisé inconnu : "+e.getMessage());

		} catch (SignatureException e) {
			throw new ModelException("Problème de signature : "+e.getMessage());

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
	 * Create a PrivateKey from a key and a Certificate
	 * @param keypath
	 * @param certpath
	 * @throws ModelException
	 */
	public MTPrivateKey(String keypath, String certpath) throws ModelException {
		try {
	        FileInputStream keyFis;

			keyFis = new FileInputStream(keypath);
		
	        DataInputStream keyDis = new DataInputStream(keyFis);
	        byte[] keyBytes = new byte[keyDis.available()];
	        keyDis.readFully(keyBytes);
	        ByteArrayInputStream bais = new ByteArrayInputStream(keyBytes);

            // loading Key
            byte[] key = new byte[bais.available()];
            KeyFactory kf = KeyFactory.getInstance("RSA");
            bais.read ( key, 0, bais.available() );
            bais.close();
            PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec ( key );
            PrivateKey ff = kf.generatePrivate (keysp);

            // loading CertificateChain
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream certFis = new FileInputStream(keypath);
	        DataInputStream certDis = new DataInputStream(certFis);
	        byte[] certBytes = new byte[certDis.available()];
	        certDis.readFully(certBytes);
	        InputStream certstream = new ByteArrayInputStream(certBytes);

            Collection<? extends Certificate> c = cf.generateCertificates(certstream) ;
            Certificate[] certs = new Certificate[c.toArray().length];
            
            if (c.size() == 1) {
                System.out.println("One certificate, no chain.");
                Certificate cert = cf.generateCertificate(certstream) ;
                certs[0] = cert;
                
                this.key = ff;
                this.certificate = cert;
            } else {
                throw new ModelException("Pas de gestion de certificat chaîné.");
            }

            
		} catch (FileNotFoundException e) {
			throw new ModelException("Fichier non trouvé ! : "+e.getMessage());
		} catch (IOException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());
		} catch (CertificateException e) {
			throw new ModelException("Problème de certificat : "+e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ModelException("Algorithme utilisé inconnu : "+e.getMessage());
		} catch (InvalidKeySpecException e) {
			throw new ModelException("Problème de clé privée : "+e.getMessage());

		}
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
