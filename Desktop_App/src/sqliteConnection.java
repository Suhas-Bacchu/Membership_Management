import java.sql.*;
import javax.swing.*;

/**
 * This class connects to the database
 * @author Suhas
 */
public class sqliteConnection {
	
	Connection conn = null;
	
	public static Connection dbConnector(){
		try{
			Class.forName("org.sqlite.JDBC");
			//getting connection from JDBC
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
			return conn;
		}catch(Exception e){
			//shows exception when there is an error
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
}
