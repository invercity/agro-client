package agro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField login;
	private JTextField password;
	private Main parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoginForm dialog = new LoginForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoginForm() {
		setTitle("Login");
		setBounds(100, 100, 315, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblLogin = new JLabel("Login");
			GridBagConstraints gbc_lblLogin = new GridBagConstraints();
			gbc_lblLogin.anchor = GridBagConstraints.EAST;
			gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
			gbc_lblLogin.gridx = 0;
			gbc_lblLogin.gridy = 0;
			contentPanel.add(lblLogin, gbc_lblLogin);
		}
		{
			login = new JTextField();
			GridBagConstraints gbc_login = new GridBagConstraints();
			gbc_login.insets = new Insets(0, 0, 5, 0);
			gbc_login.fill = GridBagConstraints.HORIZONTAL;
			gbc_login.gridx = 1;
			gbc_login.gridy = 0;
			contentPanel.add(login, gbc_login);
			login.setColumns(10);
			login.setText("test");
		}
		{
			JLabel lblPassword = new JLabel("Password");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 1;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			password = new JTextField();
			GridBagConstraints gbc_password = new GridBagConstraints();
			gbc_password.fill = GridBagConstraints.HORIZONTAL;
			gbc_password.gridx = 1;
			gbc_password.gridy = 1;
			contentPanel.add(password, gbc_password);
			password.setColumns(10);
			password.setText("test");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						parent.setUser(login.getText(), password.getText());
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
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	LoginForm(Main p) {
		this();
		parent = p;
	}

}
