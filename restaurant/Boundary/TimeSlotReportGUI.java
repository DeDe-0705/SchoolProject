

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class TimeSlotReportGUI {
	
	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public TimeSlotReportGUI() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		OnedayTime revenue = new OnedayTime();  //Get the data of revenue in a day.
		
		frmLogin = new JFrame();
		frmLogin.setTitle("Report for time slot of each day");
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
		
		JLabel title1 = new JLabel("TIME SLOT OF EACH DAY");
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
					new WeekDayReportGUI(); 
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
		
		JPanel TimeSlotMainPanel = new JPanel();
		TimeSlotMainPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),"Time Slot Sales Status", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		TimeSlotMainPanel.setBounds(10, 70, 714, 255);
		frmLogin.getContentPane().add(TimeSlotMainPanel);
		TimeSlotMainPanel.setLayout(null);
		
		JPanel TimeSlotPanel = new JPanel();
		TimeSlotPanel.setBorder(new TitledBorder(null,"Time Slot", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		TimeSlotPanel.setBounds(15, 30, 250, 210);
		TimeSlotMainPanel.add(TimeSlotPanel);
		TimeSlotPanel.setLayout(null);
		
		JLabel TimeSlot1Label = new JLabel("07:00 AM -- 09:00 AM");
		TimeSlot1Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TimeSlot1Label.setBounds(15, 35, 170, 29);
		TimeSlotPanel.add(TimeSlot1Label);
		
		JLabel TimeSlot2Label = new JLabel("09:00 AM -- 11:00 AM");
		TimeSlot2Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TimeSlot2Label.setBounds(15, 60, 170, 29);
		TimeSlotPanel.add(TimeSlot2Label);
		
		JLabel TimeSlot3Label = new JLabel("11:00 AM -- 02:00 PM");
		TimeSlot3Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TimeSlot3Label.setBounds(15, 85, 170, 29);
		TimeSlotPanel.add(TimeSlot3Label);
		
		JLabel TimeSlot4Label = new JLabel("02:00 PM -- 05:00 PM");
		TimeSlot4Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TimeSlot4Label.setBounds(15, 110, 170, 29);
		TimeSlotPanel.add(TimeSlot4Label);
		
		JLabel TimeSlot5Label = new JLabel("05:00 PM -- 08:00 PM");
		TimeSlot5Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TimeSlot5Label.setBounds(15, 135, 170, 29);
		TimeSlotPanel.add(TimeSlot5Label);
		
		JLabel TimeSlot6Label = new JLabel("08:00 PM -- 10:00 PM");
		TimeSlot6Label.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TimeSlot6Label.setBounds(15, 160, 170, 29);
		TimeSlotPanel.add(TimeSlot6Label);

		JPanel RevenuePanel = new JPanel();
		RevenuePanel.setBorder(new TitledBorder(null,"Revenue", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		RevenuePanel.setBounds(275, 30, 425, 210);
		TimeSlotMainPanel.add(RevenuePanel);
		RevenuePanel.setLayout(null);
		
		DecimalFormat df = new DecimalFormat("#0.00");
		String r1 = df.format(revenue.TimeRevenue(1));
		String r2 = df.format(revenue.TimeRevenue(2));
		String r3 = df.format(revenue.TimeRevenue(3));
		String r4 = df.format(revenue.TimeRevenue(4));
		String r5 = df.format(revenue.TimeRevenue(5));
		String r6 = df.format(revenue.TimeRevenue(6));
		
		JLabel RevenueLabel1 = new JLabel(r1);//Time_slot_1_revenue
		RevenueLabel1.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel1.setBounds(30, 35, 170, 29);
		RevenuePanel.add(RevenueLabel1);
		
		JLabel RevenueLabel2 = new JLabel(r2);//Time_slot_2_revenue
		RevenueLabel2.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel2.setBounds(30, 60, 170, 29);
		RevenuePanel.add(RevenueLabel2);
		
		JLabel RevenueLabel3 = new JLabel(r3);//Time_slot_3_revenue
		RevenueLabel3.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel3.setBounds(30, 85, 170, 29);
		RevenuePanel.add(RevenueLabel3);
		
		JLabel RevenueLabel4 = new JLabel(r4);//Time_slot_4_revenue
		RevenueLabel4.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel4.setBounds(30, 110, 170, 29);
		RevenuePanel.add(RevenueLabel4);
		
		JLabel RevenueLabel5 = new JLabel(r5);//Time_slot_5_revenue
		RevenueLabel5.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel5.setBounds(30, 135, 170, 29);
		RevenuePanel.add(RevenueLabel5);
		
		JLabel RevenueLabel6 = new JLabel(r6);//Time_slot_6_revenue
		RevenueLabel6.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel6.setBounds(30, 160, 170, 29);
		RevenuePanel.add(RevenueLabel6);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
