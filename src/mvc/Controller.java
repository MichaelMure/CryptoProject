package mvc;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class Controller {
  protected View view;
  protected Model model;
 
  Controller(Model model, View view){
    this.view = view;
    this.view.addItemQuitListener(new ItemQuitListener());
 
    this.model = model;
  }
 
  class ItemQuitListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	view.dispose();
    }
  }
  
  /*
  class ClosingListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  }
 
  class StrStpBtnListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    }
  }
 
  class SetPrdBtnListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      //int period = view.getPrdFld();
    }
  }
 
  class SecondsObserver implements Observer {
    public void update(Observable obs, Object arg) {
    }
  }
 
  class QuestionObserver implements Observer {
    public void update(Observable obs, Object arg) {
    }
  }*/
}