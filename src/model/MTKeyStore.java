package model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Enumeration;

public class MTKeyStore {
	private static String mdp = "keytool";
	private KeyStore ks;

	public MTKeyStore(String path, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		KeyStore ks = KeyStore.getInstance("JCEKS");
		// FIXME : process de test
//		FileInputStream fis = new FileInputStream(path);
//		ks.load(fis, password.toCharArray());
		FileInputStream fis = new FileInputStream("store.ks");
		ks.load(fis, mdp.toCharArray());
		this.ks = ks;
	}
	
	/**
	 * Récupèrer les clés d'un KeyStore
	 * @return un tableau de clés
	 * @throws KeyStoreException
	 */
	public String[] getKeys() throws KeyStoreException
	{
		 Enumeration<String> aliases = ks.aliases();
		 String alias;
		 ArrayList<String> keys = new ArrayList<String>();
		 while(aliases.hasMoreElements())
		 {
			 alias =  aliases.nextElement();
			 if(ks.isKeyEntry(alias))
			 {
				 keys.add(alias);
			 }
		 }
		 
		 return (String[]) keys.toArray();
	}
	/**
	 * Récupérer les certificats d'un keystore
	 * @return un tableau de certificat
	 * @throws KeyStoreException
	 */
	public String[] getCertificates() throws KeyStoreException
	{
		 Enumeration<String> aliases = ks.aliases();
		 String alias;
		 ArrayList<String> certificates = new ArrayList<String>();
		 while(aliases.hasMoreElements())
		 {
			 alias =  aliases.nextElement();
			 if(ks.isCertificateEntry(alias))
			 {
				 certificates.add(alias);
			 }
		 }
		 
		 return (String[]) certificates.toArray();
	}
	
	/**
	 * Ajouter un certificat X509 à partir de son emplacement sur le disque
	 * @param alias l'alias du certificat dans le keystore
	 * @param path le chemin vers le certificat à importer
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 */
	public void addCertificate(String alias, String path) throws IOException, FileNotFoundException, CertificateException, KeyStoreException
	{
		FileInputStream fis;
		fis = new FileInputStream(path);

		BufferedInputStream bis = new BufferedInputStream(fis);

		CertificateFactory cf;
		cf = CertificateFactory.getInstance("X.509");

		// S'il y a plusieurs certificats dans le fichier
		// ça pose problème pour les alias
//		while (bis.available() > 0) {
			Certificate cert;
			cert = cf.generateCertificate(bis);
			System.out.println(cert.toString());
			ks.setCertificateEntry(alias, cert);
//		}
		 
	}
	
}
