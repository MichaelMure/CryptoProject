package keytool.model;

import java.security.Key;

public class MTPublicKey extends MTKey {

	public MTPublicKey(Key key2) {
		this.key = key2;
	}
	
	public String getDetails() {
		StringBuffer details = new StringBuffer();
		details.append(" - Clé publique ----- ");
		details.append("\nAlgorithme:\n  ").append(this.key.getAlgorithm());
		details.append("\nFormat :\n  ").append(this.key.getFormat());
		details.append("\nInstance de :\n  ").append(this.key.getClass().getName());
		return details.toString();
	}


}
