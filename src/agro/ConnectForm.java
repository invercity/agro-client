package agro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ConnectForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField login;
	private JTextField pass;
	private JTextField url;
	private Main parent;
	private JTextField db;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConnectForm dialog = new ConnectForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConnectForm() {
		setTitle("Connection");
		setBounds(100, 100, 457, 347);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblDb = new JLabel("DB:");
			GridBagConstraints gbc_lblDb = new GridBagConstraints();
			gbc_lblDb.insets = new Insets(0, 0, 5, 5);
			gbc_lblDb.gridx = 2;
			gbc_lblDb.gridy = 0;
			contentPanel.add(lblDb, gbc_lblDb);
		}
		{
			JLabel lblNewLabel = new JLabel("Login");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 4;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			login = new JTextField();
			GridBagConstraints gbc_login = new GridBagConstraints();
			gbc_login.insets = new Insets(0, 0, 5, 0);
			gbc_login.fill = GridBagConstraints.HORIZONTAL;
			gbc_login.gridx = 6;
			gbc_login.gridy = 0;
			contentPanel.add(login, gbc_login);
			login.setColumns(10);
			login.setText(Db.DEFAULT_USER);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Password");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 4;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			pass = new JTextField();
			GridBagConstraints gbc_pass = new GridBagConstraints();
			gbc_pass.insets = new Insets(0, 0, 5, 0);
			gbc_pass.fill = GridBagConstraints.HORIZONTAL;
			gbc_pass.gridx = 6;
			gbc_pass.gridy = 1;
			contentPanel.add(pass, gbc_pass);
			pass.setColumns(10);
			pass.setText(Db.DEFAULT_PASS);
		}
		{
			JLabel lblDatabase = new JLabel("Database");
			GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
			gbc_lblDatabase.insets = new Insets(0, 0, 5, 5);
			gbc_lblDatabase.gridx = 4;
			gbc_lblDatabase.gridy = 2;
			contentPanel.add(lblDatabase, gbc_lblDatabase);
		}
		{
			db = new JTextField();
			GridBagConstraints gbc_db = new GridBagConstraints();
			gbc_db.insets = new Insets(0, 0, 5, 0);
			gbc_db.fill = GridBagConstraints.HORIZONTAL;
			gbc_db.gridx = 6;
			gbc_db.gridy = 2;
			contentPanel.add(db, gbc_db);
			db.setColumns(10);
			db.setText(Db.DEFAULT_DB);
		}
		{
			JLabel lblUrl = new JLabel("Url");
			GridBagConstraints gbc_lblUrl = new GridBagConstraints();
			gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl.gridx = 4;
			gbc_lblUrl.gridy = 3;
			contentPanel.add(lblUrl, gbc_lblUrl);
		}
		{
			url = new JTextField();
			GridBagConstraints gbc_url = new GridBagConstraints();
			gbc_url.insets = new Insets(0, 0, 5, 0);
			gbc_url.fill = GridBagConstraints.HORIZONTAL;
			gbc_url.gridx = 6;
			gbc_url.gridy = 3;
			contentPanel.add(url, gbc_url);
			url.setColumns(10);
			url.setText(Db.DEFAULT_URL);
		}
		{
			JLabel lblCore = new JLabel("Core");
			GridBagConstraints gbc_lblCore = new GridBagConstraints();
			gbc_lblCore.insets = new Insets(0, 0, 0, 5);
			gbc_lblCore.gridx = 2;
			gbc_lblCore.gridy = 4;
			contentPanel.add(lblCore, gbc_lblCore);
		}
		{
			JLabel lblCoreIp = new JLabel("Core IP");
			GridBagConstraints gbc_lblCoreIp = new GridBagConstraints();
			gbc_lblCoreIp.insets = new Insets(0, 0, 0, 5);
			gbc_lblCoreIp.gridx = 4;
			gbc_lblCoreIp.gridy = 4;
			contentPanel.add(lblCoreIp, gbc_lblCoreIp);
		}
		{
			textField = new JTextField();
			textField.setText("192.168.101.50");
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 6;
			gbc_textField.gridy = 4;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Db database = new Db(login.getText(), pass.getText(), db.getText(), url.getText());
						parent.setConnection(database);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public ConnectForm(Main parent) {
		this();
		this.parent = parent;
	}
}
