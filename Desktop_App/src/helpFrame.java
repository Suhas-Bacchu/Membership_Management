import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 * This class opens up the help window
 * @author Suhas
 */
public class helpFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					helpFrame frame = new helpFrame();
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
	public helpFrame() {
		setResizable(false);
		
		setTitle("Help");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		ImageIcon frameIcon = new ImageIcon("fbla-icon.JPG");
		setIconImage(frameIcon.getImage());
		
		//creating a scroller for the text area
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 564, 439);
		contentPane.add(scrollPane);
		
		//viewable area for the information
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(null);
		
		
		try {
			
			// getting the written file
			BufferedReader in = new BufferedReader(new FileReader("ReadMe.txt"));
			String line;
			
			//adding data from text file to textfield
			while((line = in.readLine()) != null)
			{
				textArea.append("\n"+line);
			}
			in.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		//getting the scroller back to the top
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 
			       scrollPane.getVerticalScrollBar().setValue(0);
			   }
			});
		
	}
}
