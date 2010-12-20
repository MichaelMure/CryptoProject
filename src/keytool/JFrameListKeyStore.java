package keytool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

public class JFrameListKeyStore extends KeytoolView implements ActionListener, FocusListener {
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

	public void refreshKeys(KeyStore ks) {
		HashMap<String,Key> keys = new HashMap<String,Key>();
		String alias;
		String password = "keytool";
		try {
			Enumeration<String> aliases = ks.aliases();
			while(aliases.hasMoreElements()) {
				alias = aliases.nextElement();
				if(ks.isKeyEntry(alias))
				{
					ListKeysModel.addElement(alias);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshDetails(Key key) {
		LablDetails.setText(key.toString());
	}
	
	public void keystoreChanged(KeyStoreChangedEvent event) {
		refreshKeys(event.getNewKeyStore());
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
		// TODO Auto-generated method stub
		
	}

}
