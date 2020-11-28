

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class SpicinessLevelReportGUI {
	
	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public SpicinessLevelReportGUI() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Report for spiciness level");
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
		
		JLabel title1 = new JLabel("SPICINESS LEVEL STATUS");
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
					new AdminReportGUI(); 
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
		
		final JButton btnNext = new JButton("NEXT");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnNext) {
					frmLogin.dispose();
					new TimeSlotReportGUI(); 
		        }
		        else {
		            JOptionPane.showMessageDialog(frmLogin,"Error! Please contact admin");
		            System.exit(0);
		        }
			}
		});
		btnNext.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
		btnNext.setBounds(604, 408, 120, 31);
		frmLogin.getContentPane().add(btnNext);
		
		JPanel SpicinessLevelPanel = new JPanel();
		SpicinessLevelPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),"Spiciness", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		SpicinessLevelPanel.setBounds(10, 70, 714, 265);
		frmLogin.getContentPane().add(SpicinessLevelPanel);
		SpicinessLevelPanel.setLayout(null);
		
		JPanel LevelPanel = new JPanel();
		LevelPanel.setBorder(new TitledBorder(null,"Level", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		LevelPanel.setBounds(15, 30, 250, 220);
		SpicinessLevelPanel.add(LevelPanel);
		LevelPanel.setLayout(null);
		
		JLabel SpicinessLevel1Label = new JLabel("0  (NO)");
		SpicinessLevel1Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SpicinessLevel1Label.setBounds(20, 45, 150, 29);
		LevelPanel.add(SpicinessLevel1Label);
		
		JLabel SpicinessLevel2Label = new JLabel("1");
		SpicinessLevel2Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SpicinessLevel2Label.setBounds(20, 70, 150, 29);
		LevelPanel.add(SpicinessLevel2Label);
		
		JLabel SpicinessLevel3Label = new JLabel("2");
		SpicinessLevel3Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SpicinessLevel3Label.setBounds(20, 95, 150, 29);
		LevelPanel.add(SpicinessLevel3Label);
		
		JLabel SpicinessLevel4Label = new JLabel("3");
		SpicinessLevel4Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SpicinessLevel4Label.setBounds(20, 120, 150, 29);
		LevelPanel.add(SpicinessLevel4Label);
		
		JLabel SpicinessLevel5Label = new JLabel("4");
		SpicinessLevel5Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SpicinessLevel5Label.setBounds(20, 145, 150, 29);
		LevelPanel.add(SpicinessLevel5Label);
		
		JLabel SpicinessLevel6Label = new JLabel("5  (MAX)");
		SpicinessLevel6Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SpicinessLevel6Label.setBounds(20, 170, 150, 29);
		LevelPanel.add(SpicinessLevel6Label);
		
		JPanel bowlPanel = new JPanel();
		bowlPanel.setBorder(new TitledBorder(null,"Sold Bowls", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		bowlPanel.setBounds(277, 30, 417, 220);
		SpicinessLevelPanel.add(bowlPanel);
		bowlPanel.setLayout(null);
		
			Stats stats = new Stats();
			List<Integer> list = new ArrayList<Integer>();
			list = stats.returnData();
			String [] buffer = new String [22];
			for(int i = 0; i<22;i++) {
				buffer [i] = Integer.toString(list.get(i));
			}
			
			JLabel bowlLabel1 = new JLabel(buffer[16]);//level1_sold_bowls
			bowlLabel1.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
			bowlLabel1.setBounds(30, 45, 150, 29);
			bowlPanel.add(bowlLabel1);
			
			JLabel bowlLabel2 = new JLabel(buffer[17]);//level2_sold_bowls
			bowlLabel2.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
			bowlLabel2.setBounds(30, 70, 150, 29);
			bowlPanel.add(bowlLabel2);
			
			JLabel bowlLabel3 = new JLabel(buffer[18]);//level3_sold_bowls
			bowlLabel3.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
			bowlLabel3.setBounds(30, 95, 150, 29);
			bowlPanel.add(bowlLabel3);
			
			JLabel bowlLabel4 = new JLabel(buffer[19]);//level4_sold_bowls
			bowlLabel4.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
			bowlLabel4.setBounds(30, 120, 150, 29);
			bowlPanel.add(bowlLabel4);
			
			JLabel bowlLabel5 = new JLabel(buffer[20]);//level5_sold_bowls
			bowlLabel5.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
			bowlLabel5.setBounds(30, 145, 150, 29);
			bowlPanel.add(bowlLabel5);
			
			JLabel bowlLabel6 = new JLabel(buffer[21]);//level6_sold_bowls
			bowlLabel6.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
			bowlLabel6.setBounds(30, 170, 150, 29);
			bowlPanel.add(bowlLabel6);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
