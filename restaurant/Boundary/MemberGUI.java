

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MemberGUI {

	private JFrame frmLogin;
	private JTextField texMemNo;
	public static Menuuser ordering_1;
	/**
	 * Create the application.
	 */
	public MemberGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Member Login");
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
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Member Login:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(30, 130, 326, 206);
		frmLogin.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInput = new JLabel("Please input your Member Number:");
		lblInput.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		lblInput.setBounds(27, 61, 289, 31);
		panel.add(lblInput);
		
		texMemNo = new JTextField();
		texMemNo.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		texMemNo.setBounds(77, 102, 173, 31);
		panel.add(texMemNo);
		texMemNo.setColumns(10);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnBack) {
					frmLogin.dispose();
					new CusLoginGUI(); 
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
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnConfirm) {
					
					String membershipnumber=texMemNo.getText();
					Boolean key = verify(texMemNo.getText());
					
					 if(!key){
						String message1="Please enter the correct Member number\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
		            }
		             else{
		                String message2="Login succsessfully\n";
		                JOptionPane.showMessageDialog(frmLogin,message2);
		                ordering_1 = new Menuuser(membershipnumber,"Yes");  //Enter the ordering interface¡£
		                frmLogin.dispose();
		                
		            }
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Thanks for using the system!");
		            System.exit(0);
		        }
			}
		});
		btnConfirm.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnConfirm.setBounds(228, 407, 109, 31);
		frmLogin.getContentPane().add(btnConfirm);
		
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
	public static Boolean verify(String ID) {
		String cusID = ID;
		FileOpen.getInfoByAccount(cusID);
		
		if(Member.membershipnumber==null) {
			
			return false;
		}
		else {
			Member.membershipnumber=null;
			return true;
		}
	}
}
