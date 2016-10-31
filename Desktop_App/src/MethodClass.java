import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;


/**
 * This class has all the additional methods this application uses. 
 * @author Suhas
 */
public class MethodClass {
	
	static Connection connection = sqliteConnection.dbConnector();
	
	/*
	 * this method refreshes the table in the main page
	 */
	public static void refresh(){
		try{
			
			//database query for the table
			String q = "SELECT Membership_Number AS 'Member Number',First_Name AS 'First Name',Last_Name AS 'Last Name',School,School_Year AS 'School Year',State,Email, Year_Joined AS 'Year Joined', Active,Amount_Owed AS 'Amount Owed' FROM StudentData";
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			
			//inserting query result to the table so it can be seen
			Main.table.setModel(DbUtils.resultSetToTableModel(rs));
			
			//closing the query
			ps.close();
			rs.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		Main.dashboardRefresh();
	}
	
	/*
	 * This method opens up the export file selector and lets you export the data
	 */
	public static void exportTable(JTable table){
		
		//opening the file chooser option when you click export
		JFileChooser fileSelect = new JFileChooser(new File("c:\\"));
		fileSelect.setDialogTitle("Export as Excel File");
		//setting viewable file types
		fileSelect.setFileFilter(new FileTypeFilter(".xls","Excel File"));
		int result = fileSelect.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){
			File fi = fileSelect.getSelectedFile();
			//appending .xlsx to given file name
			File f = new File(fi.toString()+".xls");
			try{
				//exporting
				ExcelExporter exp = new ExcelExporter();
				exp.exportTable(table, f);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	/*
	 * This method finds the information for the dash board in the home page
	 */
	public static void getDashboardInformation(){
		try{

			//creating query to update the total number of people in the database
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM StudentData");
			r.next();
			Main.totalMembers = r.getInt("rowcount");
			r.close();
			
			//creating query to get the total number of active members
			Statement s1 = connection.createStatement();
			ResultSet r1 = s1.executeQuery("SELECT COUNT(*) AS rowcount FROM StudentData WHERE Active = 'Yes'");
			r1.next();
			Main.totalActiveMembers = r1.getInt("rowcount");
			r1.close();
			
			//calculating total Non-Active members
			Main.totalNonActiveMembers = Main.totalMembers-Main.totalActiveMembers;
			
			//this query gets the total number of students who owe money to FBLA
			Statement s2 = connection.createStatement();
			ResultSet r2 = s2.executeQuery("SELECT COUNT(*) AS rowcount FROM StudentData WHERE Amount_Owed > 0");
			r2.next();
			Main.totalMembersOwing = r2.getInt("rowcount");
			r2.close();
			
			//this query adds the total amount of money that is owed to FBLA
			Statement s3 = connection.createStatement();
			ResultSet r3 = s3.executeQuery("SELECT SUM(Amount_Owed) AS rowcount FROM StudentData");
			r3.next();
			Main.totalOwingAmount = r3.getInt("rowcount");
			r3.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void searchForMembers(String firstName, String lastName, String memberNumber, String school, String state, String email, String compareYearJoined, String yearJoined, String compareAmountOwed, String amountOwed, String active){
		firstName = firstName + "%";
		lastName = lastName + "%";
		memberNumber = memberNumber + "%";
		school = school + "%";
		state = state + "%";
		email = email + "%";
		if(yearJoined.equals("")){
			yearJoined = "0";
		}
		if(amountOwed.equals("")){
			amountOwed = "0";
		}
		if(active.equals("All")){
			active = "%";
		}else if(active.equals("Active")){
			active = "Yes%";
		}else if(active.equals("Not Active")){
			active = "No%";
		}
		String strSearchQuery = "SELECT Membership_Number AS 'Member Number',First_Name AS 'First Name',Last_Name AS 'Last Name',School,School_Year AS 'School Year',State,Email, Year_Joined AS 'Year Joined', Active,Amount_Owed AS 'Amount Owed' FROM StudentData " + 
								"WHERE First_Name LIKE '" + firstName + "' AND Last_Name LIKE '" + lastName + "' AND Membership_Number LIKE '" + memberNumber + "' AND School LIKE '" + school + "' AND State LIKE '" + state + "' AND Email LIKE '" + email + 
								"' AND Year_Joined" + compareYearJoined + yearJoined + " AND Amount_Owed" + compareAmountOwed + amountOwed + " AND Active LIKE '" + active + "'";
		
		try{
			
			//database query for the table
			PreparedStatement ps = connection.prepareStatement(strSearchQuery);
			ResultSet rs = ps.executeQuery();
			
			//inserting query result to the table so it can be seen
			Main.table.setModel(DbUtils.resultSetToTableModel(rs));
			
			//closing the query
			ps.close();
			rs.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void accountLogin(String Username, String password){
		String strSearchQuery = "SELECT Username AS userName, Password AS password, isAdmin AS admin FROM Users WHERE Username='"+Username+"' AND Password='"+password+"'";
		try {
			PreparedStatement ps = connection.prepareStatement(strSearchQuery);
			ResultSet rs = ps.executeQuery();
			
			String RSusername = rs.getString("userName");
			String RSpassword = rs.getString("password");
			int RSadmin = rs.getInt("admin");
			
			ps.close();
			rs.close();
			
			if(RSusername.equals(Username) && RSpassword.equals(password)){
				if(RSadmin == 1){
					Main main = new Main(0);
					main.frame.setVisible(true);
					Login.frame.dispose();
				}else{
					Main main = new Main(1);
					main.frame.setVisible(true);
					Login.frame.dispose();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
			}
		} catch (SQLException e) {
			//Displaying the user that the entered information is incorrect
			JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
		}
	}
	
	public static void getUsersList(){
		try{
			
			//database query for the table
			String q = "SELECT Username FROM Users";
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			
			//inserting query result to the table so it can be seen
			UsersFrame.table.setModel(DbUtils.resultSetToTableModel(rs));
			
			//closing the query
			ps.close();
			rs.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void deleteMember(int conformation, String memberNumber){
		if (conformation == 0) {
			try {

				//creating the query to find and delete the specified person
				String query = "delete from StudentData where Membership_Number='" + memberNumber + "'";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.execute();

				//verification that member is deleted
				JOptionPane.showMessageDialog(null, "Member Deleted");

				ps.close();

				//refreshes the homepage table
				MethodClass.refresh();

			} catch (Exception e3) {
				JOptionPane.showMessageDialog(null, e3);
			}
		}
	}
	
	public static void deleteUser(int conformation, String userName){
		//making sure that the user cannot delete the admin
		if (userName.equals("Admin")){
			JOptionPane.showMessageDialog(null, "You cannot delete Admin");
		}else {
			if (conformation == 0) {
				try {
					//creating the query to find and delete the specified User
					String query = "delete from Users where Username='" + userName + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					//verification that member is deleted
					JOptionPane.showMessageDialog(null, "User Deleted");
					pst.close();
					//refreshes the Frame table
					MethodClass.getUsersList();
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, e3);
				}
			}
		}
	}
	
	public static void addUser(String username, String password, String confirm, int adminRights){
		
		if(password.length()<=6){	//making sure that the password is at least 6 characters in length
			JOptionPane.showMessageDialog(null, "password should be at least 6 characters in length");
		}else if(!username.equals("") && password.equals(confirm)){  //checking if the username is blank and checking if the passwords match
			try{
				//query to add a new user
				String query = "INSERT INTO Users (Username, Password, isAdmin) values (?,?,?)";
				PreparedStatement pst = connection.prepareStatement(query);
				
				//adding to the query the user provided information
				pst.setString(1, username);
				pst.setString(2, password);
				pst.setString(3, ""+adminRights);
				
				//executing the query
				pst.execute();
				//closing connection to database
				pst.close();
				
				//tells the user that the information was successfully added to the database
				JOptionPane.showMessageDialog(null, "User Added");
				
				//refresh the table
				getUsersList();
			}catch(Exception e){
				if(e.toString().equals("java.sql.SQLException: [SQLITE_CONSTRAINT]  Abort due to constraint violation (column Username is not unique)")){
					JOptionPane.showMessageDialog(null, "Username already in use");
				}
			}
		}else if(username.equals("")){	//making sure the username is not empty
			JOptionPane.showMessageDialog(null, "Username is empty");
		}else{	//telling the user that the passwords do not match
			JOptionPane.showMessageDialog(null, "Passwords do not match");
		}
		
	}
	
	public static void firstReportRefresh(){
		try{
			
			//database query for the table
			String q = "SELECT Membership_Number,First_Name,Last_Name,School,School_Year,State,Email,Year_Joined,Active,Amount_Owed FROM StudentData WHERE Amount_Owed > 0 ORDER BY State";
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			
			FirstReport.ReportTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			ps.close();
			rs.close();
			
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, e1);
		}
	}
	
	public static void addMember(String firstName,String lastName,String membershipNumber,String school,String schoolYear,String state,String email,String yearJoined,String active,String amountOwed){
		try {
			
			//database query for the table
			String query = "insert into StudentData (First_Name,Last_Name,Membership_Number,School,School_Year,State,Email, Year_Joined, Active,Amount_Owed) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection
					.prepareStatement(query);
			
			//adding to the query the user provided information
			pst.setString(1, firstName);
			pst.setString(2, lastName);
			pst.setString(3, membershipNumber);
			pst.setString(4, school);
			pst.setString(5, schoolYear);
			pst.setString(6, state);
			pst.setString(7, email);
			pst.setString(8, yearJoined);
			pst.setString(9, active);
			pst.setString(10, amountOwed);
			
			//executing the query
			pst.execute();
			
			//tells the user that the information was successfully added to the database
			JOptionPane.showMessageDialog(null, "Member Added");

			pst.close();
			
			MethodClass.refresh();
			
			Main.frame.setEnabled(true);

		} catch (Exception e) {
			if(e.toString().equals("java.sql.SQLException: [SQLITE_CONSTRAINT]  Abort due to constraint violation (column Membership_Number is not unique)")){
				JOptionPane.showMessageDialog(null, "Membership Number is not Unique");
			}else{
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public static void secondReportRefresh(){
		try{
			//query to get all seniors from database and order it by state
			String q = "SELECT Membership_Number,First_Name,Last_Name,School,School_Year,State,Email,Year_Joined,Active,Amount_Owed FROM StudentData WHERE School_Year = 'Senior' ORDER BY State";
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			
			SecondReport.reportTable2.setModel(DbUtils.resultSetToTableModel(rs));
			
			ps.close();
			rs.close();
			
			//query for getting total number of seniors
			Statement s2 = connection.createStatement();
			ResultSet r2 = s2.executeQuery("SELECT COUNT(*) AS rowcount FROM StudentData WHERE School_Year = 'Senior'");
			r2.next();
			SecondReport.totalOwing = r2.getInt("rowcount");
			r2.close();
		}catch(Exception e2){
			JOptionPane.showMessageDialog(null, e2);
		}
	}
	
}
