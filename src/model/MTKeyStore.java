package model;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.Provider;
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
			 if(this.isKeyEntry(alias))
			 {
				 certificates.add(alias);
			 }
		 }
		 
		 return (String[]) certificates.toArray();
	}
}
