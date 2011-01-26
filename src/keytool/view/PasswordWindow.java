package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle;

/**
 * This class holds the password window
 * @author michael
 *
 */
public class PasswordWindow extends JDialog {

	private static final long serialVersionUID = -4537433709703714282L;

	private javax.swing.JButton BtnCancel;
    private javax.swing.JButton BtnValidate;
    private javax.swing.JLabel LblPassword;
    private javax.swing.JPasswordField FldPassword;

    /**
     * Constructor of the password window
     */
    public PasswordWindow() {
    	initComponents();
    }
    
    /**
     * Create and initialize the password window component
     */
    private void initComponents() {
        FldPassword = new JPasswordField();
        LblPassword = new JLabel();
        BtnCancel = new JButton();
        BtnValidate = new JButton();

        this.setTitle("Mot de passe");
        LblPassword.setText("Mot de passe");

        BtnCancel.setText("Annuler");

        BtnValidate.setText("Valider");


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(FldPassword, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtnCancel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(BtnValidate))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(LblPassword)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblPassword)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCancel)
                    .addComponent(BtnValidate))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

	/**
	 * Reset the password field
	 */
	public void resetField() {
		this.FldPassword.setText("");
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
	 * @param actLst
	 */
	public void addBtnValidateListener(ActionListener actLst) {
    	BtnValidate.addActionListener(actLst);
	}
	
	/**
	 * Add a window listener to the password window
	 * @param wdLst
	 */
	public void addCWWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
	
	/**
	 * Add a key listener to the password field
	 * @param keyLst
	 */
	public void addKeyboardListener(KeyListener keyLst) {
		this.FldPassword.addKeyListener( keyLst);
	}
	
	/**
	 * 
	 * @return the password
	 */
	public char[] getPasswordField() {
		return this.FldPassword.getPassword();
	}
}
