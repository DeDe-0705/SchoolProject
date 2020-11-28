
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.TitledBorder;

import javax.swing.border.EtchedBorder;

public class CusLoginGUI {

	private JFrame frmLogin;
	public static Menuuser ordering_2;
	/**
	 * Create the application.
	 */
	public CusLoginGUI() {
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "You are:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(31, 100, 326, 192);
		frmLogin.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnMember = new JButton("Member");
		btnMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnMember) {
					frmLogin.dispose();
					new MemberGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnMember.setFont(new Font("Gill Sans MT", Font.PLAIN, 28));
		btnMember.setBounds(33, 34, 261, 53);
		panel.add(btnMember);
		
		JButton btnNonMember = new JButton("Non-Member");
		btnNonMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ordering_2 = new Menuuser(" ","No"); 
				frmLogin.dispose();
			}
		});
		btnNonMember.setFont(new Font("Gill Sans MT", Font.PLAIN, 28));
		btnNonMember.setBounds(33, 114, 261, 53);
		panel.add(btnNonMember);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Want to take advantage of a discount? Join us now!", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(31, 302, 326, 97);
		frmLogin.getContentPane().add(panel_1);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnRegister) {
					frmLogin.dispose();
					new RegisterGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnRegister.setFont(new Font("Gill Sans MT", Font.PLAIN, 28));
		btnRegister.setBounds(32, 25, 261, 53);
		panel_1.add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnBack) {
					frmLogin.dispose();
					new LoginGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnBack.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnBack.setBounds(41, 407, 109, 31);
		frmLogin.getContentPane().add(btnBack);
		
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
