package keytool;

import java.security.Key;
import java.util.EventObject;

public class KeySelectedEvent extends EventObject {
	private Key selectedKey;
	
	public KeySelectedEvent(Object source, Key selectedKey) {
		super(source);

		this.selectedKey = selectedKey;
	}

	public Key getSelectedKey() {
		return selectedKey;
	}
}
