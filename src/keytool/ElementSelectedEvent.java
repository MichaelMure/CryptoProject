package keytool;

import java.security.Key;
import java.util.EventObject;

public class ElementSelectedEvent extends EventObject {
	private Object selectedElement;
	
	public ElementSelectedEvent(Object source, Object selectedElement) {
		super(source);

		this.selectedElement = selectedElement;
	}

	public Object getSelectedElement() {
		return selectedElement;
	}
}
