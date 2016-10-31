import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Font;


/**
 * This Class creates a frame where a person can add a new member to the database
 * @author Suhas
 */
public class addFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtMembershipNumber;
	private JTextField txtSchool;
	private JTextField txtEmail;
	private JTextField txtAmountOwed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addFrame frame = new addFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public addFrame() {
		setResizable(false);
		
		//getting the connection to the database
//		connection = sqliteConnection.dbConnector();
		
		//setting basic information about the JFrame
		setTitle("Add Member");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, -22, 350, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		setIconImage(frameIcon.getImage());
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e){
				 Main.frame.setEnabled(true);
				 dispose();
			 }
		});
		
		//Creating a label for the first name
		JLabel lblFirstName = new JLabel("First Name*");
		lblFirstName.setBounds(25, 10, 130, 20);
		contentPane.add(lblFirstName);
		
		//Creating a label for last name
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(25, 40, 130, 20);
		contentPane.add(lblLastName);
		
		//Creating a label for the membership number
		JLabel lblMembershipNumber = new JLabel("Membership Number*");
		lblMembershipNumber.setBounds(25, 70, 130, 20);
		contentPane.add(lblMembershipNumber);
		
		//Creating a label for school
		JLabel lblSchool = new JLabel("School");
		lblSchool.setBounds(25, 100, 130, 20);
		contentPane.add(lblSchool);
		
		//Creating a label for State
		JLabel lblState = new JLabel("State");
		lblState.setBounds(25, 162, 130, 20);
		contentPane.add(lblState);
		
		//Creating a label for Email
		JLabel lblEmail = new JLabel("Email*");
		lblEmail.setBounds(25, 192, 130, 20);
		contentPane.add(lblEmail);
		
		//Creating a label for year joined
		JLabel lblYearJoined = new JLabel("Year Joined");
		lblYearJoined.setBounds(25, 222, 130, 20);
		contentPane.add(lblYearJoined);
		
		//Creating a label to know if active or not
		JLabel lblActive = new JLabel("Active");
		lblActive.setBounds(25, 252, 130, 20);
		contentPane.add(lblActive);
		
		//user to fill in the first name
		txtFirstName = new JTextField();
		txtFirstName.setBounds(185, 10, 139, 20);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		//user to fill in the last name
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(185, 40, 139, 20);
		contentPane.add(txtLastName);
		
		//user to fill in the membership number
		txtMembershipNumber = new JTextField();
		txtMembershipNumber.setColumns(10);
		txtMembershipNumber.setBounds(185, 70, 139, 20);
		contentPane.add(txtMembershipNumber);
		
		//user to fill in the School
		txtSchool = new JTextField();
		txtSchool.setColumns(10);
		txtSchool.setBounds(185, 100, 139, 20);
		contentPane.add(txtSchool);
		
		/*
		 * I used a JComboBox because the user can select a state instead of typing it and getting spelling mistakes
		 */
		JComboBox cmbState = new JComboBox();
		cmbState.setModel(new DefaultComboBoxModel(new String[] {"Alabama ", "Alaska ", "Arizona ", "Arkansas ", "California ", "Colorado ", "Connecticut ", "Delaware ", "Florida ", "Georgia ", "Hawaii ", "Idaho ", "Illinois", "Indiana ", "Iowa ", "Kansas ", "Kentucky ", "Louisiana ", "Maine ", "Maryland ", "Massachusetts ", "Michigan ", "Minnesota ", "Mississippi ", "Missouri ", "Montana", "Nebraska ", "Nevada ", "New Hampshire ", "New Jersey ", "New Mexico ", "New York ", "North Carolina ", "North Dakota ", "Ohio ", "Oklahoma ", "Oregon ", "Pennsylvania", "Rhode Island ", "South Carolina ", "South Dakota ", "Tennessee ", "Texas ", "Utah ", "Vermont ", "Virginia ", "Washington ", "West Virginia ", "Wisconsin ", "Wyoming"}));
		cmbState.setToolTipText("");
		cmbState.setBounds(185, 162, 139, 20);
		contentPane.add(cmbState);
		
		//user to fill in the email
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(185, 192, 139, 20);
		contentPane.add(txtEmail);
		
		/*
		 * I used a JComboBox because the user can select a year and mistyping it
		 */
		JComboBox cmbYearJoined = new JComboBox();
		cmbYearJoined.setModel(new DefaultComboBoxModel(new String[] {"2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940"}));
		cmbYearJoined.setToolTipText("");
		cmbYearJoined.setBounds(185, 222, 139, 20);
		contentPane.add(cmbYearJoined);
		
		/*
		 * I used a JComboBox because the user can select yes or no much faster
		 */
		JComboBox cmbActive = new JComboBox();
		cmbActive.setModel(new DefaultComboBoxModel(new String[] {"Yes", "No"}));
		cmbActive.setToolTipText("Active ?");
		cmbActive.setBounds(185, 252, 139, 20);
		contentPane.add(cmbActive);
		
		//Creating a label for the school year
		JLabel lblSchoolYear = new JLabel("School Year");
		lblSchoolYear.setBounds(25, 131, 130, 20);
		contentPane.add(lblSchoolYear);
		
		/*
		 * I used a JComboBox because the user can select school year instead of typing it and getting spelling mistakes
		 */ 
		JComboBox cmbSchoolYear = new JComboBox();
		cmbSchoolYear.setModel(new DefaultComboBoxModel(new String[] {"Freshman", "Sophomore", "Junior", "Senior"}));
		cmbSchoolYear.setToolTipText("");
		cmbSchoolYear.setBounds(185, 131, 139, 20);
		contentPane.add(cmbSchoolYear);
		
		//Creating a label for amount owed
		JLabel lblAmountOwed = new JLabel("Amount Owed*");
		lblAmountOwed.setBounds(25, 283, 130, 20);
		contentPane.add(lblAmountOwed);
		
		//user to fill in amount owed
		txtAmountOwed = new JTextField();
		txtAmountOwed.setColumns(10);
		txtAmountOwed.setBounds(185, 283, 139, 20);
		contentPane.add(txtAmountOwed);
		
		//button to save changes to the database
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//checking if the user has searched for a member
				if (txtFirstName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter first name");
				}else{
					try {
						//this checks if there is an actual number in the amount owed box 
						double y = Double.parseDouble(txtAmountOwed.getText());
						//this checks if the user entered email has an @ sign and a .com or .new or .org
						if (txtEmail.getText().indexOf("@") > 0 
							&& (txtEmail.getText().indexOf(".com") > 0
							|| txtEmail.getText().indexOf(".net") > 0 
							|| txtEmail.getText().indexOf(".org") > 0)) {
								MethodClass.addMember(txtFirstName.getText(), txtLastName.getText(), txtMembershipNumber.getText(), txtSchool.getText(), cmbSchoolYear.getSelectedItem().toString(), cmbState.getSelectedItem().toString(), txtEmail.getText(), cmbYearJoined.getSelectedItem().toString(), cmbActive.getSelectedItem().toString(), txtAmountOwed.getText());
								dispose();
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
		btnAdd.setBounds(130, 323, 100, 25);
		contentPane.add(btnAdd);
		
		JLabel lblRequired = new JLabel("* Required");
		lblRequired.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRequired.setBounds(10, 346, 90, 14);
		contentPane.add(lblRequired);
		
		
	}
}
