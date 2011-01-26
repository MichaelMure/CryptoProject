package keytool.view;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

import keytool.model.Model;

/**
 * This class hold the main window of this software
 * @author michael
 *
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 2831115647099397913L;
	
	private JButton BtnExport;
    private JButton BtnImport;
    private JButton BtnDelete;
    private JButton BtnNewKey;
    private JMenuItem ItemNewKeyStore;
    private JMenuItem ItemOpen;
    private JMenuItem ItemSave;
    private JMenuItem ItemSaveAs;
    private JMenuItem ItemQuit;
    private JTextArea TxtDetails;
    private JList ListCertificates;
    private JList ListKeys;
    private JMenuBar Menu;
    private JMenu MenuKeytool;
    private JScrollPane ScrollCertificatPanel;
    private JScrollPane ScrollKeyPanel;
    private JSplitPane SplitPanel;
    private JTabbedPane TabbedPanel;

    /**
     * Constructor of the main window
     * @param model
     */
	public MainWindow(Model model){
		super("View");
		this.initComponent();
		this.setLocation(250,250);
	}

	/**
	 * Create and initialize the main window components
	 */
    private void initComponent() {
        this.BtnImport = new JButton();
        this.BtnExport = new JButton();
        this.BtnDelete = new JButton();
        this.BtnNewKey = new JButton();
        this.SplitPanel = new JSplitPane();
        this.TabbedPanel = new JTabbedPane();
        this.ScrollKeyPanel = new JScrollPane();
        this.ListKeys = new JList();
        this.ScrollCertificatPanel = new JScrollPane();
        this.ListCertificates = new JList();
        this.TxtDetails = new JTextArea();
        this.Menu = new JMenuBar();
        this.MenuKeytool = new JMenu();
        this.ItemNewKeyStore = new JMenuItem();
        this.ItemOpen = new JMenuItem();
        this.ItemSave = new JMenuItem();
        this.ItemSaveAs = new JMenuItem();
        this.ItemQuit = new JMenuItem();
        
        /* Fenetre */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("KeyTool");

        /* Bouton */
        BtnImport.setText("Importer"); 
        BtnExport.setText("Exporter");
        BtnDelete.setText("Supprimer");
        BtnNewKey.setText("Créer une nouvelle clé");

        /* Liste Clés */
        ScrollKeyPanel.setViewportView(ListKeys);
        ListKeys.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        /* Liste Certificats */
        ScrollCertificatPanel.setViewportView(ListCertificates);
        ListCertificates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        /* TabbedPanel */
        TabbedPanel.addTab("Clés", ScrollKeyPanel);
        TabbedPanel.addTab("Certificats", ScrollCertificatPanel);
        
        /* TxtDetails */
        TxtDetails.setEditable(false);
        
        /* SplitPanel */
        SplitPanel.setDividerLocation(300);
        SplitPanel.setContinuousLayout(true);
        SplitPanel.setLeftComponent(TabbedPanel);
        SplitPanel.setRightComponent(TxtDetails);

        /* Menu */
        MenuKeytool.setText("Keytool");

        /* Menu New KeyStore */
        ItemNewKeyStore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        ItemNewKeyStore.setText("Nouveau KeyStore");
        MenuKeytool.add(ItemNewKeyStore);

        /* Menu Open */
        ItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        ItemOpen.setText("Ouvrir");
        MenuKeytool.add(ItemOpen);

        /* Menu Save */
        ItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        ItemSave.setText("Sauvegarder");
        MenuKeytool.add(ItemSave);

        /* Menu SaveAs */
        ItemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        ItemSaveAs.setText("Sauvegarder sous ...");
        MenuKeytool.add(ItemSaveAs);
        
        /* Menu Fermer */
        ItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
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
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnDelete)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnNewKey)
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
                    .addComponent(BtnExport)
                    .addComponent(BtnDelete)
                    .addComponent(BtnNewKey)))
        );

        pack();
    }

    /**
     * Add an action listener to the quit item
     * @param actLst
     */
    public void addItemQuitListener(ActionListener actLst) {
    	ItemQuit.addActionListener(actLst);
	}

    /**
     * Add an action listener to the new key item
     * @param actLst
     */
    public void addItemNewKeyStoreListener(ActionListener actLst) {
    	ItemNewKeyStore.addActionListener(actLst);
	}
    
    /**
     * Add an action listener to the open item
     * @param actLst
     */
    public void addItemOpenListener(ActionListener actLst) {
    	ItemOpen.addActionListener(actLst);
	}

    /**
     * Add an action listener to the save item
     * @param actLst
     */
    public void addItemSaveListener(ActionListener actLst) {
    	ItemSave.addActionListener(actLst);
	}
    
    /**
     * Add an action listener to the save as item
     * @param actLst
     */
    public void addItemSaveAsListener(ActionListener actLst) {
    	ItemSaveAs.addActionListener(actLst);
	}
    
    /**
     * Add a change listener to the key/certificate tab
     * @param actLst
     */
    public void addChangeTabListener(ChangeListener actLst) {
    	TabbedPanel.addChangeListener(actLst);
    }
    
    /**
     * Add an action listener to the import button
     * @param actLst
     */
    public void addBtnImportListener(ActionListener actLst) {
    	BtnImport.addActionListener(actLst);
	}

    /**
     * Add an action listener to the export button
     * @param actLst
     */
    public void addBtnExportListener(ActionListener actLst) {
    	BtnExport.addActionListener(actLst);
	}
    
    /**
     * Add an action listener to the delete button
     * @param actLst
     */
    public void addBtnDeleteListener(ActionListener actLst) {
    	BtnDelete.addActionListener(actLst);
	}
    
    /**
     * Add an action listener to the new key button
     * @param actLst
     */
    public void addBtnNewKeyListener(ActionListener actLst) {
    	BtnNewKey.addActionListener(actLst);
	}

    /**
     * Add a list selection listener to the key list
     * @param LstSlctLst
     */
    public void addKeyListListener(ListSelectionListener LstSlctLst) {
    	this.ListKeys.addListSelectionListener(LstSlctLst);
    }
    
    /**
     * Add a certificate listener to the certificate list
     * @param LstSlctLst
     */
    public void addCertificatesListListener(ListSelectionListener LstSlctLst) {
    	this.ListCertificates.addListSelectionListener(LstSlctLst);
    }

    /**
     * Set the key list
     * @param keysList
     */
    public void setKeysList(DefaultListModel keysList) {
    	this.ListKeys.setModel(keysList);
    }
    
    /**
     * Set the certificate list
     * @param certList
     */
    public void setCertificatesList(DefaultListModel certList) {
    	this.ListCertificates.setModel(certList);
    }
    
    /**
     * Add a change listener to the key/certificate tab
     * @param actLst
     */
    public void addTabChangeListener(ChangeListener actLst) {
    	this.TabbedPanel.addChangeListener(actLst);
    }
    
    /**
     * 
     * @return whether or not the key tab is selected.
     */
    public boolean isKeysTabSelected() {
    	return (this.TabbedPanel.getSelectedIndex() == 0);
    }
    
    /**
     * 
     * @return whether or not the certificate tab is selected.
     */
    public boolean isCertificatesTabSelected() {
    	return (this.TabbedPanel.getSelectedIndex() == 1);
    }
    
    /**
     * 
     * @return the current selected key, or -1 if any.
     */
	public String getSelectedKey() {
		int index = this.ListKeys.getSelectedIndex();
		
		if(index == -1)
			return null;
		else
			return (String) this.ListKeys.getModel().getElementAt(index);
	}
	
	/**
	 * 
	 * @return the current selected certificate, or -1 if any.
	 */
	public String getSelectedCertificate() {
		int index = this.ListCertificates.getSelectedIndex();
		
		if(index == -1)
			return null;
		else
			return (String) this.ListCertificates.getModel().getElementAt(index);
	}
	
	/**
	 * Set the text of the details panel
	 * @param details
	 */
	public void setDetails(String details) {
		this.TxtDetails.setText(details);
	}

	/**
	 * Enable or not the button
	 * @param enable
	 */
	public void setEnabledFields(boolean enable) {
		this.BtnDelete.setEnabled(enable);
		this.BtnExport.setEnabled(enable);
		this.BtnImport.setEnabled(enable);
		this.BtnNewKey.setEnabled(enable);
		this.ItemSave.setEnabled(enable);
		this.ItemSaveAs.setEnabled(enable);
	}
	
	/**
	 * Set the window title
	 */
	public void setTitle(String path) {
		String append = "";
		if(path != null)
			append = " : "+path; 
		super.setTitle("KeyTool "+append);
	}
}