package agro;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TableHandler {
	
	// link to table
	private JTable tbl;
	// table model
	private DefaultTableModel model;
	
	public TableHandler() {
		model = new DefaultTableModel();
	}
	
	public TableHandler(JTable table) {
		this();
		tbl = table;
		tbl.setEnabled(false);
		model.addColumn("Item");
		model.addColumn("Value");
		tbl.setModel(model);
		tbl.getColumnModel().getColumn(0).setCellRenderer(new RowHeaderRenderer());
		
	}
	
	/*
	 *  add row to table
	 */
	public void addRow(String head, String data) {
		String[] d = {head, data};
		model.addRow(d);
	}
	
	public void clear() {
		model.setRowCount(0);
	}
	
	// additional class for Row Header Decorating
	static class RowHeaderRenderer extends DefaultTableCellRenderer {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RowHeaderRenderer() {
	        setHorizontalAlignment(JLabel.CENTER);
	    }

	    public Component getTableCellRendererComponent(JTable table,
	            Object value, boolean isSelected, boolean hasFocus, int row,
	            int column) {
	        if (table != null) {
	            JTableHeader header = table.getTableHeader();

	            if (header != null) {
	                setForeground(header.getForeground());
	                setBackground(header.getBackground());
	                setFont(header.getFont());
	            }
	        }

	        if (isSelected) {
	            setFont(getFont().deriveFont(Font.BOLD));
	        }

	        setValue(value);
	        return this;
	    }
	}


}
