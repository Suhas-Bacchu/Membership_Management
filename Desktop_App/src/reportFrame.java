import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class opens a window where a user can select 
 * what kind of  report he wants to create
 * @author Suhas
 */
public class reportFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reportFrame frame = new reportFrame();
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
	public reportFrame() {
		setResizable(false);
		//basic window information
		setTitle("Reports");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
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
		
		//button for the first report
		JButton btnReport_1 = new JButton("Owing Member Report");
		btnReport_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FirstReport fr = new FirstReport();
				fr.setVisible(true);
				Main.frame.setEnabled(true);
				Main.frame.setVisible(false);
				dispose();
			}
		});
		btnReport_1.setBounds(30, 20, 190, 25);
		contentPane.add(btnReport_1);
		
		//button for the second report
		JButton btnReport_2 = new JButton("Senior Report");
		btnReport_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SecondReport sr = new SecondReport();
				sr.setVisible(true);
				Main.frame.setEnabled(true);
				Main.frame.setVisible(false);
				dispose();
			}
		});
		btnReport_2.setBounds(30, 130, 190, 25);
		contentPane.add(btnReport_2);
		
		// shows information about the reports
		JLabel lblListOfMembers = new JLabel("list of members who owe money which is sorted by state that has");
		lblListOfMembers.setBounds(30, 56, 394, 14);
		contentPane.add(lblListOfMembers);
		
		JLabel lblMemberNumberMember = new JLabel("the member number, member name, email, year joined,");
		lblMemberNumberMember.setBounds(30, 81, 394, 14);
		contentPane.add(lblMemberNumberMember);
		
		JLabel lblGradeInSchool = new JLabel("grade in school, and amount owed.");
		lblGradeInSchool.setBounds(30, 106, 325, 14);
		contentPane.add(lblGradeInSchool);
		
		JLabel lblListsAllSeniors = new JLabel("lists all seniors and their email addresses, sorted by state");
		lblListsAllSeniors.setBounds(30, 166, 344, 14);
		contentPane.add(lblListsAllSeniors);
	}
}
