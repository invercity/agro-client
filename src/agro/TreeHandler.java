package agro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeHandler {
	
	private JTree tree = null;
	private Db dataDb = null;
	Statement state = null;
	ResultSet res = null;
	DefaultMutableTreeNode root = null;
	DefaultTreeModel model = null;
	// debug constant
	private static String my = "TreeHandler: ";
	// defaults
	private static String QUERY_GET_FIELDS = "select * from field_newww";
	private static String QUERY_GET_SEASON = "select * from rasteniya_db.sezon";
	private static String QUERY_GET_CULTIVATION = "select * from plan_db.cultivation_technology";
	private static String QUERY_GET_EXECUTIVE = "select * from plan_db.executive_plan";
	// rows
	public static String[] FIELDS_PROPERTY = {
		"Shape leng",
		"Shape area",
		"koatyy"
	};
	public static String[] SEASON_PROPERTY = {
		"Sevoborot",
		"Culture",
		"Sort"
	};
	public static String[] CULTIVATION_PROPERTY = {
		"Season",
		"Work",
		"Stage"
	};
	public static String[] EXECUTIVE_PROPERTY = {
		"Work",
		"Season",
		"Cultivation"
	};
 	public static String ID = "id";
	public TreeHandler(JTree tree) {
		this();
		this.tree = tree;
		update();
	}
	
	public TreeHandler() {
		root = new DefaultMutableTreeNode("Database");
		model = new DefaultTreeModel(root);
	}
	
	/*
	 * Update tree items
	 */
	public void update() {
		if ((dataDb == null) || (dataDb.getConnection() == null) || (!dataDb.loginned())) {
			// if not connected
			tree.setVisible(false);
			root.removeAllChildren();
		}
		else {
			try {
				state = dataDb.getConnection().createStatement();
				addTable("Fields", QUERY_GET_FIELDS);
				addTable("Seasons", QUERY_GET_SEASON);
				addTable("Cultivation", QUERY_GET_CULTIVATION);
				addTable("Executive", QUERY_GET_EXECUTIVE);
				tree.setVisible(true);
				//System.out.println(my + "update finished");
				
			} catch (SQLException e) {
				System.out.println(my + "update error");
			}
		}
		tree.setModel(model);
		//root.
		model.reload();
	}
	
	/*
	 * Add table to tree root
	 */
	private void addTable(String name, String sql) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		root.add(node);
		try {
			res = state.executeQuery(sql);
			while (res.next()) {
				node.add(new DefaultMutableTreeNode(res.getString(1)));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/*
	 * set tree connection
	 */
	public void connect(Db database) {
		this.dataDb = database;
		update();
	}
	
	/*
	 * get data of each row
	 */
	public String[] getData(int type, int row) {
		int i = 0;
		String sql = "";
		switch (type) {
		case 0: sql = QUERY_GET_FIELDS;
		break;
		case 1: sql = QUERY_GET_SEASON;
		break;
		case 2: sql = QUERY_GET_CULTIVATION;
		break;
		case 3: sql = QUERY_GET_EXECUTIVE;
		}
		try {
			res = dataDb.getConnection().createStatement().executeQuery(sql);
			while (res.next()) {
				if (i >= row) break;
				i++;
			}
			String[] result = {
					res.getString(1),
					res.getString(2),
					res.getString(3),
					res.getString(4)
			};
			return result;
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(my + "error getting data from result set");
		}
		return null;
	}
}
