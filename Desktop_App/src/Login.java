import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login {

	public static JFrame frame;
	private JPasswordField password;
	private JLabel lblPassword;
	private JLabel lblUsername;
	private JTextField txtUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		frame.setIconImage(frameIcon.getImage());
		
		password = new JPasswordField();
		password.setBounds(226, 177, 100, 20);
		frame.getContentPane().add(password);
		
		JLabel lblImage = new JLabel("");
		ImageIcon img = new ImageIcon("FBLA.png");
		Image i = img.getImage();
		Image newI = i.getScaledInstance( 225, 125, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(newI);
		lblImage.setIcon(newImg);
		lblImage.setBounds(101, 11, 225, 125);
		frame.getContentPane().add(lblImage);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				MethodClass.accountLogin(txtUsername.getText(), password.getText());
			}
		});
		btnLogin.setBounds(170, 220, 100, 30);
		frame.getContentPane().add(btnLogin);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(101, 177, 100, 20);
		frame.getContentPane().add(lblPassword);
		
		lblUsername = new JLabel("Username");
		lblUsername.setBounds(101, 147, 100, 20);
		frame.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(226, 147, 100, 20);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
	}
}
