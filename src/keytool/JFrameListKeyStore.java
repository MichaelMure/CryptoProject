package keytool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

import java.util.ArrayList;

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

public class JFrameListKeyStore extends KeytoolView implements ActionListener, ChangeListener, MouseListener {
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
        ListKeys.addMouseListener(this);
        ScrollKeyPanel.setViewportView(ListKeys);

        /* Liste Certificats */
        ListCertificateModel = new DefaultListModel();
        ListCertificats.setModel(ListCertificateModel);
        ListCertificats.addMouseListener(this);
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
        ItemOpen.setActionCommand("ouvrir");
        MenuKeytool.add(ItemOpen);

        /* Menu Fermer */
        ItemQuit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        ItemQuit.setText("Quitter");
        ItemQuit.setActionCommand("quitter");
        ItemQuit.addActionListener(this);
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
		refreshCertificates();
		refreshKeys();
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
	
	public void refreshDetails(Object obj) {
		StringBuilder sb = new StringBuilder();
		LablDetails.setText("empty");
		
		try {
			Key key = (Key) obj;
			sb.append("Algorithm: ");
			sb.append(key.getAlgorithm());
			sb.append("\n Format: ");
			sb.append(key.getFormat());
		} catch(Exception e) {
			Certificate cert = (Certificate) obj;
			sb.append("Certificat !");
		}
		LablDetails.setText(sb.toString());
	}
	
	public void keystoreChanged(KeyStoreChangedEvent event) {
		refreshKeys();
	}
	
	public void elementSelected(ElementSelectedEvent event) {
		refreshDetails(event.getSelectedElement());
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("import")) {
			getController().notifyKeyStoreChanged(null);
		} else if(action.equals("quitter")) {
			this.close();
		}
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		getController().notifyElementSelected(e);
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}