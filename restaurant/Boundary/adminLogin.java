

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class adminLogin {
	
	public adminLogin() {
		
		this.LogGUI();
	}
	public void LogGUI() {
		JFrame login = new JFrame();
		login.setTitle("Login");
		login.setBounds(100, 100, 400, 500);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.getContentPane().setLayout(null);
		login.setVisible(true);
		GUIUtil.toCenter(login);
		
		JLabel logoLabel = new JLabel("logo");
		String FilePath = this.getClass().getResource("/").getPath() + "TOROTO.jpg";
		Image logo = new ImageIcon(FilePath).getImage();
		logo = logo.getScaledInstance(120, 90, 0);
		logoLabel.setBounds(264, 0, 120, 90);
		logoLabel.setIcon(new ImageIcon(logo));
		login.getContentPane().add(logoLabel);
		
		JLabel title1 = new JLabel("Totoro");
		title1.setForeground(new Color(64, 224, 208));
		title1.setHorizontalAlignment(SwingConstants.CENTER);
		title1.setFont(new Font("Gill Sans MT", Font.BOLD, 30));
		title1.setBounds(0, 10, 264, 31);
		login.getContentPane().add(title1);
		
		JLabel title2 = new JLabel("           Ramen");
		title2.setForeground(new Color(153, 102, 0));
		title2.setFont(new Font("Gill Sans MT", Font.BOLD, 35));
		title2.setBounds(0, 48, 264, 42);
		login.getContentPane().add(title2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Administrator Login:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(20, 130, 350, 250);
		login.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInput = new JLabel("Please input your Adminstrator Number:");
		lblInput.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		lblInput.setBounds(10, 34, 289, 31);
		panel.add(lblInput);
		
		JTextField texMemNo = new JTextField();
		texMemNo.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		texMemNo.setBounds(77, 95, 180, 31);
		panel.add(texMemNo);
		texMemNo.setColumns(10);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnBack) {
					login.dispose();
					new LoginGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(login,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnBack.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnBack.setBounds(41, 407, 115, 31);
		login.getContentPane().add(btnBack);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnConfirm) {
					
					Boolean key = logIn(texMemNo.getText());
					 if(!key){
						String message1="Please enter the correct administrator number\n";
		                JOptionPane.showMessageDialog(login,message1);
		                return;
		            }
		             else{
		                String message2="Login succsessfully\n";
		                JOptionPane.showMessageDialog(login,message2);
		                new AdminMainGUI();
		                login.dispose();
		                
		            }
		        }
		        else {
		            JOptionPane.showMessageDialog(login,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnConfirm.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnConfirm.setBounds(228, 407, 115, 31);
		login.getContentPane().add(btnConfirm);
	}
	public static Boolean logIn(String ID) {
		 String adminid= ID ;
		 adminFileOpe.getInfoByadmin(adminid);
		 
		 if(Configure.adminid==null) {
			 return false;
		 }
		 else {
			 Configure.adminid=null;
			 return true;
		 }
	 }
	
}