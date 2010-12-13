package mvc;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
 
public class Controller {
  private View view;
  private Model model;
 
  Controller(Model model, View view){
    this.view = view;
    view.addstrStpBtnListener(new StrStpBtnListener());
    view.addSetPrdBtnListener(new SetPrdBtnListener());
    view.addClosingListener(new ClosingListener());
    view.setScnFld(0);
    view.setPrdFld(Model.DEFAULT_PERIOD);
 
    this.model = model;
  }
 
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
      int period = view.getPrdFld();
    }
  }
 
  class SecondsObserver implements Observer {
    public void update(Observable obs, Object arg) {
    }
  }
 
  class QuestionObserver implements Observer {
    public void update(Observable obs, Object arg) {
    }
  }
}