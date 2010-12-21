package keytool.view;

import java.util.EventListener;

public interface KeytoolListener extends EventListener {
	public void keystoreChanged(KeyStoreChangedEvent event);
	public void elementSelected(ElementSelectedEvent event);
	public void selectedTabChanged(TabChangedEvent event);
}
