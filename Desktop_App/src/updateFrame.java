import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class opens up a new window where you can update member information
 * @author Suhas
 */
public class updateFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtSchool;
	private JTextField txtEmail;
	Connection connection = null;
	private JTextField txtAmountOwed;
	
	private String firstName;
	private String lastName;
	private String school;
	private String schoolYear;
	private String state;
	private String email;
	private String active;
	private String amountOwed;
	private String memberNumber;

//	public void setMemberNumber(String memberNumber) {
//		this.memberNumber = memberNumber;
//	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateFrame frame = new updateFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public updateFrame(){
		setResizable(false);
		initialize();
	}
	
	public updateFrame(String s){
		setResizable(false);
		memberNumber = s;
		initialize();
	}
	

	/**
	 * Create the frame.
	 */
	public void initialize() {
		
		//database connection
		connection = sqliteConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Update");
		setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		setIconImage(frameIcon.getImage());
		
		//getting information to pre-fill the window
		try{
			String queryToGetInfo = "select * from StudentData where Membership_Number=? ";
			PreparedStatement pst = connection.prepareStatement(queryToGetInfo);
			pst.setString(1, memberNumber);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				firstName = rs.getString("First_Name");
				lastName = rs.getString("Last_Name");
				school = rs.getString("School");
				schoolYear = rs.getString("School_Year");
				state = rs.getString("State");
				email = rs.getString("Email");
				active = rs.getString("Active");
				amountOwed = rs.getString("Amount_Owed");
			}
			
			rs.close();
			pst.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		
		//label show information about field next to it
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(25, 10, 100, 20);
		contentPane.add(lblFirstName);
		
		//label sshow information about field next to it
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(25, 40, 100, 20);
		contentPane.add(lblLastName);
		
		//label sshow information about field next to it
		JLabel lblSchool = new JLabel("School");
		lblSchool.setBounds(25, 71, 100, 20);
		contentPane.add(lblSchool);
		
		//label sshow information about field next to it
		JLabel lblState = new JLabel("State");
		lblState.setBounds(25, 133, 100, 20);
		contentPane.add(lblState);
		
		//label sshow information about field next to it
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(25, 163, 100, 20);
		contentPane.add(lblEmail);
		
		//label sshow information about field next to it
		JLabel lblActive = new JLabel("Active");
		lblActive.setBounds(25, 194, 100, 20);
		contentPane.add(lblActive);
		
		//editable field
		txtFirstName = new JTextField();
		txtFirstName.setBounds(175, 10, 149, 20);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);
		txtFirstName.setText(firstName);
		
		//editable field
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(175, 40, 149, 20);
		contentPane.add(txtLastName);
		txtLastName.setText(lastName);
		
		//editable field
		txtSchool = new JTextField();
		txtSchool.setColumns(10);
		txtSchool.setBounds(175, 71, 149, 20);
		contentPane.add(txtSchool);
		txtSchool.setText(school);
		
		//selectable box
		String [] st = new String[] {"Alabama ", "Alaska ", "Arizona ", "Arkansas ", "California ", "Colorado ", "Connecticut ", "Delaware ", "Florida ", "Georgia ", "Hawaii ", "Idaho ", "Illinois", "Indiana ", "Iowa ", "Kansas ", "Kentucky ", "Louisiana ", "Maine ", "Maryland ", "Massachusetts ", "Michigan ", "Minnesota ", "Mississippi ", "Missouri ", "Montana", "Nebraska ", "Nevada ", "New Hampshire ", "New Jersey ", "New Mexico ", "New York ", "North Carolina ", "North Dakota ", "Ohio ", "Oklahoma ", "Oregon ", "Pennsylvania", "Rhode Island ", "South Carolina ", "South Dakota ", "Tennessee ", "Texas ", "Utah ", "Vermont ", "Virginia ", "Washington ", "West Virginia ", "Wisconsin ", "Wyoming"};
		JComboBox cmbState = new JComboBox();
		cmbState.setModel(new DefaultComboBoxModel(st));
		cmbState.setToolTipText("");
		cmbState.setBounds(175, 133, 149, 20);
		contentPane.add(cmbState);
		String nameofState = state;
		int indexofstate = Arrays.binarySearch(st, nameofState);
		indexofstate = Math.abs(indexofstate);
		cmbState.setSelectedIndex(indexofstate);
		
		//editable field
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(175, 163, 149, 20);
		contentPane.add(txtEmail);
		txtEmail.setText(email);
		
		//selectable box
		String [] act = new String[] {"Yes", "No"};
		JComboBox cmbActive = new JComboBox();
		cmbActive.setModel(new DefaultComboBoxModel(act));
		cmbActive.setToolTipText("Active ?");
		cmbActive.setBounds(175, 194, 149, 20);
		contentPane.add(cmbActive);
		String isActive = active;
		int indexofactive = Arrays.binarySearch(act, isActive);
		indexofactive = Math.abs(indexofactive);
		cmbActive.setSelectedIndex(indexofactive);
		
		//label sshow information about field next to it
		JLabel lblSchoolYear = new JLabel("School Year");
		lblSchoolYear.setBounds(25, 102, 100, 20);
		contentPane.add(lblSchoolYear);
		
		//selectable box
		String [] sch = new String[] {"Freshman", "Sophomore", "Junior", "Senior"};
		JComboBox cmbSchoolYear = new JComboBox();
		cmbSchoolYear.setModel(new DefaultComboBoxModel(sch));
		cmbSchoolYear.setToolTipText("");
		cmbSchoolYear.setBounds(175, 102, 149, 20);
		contentPane.add(cmbSchoolYear);
		String schYear = schoolYear;
		int indexofschYear = Arrays.binarySearch(sch, schYear);
		indexofschYear = Math.abs(indexofschYear);
		cmbSchoolYear.setSelectedIndex(indexofschYear);
		
		//label show information about field next to it
		JLabel lblAmountOwed = new JLabel("Amount Owed");
		lblAmountOwed.setBounds(25, 225, 100, 20);
		contentPane.add(lblAmountOwed);
		
		//editable field
		txtAmountOwed = new JTextField();
		txtAmountOwed.setColumns(10);
		txtAmountOwed.setBounds(175, 225, 149, 20);
		contentPane.add(txtAmountOwed);
		txtAmountOwed.setText(amountOwed);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checking if the user has searched for a member
				if (txtFirstName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter first name");
				}else{
					try {
						//this checks if there is an actual number in the amount owed box 
						double y = Double.parseDouble(txtAmountOwed.getText());
						//this checks if the user entered email has an @ sign and a .com or .new or .org
						if (txtEmail.getText().indexOf("@") > 0 && (txtEmail.getText().indexOf(".com") > 0
							|| txtEmail.getText().indexOf(".net") > 0 || txtEmail.getText().indexOf(".org") > 0)) {
							
							try{
								//creating query to update the database
								String query = "Update StudentData set First_Name='"+txtFirstName.getText()+"', Last_Name='"+txtLastName.getText()+"', School='"+txtSchool.getText()+"', School_Year='"+cmbSchoolYear.getSelectedItem().toString()+"', State='"+cmbState.getSelectedItem().toString()+"', Email='"+txtEmail.getText()+"', Active='"+cmbActive.getSelectedItem().toString()+"', Amount_Owed='"+txtAmountOwed.getText()+"' where Membership_Number='"+memberNumber+"'";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.execute();
								
								JOptionPane.showMessageDialog(null, "Member Information Updated");
								
								pst.close();
								MethodClass.refresh();
								
								dispose();
							}catch(Exception e1){
								JOptionPane.showMessageDialog(null, e1);
							}
							
						} else {
							//email validation
							JOptionPane.showMessageDialog(null, "Please Enter a valid email");
						}
					} catch (NumberFormatException e1) {
						//amount owed validation
						JOptionPane.showMessageDialog(null,
								"Please Enter a valid number for Amount Owed");
					}
				}
			}
		});
		btnSaveChanges.setBounds(100, 270, 125, 30);
		contentPane.add(btnSaveChanges);
		
	}

	public void setMemberNumber(String s) {
		memberNumber = s;
	}

}
