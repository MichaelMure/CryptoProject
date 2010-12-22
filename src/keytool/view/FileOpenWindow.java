package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileOpenWindow extends JFrame {

	private static final long serialVersionUID = -7175771389359615082L;

	private JFileChooser FileChooser;
	
	public FileOpenWindow() {
		initComponents();
	}
	
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


    public void addFOActionListener(ActionListener actLst) {
    	this.FileChooser.addActionListener(actLst);
	}
    
    public void addFOWindowListener(WindowListener wdLst) {
    	this.addWindowListener(wdLst);
	}
}
