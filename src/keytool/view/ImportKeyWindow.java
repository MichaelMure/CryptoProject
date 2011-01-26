package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This class holds the import key window
 * @author michael
 *
 */
public class ImportKeyWindow extends JFrame {
	private static final long serialVersionUID = -8090384481447967419L;
	
	private JButton BtnChooseKey;
	private JButton BtnChooseCertificate;
	private JButton BtnValidate;
	private JButton BtnCancel;
	private JLabel LblKeyFile;
	private JLabel LblCertificateFile;
	private JLabel LblAlias;
	private JLabel LblVoid1;
	private JLabel LblVoid2;
	private JLabel LblVoid3;
	private JLabel LblTip;
	private JTextField FldKeyFile;
	private JTextField FldCertificateFile;
	private JTextField FldAlias;
	
	/**
	 * ImportKeyWindow constructor.
	 */
	public ImportKeyWindow() {
		initComponents();
	}
	
	/**
	 * Create and initialize all the components of the import key window
	 */
	private void initComponents() {
		LblKeyFile = new JLabel();
		LblCertificateFile = new JLabel();
		LblAlias = new JLabel();
		LblVoid1 = new JLabel();
		LblVoid2 = new JLabel();
		LblVoid3 = new JLabel();
		LblTip = new JLabel();
		FldKeyFile = new JTextField();
		FldCertificateFile = new JTextField();
		FldAlias = new JTextField();
		BtnChooseKey = new JButton();
		BtnChooseCertificate = new JButton();
		BtnCancel = new JButton();
		BtnValidate = new JButton();

		setTitle("Import d'une clé/certificat associé");
		getContentPane().setLayout(new java.awt.GridLayout(5, 3, 10, 10));

		getContentPane().add(LblVoid1);
		LblTip.setText("Pour importer un certificat, laissez vide le champ \"Clé\"");
		getContentPane().add(LblTip);
		getContentPane().add(LblVoid2);
		
		LblAlias.setText("Alias");
		getContentPane().add(LblAlias);
		getContentPane().add(FldAlias);
		getContentPane().add(LblVoid3);
		
		LblKeyFile.setText("Fichier de la clé");
		getContentPane().add(LblKeyFile);
		getContentPane().add(FldKeyFile);
		BtnChooseKey.setText("Parcourir");
		getContentPane().add(BtnChooseKey);
		
		LblCertificateFile.setText("Fichier du certificat");
		getContentPane().add(LblCertificateFile);
		getContentPane().add(FldCertificateFile);
		BtnChooseCertificate.setText("Parcourir");
		getContentPane().add(BtnChooseCertificate);
		
		BtnCancel.setText("Annuler");
		getContentPane().add(BtnCancel);
		BtnValidate.setText("Valider");
		getContentPane().add(BtnValidate);

		pack();
	}
	
	/**
	 * Reset all the fields of the window
	 */
	public void resetField() {
		this.FldAlias.setText("");
		this.FldKeyFile.setText("");
		this.FldCertificateFile.setText("");
	}
	
	/**
	 * Add an action listener to the Choose key button
	 * @param actLst
	 */
	public void addBtnChooseKeyListener(ActionListener actLst) {
    	BtnChooseKey.addActionListener(actLst);
	}
	
	/**
	 * Add an action listener to the choose certificate button
	 * @param actLst
	 */
	public void addBtnChooseCertificateListener(ActionListener actLst) {
    	BtnChooseCertificate.addActionListener(actLst);
	}
	
	/**
	 * Add an action listener to the cancel button
	 * @param actLst
	 */
	public void addBtnCancelListener(ActionListener actLst) {
    	BtnCancel.addActionListener(actLst);
	}
	
	/**
	 * Add an action listener to the validate button
	 */
	public void addBtnValidateListener(ActionListener actLst) {
    	BtnValidate.addActionListener(actLst);
	}
	
	/**
	 * Add a window listener to the Import key window
	 * @param wdLst
	 */
	public void addIWWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
	
	/**
	 * 
	 * @return the alias field
	 */
	public String getAliasField() {
		return this.FldAlias.getText();
	}
	
	/**
	 * 
	 * @return the path of the key file.
	 */
	public String getKeyFileField() {
		return this.FldKeyFile.getText();
	}
	
	/**
	 * 
	 * @return the path of the certificate file.
	 */
	public String getCertificateFileField() {
		return this.FldCertificateFile.getText();
	}
	
	/**
	 * Set the key file path field
	 * @param value
	 */
	public void setKeyFileField(String value) {
		this.FldKeyFile.setText(value);
	}
	
	/**
	 * Set the certificate file path field
	 * @param value
	 */
	public void setCertificateFileField(String value) {
		this.FldCertificateFile.setText(value);
	}
}
