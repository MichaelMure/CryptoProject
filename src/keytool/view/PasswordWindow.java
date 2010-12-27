package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PasswordWindow extends JFrame {
	private static final long serialVersionUID = 6203909852639047109L;

	private JButton BtnCancel;
	private JButton BtnValidate;
	private JLabel LblPassword;
	private JTextField FldPassword;


	public PasswordWindow() {
		initComponents();
	}
	
	private void initComponents() {
		LblPassword = new JLabel();
		FldPassword = new JTextField();
		BtnCancel = new JButton();
		BtnValidate = new JButton();

		setTitle("Mot de passe");
		getContentPane().setLayout(new java.awt.GridLayout(8, 2, 10, 10));

		LblPassword.setText("Mot de passe");
		getContentPane().add(LblPassword);
		getContentPane().add(FldPassword);
		
		BtnCancel.setText("Annuler");
		getContentPane().add(BtnCancel);
		BtnValidate.setText("Valider");
		getContentPane().add(BtnValidate);

		pack();
	}
	
	public void resetField() {
		this.FldPassword.setText("");
	}
	
	public void addBtnCancelListener(ActionListener actLst) {
    	BtnCancel.addActionListener(actLst);
	}
	
	public void addBtnValidateListener(ActionListener actLst) {
    	BtnValidate.addActionListener(actLst);
	}
	
	public void addCWWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
	
	public String getPasswordField() {
		return this.FldPassword.getText();
	}
}
