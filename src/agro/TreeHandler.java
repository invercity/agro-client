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
	private static String my = "TreeHandler: ";
	private static String QUERY_GET_FIELDS = "select * from field_newww";
	private static String QUERY_GET_SEASON = "select * from rasteniya_db.sezon";
	private static String QUERY_GET_CULTIVATION = "select * from plan_db.cultivation_technology";
	private static String QUERY_GET_EXECUTIVE = "select * from plan_db.executive_plan";
	
	public TreeHandler(JTree tree) {
		this();
		this.tree = tree;
		update();
	}
	
	public TreeHandler() {
		root = new DefaultMutableTreeNode("Database");
		model = new DefaultTreeModel(root);
	}
	
	public void update() {
		if ((dataDb == null) || (dataDb.getConnection() == null)) {
			// if not connected
			root.removeAllChildren();
		}
		else {
			try {
				state = dataDb.getConnection().createStatement();
				addTable("Fields", QUERY_GET_FIELDS);
				addTable("Seasons", QUERY_GET_SEASON);
				addTable("Cultivation", QUERY_GET_CULTIVATION);
				addTable("Executive", QUERY_GET_EXECUTIVE);
				System.out.println(my + "update finished");
				
			} catch (SQLException e) {
				System.out.println(my + "update error");
			}
		}
		tree.setModel(model);
		//root.
		model.reload();
	}
	
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
	
	public void connect(Db database) {
		this.dataDb = database;
		update();
	}
	
	public String[] getData(int row) {
		int i = 0;
		try {
			res = dataDb.getConnection().createStatement().executeQuery(QUERY_GET_FIELDS);
			while (res.next()) {
				if (i >= row) break;
				i++;
			}
			String[] result = {
					res.getString(1),
					res.getString(2),
					res.getString(3)
			};
			return result;
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(my + "error getting data from result set");
		}
		return null;
	}
}
