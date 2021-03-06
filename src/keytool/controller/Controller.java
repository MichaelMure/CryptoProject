package keytool.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import keytool.model.MTCertificate;
import keytool.model.MTPrivateKey;
import keytool.model.Model;
import keytool.model.ModelException;
import keytool.view.MainWindow;
import keytool.view.View;

/*
@startuml

[*] --> StateWait
StateWait --> StateSaving : menuSave
StateSaving --> StateWait : FCopen
StateSaving --> StateWait : FCcancel

StateWait --> StateOpening : menuOpen
StateOpening --> StateWait : FCcancel
StateOpening --> StatePickPassword : FCOpen
StatePickPassword --> StateWait : PWValidate
StatePickPassword --> StatePickPassword : PWValidate
StatePickPassword --> StateWait : PWCancel

StateWait --> StateExporting : itemExport
StateExporting --> StateWait : FCopen
StateExporting --> StateWait : FCcancel

StateWait --> StateImporting : itemImport
StateImporting --> StateChoosingKey : BtnChooseKey
StateChoosingKey --> StateImporting : FCopen
StateChoosingKey --> StateImporting : FCcancel
StateImporting --> StateChoosingCertificate : BtnChooseCertificate
StateChoosingCertificate --> StateImporting : FCopen
StateChoosingCertificate --> StateImporting : FCcancel
StateImporting --> StateWait : BtnValidate
StateImporting --> StateWait : BtnCancel

@enduml
 */

/**
 * This class is the controller part of the MVC pattern.
 * This class hold various listener classes, which are instantiated through the constructor,
 * and connected to the view.
 */
public class Controller {
	private View view;
	private Model model;
	private enum State {StateWAIT,
									StateSAVING,
									StateOPENING,
									StatePICKPASSWORD,
									StateOPENINGFAIL,
									StateIMPORTING,
									StateEXPORTING,
									StateCREATINGKEY,
									StateCHOOSINGKEY,
									StateCHOOSINGCERTIFICATE,
									StateCHOOSINGPASSWORDKEYSTORE};

	private State state;

	/**
	 * Constructor of the Controller
	 * @param model The model
	 * @param view The view
	 */
	public Controller(Model model, View view){
		this.view = view;
		this.model = model;
		this.state = State.StateWAIT;

		initMainWindowListener();
		refreshKeysList();
		refreshCertificateList();

		initFCWindowListener();
		initCreateKeyWindowListener();
		initImportKeyWindowListener();
		initPasswordWindowListener();
	}

	/* MainWindow */
	/**
	 * Initialize the listener for the main window
	 */
	private void initMainWindowListener() {
		MainWindow mw = this.view.getMainWindow();
		mw.addItemNewKeyStoreListener(new ItemNewListener());
		mw.addItemOpenListener(new ItemOpenListener());
		mw.addItemSaveListener(new ItemSaveListener());
		mw.addItemSaveAsListener(new ItemSaveAsListener());
		mw.addItemQuitListener(new ItemQuitListener());
		mw.addBtnImportListener(new BtnImportListener());
		mw.addBtnExportListener(new BtnExportListener());
		mw.addBtnDeleteListener(new BtnDeleteListener());
		mw.addBtnNewKeyListener(new BtnNewKeyListener());
		mw.addKeyListListener(new ListKeysListener());
		mw.addCertificatesListListener(new ListCertificatesListener());
		mw.addTabChangeListener(new ChangeTabListener());
		refreshMainWindow();
	}

	/**
	 * Refresh both certificate and key of the main window.
	 */
	private void refreshLists() {
		this.refreshKeysList();
		this.refreshCertificateList();
	}
	
	/**
	 * Refresh the key list of the main window.
	 */
	private void refreshKeysList() {
		DefaultListModel list = new DefaultListModel();

		try {
			if(model.isInitialized())
				list = this.model.getKeys();
		} catch (ModelException e) {
			this.view.createErrorWindow(e.getMessage());
		} finally {
			this.view.getMainWindow().setKeysList(list);
		}
	}
	
	/**
	 * Refresh the certificate list of the main window.
	 */
	private void refreshCertificateList() {
		DefaultListModel list = new DefaultListModel();
		// Si le modèle n'est pas initialisé, la liste reste vide

		try {
			if(model.isInitialized())
				list = this.model.getCertificates();
		} catch (ModelException e) {
			this.view.createErrorWindow(e.getMessage());
		} finally {
			this.view.getMainWindow().setCertificatesList(list);
		}
	}

