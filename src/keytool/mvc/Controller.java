package keytool.mvc;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import keytool.view.MainWindow;
import keytool.view.View;
 
public class Controller {
  protected View view;
  protected Model model;
 
  Controller(Model model, View view){
    this.view = view;
    MainWindow mw = this.view.getMainWindow();
    mw.addItemQuitListener(new ItemQuitListener());
    mw.addItemOpenListener(new ItemOpenListener());
    mw.addBtnImportListener(new BtnImportListener());
    mw.addBtnExportListener(new BtnExportListener());
    
    this.model = model;
  }
 
  class ItemQuitListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	view.getMainWindow().dispose();
    }
  }
  
  class ItemOpenListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

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
  
}