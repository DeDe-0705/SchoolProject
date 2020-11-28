

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

	private JFrame frmLogin;

	/**
	 * Launch the application.
	 * @param args no meaning
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 400, 500);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel logoLabel = new JLabel("logo");
		String FilePath = this.getClass().getResource("/").getPath() + "TOROTO.jpg";
		Image logo = new ImageIcon(FilePath).getImage();
		logo = logo.getScaledInstance(120, 90, 0);
		logoLabel.setBounds(264, 0, 120, 90);
		logoLabel.setIcon(new ImageIcon(logo));
		frmLogin.getContentPane().add(logoLabel);
		
		JLabel title1 = new JLabel("Totoro");
		title1.setForeground(new Color(64, 224, 208));
		title1.setHorizontalAlignment(SwingConstants.CENTER);
		title1.setFont(new Font("Gill Sans MT", Font.BOLD, 30));
		title1.setBounds(0, 10, 264, 31);
		frmLogin.getContentPane().add(title1);
		
		JLabel title2 = new JLabel("           Ramen");
		title2.setForeground(new Color(153, 102, 0));
		title2.setFont(new Font("Gill Sans MT", Font.BOLD, 35));
		title2.setBounds(0, 48, 264, 42);
		frmLogin.getContentPane().add(title2);
		
		JButton btnCustomer = new JButton("Customer");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnCustomer) {
					frmLogin.dispose();
					new CusLoginGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnCustomer.setFont(new Font("Gill Sans MT", Font.PLAIN, 30));
		btnCustomer.setBounds(50, 132, 280, 90);
		frmLogin.getContentPane().add(btnCustomer);
		
		JButton btnAdministrator = new JButton("Administrator");
		btnAdministrator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnAdministrator) {
					frmLogin.dispose();
					new adminLogin();
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnAdministrator.setFont(new Font("Gill Sans MT", Font.PLAIN, 30));
		btnAdministrator.setBounds(50, 291, 280, 90);
		frmLogin.getContentPane().add(btnAdministrator);
		
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
	
}