	/**
	 * Refresh the details in the main window.
	 */
	private void refreshDetails() {
		if(!model.isInitialized())
			view.getMainWindow().setDetails("");
		else {
			String details = "";
			if(view.getMainWindow().isKeysTabSelected()) {
				String selectedKey = view.getMainWindow().getSelectedKey();
				try {
					if(selectedKey != null)
						details = model.getKey(selectedKey).getDetails();
				} catch (ModelException e) {
					view.createErrorWindow(e.getMessage());
				}
			} else if(view.getMainWindow().isCertificatesTabSelected()) {
				String selectedCertificate = view.getMainWindow().getSelectedCertificate();
				try {
					if(selectedCertificate != null)
						details = model.getCertificate(selectedCertificate).getDetails();
				} catch (ModelException e) {
					view.createErrorWindow(e.getMessage());
				}
			}
			view.getMainWindow().setDetails(details);
		}
	}
	
	/**
	 * Met à jour la fenêtre principale :
	 * - active/désactive les boutons
	 * - met à jour les listes
	 */
	private void refreshMainWindow() {
		// Details are computed from the lists
		// DO refresh the lists before the details
		refreshLists();
		refreshDetails();
		
		if(model.isInitialized()) {
			this.setEnable(true);
			view.getMainWindow().setTitle(model.getCurrentPath());
		} else {
			this.setEnable(false);
			view.getMainWindow().setTitle(null);
		}
	}
	
	/**
	 * Enable or not the button of the main window
	 * @param enable
	 */
	private void setEnable(boolean enable) {
		view.getMainWindow().setEnabledFields(enable);
	}

