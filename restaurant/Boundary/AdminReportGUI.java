

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminReportGUI {

	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public AdminReportGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Admin Reports");
		frmLogin.setBounds(100, 100, 750, 500);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel logoLabel = new JLabel("logo");
		String FilePath = this.getClass().getResource("/").getPath() + "TOROTO.jpg";
		Image logo = new ImageIcon(FilePath).getImage();
		logo = logo.getScaledInstance(100, 68, 0);
		logoLabel.setBounds(634, 0, 100, 68);
		logoLabel.setIcon(new ImageIcon(logo));
		frmLogin.getContentPane().add(logoLabel);
		
		JLabel title1 = new JLabel("REPORT");
		title1.setForeground(Color.BLACK);
		title1.setHorizontalAlignment(SwingConstants.CENTER);
		title1.setFont(new Font("Gill Sans MT", Font.BOLD, 40));
		title1.setBounds(236, 14, 264, 42);
		frmLogin.getContentPane().add(title1);
		
		JLabel title2 = new JLabel("    List");
		title2.setForeground(Color.GRAY);
		title2.setFont(new Font("Gill Sans MT", Font.BOLD, 30));
		title2.setBounds(306, 48, 147, 42);
		frmLogin.getContentPane().add(title2);
		
		final JButton btn1 = new JButton("1. Each item's sale status");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btn1) {
					frmLogin.dispose();
					new EachItemReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		/**
		 * Enter the report of each item's sale status
		 */
		btn1.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btn1.setBounds(45, 100, 645, 31);
		frmLogin.getContentPane().add(btn1);
		
		final JButton btn2 = new JButton("2. Spiciness level status");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btn2) {
					frmLogin.dispose();
					new SpicinessLevelReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		/**
		 * Enter the report of spiciness level status
		 */
		btn2.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btn2.setBounds(45, 141, 645, 31);
		frmLogin.getContentPane().add(btn2);
		
		final JButton btn3 = new JButton("3. Time slot of each day");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btn3) {
					frmLogin.dispose();
					new TimeSlotReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		/**
		 * Enter the report of time slot
		 */
		btn3.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btn3.setBounds(45, 182, 645, 31);
		frmLogin.getContentPane().add(btn3);
		
		final JButton btn4 = new JButton("4. Day of each week");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btn4) {
					frmLogin.dispose();
					new WeekDayReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		/**
		 * Enter the report of day of each week
		 */
		btn4.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btn4.setBounds(45, 223, 645, 31);
		frmLogin.getContentPane().add(btn4);
		
		final JButton btn5 = new JButton("5. Food ingredients stock status");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btn5) {
					frmLogin.dispose();
					new FoodIngredientsStockReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		/**
		 * Enter the report of food ingredients stock status
		 */
		btn5.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btn5.setBounds(45, 264, 645, 31);
		frmLogin.getContentPane().add(btn5);
		
		final JButton btnExit = new JButton("EXIT VIEWING");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnExit) {
					frmLogin.dispose();
					new AdminMainGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		btnExit.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnExit.setBounds(45, 383, 645, 31);
		frmLogin.getContentPane().add(btnExit);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
