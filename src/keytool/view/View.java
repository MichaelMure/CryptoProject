package keytool.view;

import keytool.mvc.Model;

public class View {
	
	private MainWindow mainWindow;
	
	public View(Model model) {
		this.mainWindow = new MainWindow(model);
		this.mainWindow.setVisible(true);
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

}
