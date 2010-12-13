package mvc;
 
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
 
public class View extends JFrame {
  private JTextField scnFld = new JTextField(5);
  private JTextField prdFld = new JTextField(5);
  private JButton setPrdBtn = new JButton("Set");
  private JButton strStpBtn = new JButton("Start/Stop");
 
  public View(){
    super("View");
 
    // Container
    Container container = this.getContentPane();
 
    // Layout
    GridBagLayout gbl = new GridBagLayout();
    container.setLayout(gbl);
    GridBagConstraints format = new GridBagConstraints();
 
    //(0,0) Seconds Label
    JLabel scnLbl = new JLabel("Seconds");
    format = new GridBagConstraints(0,0, 1,1, 0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
    gbl.setConstraints(scnLbl, format);
    container.add(scnLbl);
 
    //(1,0) Seconds Filed
    scnFld.setEditable(false);
    format = new GridBagConstraints(1,0, 1,1, 0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
    gbl.setConstraints(scnFld, format);
    container.add(scnFld);
 
    //(2,0) Start/Stop Button
    format = new GridBagConstraints(2,0, 1,1, 0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
    gbl.setConstraints(strStpBtn, format);
    container.add(strStpBtn);
 
    //(0,1) Period Label
    JLabel prdLbl = new JLabel("Period");
    format = new GridBagConstraints(0,1, 1,1, 0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
    gbl.setConstraints(prdLbl, format);
    container.add(prdLbl);
 
    //(1,1) Period Field
    format = new GridBagConstraints(1,1, 1,1, 0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
    gbl.setConstraints(prdFld, format);
    container.add(prdFld);
 
    //(2,1) Set Period Button
    format = new GridBagConstraints(2,1, 1,1, 0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
    gbl.setConstraints(setPrdBtn, format);
    container.add(setPrdBtn);
 
    // Show frame
    this.pack();
    this.setResizable(false);
    this.setLocation(250,250);
    this.setVisible(true);
  }
 
  // Closing Window Listener
  public void addClosingListener(WindowAdapter wa){
    this.addWindowListener(wa);
  }
 
  // Start/Stop Button Listener
  public void addstrStpBtnListener(ActionListener actLst) {
    strStpBtn.addActionListener(actLst);
  }
 
  // Set Period Button Listener
  public void addSetPrdBtnListener(ActionListener actLst) {
    setPrdBtn.addActionListener(actLst);
  }
 
  // Set seconds Field
  public void setScnFld(int sec){
    scnFld.setText((new Integer(sec)).toString());
  }
 
  // Set seconds Field
  public void setPrdFld(int period){
      prdFld.setText((new Integer(period)).toString());
  }
 
  // Get period Field 
  public int getPrdFld() {
    return Integer.parseInt(prdFld.getText());
  }
 
  // Question
  public boolean question(){
    int confirmation = JOptionPane.showConfirmDialog(null, "Restart ?", "Information", JOptionPane.YES_NO_OPTION);
    if (confirmation == 0) return true;
    if (confirmation == 1) return false;
 
    return false;
  }
}