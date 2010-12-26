package keytool.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import keytool.model.Model;

public class View {
	
	private MainWindow mainWindow;
	private FileChooserWindow fileOpenWindow;
	private CreateKeyWindow createKeyWindow;
	private ImportKeyWindow importKeyWindow;
	
	public View(Model model) {
		this.mainWindow = new MainWindow(model);
		this.mainWindow.setVisible(true);
		
		this.fileOpenWindow = new FileChooserWindow();
		this.createKeyWindow = new CreateKeyWindow();
		this.importKeyWindow = new ImportKeyWindow();
		this.importKeyWindow.setVisible(true);
	}

	public void disposeAll() {
		this.fileOpenWindow.dispose();
		this.mainWindow.dispose();
		this.createKeyWindow.dispose();
		this.importKeyWindow.dispose();
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
	public FileChooserWindow getFileOpenWindow() {
		return this.fileOpenWindow;
	}
	
	public void showFileOpenWindow() {
		this.fileOpenWindow.setVisible(true);
	}

	public void hideFileOpenWindow() {
		this.fileOpenWindow.setVisible(false);
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
}
