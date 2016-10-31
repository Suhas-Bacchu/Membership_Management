import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;

/**
 * This class opens a new window where it 
 * shows a table with the second report 
 * with a list of all seniors
 * @author Suhas
 */
public class SecondReport extends JFrame {

	private JPanel contentPane;
	public static JTable reportTable2;
//	static Connection connection = null;
	private JButton btnExport;
	private JButton btnPrint;
	public static int totalOwing;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecondReport frame = new SecondReport();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public SecondReport(){
		setResizable(false);
		//getting the connection to the database
//		connection = sqliteConnection.dbConnector();
		//creates the screen
		initialize();
		//adds data from query to table
		MethodClass.secondReportRefresh();
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		
		setTitle("Senior Report");
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
		
		//creating the export button
		reportTable2 = new JTable();
		scrollPane.setViewportView(reportTable2);
		
		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MethodClass.exportTable(reportTable2);
			}
		});
		btnExport.setBounds(219, 401, 90, 25);
		contentPane.add(btnExport);
		
		//creating the print button
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Senior Report");
				MessageFormat footer = new MessageFormat("Total Seniors: "+totalOwing);
				try{
					reportTable2.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				}catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		btnPrint.setBounds(480, 401, 90, 25);
		contentPane.add(btnPrint);
		
	}
	
}
