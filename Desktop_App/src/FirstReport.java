import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

/**
 * This class creates the first report i.e. report 
 * of the people who owe money
 * @author Suhas
 */
@SuppressWarnings("serial")
public class FirstReport extends JFrame {

	private JPanel contentPane;
	private JTextField txtTotalActive;
	private JTextField txtTotalNonActive;
	public static JTable ReportTable;
	static Connection connection = null;
	public static int activeMembers = 0;
	private JTextField txtTotalOwing;
	private JTextField txtTotalMembersOwing;
	private JButton btnExport;
	private JButton btnPrint;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstReport frame = new FirstReport();
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
	public FirstReport(){
		setResizable(false);
		//getting the connection to the database
		connection = sqliteConnection.dbConnector();
		//creates the screen
		initialize();
		//adds data from query to table
		MethodClass.firstReportRefresh();
	}
	
	public void initialize() {
		
		setTitle("Owing Member Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		setIconImage(frameIcon.getImage());
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e){
				 Main.frame.setVisible(true);
				 dispose();
			 }
		});
		
		//this is the scroller for the table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 714, 356);
		contentPane.add(scrollPane);
		
		// a new table
		ReportTable = new JTable();
		scrollPane.setViewportView(ReportTable);
		
		//label 
		JLabel lblTotalActive = new JLabel("Total Active Members");
		lblTotalActive.setBounds(10, 382, 150, 20);
		contentPane.add(lblTotalActive);
		
		JLabel lblTotalNonActive = new JLabel("Total Non-Active Members");
		lblTotalNonActive.setBounds(10, 413, 150, 20);
		contentPane.add(lblTotalNonActive);
		
		//this field displays total active members
		txtTotalActive = new JTextField();
		txtTotalActive.setEditable(false);
		txtTotalActive.setBounds(170, 382, 100, 20);
		contentPane.add(txtTotalActive);
		txtTotalActive.setColumns(10);
		
		//this field displays total non-active members
		txtTotalNonActive = new JTextField();
		txtTotalNonActive.setEditable(false);
		txtTotalNonActive.setColumns(10);
		txtTotalNonActive.setBounds(170, 413, 100, 20);
		contentPane.add(txtTotalNonActive);
		
		JLabel lblTotalOwing = new JLabel("Total Amount Owing");
		lblTotalOwing.setBounds(312, 409, 150, 20);
		contentPane.add(lblTotalOwing);
		
		//this field displays total money owed
		txtTotalOwing = new JTextField();
		txtTotalOwing.setText("0");
		txtTotalOwing.setEditable(false);
		txtTotalOwing.setColumns(10);
		txtTotalOwing.setBounds(472, 409, 100, 20);
		contentPane.add(txtTotalOwing);
		
		//this field displays total members owing
		txtTotalMembersOwing = new JTextField();
		txtTotalMembersOwing.setText("0");
		txtTotalMembersOwing.setEditable(false);
		txtTotalMembersOwing.setColumns(10);
		txtTotalMembersOwing.setBounds(472, 378, 100, 20);
		contentPane.add(txtTotalMembersOwing);
		
		JLabel lblTotalMembersOwing = new JLabel("Total Members Owing");
		lblTotalMembersOwing.setBounds(312, 378, 150, 20);
		contentPane.add(lblTotalMembersOwing);
		
		//creating the export button - Calls the method from Main.java
		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MethodClass.exportTable(ReportTable);
			}
		});
		btnExport.setBounds(616, 378, 89, 23);
		contentPane.add(btnExport);
		
		//creating the print button
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Member Owing Report");
				MessageFormat footer = new MessageFormat("Total Active Members: " + txtTotalActive.getText() + "        Total Non-Active Members: " + txtTotalNonActive.getText() + 
															"        Total Members Oweing: " + txtTotalMembersOwing.getText() + "        Total Owed: " + txtTotalOwing.getText() + "$");
				try{
					ReportTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				}catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		btnPrint.setBounds(616, 408, 89, 23);
		contentPane.add(btnPrint);
		
		//setting footer information

		txtTotalActive.setText(""+Main.totalActiveMembers);
		txtTotalNonActive.setText(""+Main.totalNonActiveMembers);
		txtTotalMembersOwing.setText(""+Main.totalMembersOwing);
		txtTotalOwing.setText(""+Main.totalOwingAmount);
	}
	
	

}
