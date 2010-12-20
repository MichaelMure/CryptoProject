package keytool;

import java.util.EventListener;

public interface KeytoolListener extends EventListener {
	public void keystoreChanged(KeyStoreChangedEvent event);

}
