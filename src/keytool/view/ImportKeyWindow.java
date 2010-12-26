package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ImportKeyWindow extends JFrame {
	private static final long serialVersionUID = -8090384481447967419L;
	
	private JButton BtnChooseKey;
	private JButton BtnChooseCertificate;
	private JButton BtnValidate;
	private JButton BtnCancel;
	private JLabel LblKeyFile;
	private JLabel LblCertificateFile;
	private JLabel LblAlias;
	private JLabel LblVoid;
	private JTextField FldKeyFile;
	private JTextField FldCertificateFile;
	private JTextField FldAlias;
	
	public ImportKeyWindow() {
		initComponents();
	}
	
	private void initComponents() {
		LblKeyFile = new JLabel();
		LblCertificateFile = new JLabel();
		LblAlias = new JLabel();
		LblVoid = new JLabel();
		FldKeyFile = new JTextField();
		FldCertificateFile = new JTextField();
		FldAlias = new JTextField();
		BtnChooseKey = new JButton();
		BtnChooseCertificate = new JButton();
		BtnCancel = new JButton();
		BtnValidate = new JButton();

		setTitle("Import d'une clé/certificat associé");
		getContentPane().setLayout(new java.awt.GridLayout(4, 3, 10, 10));

		LblAlias.setText("Alias");
		getContentPane().add(LblAlias);
		getContentPane().add(FldAlias);
		getContentPane().add(LblVoid);
		
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
	
	public void resetField() {
		this.FldAlias.setText("");
		this.FldKeyFile.setText("");
		this.FldCertificateFile.setText("");
	}
	
	public void addBtnChooseKeyListener(ActionListener actLst) {
    	BtnChooseKey.addActionListener(actLst);
	}
	
	public void addBtnChooseCertificateListener(ActionListener actLst) {
    	BtnChooseCertificate.addActionListener(actLst);
	}
	
	public void addBtnCancelListener(ActionListener actLst) {
    	BtnCancel.addActionListener(actLst);
	}
	
	public void addBtnValidateListener(ActionListener actLst) {
    	BtnValidate.addActionListener(actLst);
	}
	
	public void addIWWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
	
	public String getAliasField() {
		return this.FldAlias.getText();
	}
	
	public String getKeyFileField() {
		return this.FldKeyFile.getText();
	}
	
	public String getCertificateFileField() {
		return this.FldCertificateFile.getText();
	}
	
	public void setKeyFileField(String value) {
		this.FldKeyFile.setText(value);
	}
	
	public void setCertificateFileField(String value) {
		this.FldCertificateFile.setText(value);
	}
}
