package model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.Provider;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Enumeration;

public class MTKeyStore extends KeyStore {
	private static String mdp = "keytool";
	
	protected MTKeyStore(KeyStoreSpi keyStoreSpi, Provider provider, String type) {
		super(keyStoreSpi, provider, type);
		// TODO Auto-generated constructor stub
	}

	public String[] getKeys() throws KeyStoreException
	{
		 Enumeration<String> aliases = this.aliases();
		 String alias;
		 ArrayList<String> keys = new ArrayList<String>();
		 while(aliases.hasMoreElements())
		 {
			 alias =  aliases().nextElement();
			 if(this.isKeyEntry(alias))
			 {
				 keys.add(alias);
			 }
		 }
		 
		 return (String[]) keys.toArray();
	}
	
	public String[] getCertificates() throws KeyStoreException
	{
		 Enumeration<String> aliases = this.aliases();
		 String alias;
		 ArrayList<String> certificates = new ArrayList<String>();
		 while(aliases.hasMoreElements())
		 {
			 alias =  aliases().nextElement();
			 if(this.isCertificateEntry(alias))
			 {
				 certificates.add(alias);
			 }
		 }
		 
		 return (String[]) certificates.toArray();
	}
	
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
			this.setCertificateEntry(alias, cert);
//		}
		 
	}
}
