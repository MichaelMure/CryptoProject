package keytool.mvc;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class View extends JFrame {
    private JButton BtnExport;
    private JButton BtnImport;
    private JMenuItem ItemOpen;
    private JMenuItem ItemQuit;
    private JLabel LablDetails;
    private JList ListCertificates;
    private JList ListKeys;
    private JMenuBar Menu;
    private JMenu MenuKeytool;
    private JScrollPane ScrollCertificatPanel;
    private JScrollPane ScrollKeyPanel;
    private JSplitPane SplitPanel;
    private JTabbedPane TabbedPanel;

    private Model model;
    
	public View(Model model){
		super("View");
		this.model = model;
		mainWindow();
		this.setLocation(250,250);
		this.setVisible(true);
	}

    private void mainWindow() {
        this.BtnImport = new JButton();
        this.BtnExport = new JButton();
        this.SplitPanel = new JSplitPane();
        this.TabbedPanel = new JTabbedPane();
        this.ScrollKeyPanel = new JScrollPane();
        this.ListKeys = new JList();
        this.ScrollCertificatPanel = new JScrollPane();
        this.ListCertificates = new JList();
        this.LablDetails = new JLabel();
        this.Menu = new JMenuBar();
        this.MenuKeytool = new JMenu();
        this.ItemOpen = new JMenuItem();
        this.ItemQuit = new JMenuItem();
        
        /* Fenetre */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("KeyTool");

        /* Bouton */
        BtnImport.setText("Importer"); 
        BtnExport.setText("Exporter");

        /* Liste Clés */
        ScrollKeyPanel.setViewportView(ListKeys);
        this.refreshKeys();
        
        /* Liste Certificats */
        ScrollCertificatPanel.setViewportView(ListCertificates);
        this.refreshCertificates();
        
        /* TabbedPanel */
        TabbedPanel.addTab("Clés", ScrollKeyPanel);
        TabbedPanel.addTab("Certificats", ScrollCertificatPanel);

        /* SplitPanel */
        SplitPanel.setDividerLocation(300);
        SplitPanel.setContinuousLayout(true);
        SplitPanel.setLeftComponent(TabbedPanel);
        SplitPanel.setRightComponent(LablDetails);

        /* Menu */
        MenuKeytool.setText("Keytool");

        /* Menu Ouvrir */
        ItemOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        ItemOpen.setText("Ouvrir");
        //ItemOpen.setActionCommand(resourceMap.getString("ItemOpen.actionCommand")); // NOI18N
        MenuKeytool.add(ItemOpen);

        /* Menu Fermer */
        ItemQuit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        ItemQuit.setText("Quitter");
        MenuKeytool.add(ItemQuit);

        Menu.add(MenuKeytool);
        setJMenuBar(Menu);

        /* Layout */
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BtnImport)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnExport)
                .addContainerGap(451, Short.MAX_VALUE))
            .addComponent(SplitPanel, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(SplitPanel, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnImport)
                    .addComponent(BtnExport)))
        );

        pack();
    }

    public void addItemQuitListener(ActionListener actLst) {
    	ItemQuit.addActionListener(actLst);
	}

    public void addItemOpenListener(ActionListener actLst) {
    	ItemOpen.addActionListener(actLst);
	}

    public void addBtnImportListener(ActionListener actLst) {
    	BtnImport.addActionListener(actLst);
	}

    public void addBtnExportListener(ActionListener actLst) {
    	BtnExport.addActionListener(actLst);
	}

    public void refreshKeys() {
        this.ListKeys.setModel(this.model.getKeys());
    }
    
    public void refreshCertificates() {
    	this.ListCertificates.setModel(this.model.getCertificates());
    }
    
	/*
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
	*/

}