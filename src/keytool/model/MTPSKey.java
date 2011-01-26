package keytool.model;

/**
 * Private or Secret Key
 * These keys need a password
 * @author Michaël Muré & Théophile Helleboid
 *
 */
public abstract class MTPSKey extends MTKey {
	protected char[] password;

	public abstract String getDetails();

}
