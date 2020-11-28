

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;


import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;


public class ModifyPriceGUI {

	private JFrame frmLogin;
	private JTextField setNum_1;
	private JTextField setNum_7;
	private JTextField setNum_8;
	private JTextField setNum_9;
	private JTextField setNum_10;
	private Price price;
	/**
	 * Create the application.
	 */
	public ModifyPriceGUI() {
		try {
			this.price = new Price();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JLabel title1 = new JLabel("MODIFY THE PRICE");
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
		ramenPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		ramenPanel.setBounds(10, 56, 714, 346);
		frmLogin.getContentPane().add(ramenPanel);
		ramenPanel.setLayout(null);
		
		JPanel soupP = new JPanel();
		soupP.setBorder(new TitledBorder(null, "Ramen", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		soupP.setBounds(10, 10, 318, 326);
		ramenPanel.add(soupP);
		soupP.setLayout(null);
		
		JPanel optionP_1 = new JPanel();
		optionP_1.setLayout(null);
		optionP_1.setBounds(10, 124, 298, 45);
		soupP.add(optionP_1);
		
		JLabel lblShoyu = new JLabel("Ramen");
		lblShoyu.setHorizontalAlignment(SwingConstants.CENTER);
		lblShoyu.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblShoyu.setBounds(0, 13, 85, 20);
		optionP_1.add(lblShoyu);
		
		setNum_1 = new JTextField();
		setNum_1.setColumns(10);
		setNum_1.setBounds(187, 14, 30, 23);
		optionP_1.add(setNum_1);
		
		JButton setBtn_1 = new JButton("Set");
		setBtn_1.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_1.setBounds(227, 12, 61, 23);
		setBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Boolean key = Verify(setNum_1.getText());
				if(key) {
					String buffer = setNum_1.getText();
					try {
						price.SetPrice("1", buffer);
						String message="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message);
	                return;
				}
				
			}
		});
		optionP_1.add(setBtn_1);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblPrice.setBounds(92, 13, 85, 20);
		optionP_1.add(lblPrice);
		
		JPanel addonP = new JPanel();
		addonP.setBorder(new TitledBorder(null, "Add-ons", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		addonP.setBounds(338, 10, 366, 326);
		ramenPanel.add(addonP);
		addonP.setLayout(null);
		
		JPanel optionP_7 = new JPanel();
		optionP_7.setLayout(null);
		optionP_7.setBounds(10, 32, 346, 45);
		addonP.add(optionP_7);
		
		JLabel lblNori = new JLabel("Nori");
		lblNori.setHorizontalAlignment(SwingConstants.CENTER);
		lblNori.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblNori.setBounds(0, 13, 85, 20);
		optionP_7.add(lblNori);
		
		setNum_7 = new JTextField();
		setNum_7.setColumns(10);
		setNum_7.setBounds(235, 14, 30, 23);
		optionP_7.add(setNum_7);
		
		JButton setBtn_7 = new JButton("Set");
		setBtn_7.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_7.setBounds(275, 12, 61, 23);
		setBtn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = Verify(setNum_7.getText());
				if(key) {
					String buffer = setNum_7.getText();
					try {
						price.SetPrice("2", buffer);
						String message="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message);
	                return;
				}
			}
		});
		optionP_7.add(setBtn_7);
		
		JLabel lblPrice_1 = new JLabel("Price:");
		lblPrice_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice_1.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblPrice_1.setBounds(140, 13, 85, 20);
		optionP_7.add(lblPrice_1);
		
		JPanel optionP_8 = new JPanel();
		optionP_8.setLayout(null);
		optionP_8.setBounds(10, 111, 346, 45);
		addonP.add(optionP_8);
		
		JLabel lblEggs = new JLabel("eggs");
		lblEggs.setHorizontalAlignment(SwingConstants.CENTER);
		lblEggs.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblEggs.setBounds(0, 13, 85, 20);
		optionP_8.add(lblEggs);
		
		setNum_8 = new JTextField();
		setNum_8.setColumns(10);
		setNum_8.setBounds(235, 14, 30, 23);
		optionP_8.add(setNum_8);
		
		JButton setBtn_8 = new JButton("Set");
		setBtn_8.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_8.setBounds(275, 12, 61, 23);
		setBtn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = Verify(setNum_8.getText());
				if(key) {
					String buffer = setNum_8.getText();
					try {
						price.SetPrice("3", buffer);
						String message="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message);
	                return;
				}
			}
		});
		optionP_8.add(setBtn_8);
		
		JLabel lblPrice_2 = new JLabel("Price:");
		lblPrice_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice_2.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblPrice_2.setBounds(140, 13, 85, 20);
		optionP_8.add(lblPrice_2);
		
		JPanel optionP_9 = new JPanel();
		optionP_9.setLayout(null);
		optionP_9.setBounds(10, 184, 346, 45);
		addonP.add(optionP_9);
		
		JLabel lblChashu = new JLabel("Chashu");
		lblChashu.setHorizontalAlignment(SwingConstants.CENTER);
		lblChashu.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblChashu.setBounds(0, 13, 85, 20);
		optionP_9.add(lblChashu);
		
		setNum_9 = new JTextField();
		setNum_9.setColumns(10);
		setNum_9.setBounds(235, 14, 30, 23);
		optionP_9.add(setNum_9);
		
		JButton setBtn_9 = new JButton("Set");
		setBtn_9.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_9.setBounds(275, 12, 61, 23);
		setBtn_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = Verify(setNum_9.getText());
				if(key) {
					String buffer = setNum_9.getText();
					try {
						price.SetPrice("4", buffer);
						String message="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message);
	                return;
				}
			}
		});
		optionP_9.add(setBtn_9);
		
		JLabel lblPrice_3 = new JLabel("Price:");
		lblPrice_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice_3.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblPrice_3.setBounds(140, 13, 85, 20);
		optionP_9.add(lblPrice_3);
		
		JPanel optionP_10 = new JPanel();
		optionP_10.setLayout(null);
		optionP_10.setBounds(10, 258, 346, 45);
		addonP.add(optionP_10);
		
		JLabel lblbamboo = new JLabel("Bamboo");
		lblbamboo.setHorizontalAlignment(SwingConstants.CENTER);
		lblbamboo.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblbamboo.setBounds(0, 13, 85, 20);
		optionP_10.add(lblbamboo);
		
		setNum_10 = new JTextField();
		setNum_10.setColumns(10);
		setNum_10.setBounds(235, 14, 30, 23);
		optionP_10.add(setNum_10);
		
		JButton setBtn_10 = new JButton("Set");
		setBtn_10.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		setBtn_10.setBounds(275, 12, 61, 23);
		setBtn_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean key = Verify(setNum_10.getText());
				if(key) {
					String buffer = setNum_10.getText();
					try {
						price.SetPrice("5", buffer);
						String message="Modify Successfully!\n";
		                JOptionPane.showMessageDialog(frmLogin,message);
		                return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					String message="Error Format!\n";
	                JOptionPane.showMessageDialog(frmLogin,message);
	                return;
				}
			}
		});
		optionP_10.add(setBtn_10);
		
		JLabel lblPrice_4 = new JLabel("Price:");
		lblPrice_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice_4.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
		lblPrice_4.setBounds(140, 13, 85, 20);
		optionP_10.add(lblPrice_4);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
	public Boolean Verify(String x) {
		String reg = "^[0-9]+(.[0-9]+)?$";
		return x.matches(reg);
	}
}
