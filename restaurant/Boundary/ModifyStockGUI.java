

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;



import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ModifyStockGUI {

	private JFrame frmLogin;
	private JTextField setNum;
	private JTextField setNum_1;
	private JTextField setNum_2;
	private JTextField setNum_3;
	private JTextField setNum_4;
	private JTextField setNum_5;
	private JTextField setNum_6;
	private JTextField setNum_7;
	private JTextField setNum_8;
	private JTextField setNum_9;
	private JTextField setNum_10;
	private Stock stock;
	/**
	 * Create the application.
	 */
	public ModifyStockGUI() {
		this.stock = new Stock();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Modify Menu");
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
		
		JLabel title1 = new JLabel("MODIFY THE STOCK");
		title1.setForeground(Color.BLACK);
		title1.setHorizontalAlignment(SwingConstants.CENTER);
		title1.setFont(new Font("Gill Sans MT", Font.BOLD, 30));
		title1.setBounds(45, 10, 559, 42);
		frmLogin.getContentPane().add(title1);
		
		final JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnBack) {
					frmLogin.dispose();
					new AdminMainGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		btnBack.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnBack.setBounds(25, 408, 120, 31);
		frmLogin.getContentPane().add(btnBack);
		
		JPanel ramenPanel = new JPanel();
		ramenPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ramen", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		ramenPanel.setBounds(10, 56, 714, 346);
		frmLogin.getContentPane().add(ramenPanel);
		ramenPanel.setLayout(null);
		
		JPanel soupP = new JPanel();
		soupP.setBorder(new TitledBorder(null, "Soup", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		soupP.setBounds(10, 42, 222, 294);
		ramenPanel.add(soupP);
		soupP.setLayout(null);
		
		JPanel optionP = new JPanel();
		optionP.setBounds(10, 40, 202, 45);
		soupP.add(optionP);
		optionP.setLayout(null);
		
		JLabel lblTonkotsu = new JLabel("1. Tonkotsu");
		lblTonkotsu.setBounds(0, 13, 85, 20);
		lblTonkotsu.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		optionP.add(lblTonkotsu);
		
		setNum = new JTextField();
		setNum.setBounds(101, 12, 30, 23);
		optionP.add(setNum);
		setNum.setColumns(10);
		
		JButton setBtn = new JButton("Set");
		setBtn.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn.setBounds(141, 12, 61, 23);
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum.getText());
					try {
						stock.addStock(1, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP.add(setBtn);
		
		JPanel optionP_1 = new JPanel();
		optionP_1.setLayout(null);
		optionP_1.setBounds(10, 124, 202, 45);
		soupP.add(optionP_1);
		
		JLabel lblShoyu = new JLabel("2. Shoyu");
		lblShoyu.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblShoyu.setBounds(0, 13, 85, 20);
		optionP_1.add(lblShoyu);
		
		setNum_1 = new JTextField();
		setNum_1.setColumns(10);
		setNum_1.setBounds(101, 12, 30, 23);
		optionP_1.add(setNum_1);
		
		JButton setBtn_1 = new JButton("Set");
		setBtn_1.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_1.setBounds(141, 12, 61, 23);
		setBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_1.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_1.getText());
					try {
						stock.addStock(2, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_1.add(setBtn_1);
		
		JPanel optionP_2 = new JPanel();
		optionP_2.setLayout(null);
		optionP_2.setBounds(10, 211, 202, 45);
		soupP.add(optionP_2);
		
		JLabel lblShio = new JLabel("3. Shio");
		lblShio.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblShio.setBounds(0, 13, 85, 20);
		optionP_2.add(lblShio);
		
		setNum_2 = new JTextField();
		setNum_2.setColumns(10);
		setNum_2.setBounds(101, 12, 30, 23);
		optionP_2.add(setNum_2);
		
		JButton setBtn_2 = new JButton("Set");
		setBtn_2.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_2.setBounds(141, 12, 61, 23);
		setBtn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_2.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_2.getText());
					try {
						stock.addStock(3, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_2.add(setBtn_2);
		
		JPanel noodlesP = new JPanel();
		noodlesP.setBorder(new TitledBorder(null, "Noodles", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		noodlesP.setBounds(246, 42, 222, 294);
		ramenPanel.add(noodlesP);
		noodlesP.setLayout(null);
		
		JPanel optionP_3 = new JPanel();
		optionP_3.setLayout(null);
		optionP_3.setBounds(10, 40, 202, 45);
		noodlesP.add(optionP_3);
		
		JLabel lblSoft = new JLabel("1. Soft");
		lblSoft.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblSoft.setBounds(0, 13, 85, 20);
		optionP_3.add(lblSoft);
		
		setNum_3 = new JTextField();
		setNum_3.setColumns(10);
		setNum_3.setBounds(101, 12, 30, 23);
		optionP_3.add(setNum_3);
		
		JButton setBtn_3 = new JButton("Set");
		setBtn_3.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_3.setBounds(141, 12, 61, 23);
		setBtn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_3.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_3.getText());
					try {
						stock.addStock(8, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_3.add(setBtn_3);
		
		JPanel optionP_4 = new JPanel();
		optionP_4.setLayout(null);
		optionP_4.setBounds(10, 124, 202, 45);
		noodlesP.add(optionP_4);
		
		JLabel lblMedium = new JLabel("2. Medium");
		lblMedium.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblMedium.setBounds(0, 13, 85, 20);
		optionP_4.add(lblMedium);
		
		setNum_4 = new JTextField();
		setNum_4.setColumns(10);
		setNum_4.setBounds(101, 12, 30, 23);
		optionP_4.add(setNum_4);
		
		JButton setBtn_4 = new JButton("Set");
		setBtn_4.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_4.setBounds(141, 12, 61, 23);
		setBtn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=setNum_4.getText().length();--i>=0;){
				      if (!Character.isDigit(setNum_4.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_4.getText());
					try {
						stock.addStock(9, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_4.add(setBtn_4);
		
		JPanel optionP_5 = new JPanel();
		optionP_5.setLayout(null);
		optionP_5.setBounds(10, 210, 202, 45);
		noodlesP.add(optionP_5);
		
		JLabel lblFirm = new JLabel("3. Firm");
		lblFirm.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblFirm.setBounds(0, 13, 85, 20);
		optionP_5.add(lblFirm);
		
		setNum_5 = new JTextField();
		setNum_5.setColumns(10);
		setNum_5.setBounds(101, 12, 30, 23);
		optionP_5.add(setNum_5);
		
		JButton setBtn_5 = new JButton("Set");
		setBtn_5.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_5.setBounds(141, 12, 61, 23);
		setBtn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_5.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_5.getText());
					try {
						stock.addStock(10, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_5.add(setBtn_5);
		
		JPanel addonP = new JPanel();
		addonP.setBorder(new TitledBorder(null, "Spring Onion and Add-ons", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 18), null));
		addonP.setBounds(482, 43, 222, 293);
		ramenPanel.add(addonP);
		addonP.setLayout(null);
		
		JPanel optionP_6 = new JPanel();
		optionP_6.setLayout(null);
		optionP_6.setBounds(10, 21, 202, 45);
		addonP.add(optionP_6);
		
		JLabel lblSpringOnion = new JLabel("1. Onion");
		lblSpringOnion.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblSpringOnion.setBounds(0, 13, 85, 20);
		optionP_6.add(lblSpringOnion);
		
		setNum_6 = new JTextField();
		setNum_6.setColumns(10);
		setNum_6.setBounds(101, 12, 30, 23);
		optionP_6.add(setNum_6);
		
		JButton setBtn_6 = new JButton("Set");
		setBtn_6.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_6.setBounds(141, 12, 61, 23);
		setBtn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_6.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					String message1="Modify Successfully!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_6.add(setBtn_6);
		
		JPanel optionP_7 = new JPanel();
		optionP_7.setLayout(null);
		optionP_7.setBounds(10, 73, 202, 45);
		addonP.add(optionP_7);
		
		JLabel lblNori = new JLabel("2. Nori");
		lblNori.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblNori.setBounds(0, 13, 85, 20);
		optionP_7.add(lblNori);
		
		setNum_7 = new JTextField();
		setNum_7.setColumns(10);
		setNum_7.setBounds(101, 12, 30, 23);
		optionP_7.add(setNum_7);
		
		JButton setBtn_7 = new JButton("Set");
		setBtn_7.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_7.setBounds(141, 12, 61, 23);
		setBtn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_7.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_7.getText());
					try {
						stock.addStock(4, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_7.add(setBtn_7);
		
		JPanel optionP_8 = new JPanel();
		optionP_8.setLayout(null);
		optionP_8.setBounds(10, 128, 202, 45);
		addonP.add(optionP_8);
		
		JLabel lblEggs = new JLabel("3. eggs");
		lblEggs.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblEggs.setBounds(0, 13, 85, 20);
		optionP_8.add(lblEggs);
		
		setNum_8 = new JTextField();
		setNum_8.setColumns(10);
		setNum_8.setBounds(101, 12, 30, 23);
		optionP_8.add(setNum_8);
		
		JButton setBtn_8 = new JButton("Set");
		setBtn_8.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_8.setBounds(141, 12, 61, 23);
		setBtn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_8.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_8.getText());
					try {
						stock.addStock(5, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_8.add(setBtn_8);
		
		JPanel optionP_9 = new JPanel();
		optionP_9.setLayout(null);
		optionP_9.setBounds(10, 183, 202, 45);
		addonP.add(optionP_9);
		
		JLabel lblChashu = new JLabel("4. Chashu");
		lblChashu.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblChashu.setBounds(0, 13, 85, 20);
		optionP_9.add(lblChashu);
		
		setNum_9 = new JTextField();
		setNum_9.setColumns(10);
		setNum_9.setBounds(101, 12, 30, 23);
		optionP_9.add(setNum_9);
		
		JButton setBtn_9 = new JButton("Set");
		setBtn_9.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_9.setBounds(141, 12, 61, 23);
		setBtn_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_9.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_9.getText());
					try {
						stock.addStock(6, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_9.add(setBtn_9);
		
		JPanel optionP_10 = new JPanel();
		optionP_10.setLayout(null);
		optionP_10.setBounds(10, 238, 202, 45);
		addonP.add(optionP_10);
		
		JLabel lblbamboo = new JLabel("5.Bamboo");
		lblbamboo.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblbamboo.setBounds(0, 13, 85, 20);
		optionP_10.add(lblbamboo);
		
		setNum_10 = new JTextField();
		setNum_10.setColumns(10);
		setNum_10.setBounds(101, 12, 30, 23);
		optionP_10.add(setNum_10);
		
		JButton setBtn_10 = new JButton("Set");
		setBtn_10.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_10.setBounds(141, 12, 61, 23);
		setBtn_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = false;
				for(int i=0;i<setNum.getText().length();i++){
				      if (!Character.isDigit(setNum_10.getText().charAt(i))){
				          key = false;
				          break;
				      }
				      else {
				    	  key = true;
				      }
				}
				if(key) {
					int buffer = Integer.valueOf(setNum_10.getText());
					try {
						stock.addStock(7, buffer);
						String message1="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message1);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message1="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message1);
	                return;
				}
			}
		});
		optionP_10.add(setBtn_10);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
