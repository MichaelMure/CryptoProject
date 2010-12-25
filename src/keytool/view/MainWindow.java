package keytool.view;

import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
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

import keytool.model.Model;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 2831115647099397913L;
	
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
    
	public MainWindow(Model model){
		super("View");
		this.initComponent();
		this.setLocation(250,250);
	}

    private void initComponent() {
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
        
        /* Liste Certificats */
        ScrollCertificatPanel.setViewportView(ListCertificates);
        
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

    public void setKeysList(DefaultListModel keysList) {
    	this.ListKeys.setModel(keysList);
    }
    
    public void setCertificatesList(DefaultListModel certList) {
    	this.ListCertificates.setModel(certList);
    }
}