package keytool.model;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.util.encoders.Base64;
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
	 * From http://www.docjar.com/html/api/org/bouncycastle/openssl/PEMReader.java.html
	 * @param keypath
	 * @param certpath
	 * @throws ModelException
	 */
	public MTPrivateKey(String keypath, String certpath) throws ModelException {
		try {
			String type;
				
	        byte[] buffer = new byte[(int) new File(keypath).length()];
	        BufferedInputStream f = new BufferedInputStream(new FileInputStream(keypath));
	        f.read(buffer);
	        String keyfileText = new String(buffer);
	        String[] strings = keyfileText.split("-----");
	        if(strings.length < 3)
	        	throw new CertificateException("Fichier incorrect");
	        String keyText = strings[2];
			
			// extract the key
			byte[] keyBytes = Base64.decode(keyText);
	
			KeySpec                 privSpec;
			ByteArrayInputStream    bIn = new ByteArrayInputStream(keyBytes);
			ASN1InputStream         aIn = new ASN1InputStream(bIn);
			ASN1Sequence            seq = (ASN1Sequence)aIn.readObject();
	
			if (keyfileText.contains("RSA"))
			{
				type = "RSA";
				// DERInteger v = (DERInteger)seq.getObjectAt(0);
				DERInteger mod = (DERInteger)seq.getObjectAt(1);
				DERInteger pubExp = (DERInteger)seq.getObjectAt(2);
				DERInteger privExp = (DERInteger)seq.getObjectAt(3);
				DERInteger p1 = (DERInteger)seq.getObjectAt(4);
				DERInteger p2 = (DERInteger)seq.getObjectAt(5);
				DERInteger exp1 = (DERInteger)seq.getObjectAt(6);
				DERInteger exp2 = (DERInteger)seq.getObjectAt(7);
				DERInteger crtCoef = (DERInteger)seq.getObjectAt(8);
			
				privSpec = new RSAPrivateCrtKeySpec(
						mod.getValue(), pubExp.getValue(), privExp.getValue(),
						p1.getValue(), p2.getValue(),
						exp1.getValue(), exp2.getValue(),
						crtCoef.getValue());
			} else if (keyfileText.contains("DSA")) {
				// "DSA"
				type = "DSA";
				// DERInteger v = (DERInteger)seq.getObjectAt(0);
				DERInteger p = (DERInteger)seq.getObjectAt(1);
				DERInteger q = (DERInteger)seq.getObjectAt(2);
				DERInteger g = (DERInteger)seq.getObjectAt(3);
				// DERInteger y = (DERInteger)seq.getObjectAt(4);
				DERInteger x = (DERInteger)seq.getObjectAt(5);
			
				privSpec = new DSAPrivateKeySpec(
						x.getValue(), p.getValue(),
						q.getValue(), g.getValue());
			} else throw new CertificateException("Mauvais type de clé !");
			
			KeyFactory fact = KeyFactory.getInstance(type, "BC");
			this.key = fact.generatePrivate(privSpec);
			
			
	        byte[] certBuffer = new byte[(int) new File(certpath).length()];
	        BufferedInputStream certBis = new BufferedInputStream(new FileInputStream(certpath));
	        certBis.read(certBuffer);
	        String certfileText = new String(certBuffer);
	        String[] certStrings = certfileText.split("-----");
	        String certText = certStrings[2];
			
	        ByteArrayInputStream certBais = new ByteArrayInputStream(Base64.decode(certText));
	        CertificateFactory certFact = CertificateFactory.getInstance("X.509", "BC");

	        this.certificate = (X509Certificate)certFact.generateCertificate(certBais);
			
		} catch (FileNotFoundException e) {
			throw new ModelException("Fichier non trouvé ! : "+e.getMessage());
		} catch (IOException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());
		} catch (CertificateException e) {
			throw new ModelException("Problème de certificat : "+e.getMessage());
		}  catch (NoSuchAlgorithmException e) {
			throw new ModelException("Algorithme utilisé inconnu : "+e.getMessage());
		} catch (InvalidKeySpecException e) {
			throw new ModelException("Problème de clé privée : "+e.getMessage());

		} catch (NoSuchProviderException e) {
			throw new ModelException("Fournisseur de chiffrement inconnu  : "+e.getMessage());

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

	public String getDetails() {
		StringBuffer details = new StringBuffer(this.getPublicKey().getDetails());
		details.append("\n\n - Certificat ----- \n");
		details.append(this.getCertificate().getDetails());
		
		return details.toString();
	}
	
	public MTPublicKey getPublicKey() {
		return new MTPublicKey(this.certificate.getPublicKey());
	}
	
	public MTCertificate getCertificate() {
		return new MTCertificate(this.certificate);
	}
}
