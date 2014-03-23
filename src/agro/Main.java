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
	// UI components
	private JFrame frmAgroClient;
	private JLabel status;
	private JMenu userText;
	private JMenuItem loginText;
	private JMenuItem connectionText;
	private JTree tree;
	private JTable table;
	private Db userDb;
	// link to this
	private Main _this = this;
	// login flag
	private boolean isLoginned = false;
	// handlers
	private TreeHandler treeHandler = null;
	private TableHandler tableHandler = null;

	/**
	 * Application entry point
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
	 * Default constructor
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
			/*
			 *  connect/disconnect handler
			 */
			public void actionPerformed(ActionEvent e) {
				// if disconnected
				if ((userDb == null) || (userDb.getConnection() == null)) {
					// open connect options form
					ConnectForm form = new ConnectForm(_this);
					form.setVisible(true);
				}
				// if connected
				else {
					// disconnect
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
			/*
			 * login handler
			 */
			public void actionPerformed(ActionEvent e) {
				// check if connection is open
				if ((userDb != null) && (userDb.getConnection() != null)) {
					// if user already login
					if (isLoginned) {
						// exit 
						isLoginned = false;
						loginText.setText("Login");
						userText.setText("User: none");
					}
					else {
						// open login form
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
		
		// application initialization
		status.setText("DISCONNECTED");
		status.setForeground(Color.red);
		
		// initialize tree
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			/*
			 * tree selection handler
			 */
			@SuppressWarnings("null")
			public void valueChanged(TreeSelectionEvent arg0) {
				// get selected element
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				// if not selected, or root - exit fom function
				if ((node != null) || ((node.isRoot()) && (node.getChildCount() == 0))) return;
				// get root element
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				// check if this is leaf
				if (node.isLeaf()) {
					// get leaf.parent
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
					// check if this is a table label (each table is empty)
					if (root.getIndex(node) != -1) {
						//textPane.setText("Table label");
					}
					// this is sub-item of table
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
				// its not a leaf
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
		// create tree handler
		treeHandler = new TreeHandler(tree);
		frmAgroClient.getContentPane().add(tree, BorderLayout.WEST);
		
		table = new JTable();
		// create table handler
		tableHandler = new TableHandler(table);
		frmAgroClient.getContentPane().add(table, BorderLayout.CENTER);
	}
	
	/*
	 *  set Connection
	 */
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
	
	/*
	 *  try to login user
	 */
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
