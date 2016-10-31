import java.awt.EventQueue;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;


public class UsersFrame {

	public JFrame frame;
//	static Connection connection = null;
	public static JTable table;
	private JButton btnAddNewUser;
	private JPopupMenu popupMenuForTable;
	private JTextField txtUsername;
	private JLabel lblNewLabel;
	private JLabel lblPassword;
	private JPasswordField password;
	private JPasswordField passwordConfirm;
	private JLabel lblAdminRights;
	private JLabel lblRequired;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersFrame window = new UsersFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UsersFrame() {
		//getting the connection to the database
//		connection  = sqliteConnection.dbConnector();
		//creates the screen
		initialize();
		//displays database on the table
		MethodClass.getUsersList();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 375, 350);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		frame.setIconImage(frameIcon.getImage());
		frame.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e){
				 Main.frame.setVisible(true);
				 frame.dispose();
			 }
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 150, 264);
		frame.getContentPane().add(scrollPane);
		
		
		
		//This is to make the pop up menu for the table
		popupMenuForTable = new JPopupMenu();
		
		//first menu item
		JMenuItem menuItemRemove = new JMenuItem("Remove Member");
		menuItemRemove.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this User?");
				String userName = (String)table.getModel().getValueAt(table.getSelectedRow(), table.getColumnModel().getColumnIndex("Username"));
				MethodClass.deleteUser(selection, userName);
			}
					
		});
		popupMenuForTable.add(menuItemRemove);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		table.setComponentPopupMenu(popupMenuForTable);
		table.addMouseListener(new TableMouseListener(table));
		
		JLabel lblListOfUsers = new JLabel("List of all users");
		lblListOfUsers.setBounds(10, 11, 125, 14);
		frame.getContentPane().add(lblListOfUsers);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"No Admin Rights", "Has Admin Rights"}));
		comboBox.setBounds(185, 201, 125, 25);
		frame.getContentPane().add(comboBox);
		
		btnAddNewUser = new JButton("Add New User");
		btnAddNewUser.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				MethodClass.addUser(txtUsername.getText(), password.getText(), passwordConfirm.getText(), comboBox.getSelectedIndex());
			}
		});
		btnAddNewUser.setBounds(185, 245, 125, 30);
		frame.getContentPane().add(btnAddNewUser);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(185, 36, 125, 25);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		lblNewLabel = new JLabel("Username*");
		lblNewLabel.setBounds(185, 11, 125, 25);
		frame.getContentPane().add(lblNewLabel);
		
		lblPassword = new JLabel("Password*");
		lblPassword.setBounds(185, 66, 125, 25);
		frame.getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(185, 91, 125, 25);
		frame.getContentPane().add(password);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password*");
		lblConfirmPassword.setBounds(185, 121, 125, 25);
		frame.getContentPane().add(lblConfirmPassword);
		
		passwordConfirm = new JPasswordField();
		passwordConfirm.setBounds(185, 146, 125, 25);
		frame.getContentPane().add(passwordConfirm);
		
		lblAdminRights = new JLabel("Admin Rights");
		lblAdminRights.setBounds(185, 176, 125, 25);
		frame.getContentPane().add(lblAdminRights);
		
		lblRequired = new JLabel("* Required");
		lblRequired.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRequired.setBounds(259, 286, 90, 14);
		frame.getContentPane().add(lblRequired);
		
	}
}
