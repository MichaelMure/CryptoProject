package keytool.model;

import java.security.Key;


public class MTKey {
	private Key key;
	
	public MTKey(Key key2) {
		this.key = key2;
	}


	public Key getKey() {
		return this.key;
	}
	public void setKey(Key key) {
		this.key = key;
	}

	
	public String toString() {
		return "clé "+key.getAlgorithm()+", "+key.getFormat()+", "+key.getClass().getName();
	}
	
	public String getDetails() {
		return this.key.getClass().getName();
	}
	

}
