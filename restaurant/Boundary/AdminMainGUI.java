

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.util.*;
import java.util.List;


import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMainGUI {

	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public AdminMainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Administrator Management System");
		frmLogin.setBounds(100, 100, 750, 500);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel title1 = new JLabel(" MANAGEMENT SYSTEM");
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
					new LoginGUI(); 
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
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(null, "Menu", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		menuPanel.setBounds(10, 56, 237, 332);
		frmLogin.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		
		final JButton btnModifyStock = new JButton("Modify Stock");
		btnModifyStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnModifyStock) {
					frmLogin.dispose();
					new ModifyStockGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		btnModifyStock.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnModifyStock.setBounds(32, 111, 170, 31);
		menuPanel.add(btnModifyStock);
		
		JButton btnModifyPrice = new JButton("Modify Price");
		btnModifyPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnModifyPrice) {
					frmLogin.dispose();
					new ModifyPriceGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		btnModifyPrice.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnModifyPrice.setBounds(32, 193, 170, 31);
		menuPanel.add(btnModifyPrice);
		
		JPanel reportsPanel = new JPanel();
		reportsPanel.setLayout(null);
		reportsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Reports", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), new Color(0, 0, 0)));
		reportsPanel.setBounds(248, 56, 237, 332);
		frmLogin.getContentPane().add(reportsPanel);
		
		final JButton btnViewReports = new JButton("View Reports");
		btnViewReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnViewReports) {
					frmLogin.dispose();
					new AdminReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		btnViewReports.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnViewReports.setBounds(34, 152, 170, 31);
		reportsPanel.add(btnViewReports);
		
		JPanel feedbackPanel_1 = new JPanel();
		feedbackPanel_1.setLayout(null);
		feedbackPanel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Feedback", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), new Color(0, 0, 0)));
		feedbackPanel_1.setBounds(487, 56, 237, 332);
		frmLogin.getContentPane().add(feedbackPanel_1);
		
		JButton btnSendReportTo = new JButton("Send Report");
		btnSendReportTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options ={ "Send", "Back" }; 
				Stats rank = new Stats();
				List<Integer> rankList = new ArrayList<Integer>();
				rankList = rank.returnData();
				
				int nori = rankList.get(9)+rankList.get(12);
				int chashu = rankList.get(10)+rankList.get(13); 
				int boiled = rankList.get(11)+rankList.get(14);
				
				String spi [] = {"No","level_1","level_2","level_3","level_4","Max"};
				int spiRank [] = {rankList.get(16),rankList.get(17),rankList.get(18),rankList.get(19),rankList.get(20),rankList.get(21)};
				for(int i = 0;i<5;i++) {
					for(int j = 0;j<5-i;j++) {
						if(spiRank[j]>spiRank[j+1]) {
							int temp1 = spiRank[j];
							spiRank[j] = spiRank[j+1];
							spiRank[j+1] = temp1;
							String temp2= spi[j];
							spi[j] = spi[j+1];
							spi[j+1] = temp2;
						}
					}
				}
				new Report();
				// Change "change here"
				String sendMes = "Weekly Sales Report:\nRroducts:\n    Tonkotsu: "+ rankList.get(0)
								+ "\n    Shoyu: "+ rankList.get(1) 
								+ "\n    Shio: "+ rankList.get(2) 
								+ "\n    Nori: "+ nori
								+ "\n    Chashu:"+ chashu 
								+ "\n    Egg: "+ boiled
								+ "\n    Bamboo: "+ rankList.get(15) 
								+"\nSpicy Levels:\n    1: "+ spi[5]
								+ "\n    2: "+ spi[4]
								+ "\n    3: "+ spi[3];
				int n = JOptionPane.showOptionDialog(null, sendMes, "Confirm sending message",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if(n == 0)
					;//SEND
			}
		});
		btnSendReportTo.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnSendReportTo.setBounds(33, 153, 170, 31);
		feedbackPanel_1.add(btnSendReportTo);
		
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
