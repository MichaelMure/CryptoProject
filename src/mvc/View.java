package mvc;

import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
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
    private JList ListCertificats;
    private JList ListKeys;
    private JMenuBar Menu;
    private JMenu MenuKeytool;
    private JScrollPane ScrollCertificatPanel;
    private JScrollPane ScrollKeyPanel;
    private JSplitPane SplitPanel;
    private JTabbedPane TabbedPanel;

	public View(){
		super("View");
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
        this.ListCertificats = new JList();
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
        ListKeys.setModel(new AbstractListModel() {
            String[] strings = { "Clé 1", "Clé 2", "Clé 3", "Clé 4", "Clé 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        ScrollKeyPanel.setViewportView(ListKeys);

        /* Liste Certificats */
        ListCertificats.setModel(new AbstractListModel() {
            String[] strings = { "Cert 1", "Cert 2", "Cert 3", "Cert 4", "Cert 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        ScrollCertificatPanel.setViewportView(ListCertificats);

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