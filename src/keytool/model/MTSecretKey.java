package keytool.model;

import java.security.Key;

import javax.crypto.SecretKey;

/**
 * Secret Key encapsulation :
 * @author Michaël Muré & Théophile Helleboid
 *
 */
public class MTSecretKey extends MTPSKey {
	
	public MTSecretKey(Key key2, char[] password2) {
		this.key = key2;
		this.password = password2;
	}
	
	public String getDetails() {
		StringBuffer details = new StringBuffer();
		details.append(" - Clé secrète ----- ");
		details.append("\nAlgorithme:\n  ").append(this.key.getAlgorithm());
		details.append("\nFormat :\n  ").append(this.key.getFormat());
		details.append("\nInstance de :\n  ").append(this.key.getClass().getName());
		return details.toString();
	}

	/**
	 * Add this Key to a keystore with an alias
	 * @param keystore
	 * @param alias
	 * @throws ModelException
	 */
	public void addToKeyStore(Model keystore, String alias) throws ModelException {

		keystore.addSecretKey(alias, (SecretKey)this.key, password);
	}

	
}
