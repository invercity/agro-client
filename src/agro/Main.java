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
import javax.swing.JScrollPane;

public class Main {
	// UI components
	private JFrame frmAgroClient;
	private JLabel status;
	private JMenu userText;
	private JMenuItem loginText;
	private JMenuItem connectionText;
	private JTable table;
	private Db userDb;
	// link to this
	private Main _this = this;
	// login flag
	//private boolean isLoginned = false;
	// handlers
	private TreeHandler treeHandler = null;
	private TableHandler tableHandler = null;
	private JScrollPane scrollPane;
	private JTree tree;
	private JMenu textEvent;
	private JMenuItem eventCreate;

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
		frmAgroClient.setBounds(100, 100, 670, 444);
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
					loginText.setText("Login");
					userText.setText("User: none");
					treeHandler.update();
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
					if (userDb.loginned()) {
						// exit 
						userDb.authorization();
						loginText.setText("Login");
						userText.setText("User: none");
						treeHandler.update();
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
		
		textEvent = new JMenu("Event");
		menuBar.add(textEvent);
		
		eventCreate = new JMenuItem("Create");
		eventCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((userDb != null) && (userDb.getConnection() != null) && (userDb.loginned())) {
					EventForm form = new EventForm(userDb);
					form.setVisible(true);
				}
			}
		});
		textEvent.add(eventCreate);
		
		status = new JLabel("");
		frmAgroClient.getContentPane().add(status, BorderLayout.SOUTH);
		
		// application initialization
		status.setText("DISCONNECTED");
		status.setForeground(Color.red);
		
		table = new JTable();
		// create table handler
		tableHandler = new TableHandler(table);
		frmAgroClient.getContentPane().add(table, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		frmAgroClient.getContentPane().add(scrollPane, BorderLayout.WEST);
		
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			/*
			 * tree selection handler
			 */
			//@SuppressWarnings("null")
			public void valueChanged(TreeSelectionEvent arg0) {
				// clear table
				tableHandler.clear();
				// get selected element
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				// if not selected, or root - exit fom function
				if ((node == null) || ((node.isRoot()) && (node.getChildCount() == 0))) return;
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
							int indexItem = parent.getIndex(node);
							String[] res = treeHandler.getData(indexTableLabel, indexItem);
							tableHandler.addRow(TreeHandler.ID, res[0]);
							tableHandler.addRow(TreeHandler.FIELDS_PROPERTY[0], res[1]);
							tableHandler.addRow(TreeHandler.FIELDS_PROPERTY[1], res[2]);
							tableHandler.addRow(TreeHandler.FIELDS_PROPERTY[2], res[3]);
						}
						// table "Season
						if (indexTableLabel == 1) {
							int indexItem = parent.getIndex(node);
							String[] res = treeHandler.getData(indexTableLabel, indexItem);
							tableHandler.addRow(TreeHandler.ID, res[0]);
							tableHandler.addRow(TreeHandler.SEASON_PROPERTY[0], res[1]);
							tableHandler.addRow(TreeHandler.SEASON_PROPERTY[1], res[2]);
							tableHandler.addRow(TreeHandler.SEASON_PROPERTY[2], res[3]);
						}
						// table "Cultivation"
						if (indexTableLabel == 2) {
							int indexItem = parent.getIndex(node);
							String[] res = treeHandler.getData(indexTableLabel, indexItem);
							tableHandler.addRow(TreeHandler.ID, res[0]);
							tableHandler.addRow(TreeHandler.CULTIVATION_PROPERTY[0], res[1]);
							tableHandler.addRow(TreeHandler.CULTIVATION_PROPERTY[1], res[2]);
							tableHandler.addRow(TreeHandler.CULTIVATION_PROPERTY[2], res[3]);
						}
						// table "Executive plan"
						if (indexTableLabel == 3) {
							int indexItem = parent.getIndex(node);
							String[] res = treeHandler.getData(indexTableLabel, indexItem);
							tableHandler.addRow(TreeHandler.ID, res[0]);
							tableHandler.addRow(TreeHandler.EXECUTIVE_PROPERTY[0], res[1]);
							tableHandler.addRow(TreeHandler.EXECUTIVE_PROPERTY[1], res[2]);
							tableHandler.addRow(TreeHandler.EXECUTIVE_PROPERTY[2], res[3]);
						}
					}
				}
			}
		});
		treeHandler = new TreeHandler(tree);
		scrollPane.setViewportView(tree);
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
			userDb.authorization(login, pass);
			if (userDb.loginned()) {
				loginText.setText("Logout");
				userText.setText("User: " + userDb.getUser());
			}
		}
		treeHandler.update();
	}
}
