package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * This class holds the file chooser window.
 * @author michael
 *
 */
public class FileChooserWindow extends JFrame {

	private static final long serialVersionUID = -7175771389359615082L;

	private JFileChooser FileChooser;
	
	/** 
	 * FileChooserWindow constructor
	 */
	public FileChooserWindow() {
		initComponents();
	}
	
	/**
	 * Initialize the window's component
	 */
    private void initComponents() {

        FileChooser = new JFileChooser();

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(FileChooser, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(FileChooser, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
        );

        pack();
    }


    /**
     * Add an action listener to the file chooser
     * @param actLst
     */
    public void addFCActionListener(ActionListener actLst) {
    	this.FileChooser.addActionListener(actLst);
	}
    
    /**
     * Add a window listener to the file chooser window
     * @param wdLst
     */
    public void addFCWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
    
    /**
     * Set the file chooser in saving mode, to display "Save" in the button
     */
    public void setSaveDialog() {
    	this.FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    }
    
    /**
     * Set the file chooser in open mode, to display "Open" in the button
     */
    public void setOpenDialog() {
    	this.FileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    }
    
    /**
     * 
     * @return the path of the file.
     */
    public String getPath() {
    	return this.FileChooser.getSelectedFile().getPath();
    }
}
