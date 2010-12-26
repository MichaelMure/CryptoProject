package keytool.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateWindow extends JFrame {
	private static final long serialVersionUID = -8090384481447967419L;
	
	private JButton BtnCancel;
	private JButton BtnValidate;
	private JLabel LblName;
	private JLabel LblOU;
	private JLabel LblOrg;
	private JLabel LblCity;
	private JLabel LblState;
	private JLabel LblCountry;
	private JTextField FldName;
	private JTextField FldOU;
	private JTextField FldOrg;
	private JTextField FldCity;
	private JTextField FldState;
	private JTextField FldCountry;
	
	public CreateWindow() {
		initComponents();
	}
	
	private void initComponents() {
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

		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Creation d'un certificat");
		getContentPane().setLayout(new java.awt.GridLayout(7, 2, 10, 10));

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
}
