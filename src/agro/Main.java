package agro;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTable;

public class Main {

	private JFrame frmAgroClient;
	private JLabel status;
	JMenu userText;
	JMenuItem loginText;
	JMenuItem connectionText;
	private Db userDb;
	private Main _this = this;
	private boolean isLoginned = false;
	private JTree tree;
	private TreeHandler treeHandler = null;
	private TableHandler tableHandler = null;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmAgroClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAgroClient = new JFrame();
		frmAgroClient.setTitle("Agro client");
		frmAgroClient.setBounds(100, 100, 450, 300);
		frmAgroClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmAgroClient.setJMenuBar(menuBar);
		
		JMenu mnUser = new JMenu("Connection");
		menuBar.add(mnUser);
		
		connectionText = new JMenuItem("Connect");
		connectionText.addActionListener(new ActionListener() {
			// connect/disconnect
			public void actionPerformed(ActionEvent e) {
				if ((userDb == null) || (userDb.getConnection() == null)) {
					ConnectForm form = new ConnectForm(_this);
					form.setVisible(true);
				}
				else {
					userDb.disconnect();
					setConnection(userDb);
				}
				
			}
		});
		mnUser.add(connectionText);
		
		userText = new JMenu("User: none");
		menuBar.add(userText);
		
		loginText = new JMenuItem("Login");
		loginText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check if connection is open
				if ((userDb != null) && (userDb.getConnection() != null)) {
					if (isLoginned) {
						isLoginned = false;
						loginText.setText("Login");
						userText.setText("User: none");
					}
					else {
						LoginForm frm = new LoginForm(_this);
						frm.setVisible(true);
					}
				}
				else {
					// add handler for disconnected db
				}
			}
		});
		userText.add(loginText);
		
		status = new JLabel("");
		frmAgroClient.getContentPane().add(status, BorderLayout.SOUTH);
		
		// app init
		status.setText("DISCONNECTED");
		status.setForeground(Color.red);
		
		// init tree
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@SuppressWarnings("null")
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = 
						(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if ((node != null) || ((node.isRoot()) && (node.getChildCount() == 0))) return;
				DefaultMutableTreeNode root = 
						(DefaultMutableTreeNode) tree.getModel().getRoot();
				if (node.isLeaf()) {
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
					// check if this is a table label (each table is empty)
					if (root.getIndex(node) != -1) {
						//textPane.setText("Table label");
					}
					else {
						int indexTableLabel = root.getIndex(parent);
						// table "Field"
						if (indexTableLabel == 0) {
							//int indexItem = parent.getIndex(node);
							//String[] res = treeHandler.getData(indexItem);
							//String text = "shape leng: " + res[1] + ", shape area: " + res[2] + ".";
							//textPane.setText(text);
						}
						
					}
				}
				else {
					//int index = root.getIndex(node);
			       // switch (index) {
					//case -1: textPane.setText("Root item");
				//	break;
					//case 0: textPane.setText("Table Field");
				//	break;
				//	}
				}
			}
		});
		treeHandler = new TreeHandler(tree);
		frmAgroClient.getContentPane().add(tree, BorderLayout.WEST);
		
		table = new JTable();
		tableHandler = new TableHandler(table);
		//tableHandler.addRow("2", "2");
		//tableHandler.addRow("3", "6");
		frmAgroClient.getContentPane().add(table, BorderLayout.CENTER);
	}
	
	public void setConnection(Db d) {
		this.userDb  = d;
		if (userDb.getConnection() != null) {
			status.setText("CONNECTED");
			connectionText.setText("Disconnect");
			status.setForeground(Color.blue);
			treeHandler.connect(userDb);
		}
		else {
			status.setText("DISCONNECTED");
			connectionText.setText("Connect");
			status.setForeground(Color.red);
			treeHandler.connect(userDb);
		}
	}
	
	public void setUser(String login, String pass) {
		if (userDb.getConnection() != null) {
			isLoginned = userDb.authorization(login, pass);
			if (isLoginned) {
				loginText.setText("Logout");
				userText.setText("User: " + login);
			}
		}
	}
}