	/**
	 * Action listener for the new button of the main window
	 */
	class ItemNewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			state = State.StateCHOOSINGPASSWORDKEYSTORE;
			view.getPasswordWindow().resetField();
			view.showPasswordWindow();
		}
	}
	
	/**
	 * Action listener for the open button of the main window
	 */
	class ItemOpenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.getFileChooserWindow().setOpenDialog();
			view.showFileChooserWindow("Choix du KeyStore");
			state = State.StateOPENING;
		}
	}

	/**
	 * Action listener for the quit button of the main window
	 */
	class ItemQuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.disposeAll();
		}
	}

	/**
	 * Action listener of the save button of the main window
	 */
	class ItemSaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(model.getCurrentPath() == null)
					saveModelAs();
				else
					model.save();
			} catch (ModelException e1) {
				view.createErrorWindow(e1.getMessage());
			}
		}
	}

	/**
	 * Action listener of the save as button of the main window
	 */
	class ItemSaveAsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			saveModelAs();
		}
	}

	/**
	 * Helper to save the model in a file
	 */
	private void saveModelAs() {
		view.getFileChooserWindow().setSaveDialog();
		view.showFileChooserWindow("Sauvegarder le KeyStore sous... ");
		state = State.StateSAVING;
	}
	
	/**
	 * Change listener for the key/certificate tab in the main window
	 */
	class ChangeTabListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			refreshDetails();
		}
	}
	
	/**
	 * Action listener for the import button in the main window
	 */
	class BtnImportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.showImportKeyWindow();
			state = State.StateIMPORTING;
		}
	}

	/**
	 * Action listener for the export button of the main window
	 */
	class BtnExportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.showFileChooserWindow("Exporter l'élement sous...");
			state = State.StateEXPORTING;
		}
	}
	
	/**
	 * Action listener for the delete button in the main window
	 */
	class BtnDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(view.getMainWindow().isKeysTabSelected()) {
					String alias = view.getMainWindow().getSelectedKey();
					model.delEntry(alias);					
				} else if(view.getMainWindow().isCertificatesTabSelected()) {
					String alias = view.getMainWindow().getSelectedCertificate();
					model.delEntry(alias);				
				}
			}
			catch(ModelException e1) {
				view.createErrorWindow(e1.getMessage());
			}
			refreshLists();
			refreshDetails();
		}
	}
	
	/**
	 * Action listener for the new button in the main window
	 */
	class BtnNewKeyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.showCreateKeyWindow();
			state = State.StateCREATINGKEY;
		}
	}
	
	/**
	 * List selection listener for the key list of the main window
	 */
	class ListKeysListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent arg0) {
			refreshDetails();
		}
	}

	/**
	 * List selection listener for the certificate list in the main window
	 */
	class ListCertificatesListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent arg0) {
			refreshDetails();
		}
	}
	
	/* FileChooserWindow */
	/**
	 * Initialize the File chooser window listener
	 */
	private void initFCWindowListener() {
		this.view.getFileChooserWindow().addFCActionListener(new FCActionListener());
		this.view.getFileChooserWindow().addFCWindowListener(new FCWindowListener());
	}

	/**
	 * Action listener for the file chooser
	 */
	class FCActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
				switch(state) {
				case StateOPENING:
					File file = new File(view.getFileChooserWindow().getPath());
					if(!file.exists()) {
						try {
							throw new ModelException("Le fichier "+file.getPath()+" n'existe pas !");
						} catch (ModelException e1) {
							view.createErrorWindow(e1.getMessage());
						}
					} else {
						view.hideFileChooserWindow();
						view.showPasswordWindow();
						state = State.StatePICKPASSWORD;
					}
					break;
				case StateSAVING:
					try {
						view.hideFileChooserWindow();
						model.saveAs(view.getFileChooserWindow().getPath());
						refreshMainWindow();
					} catch (ModelException e1) {
						view.createErrorWindow(e1.getMessage());
					}
					break;
				case StateEXPORTING:
					try {
						if(view.getMainWindow().isKeysTabSelected()) {
							String alias = view.getMainWindow().getSelectedKey();
							if(! alias.equals(""))
								model.getKey(alias).exportTo(view.getFileChooserWindow().getPath());
								
						} else if(view.getMainWindow().isCertificatesTabSelected()) {
							String alias = view.getMainWindow().getSelectedCertificate();
							if(! alias.equals(""))
								model.getCertificate(alias).exportTo(view.getFileChooserWindow().getPath());
							
						}
					} catch (ModelException e1) {
						view.createErrorWindow(e1.getMessage());
					}
					view.hideFileChooserWindow();
					break;
				case StateCHOOSINGKEY:
					view.getImportKeyWindow().setKeyFileField(view.getFileChooserWindow().getPath());
					view.hideFileChooserWindow();
					state = State.StateIMPORTING;
					break;
				case StateCHOOSINGCERTIFICATE:
					view.getImportKeyWindow().setCertificateFileField(view.getFileChooserWindow().getPath());
					view.hideFileChooserWindow();
					state = State.StateIMPORTING;
					break;
				}
			} else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
				switch(state) {
				case StateEXPORTING:
				case StateOPENING:
				case StateSAVING:
					view.hideFileChooserWindow();
					state = State.StateWAIT;
					break;
				case StateCHOOSINGKEY:
				case StateCHOOSINGCERTIFICATE:
					view.hideFileChooserWindow();
					state = State.StateIMPORTING;
					break;
				}
			}
		}
	}

	/**
	 * Window adaptater for the file chooser window
	 */
	class FCWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			switch(state) {
			case StateEXPORTING:
			case StateOPENING:
			case StateSAVING:
				view.hideFileChooserWindow();
				state = State.StateWAIT;
				break;
			case StateCHOOSINGKEY:
			case StateCHOOSINGCERTIFICATE:
				view.hideFileChooserWindow();
				state = State.StateIMPORTING;
				break;
			}
		}
	}
	
	/* CreateKeyWindow */
	/**
	 * Initialize the create key window listener
	 */
	private void initCreateKeyWindowListener() {
		this.view.getCreateKeyWindow().addBtnCancelListener(new CKWBtnCancelListener());
		this.view.getCreateKeyWindow().addBtnValidateListener(new CKWBtnValidateListener());
		this.view.getCreateKeyWindow().addCWWindowListener(new CKWWindowListener());
	}
	
	/**
	 * Action listener for the cancel button of the create key window
	 */
	class CKWBtnCancelListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			view.getCreateKeyWindow().resetField();
			view.hideCreateKeyWindow();
		}
	}
	
	/**
	 * Action listener for the validate button of the create key window
	 */
	class CKWBtnValidateListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				if(view.getCreateKeyWindow().getAliasField().equals(""))
					throw new ModelException("L'alias ne doit pas être vide !");
				
				StringBuilder subject = new StringBuilder();
				subject.append("CN="+view.getCreateKeyWindow().getNameField());
				subject.append(", ");
				subject.append("OU="+view.getCreateKeyWindow().getOUField());
				subject.append(", ");
				subject.append("O="+view.getCreateKeyWindow().getOrgField());
				subject.append(", ");
				subject.append("L="+view.getCreateKeyWindow().getCityField());
				subject.append(", ");
				subject.append("ST="+view.getCreateKeyWindow().getStateField());
				subject.append(", ");
				subject.append("C="+view.getCreateKeyWindow().getCountryField());

				MTPrivateKey key = new MTPrivateKey(subject.toString());
				key.addToKeyStore(model, view.getCreateKeyWindow().getAliasField());
				view.hideCreateKeyWindow();
				view.getCreateKeyWindow().resetField();
				refreshLists();
			} catch (ModelException e) {
				view.createErrorWindow(e.getMessage());
			}
		}
	}
	
	/**
	 * Window adapter for the create key window
	 */
	class CKWWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			view.getCreateKeyWindow().resetField();
			view.hideCreateKeyWindow();
		}
	}
	
	/* ImportKeyWindow */
	/**
	 * Initialize the import key window listener
	 */
	private void initImportKeyWindowListener() {
		this.view.getImportKeyWindow().addBtnChooseKeyListener(new IKWBtnChooseKeyListener());
		this.view.getImportKeyWindow().addBtnChooseCertificateListener(new IKWBtnChooseCertificateListener());
		this.view.getImportKeyWindow().addBtnCancelListener(new IKWBtnCancelListener());
		this.view.getImportKeyWindow().addBtnValidateListener(new IKWBtnValidateListener());
		this.view.getImportKeyWindow().addIWWindowListener(new IKWWindowListener());
	}
	
	/**
	 * Action listener for the choose button of the import key window
	 */
	class IKWBtnChooseKeyListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			view.showFileChooserWindow("Importer la clé...");
			state = State.StateCHOOSINGKEY;
		}
	}
	
	/**
	 * Action listener for the choose certificate button of the import key window
	 */
	class IKWBtnChooseCertificateListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			view.showFileChooserWindow("Importer le certificat...");
			state = State.StateCHOOSINGCERTIFICATE;
		}
	}
	
	/**
	 * Action listener for the cancel button of the import key window
	 */
	class IKWBtnCancelListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			view.hideImportKeyWindow();
			view.getImportKeyWindow().resetField();
			state = State.StateWAIT;
		}
	}
	
	/**
	 * Action listener for the validate button of the import key window
	 */
	class IKWBtnValidateListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				String certpath = view.getImportKeyWindow().getCertificateFileField();
				String keypath = view.getImportKeyWindow().getKeyFileField();
				String alias = view.getImportKeyWindow().getAliasField();
				if(alias.isEmpty())
					throw new ModelException("L'alias est obligatoire");
				
				if(keypath.isEmpty()) {
					// Import a certificate
					MTCertificate certificate;
					try {
						certificate = new MTCertificate(new FileInputStream(certpath));
					} catch (FileNotFoundException e) {
						throw new ModelException("Fichier non trouvé :"+certpath);
					}
					certificate.addToKeyStore(model, alias);
				} else {
					// Import a PrivateKey
					MTPrivateKey key = new MTPrivateKey(keypath, certpath);
					key.addToKeyStore(model, alias);
				}
				view.hideImportKeyWindow();
				view.getImportKeyWindow().resetField();
				refreshLists();
			} catch (ModelException e) {
				view.createErrorWindow(e.getMessage());
			}
		}
	}
	
	/**
	 * Window listener for the import key window
	 */
	class IKWWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			view.hideImportKeyWindow();
			view.getImportKeyWindow().resetField();
			state = State.StateWAIT;
		}
	}
	
	/* PasswordWindow */
	/**
	 * Initialize the password window listener
	 */
	private void initPasswordWindowListener() {
		this.view.getPasswordWindow().addCWWindowListener(new PWCWindowListener());
		this.view.getPasswordWindow().addBtnCancelListener(new PWBtnCancelListener());
		this.view.getPasswordWindow().addBtnValidateListener(new PWBtnValidateListener());
		this.view.getPasswordWindow().addKeyboardListener(new PWKeyListener());
	}
	
	/**
	 * Action listener for the cancel button of the password window
	 */
	class PWBtnCancelListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			view.hidePasswordWindow();
			view.resetPasswordWindow();
			state = State.StateWAIT;
		}
	}
	
	/**
	 * Action listener for the validate button of the password window
	 */
	class PWBtnValidateListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			validatePassword();
		}
	}
	
	/**
	 * helper to validate the password given by the user, and display an error message if wrong
	 */
	private void validatePassword() {
		switch(state) {
		case StatePICKPASSWORD:
			try {
				model.openKeyStore(view.getFileChooserWindow().getPath(), view.getPasswordWindow().getPasswordField());
				view.hidePasswordWindow();
				state = State.StateWAIT;

			} catch (ModelException e) {
				// Erreur de mot de passe : pas de changement d'etat
				view.createErrorWindow(e.getMessage());
			} finally {
				refreshMainWindow();
				view.resetPasswordWindow();

			}
			
			break;
		case StateCHOOSINGPASSWORDKEYSTORE:
			try {
				view.hidePasswordWindow();
				model.newKeyStore(view.getPasswordWindow().getPasswordField());
				state = State.StateWAIT;
				refreshMainWindow();
			} catch (ModelException e) {
				// Erreur dans la création du keystore
				view.createErrorWindow(e.getMessage());
			}
			break;
		}
	}
	
	/**
	 * Window listener for the password window
	 */
	class PWCWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			view.hidePasswordWindow();
			view.resetPasswordWindow();
			state = State.StateWAIT;
		}
	}
	
	/**
	 * Key listener for the keyboard input in the password window
	 */
	class PWKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				validatePassword();
			}
			
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
		
	}
}