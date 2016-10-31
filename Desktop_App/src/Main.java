import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

/**
 * This is the main class that opens up the home page
 * @author Suhas
 */
public class Main {

	public static JFrame frame;
//	static Connection connection = null;
	public static JTable table;
	
	public static int totalMembers = 0;
	public static int totalActiveMembers = 0;
	public static int totalNonActiveMembers = 0;
	public static int totalMembersOwing = 0;
	public static int totalOwingAmount = 0;
	public static JTextField txtTotalActive;
	public static JTextField txtTotalNonActive;
	public static JTextField txtTotalMembersOwing;
	public static JTextField txtTotalOwingAmount;
	private static JTextField txtTotalMembers;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtMemberNumber;
	private JTextField txtSchool;
	private JTextField txtState;
	private JTextField txtEmail;
	private JTextField txtYearJoined;
	private JTextField txtAmountOwed;
	private JComboBox cmbYearJoined;
	private JComboBox cmbAmountOwed;
	private JComboBox cmbActive;
	private int columnValue = -1; 
	private int columnNewValue = -1;
	private JButton btnUsers;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main(0);
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
	public Main(int adminRights) {
		//creates the screen
		initialize();
		//displays database on the table
		MethodClass.refresh();
		
		//Determining the rights of a user
		if(adminRights == 1){
			btnUsers.setVisible(false);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame("FBLA Members Information");
		frame.setBounds(100, 100, 1450, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		frame.setIconImage(frameIcon.getImage());
		
		// the search button
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MethodClass.searchForMembers(txtFirstName.getText(), txtLastName.getText(), txtMemberNumber.getText(), txtSchool.getText(),
						txtState.getText(), txtEmail.getText(), cmbYearJoined.getSelectedItem().toString(), txtYearJoined.getText(),
						cmbAmountOwed.getSelectedItem().toString(), txtAmountOwed.getText(), cmbActive.getSelectedItem().toString());
			}
		});
		btnSearch.setBounds(1111, 139, 110, 30);
		frame.getContentPane().add(btnSearch);
		
		//this is the scroller for the table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(255, 180, 1097, 495);
		frame.getContentPane().add(scrollPane);
		
		
		//This is to make the pop up menu for the table
		JPopupMenu popupMenuForTable = new JPopupMenu();
		
		// a new table
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.setComponentPopupMenu(popupMenuForTable);
		table.addMouseListener(new TableMouseListener(table));
		table.getTableHeader().setReorderingAllowed(true);
		table.getColumnModel().addColumnModelListener(new TableColumnModelListener(){ 
		    public void columnAdded(TableColumnModelEvent e) {} 

		    public void columnMarginChanged(ChangeEvent e) {} 

		    public void columnMoved(TableColumnModelEvent e){ 
		        if (columnValue == -1) {
		            columnValue = e.getFromIndex();
		        }

		        columnNewValue = e.getToIndex(); 
		    } 

		    public void columnRemoved(TableColumnModelEvent e) {} 

		    public void columnSelectionChanged(ListSelectionEvent e) {} 
		}); 
		table.getTableHeader().addMouseListener(new MouseAdapter() { 
		    @Override 
		    public void mouseReleased(MouseEvent e) 
		    { 
		        if (columnValue != -1 && (columnValue == 0 || columnNewValue == 0)) 
		        table.moveColumn(columnNewValue, columnValue); 

		        columnValue = -1; 
		        columnNewValue = -1; 
		    } 
		}); 
		table.enable(false);
		
