package keytool.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import keytool.model.Model;

public class View {
	
	private MainWindow mainWindow;
	private FileChooserWindow fileOpenWindow;
	
	public View(Model model) {
		this.mainWindow = new MainWindow(model);
		this.mainWindow.setVisible(true);
		
		this.fileOpenWindow = new FileChooserWindow();
		this.fileOpenWindow.setVisible(false);
	}

	public void disposeAll() {
		this.fileOpenWindow.dispose();
		this.mainWindow.dispose();
	}
	
	public MainWindow getMainWindow() {
		return this.mainWindow;
	}
	
	public void showMainWindow() {
		this.mainWindow.setVisible(true);
	}

	public void hideMainWindow() {
		this.mainWindow.setVisible(false);
	}

	public FileChooserWindow getFileOpenWindow() {
		return this.fileOpenWindow;
	}
	
	public void showFileOpenWindow() {
		this.fileOpenWindow.setVisible(true);
	}

	public void hideFileOpenWindow() {
		this.fileOpenWindow.setVisible(false);
	}
	
	public void createErrorWindow(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Erreur",
		        JOptionPane.ERROR_MESSAGE);
	}
}
