package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This class is the window for creating a key.
 * It holds a list of text field to configure the new key.
 * @author michael
 *
 */
public class CreateKeyWindow extends JFrame {
	private static final long serialVersionUID = -8090384481447967419L;
	
	private JButton BtnCancel;
	private JButton BtnValidate;
	private JLabel LblAlias;
	private JLabel LblName;
	private JLabel LblOU;
	private JLabel LblOrg;
	private JLabel LblCity;
	private JLabel LblState;
	private JLabel LblCountry;
	private JTextField FldAlias;
	private JTextField FldName;
	private JTextField FldOU;
	private JTextField FldOrg;
	private JTextField FldCity;
	private JTextField FldState;
	private JTextField FldCountry;
	
	public CreateKeyWindow() {
		initComponents();
	}
	
	/**
	 * Create and initialize the different component of the JFrame.
	 */
	private void initComponents() {
		LblAlias = new JLabel();
		FldAlias = new JTextField();
		LblName = new JLabel();
		FldName = new JTextField();
		LblOU = new JLabel();
		FldOU = new JTextField();
		LblOrg = new JLabel();
		FldOrg = new JTextField();
		LblCity = new JLabel();
		FldCity = new JTextField();
		LblState = new JLabel();
		FldState = new JTextField();
		LblCountry = new JLabel();
		FldCountry = new JTextField();
		BtnCancel = new JButton();
		BtnValidate = new JButton();

		setTitle("Creation d'une clé");
		getContentPane().setLayout(new java.awt.GridLayout(8, 2, 10, 10));

		LblAlias.setText("Nom de la clé");
		getContentPane().add(LblAlias);
		getContentPane().add(FldAlias);
		
		LblName.setText("Nom complet");
		getContentPane().add(LblName);
		getContentPane().add(FldName);
		
		LblOU.setText("Unité d'organisation");
		getContentPane().add(LblOU);
		getContentPane().add(FldOU);
		
		LblOrg.setText("Organisation");
		getContentPane().add(LblOrg);
		getContentPane().add(FldOrg);
		
		LblCity.setText("Ville");
		getContentPane().add(LblCity);
		getContentPane().add(FldCity);
		
		LblState.setText("État/Province");
		getContentPane().add(LblState);
		getContentPane().add(FldState);
		
		LblCountry.setText("Code pays (2 lettres)");
		getContentPane().add(LblCountry);
		getContentPane().add(FldCountry);

		BtnCancel.setText("Annuler");
		getContentPane().add(BtnCancel);
		BtnValidate.setText("Valider");
		getContentPane().add(BtnValidate);

		pack();
	}
	
	/**
	 * Reset all the text fields.
	 */
	public void resetField() {
		this.FldAlias.setText("");
		this.FldName.setText("");
		this.FldOU.setText("");
		this.FldOrg.setText("");
		this.FldCity.setText("");
		this.FldState.setText("");
		this.FldCountry.setText("");
	}
	
	/**
	 * Add a listener to the cancel button.
	 * @param actLst
	 */
	public void addBtnCancelListener(ActionListener actLst) {
    	BtnCancel.addActionListener(actLst);
	}
	
	/**
	 * Add a listener to the validate button.
	 * @param actLst
	 */
	public void addBtnValidateListener(ActionListener actLst) {
    	BtnValidate.addActionListener(actLst);
	}
	
	/**
	 * Add a listener to the window.
	 * @param wdLst
	 */
	public void addCWWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
	
	/**
	 * 
	 * @return the Alias text field.
	 */
	public String getAliasField() {
		return this.FldAlias.getText();
	}
	
	/**
	 * 
	 * @return the name text field.
	 */
	public String getNameField() {
		return this.FldName.getText();
	}
	
	/**
	 * 
	 * @return the OU text field.
	 */
	public String getOUField() {
		return this.FldOU.getText();
	}
	
	/**
	 * 
	 * @return the Org text field.
	 */
	public String getOrgField() {
		return this.FldOrg.getText();
	}
	
	/**
	 * 
	 * @return the City text field.
	 */
	public String getCityField() {
		return this.FldCity.getText();
	}
	
	/**
	 * 
	 * @return the State text field.
	 */
	public String getStateField() {
		return this.FldState.getText();
	}
	
	/**
	 * 
	 * @return the Country text field.
	 */
	public String getCountryField() {
		return this.FldCountry.getText();
	}
	
}
