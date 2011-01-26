package keytool.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import keytool.model.Model;

/**
 * This class is the view part of the MVC pattern
 * This class holds a copy of each window used in the application, and abstract access to each for the controller.
 * @author michael
 *
 */
public class View {
	
	private MainWindow mainWindow;
	private FileChooserWindow fileChooserWindow;
	private CreateKeyWindow createKeyWindow;
	private ImportKeyWindow importKeyWindow;
	private PasswordWindow passwordWindow;
	
	/**
	 * Constructor of the view 
	 * @param model
	 */
	public View(Model model) {
		this.mainWindow = new MainWindow(model);
		this.mainWindow.setVisible(true);
		
		this.fileChooserWindow = new FileChooserWindow();
		this.createKeyWindow = new CreateKeyWindow();
		this.importKeyWindow = new ImportKeyWindow();
		this.passwordWindow = new PasswordWindow();
	}

	/**
	 * Delete all the window
	 */
	public void disposeAll() {
		this.fileChooserWindow.dispose();
		this.mainWindow.dispose();
		this.createKeyWindow.dispose();
		this.importKeyWindow.dispose();
		this.passwordWindow.dispose();
	}
	
	/* MainWindow */
	/**
	 * Return the main window
	 */
	public MainWindow getMainWindow() {
		return this.mainWindow;
	}
	
	/**
	 * Display the main window
	 */
	public void showMainWindow() {
		this.mainWindow.setVisible(true);
	}

	/**
	 * Hide the main window
	 */
	public void hideMainWindow() {
		this.mainWindow.setVisible(false);
	}

	/* FileChooserWindow */
	/**
	 * Return the file chooser window
	 */
	public FileChooserWindow getFileChooserWindow() {
		return this.fileChooserWindow;
	}
	
	/**
	 * Display the file chooser window
	 */
	public void showFileChooserWindow() {
		this.fileChooserWindow.setVisible(true);
	}

	/**
	 * Display the file chooser window and set his title.
	 * @param name
	 */
	public void showFileChooserWindow(String name) {
		this.fileChooserWindow.setTitle(name);
		this.showFileChooserWindow();
	}
	
	/**
	 * Hide the file chooser window
	 */
	public void hideFileChooserWindow() {
		this.fileChooserWindow.setVisible(false);
	}
	
	/* ErrorWindow */
	/**
	 * Create an error window
	 */
	public void createErrorWindow(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
		        JOptionPane.ERROR_MESSAGE);
	}
	
	/* CreateKeyWindow */
	/**
	 * Return the create key window
	 */
	public CreateKeyWindow getCreateKeyWindow() {
		return this.createKeyWindow;
	}
	
	/**
	 * Display the create key window
	 */
	public void showCreateKeyWindow() {
		this.createKeyWindow.setVisible(true);
	}
	
	/**
	 * Reset the fields of the create key window
	 */
	public void resetCreateKeyWindow() {
		this.createKeyWindow.resetField();
	}
	
	/**
	 * Hide the create key window
	 */
	public void hideCreateKeyWindow() {
		this.createKeyWindow.setVisible(false);
	}
	
	/* ImportKeyWindow */
	/**
	 * Return the import key window
	 */
	public ImportKeyWindow getImportKeyWindow() {
		return this.importKeyWindow;
	}
	
	/**
	 * Display the import key window
	 */
	public void showImportKeyWindow() {
		this.importKeyWindow.setVisible(true);
	}
	
	/**
	 * Hide the import key window
	 */
	public void hideImportKeyWindow() {
		this.importKeyWindow.setVisible(false);
	}
	
	/**
	 * Reset fields in the import key window
	 */
	public void resetImportKeyWindow() {
		this.importKeyWindow.resetField();
	}
	
	/* PasswordWindow */
	/**
	 * Return the password window
	 */
	public PasswordWindow getPasswordWindow() {
		return this.passwordWindow;
	}
	
	/**
	 * Display the password window
	 */
	public void showPasswordWindow() {
		this.passwordWindow.setVisible(true);
	}
	
	/**
	 * Hide the password window
	 */
	public void hidePasswordWindow() {
		this.passwordWindow.setVisible(false);
	}
	
	/**
	 * Reset fields in the password window
	 */
	public void resetPasswordWindow() {
		this.passwordWindow.resetField();
	}

}
