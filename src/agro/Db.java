package agro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	
	private Connection con = null;
	public static String DEFAULT_URL = "192.168.101.250";
	public static String DEFAULT_USER = "postgres";
	public static String DEFAULT_PASS = "1";
	public static String DEFAULT_DB = "qwer";
	public Db() {
		
	}
	
	/*
	 * Constructor for default url, login, pass and selected db
	 */
	public Db(String db) {
		this(DEFAULT_USER, DEFAULT_PASS, db, DEFAULT_URL);
	}
	
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
			String query = "SELECT * FROM employee where login='" + login + "' and password='" + 
			pass + "';";
			ResultSet res = st.executeQuery(query);
			int cnt = 0;
			while(res.next()) cnt++;
			if (cnt > 0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void disconnect() {
		con = null;
	}

}
