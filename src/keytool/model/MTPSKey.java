package keytool.model;

/**
 * Clé privée ou secrète
 * @author chtitux
 *
 */
public abstract class MTPSKey extends MTKey {
	protected char[] password;

	public abstract String getDetails();

}
