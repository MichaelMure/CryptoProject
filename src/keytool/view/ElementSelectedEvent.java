package keytool.view;

import java.util.EventObject;

public class ElementSelectedEvent extends EventObject {

	private static final long serialVersionUID = -8753383749153490650L;

	private Object selectedElement;
	
	public ElementSelectedEvent(Object source, Object selectedElement) {
		super(source);

		this.selectedElement = selectedElement;
	}

	public Object getSelectedElement() {
		return selectedElement;
	}
}
