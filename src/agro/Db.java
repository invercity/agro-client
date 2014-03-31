package agro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	
	// connection link
	private Connection con = null;
	// defaults
	public static String DEFAULT_URL = "192.168.101.250";
	public static String DEFAULT_USER = "postgres";
	public static String DEFAULT_PASS = "1";
	public static String DEFAULT_DB = "qwer";
	// user data
	private String login = "";
	private String pass = "";
	private String user = "";
	private boolean isLoginned = false;
	
	public Db() {
		
	}
	
	/*
	 * Constructor for default url, login, pass and selected db
	 */
	public Db(String db) {
		this(DEFAULT_USER, DEFAULT_PASS, db, DEFAULT_URL);
	}
	
	/*
	 * Constructor for any params
	 */
	public Db(String login, String pass, String db, String url) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Error - no driver");
			e.printStackTrace();
			return;
		}
		try {
			con = DriverManager.getConnection(
					"jdbc:postgresql://" + url + ":5432/" + db, login,
					pass);
 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public boolean authorization(String login, String pass) {
		Statement st = null;
		try {
			st = con.createStatement();
			String query = "SELECT * FROM personnel_db.staff where id='" + Integer.valueOf(login) + "' and num='" + 
			Integer.valueOf(pass)+ "';";
			ResultSet res = st.executeQuery(query);
			int cnt = 0;
			while(res.next()) {
				this.user = res.getString(3);
				isLoginned = true;
				this.login = login;
				this.pass = pass;
				cnt++;
			}
			if (cnt > 0) return true;
			//else 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean authorization() {
		login = "";
		pass = "";
		user = "";
		isLoginned = false;
		return true;
	}
	
	public boolean loginned() {
		return isLoginned;
	}
	
	public void disconnect() {
		con = null;
		authorization();
	}
	
	public String getUser() {
		return user;
	}

}
