package keytool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.security.cert.Certificate;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JFrameListKeyStore extends KeytoolView implements ActionListener, FocusListener, ChangeListener {
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
    private JFrame frame;
    
    private DefaultListModel ListKeysModel;
    private DefaultListModel ListCertificateModel;
    
	public JFrameListKeyStore(KeytoolController controller, KeyStore keystore) {
		super(controller);
		
		buildFrame(keystore);
	}
	
	private void buildFrame(KeyStore keystore) {
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
        this.frame = new JFrame();
        
        /* Fenetre */
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("KeyTool");

        /* Bouton */
        BtnImport.setText("Importer");
        BtnImport.setActionCommand("import");
        BtnImport.addActionListener(this);
        BtnExport.setText("Exporter");

        /* Liste Clés */
        ListKeysModel = new DefaultListModel();
        ListKeys.setModel(ListKeysModel);
        ListKeys.addFocusListener(this);
        ScrollKeyPanel.setViewportView(ListKeys);

        /* Liste Certificats */
        ListCertificateModel = new DefaultListModel();
        ListCertificats.setModel(ListCertificateModel);
        ScrollCertificatPanel.setViewportView(ListCertificats);
        
        /* TabbedPanel */
        TabbedPanel.addTab("Clés", ScrollKeyPanel);
        TabbedPanel.addTab("Certificats", ScrollCertificatPanel);
        TabbedPanel.addChangeListener(this);
        
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
        frame.setJMenuBar(Menu);

        /* Layout */
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
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

        frame.pack();
		
	}

	public void close() {
		frame.dispose();
	}

	public void display() {
		frame.setVisible(true);
	}

	public void refreshKeys() {
		ListKeysModel.clear();
		ArrayList<String> keys = getController().getKeys();
		for(int i = 0; i < keys.size(); i++) {
			ListKeysModel.addElement(keys.get(i));
		}
	}
	
	public void refreshCertificates() {
		ListCertificateModel.clear();
		ArrayList<String> certs = getController().getCertificates();
		for(int i = 0; i < certs.size(); i++) {
			ListKeysModel.addElement(certs.get(i));
		}
		ListCertificateModel.addElement("Cert1");
		ListCertificateModel.addElement("Cert2");
		ListCertificateModel.addElement("Cert3");
	}
	
	public void refreshDetails(Key key) {
		StringBuilder sb = new StringBuilder();
		sb.append("Algorithm: ");
		sb.append(key.getAlgorithm());
		sb.append("\n Format: ");
		sb.append(key.getFormat());

		LablDetails.setText(sb.toString());
	}
	
	public void keystoreChanged(KeyStoreChangedEvent event) {
		refreshKeys();
	}
	
	public void keySelected(KeySelectedEvent event) {
		refreshDetails(event.getSelectedKey());
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("import")) {
			getController().notifyKeyStoreChanged(null);
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		getController().notifyElementSelected(e);
	}

	@Override
	public void focusLost(FocusEvent e) {
	
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		getController().notifyTabChanged(e);
	}

	@Override
	public void selectedTabChanged(TabChangedEvent event) {
		if(Integer.valueOf(event.getSelectedTab()).equals(0)) {
			refreshKeys();
		} else {
			refreshCertificates();
		}
	}
}