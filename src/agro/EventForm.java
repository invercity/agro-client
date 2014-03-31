package agro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventForm extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Db db;
	//
	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel model;
	private static String COLUMN_ID = "id";
	private static String COLUMN_DEST = "dest";
	private static String COLUMN_SOURCE = "source";
	private static String COLUMN_TYPE = "type";
	private static String COLUMN_PRIORITY = "preoritet";
	private static String COLUMN_STATUS = "status";
	private static String COLUMN_DATA = "data";
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EventForm dialog = new EventForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EventForm() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				table.setFillsViewportHeight(true);
				scrollPane.setColumnHeaderView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAdd = new JButton("Add");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int index = model.getRowCount();
						String[] values = {
								"",
								(String) model.getValueAt(index - 1, 1),
								(String) model.getValueAt(index - 1, 2),
								(String) model.getValueAt(index - 1, 3),
								(String) model.getValueAt(index - 1, 4),
								(String) model.getValueAt(index - 1, 5),
								(String) model.getValueAt(index - 1, 6)
						};
						for (int i=1;i<7;i++) if ((values[i] == null) || (values[i].equals("null"))) values[i] = "0";
						String id = insert(values);
						if (id != null) {
							model.setRowCount(model.getRowCount() - 1);
							values[0] = id;
							model.addRow(values);
							model.setRowCount(model.getRowCount() + 1);
						}
					}
				});
				buttonPane.add(btnAdd);
			}
			{
				JSeparator separator = new JSeparator();
				buttonPane.add(separator);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public EventForm(Db d) {
		this();
		this.db = d;
		initData();
	}
	
	private void initData() {
		model = new DefaultTableModel();
		model.addColumn(COLUMN_ID);
		model.addColumn(COLUMN_TYPE);
		model.addColumn(COLUMN_PRIORITY);
		model.addColumn(COLUMN_DEST);
		model.addColumn(COLUMN_DATA);
		model.addColumn(COLUMN_STATUS);
		model.addColumn(COLUMN_SOURCE);
		loadData();
		//model.addRow(row);
		table.setModel(model);
	}
	
	private void loadData() {
		if ((db!=null) && (db.getConnection() != null) && (db.loginned())) {
			try {
				Statement s = db.getConnection().createStatement();
				String q = "SELECT " + COLUMN_ID + "," + COLUMN_TYPE + "," + COLUMN_PRIORITY + 
						"," + COLUMN_DEST + "," + COLUMN_DATA + "," + COLUMN_STATUS + ", " + COLUMN_SOURCE + " " + 
						"from dc_db.event_disp_agro;";
				ResultSet set = s.executeQuery(q);
				while (set.next()) {
					String[] row = {
							set.getString(1),
							set.getString(2), 
							set.getString(3), 
							set.getString(4), 
							set.getString(5),
							set.getString(6), 
							set.getString(7)
					};
					model.addRow(row);
				}
				model.setRowCount(model.getRowCount() + 1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String insert(String[] values) {
		if ((db!=null) && (db.getConnection() != null) && (db.loginned())) {
			try {
				Statement s = db.getConnection().createStatement();
				String q = "INSERT into dc_db.event_disp_agro(" + COLUMN_TYPE + ", " + COLUMN_PRIORITY + ", " + COLUMN_DEST + 
						", " + COLUMN_DATA + ", " + COLUMN_STATUS + ", " + COLUMN_SOURCE + ") VALUES('" + 
						values[1] + "', '" + values[2] + "', '" + 
						values[3] + "', '" + values[4] + "', '" + values[5] + "', '" + values[6] + "') returning id;";
				ResultSet set = s.executeQuery(q);
				String id = "";
				while (set.next()) id = set.getString(1);
				return id;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
