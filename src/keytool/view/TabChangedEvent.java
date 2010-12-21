package keytool.view;

import java.util.EventObject;

public class TabChangedEvent extends EventObject {
	private int selectedTab;
	
	public TabChangedEvent(Object source, int selectedTab) {
		super(source);
		
		this.selectedTab = selectedTab;
	}
	public int getSelectedTab() {
		return this.selectedTab;
	}
}
