package keytool.mvc;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import keytool.view.MainWindow;
import keytool.view.View;
 
public class Controller {
  protected View view;
  protected Model model;
 
  Controller(Model model, View view){
    this.view = view;
    this.model = model;
    
    initMainWindowListener();
    initFOWindowListener();
  }
 
  private void initMainWindowListener() {
	MainWindow mw = this.view.getMainWindow();
	mw.addItemQuitListener(new ItemQuitListener());
	mw.addItemOpenListener(new ItemOpenListener());
	mw.addBtnImportListener(new BtnImportListener());
	mw.addBtnExportListener(new BtnExportListener());
  }
  
  class ItemQuitListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	view.getMainWindow().dispose();
    }
  }
  
  class ItemOpenListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	view.showFileOpenWindow();
    }
  }
  
  class BtnImportListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

    }
  }
  
  class BtnExportListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

    }
  }
  
  private void initFOWindowListener() {
	  this.view.getFileOpenWindow().addFOActionListener(new FOActionListener());
	  this.view.getFileOpenWindow().addFOWindowListener(new FOWindowListener());
  }
  
  class FOActionListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    	System.out.println(e);
	    }
  }
  
  class FOWindowListener extends WindowAdapter {
	    public void windowClosing(WindowEvent e) {
	    	view.hideFileOpenWindow();
	    }
	  }
  
}