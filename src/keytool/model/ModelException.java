package keytool.model;

/**
 * Exception thrown by the application
 * @author Michaël Muré & Théophile Helleboid
 *
 */
public class ModelException extends Exception {

	private static final long serialVersionUID = 6988414290050131098L;

	public ModelException() {
	}

	/**
	 * Constructor for the Exception
	 * @param Error message 
	 */
	public ModelException(String message) {
		super(message);
	}

}
