package keytool.model;

import java.security.Key;

/**
 * Public Key encapsulation :
 * @author Michaël Muré & Théophile Helleboid
 *
 */
public class MTPublicKey extends MTKey {

	/**
	 * Constructor with a Key
	 * @param key to encapsulate
	 */
	public MTPublicKey(Key key2) {
		this.key = key2;
	}
	
	/**
	 * Get details about the key
	 * @return the details
	 */
	public String getDetails() {
		StringBuffer details = new StringBuffer();
		details.append(" - Clé publique ----- ");
		details.append("\nAlgorithme:\n  ").append(this.key.getAlgorithm());
		details.append("\nFormat :\n  ").append(this.key.getFormat());
		details.append("\nInstance de :\n  ").append(this.key.getClass().getName());
		return details.toString();
	}


}
