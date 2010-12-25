package keytool.model;

import java.security.KeyStore;

/* Il reste ici ce que je n'est pas mis tel quel dans Model, pour référence si j'ai fait une connerie */

public class MTKeyStore {
	private KeyStore keystore;
	
	/**
	 * Get the entire KeyStore
	 * @return
	 */
	public KeyStore getKeystore() {
		return keystore;
	}
	
	/**
	 * Get the list of Keys of the KeyStore
	 * @return
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 */
	/*public ArrayList<MTKey> getKeys() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		Enumeration<String> aliases = keystore.aliases();
		ArrayList<MTKey> keys = new ArrayList<MTKey>();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(keystore.isKeyEntry(alias)) {
				keys.add(new MTKey(keystore.getKey(alias, Model.DEFAULT_PASSWORD)));
			}
		}
		return keys;
	}*/
	
	/**
	 * Get the list of the certificates
	 * @return
	 * @throws KeyStoreException
	 */
	/*public ArrayList<MTCertificate> getCertificates() throws KeyStoreException {
		Enumeration<String> aliases = keystore.aliases();
		ArrayList<MTCertificate> certificates = new ArrayList<MTCertificate>();
		String alias;
		while(aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if(keystore.isCertificateEntry(alias)) {
				certificates.add(new MTCertificate(keystore.getCertificate(alias)));
			}
		}
		return certificates;
	}*/
}
