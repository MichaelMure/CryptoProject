package mvc;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class Controller {
  protected View view;
  protected Model model;
 
  Controller(Model model, View view){
    this.view = view;
    this.view.addItemQuitListener(new ItemQuitListener());
    this.view.addItemOpenListener(new ItemOpenListener());
    this.view.addBtnImportListener(new BtnImportListener());
    this.view.addBtnExportListener(new BtnExportListener());
    
    this.model = model;
  }
 
  class ItemQuitListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	view.dispose();
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
  
  /*
  class ClosingListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  }
 
  class QuestionObserver implements Observer {
    public void update(Observable obs, Object arg) {
    }
  }*/
}