		//First menu item
		JMenuItem menuItemAdd = new JMenuItem("Get Membership Number");
		menuItemAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				String s = (String)table.getModel().getValueAt(table.getSelectedRow(), table.getColumnModel().getColumnIndex("Member Number"));
				String s = (String)table.getModel().getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), table.getColumnModel().getColumnIndex("Member Number"));
				JOptionPane.showMessageDialog(null, s);
			}
		});
		
		//second menu item
		JMenuItem menuItemRemove = new JMenuItem("Remove Member");
		menuItemRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this member?");
				String memberNumber = (String)table.getModel().getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), table.getColumnModel().getColumnIndex("Member Number"));
				MethodClass.deleteMember(selection, memberNumber);
			}
			
		});
		
		//third menu item
		JMenuItem menuItemUpdate = new JMenuItem("Update Member Information");
		menuItemUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String)table.getModel().getValueAt(table.getSelectedRow(), table.getColumnModel().getColumnIndex("Member Number"));
				updateFrame update = new updateFrame(s);
				update.setVisible(true);
			}
			
		});
		 
		popupMenuForTable.add(menuItemAdd);
		popupMenuForTable.add(menuItemRemove);
		popupMenuForTable.add(menuItemUpdate);
		
		
		//the add member button
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrame addF = new addFrame();
				addF.setVisible(true);
				frame.setEnabled(false);
			}
		});
		btnAddMember.setBounds(75, 250, 110, 30);
		frame.getContentPane().add(btnAddMember);
		
		//creating the export button
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MethodClass.exportTable(table);
			}
		});
		btnExport.setBounds(1025, 686, 110, 30);
		frame.getContentPane().add(btnExport);
		
		//the print button
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//making header and footer
				MessageFormat header = new MessageFormat("All Member Information");
				MessageFormat footer = new MessageFormat("Total Members: " + totalMembers);
				try{
					//code for opening the print screen
					table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				}catch(Exception e2){
					//exception
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		btnPrint.setBounds(1145, 686, 110, 30);
		frame.getContentPane().add(btnPrint);
		
		//the report button
		JButton btnReport = new JButton("Reports");
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportFrame rf = new reportFrame();
				rf.setVisible(true);
				frame.setEnabled(false);
			}
		});
		btnReport.setBounds(75, 291, 110, 30);
		frame.getContentPane().add(btnReport);
		
		//adding the FBLA logo
		JLabel lblImage = new JLabel("");
		ImageIcon img = new ImageIcon("FBLA.png");
		Image i = img.getImage();
		Image newI = i.getScaledInstance( 209, 156, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(newI);
		lblImage.setIcon(newImg);
		lblImage.setBounds(25, 30, 220, 150);
		frame.getContentPane().add(lblImage);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				helpFrame hf = new helpFrame();
				hf.setVisible(true);
			}
		});
		btnHelp.setBounds(75, 332, 110, 30);
		frame.getContentPane().add(btnHelp);
		
		JLabel lblTotalActive = new JLabel("Total Active");
		lblTotalActive.setBounds(10, 500, 125, 20);
		frame.getContentPane().add(lblTotalActive);
		
		JLabel lblTotalNonActive = new JLabel("Total Non-Active");
		lblTotalNonActive.setBounds(10, 531, 125, 20);
		frame.getContentPane().add(lblTotalNonActive);
		
		JLabel lblTotalMembersOwing = new JLabel("Total Members Owing");
		lblTotalMembersOwing.setBounds(10, 562, 125, 20);
		frame.getContentPane().add(lblTotalMembersOwing);
		
		JLabel lblTotalOwingAmount = new JLabel("Total Owing Amount");
		lblTotalOwingAmount.setBounds(10, 593, 125, 20);
		frame.getContentPane().add(lblTotalOwingAmount);
		
		txtTotalActive = new JTextField();
		txtTotalActive.setEditable(false);
		txtTotalActive.setBounds(145, 500, 86, 20);
		frame.getContentPane().add(txtTotalActive);
		txtTotalActive.setColumns(10);
		
		txtTotalNonActive = new JTextField();
		txtTotalNonActive.setEditable(false);
		txtTotalNonActive.setColumns(10);
		txtTotalNonActive.setBounds(145, 531, 86, 20);
		frame.getContentPane().add(txtTotalNonActive);
		
		txtTotalMembersOwing = new JTextField();
		txtTotalMembersOwing.setEditable(false);
		txtTotalMembersOwing.setColumns(10);
		txtTotalMembersOwing.setBounds(145, 562, 86, 20);
		frame.getContentPane().add(txtTotalMembersOwing);
		
		txtTotalOwingAmount = new JTextField();
		txtTotalOwingAmount.setEditable(false);
		txtTotalOwingAmount.setColumns(10);
		txtTotalOwingAmount.setBounds(145, 593, 86, 20);
		frame.getContentPane().add(txtTotalOwingAmount);
		
		JLabel lblTotalMembers = new JLabel("Total Members");
		lblTotalMembers.setBounds(10, 469, 125, 20);
		frame.getContentPane().add(lblTotalMembers);
		
		txtTotalMembers = new JTextField();
		txtTotalMembers.setEditable(false);
		txtTotalMembers.setColumns(10);
		txtTotalMembers.setBounds(145, 469, 86, 20);
		frame.getContentPane().add(txtTotalMembers);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(270, 50, 125, 20);
		frame.getContentPane().add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(270, 81, 125, 20);
		frame.getContentPane().add(lblLastName);
		
		JLabel lblMembershipNumber = new JLabel("Membership Number");
		lblMembershipNumber.setBounds(270, 112, 125, 20);
		frame.getContentPane().add(lblMembershipNumber);
		
		JLabel lblSchool = new JLabel("School");
		lblSchool.setBounds(645, 50, 125, 20);
		frame.getContentPane().add(lblSchool);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(405, 50, 100, 20);
		frame.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(405, 81, 100, 20);
		frame.getContentPane().add(txtLastName);
		
		txtMemberNumber = new JTextField();
		txtMemberNumber.setColumns(10);
		txtMemberNumber.setBounds(405, 112, 100, 20);
		frame.getContentPane().add(txtMemberNumber);
		
		txtSchool = new JTextField();
		txtSchool.setColumns(10);
		txtSchool.setBounds(780, 50, 100, 20);
		frame.getContentPane().add(txtSchool);
		
		JLabel lblState = new JLabel("State");
		lblState.setBounds(645, 81, 125, 20);
		frame.getContentPane().add(lblState);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(645, 112, 125, 20);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblYearJoined = new JLabel("Year Joined");
		lblYearJoined.setBounds(1020, 50, 125, 20);
		frame.getContentPane().add(lblYearJoined);
		
		JLabel lblAmountOwed = new JLabel("Amount Owed");
		lblAmountOwed.setBounds(1020, 81, 125, 20);
		frame.getContentPane().add(lblAmountOwed);
		
		txtState = new JTextField();
		txtState.setColumns(10);
		txtState.setBounds(780, 81, 100, 20);
		frame.getContentPane().add(txtState);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(780, 112, 100, 20);
		frame.getContentPane().add(txtEmail);
		
		txtYearJoined = new JTextField();
		txtYearJoined.setColumns(10);
		txtYearJoined.setBounds(1195, 50, 100, 20);
		frame.getContentPane().add(txtYearJoined);
		
		txtAmountOwed = new JTextField();
		txtAmountOwed.setColumns(10);
		txtAmountOwed.setBounds(1195, 81, 100, 20);
		frame.getContentPane().add(txtAmountOwed);
		
		cmbYearJoined = new JComboBox();
		cmbYearJoined.setModel(new DefaultComboBoxModel(new String[] {">=", "<=", "="}));
		cmbYearJoined.setBounds(1145, 50, 40, 20);
		frame.getContentPane().add(cmbYearJoined);
		
		cmbAmountOwed = new JComboBox();
		cmbAmountOwed.setModel(new DefaultComboBoxModel(new String[] {">=", "<=", "="}));
		cmbAmountOwed.setBounds(1145, 81, 40, 20);
		frame.getContentPane().add(cmbAmountOwed);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MethodClass.refresh();
				txtFirstName.setText("");
				txtLastName.setText("");
				txtMemberNumber.setText("");
				txtSchool.setText("");
				txtState.setText("");
				txtEmail.setText("");
				txtYearJoined.setText("");
				txtAmountOwed.setText("");
				cmbActive.setSelectedIndex(0);;;
			}
		});
		btnRefresh.setBounds(1243, 143, 89, 23);
		frame.getContentPane().add(btnRefresh);
		
		btnUsers = new JButton("Users");
		btnUsers.setFont(new Font("Dialog", Font.BOLD, 12));
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsersFrame user = new UsersFrame();
				user.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnUsers.setBounds(75, 373, 110, 30);
		frame.getContentPane().add(btnUsers);
		
		JLabel Active = new JLabel("Amount Owed");
		Active.setBounds(1020, 115, 125, 20);
		frame.getContentPane().add(Active);
		
		cmbActive = new JComboBox();
		cmbActive.setModel(new DefaultComboBoxModel(new String[] {"All", "Active", "Not Active"}));
		cmbActive.setBounds(1145, 112, 100, 20);
		frame.getContentPane().add(cmbActive);
		
		JLabel lblLogout = new JLabel("Logout");
		lblLogout.setBounds(1286, 11, 46, 14);
		lblLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblLogout.addMouseListener(new MouseAdapter(){
		   public void mouseClicked(MouseEvent me)
		   {
		         Login log = new Login();
		         log.frame.setVisible(true);
		         frame.dispose();
		   }
		});
		frame.getContentPane().add(lblLogout);
		
	}
	
	public static void dashboardRefresh(){
		//Getting total members from the database
		MethodClass.getDashboardInformation();
		//setting the information into the text boxes
		txtTotalMembers.setText(""+totalMembers);
		txtTotalActive.setText(""+totalActiveMembers);
		txtTotalNonActive.setText(""+totalNonActiveMembers);
		txtTotalMembersOwing.setText(""+totalMembersOwing);
		txtTotalOwingAmount.setText(""+totalOwingAmount);
	}
}
