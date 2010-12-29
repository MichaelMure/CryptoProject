package keytool.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import keytool.model.Model;

public class View {
	
	private MainWindow mainWindow;
	private FileChooserWindow fileChooserWindow;
	private CreateKeyWindow createKeyWindow;
	private ImportKeyWindow importKeyWindow;
	private PasswordWindow passwordWindow;
	
	public View(Model model) {
		this.mainWindow = new MainWindow(model);
		this.mainWindow.setVisible(true);
		
		this.fileChooserWindow = new FileChooserWindow();
		this.createKeyWindow = new CreateKeyWindow();
		this.importKeyWindow = new ImportKeyWindow();
		this.passwordWindow = new PasswordWindow();
	}

	public void disposeAll() {
		this.fileChooserWindow.dispose();
		this.mainWindow.dispose();
		this.createKeyWindow.dispose();
		this.importKeyWindow.dispose();
		this.passwordWindow.dispose();
	}
	
	/* MainWindow */
	public MainWindow getMainWindow() {
		return this.mainWindow;
	}
	
	public void showMainWindow() {
		this.mainWindow.setVisible(true);
	}

	public void hideMainWindow() {
		this.mainWindow.setVisible(false);
	}

	/* FileChooserWindow */
	public FileChooserWindow getFileChooserWindow() {
		return this.fileChooserWindow;
	}
	
	public void showFileChooserWindow() {
		this.fileChooserWindow.setVisible(true);
	}

	public void showFileChooserWindow(String name) {
		this.fileChooserWindow.setTitle(name);
		this.showFileChooserWindow();
	}
	
	public void hideFileChooserWindow() {
		this.fileChooserWindow.setVisible(false);
	}
	
	/* ErrorWindow */
	public void createErrorWindow(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
		        JOptionPane.ERROR_MESSAGE);
	}
	
	/* CreateKeyWindow */
	public CreateKeyWindow getCreateKeyWindow() {
		return this.createKeyWindow;
	}
	
	public void showCreateKeyWindow() {
		this.createKeyWindow.setVisible(true);
	}
	
	public void resetCreateKeyWindow() {
		this.createKeyWindow.resetField();
	}
	
	public void hideCreateKeyWindow() {
		this.createKeyWindow.setVisible(false);
	}
	
	/* ImportKeyWindow */
	public ImportKeyWindow getImportKeyWindow() {
		return this.importKeyWindow;
	}
	
	public void showImportKeyWindow() {
		this.importKeyWindow.setVisible(true);
	}
	
	public void hideImportKeyWindow() {
		this.importKeyWindow.setVisible(false);
	}
	
	public void resetImportKeyWindow() {
		this.importKeyWindow.resetField();
	}
	
	/* PasswordWindow */
	public PasswordWindow getPasswordWindow() {
		return this.passwordWindow;
	}
	
	public void showPasswordWindow() {
		this.passwordWindow.setVisible(true);
	}
	
	public void hidePasswordWindow() {
		this.passwordWindow.setVisible(false);
	}
	
	public void resetPasswordWindow() {
		this.passwordWindow.resetField();
	}
<<<<<<< HEAD

=======
>>>>>>> master
}
