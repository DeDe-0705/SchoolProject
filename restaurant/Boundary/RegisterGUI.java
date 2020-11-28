package Login;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class RegisterGUI {
	
	private static final String EMAIL_REGEX = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private JFrame frmLogin;
	private JTextField texFName;
	private JTextField texSName;
	private JTextField texOptional;

	/**
	 * Create the application.
	 */
	public RegisterGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Register");
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
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Please enter your:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(31, 100, 326, 297);
		frmLogin.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblFName = new JLabel("First Name:");
		lblFName.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		lblFName.setBounds(27, 41, 121, 31);
		panel.add(lblFName);
		
		texFName = new JTextField();
		texFName.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		texFName.setBounds(120, 41, 173, 31);
		panel.add(texFName);
		texFName.setColumns(10);
		
		texSName = new JTextField();
		texSName.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		texSName.setColumns(10);
		texSName.setBounds(120, 82, 173, 31);
		panel.add(texSName);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		lblSurname.setBounds(27, 82, 121, 31);
		panel.add(lblSurname);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Email:");
		comboBox.addItem("Tel number:");
		comboBox.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		comboBox.setBounds(24, 127, 89, 23);
		panel.add(comboBox);
		
		texOptional = new JTextField();
		texOptional.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		texOptional.setColumns(10);
		texOptional.setBounds(120, 123, 173, 31);
		panel.add(texOptional);
		
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
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnRegister) {
					String firstname = texFName.getText();
					String surname = texSName.getText();
					String option = (String)comboBox.getSelectedItem();
					
					if(firstname.equals("") || surname.equals("")) {
						JOptionPane.showMessageDialog(frmLogin,"The name cannot be empty!");
		                return;
					}
					String rex = "^[A-Za-z]+$";
					Boolean a = firstname.matches(rex);
					Boolean b = surname.matches(rex);
					if(!a||!b) {
						JOptionPane.showMessageDialog(frmLogin,"Name format error!");
		                return;
					}
					if (option.equals("Email:")){
						String email = texOptional.getText();
						Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
						if (!emailMatcher.matches()) {
							JOptionPane.showMessageDialog(frmLogin,"Email format error!");
			                 return;
						}
						String mobilenumber = null;
						String printMessage = "Mobile Number:";
						int i=(int)((Math.random()*9+1)*10000000);
						String membershipnumber=String.valueOf(i);
						String stampnum = "0";
						FileOpen.updateCustomer(membershipnumber,firstname,surname,mobilenumber,email,stampnum);
						FileOpen.getInfoByAccount(membershipnumber);
					}
					else{
						String mobilenumber = texOptional.getText();
						if(mobilenumber.length()!=11) {
							JOptionPane.showMessageDialog(frmLogin,"Telephone format error!");
							return;
						}
						Boolean key = false;
						for(int i=0;i<mobilenumber.length();i++){
						      if (!Character.isDigit(mobilenumber.charAt(i))){
						          key = false;
						          break;
						      }
						      else {
						    	  key = true;
						      }
						}
						if(!key) {
							JOptionPane.showMessageDialog(frmLogin,"Telephone format error!");
							return;
						}
						
						String email = null;
						String printMessage = "Email:";
						int i=(int)((Math.random()*9+1)*10000000);
						String membershipnumber=String.valueOf(i);
						String stampnum = "0";
						FileOpen.updateCustomer(membershipnumber,firstname,surname,mobilenumber,email,stampnum);
						FileOpen.getInfoByAccount(membershipnumber);
					}
					
					

					String message="Successful registration!\n Your registration information is:\n";
					
		            message+="First Name:"+Member.firstname+"\n";
		            message+="Surname:"+Member.surname+"\n";
		            if (option.equals("Email:")){
		            	message+="Email:"+Member.firstname+"\n";
		            }
		            else {
		            	message+="Tel:"+Member.firstname+"\n";
		            }
		            message+="Member ID:"+Member.membershipnumber+"\n";
		            JOptionPane.showMessageDialog(frmLogin,message);
		            Member.membershipnumber = null;
		            frmLogin.dispose();
					new MemberGUI(); 

		        }
			}
		});
		btnRegister.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnRegister.setBounds(228, 407, 109, 31);
		frmLogin.getContentPane().add(btnRegister);
		
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
