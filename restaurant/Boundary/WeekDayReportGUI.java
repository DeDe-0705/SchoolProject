

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class WeekDayReportGUI {
	
	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public WeekDayReportGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Report for day of each week");
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
		
		JLabel title1 = new JLabel("DAY OF EACH WEEK");
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
					new FoodIngredientsStockReportGUI(); 
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
		
		JPanel WeekDayMainPanel = new JPanel();
		WeekDayMainPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),"Week Day Sales Status", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		WeekDayMainPanel.setBounds(10, 70, 714, 285);
		frmLogin.getContentPane().add(WeekDayMainPanel);
		WeekDayMainPanel.setLayout(null);
		
		JPanel WeekDayPanel = new JPanel();
		WeekDayPanel.setBorder(new TitledBorder(null,"Week Day", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		WeekDayPanel.setBounds(15, 30, 220, 240);
		WeekDayMainPanel.add(WeekDayPanel);
		WeekDayPanel.setLayout(null);
		
		JLabel MondayLabel = new JLabel("MON.");
		MondayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		MondayLabel.setBounds(20, 45, 170, 29);
		WeekDayPanel.add(MondayLabel);
		
		JLabel TuesdayLabel = new JLabel("TUE.");
		TuesdayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		TuesdayLabel.setBounds(20, 70, 170, 29);
		WeekDayPanel.add(TuesdayLabel);
		
		JLabel WednesdayLabel = new JLabel("WED.");
		WednesdayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		WednesdayLabel.setBounds(20, 95, 170, 29);
		WeekDayPanel.add(WednesdayLabel);
		
		JLabel ThursdayLabel = new JLabel("THU.");
		ThursdayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		ThursdayLabel.setBounds(20, 120, 170, 29);
		WeekDayPanel.add(ThursdayLabel);
		
		JLabel FridayLabel = new JLabel("FRI.");
		FridayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		FridayLabel.setBounds(20, 145, 170, 29);
		WeekDayPanel.add(FridayLabel);
		
		JLabel SaturdayLabel = new JLabel("SAT.");
		SaturdayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SaturdayLabel.setBounds(20, 170, 170, 29);
		WeekDayPanel.add(SaturdayLabel);
		
		JLabel SundayLabel = new JLabel("SUN.");
		SundayLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 16));
		SundayLabel.setBounds(20, 195, 170, 29);
		WeekDayPanel.add(SundayLabel);
		
		JPanel RevenuePanel = new JPanel();
		RevenuePanel.setBorder(new TitledBorder(null,"Revenue", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		RevenuePanel.setBounds(247, 30, 220, 240);
		WeekDayMainPanel.add(RevenuePanel);
		RevenuePanel.setLayout(null);
		
		WeekRevenue revenue = new WeekRevenue();
		List<Double> data = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("#0.00");
		
		data = revenue.returnData();
		String [] buffer = new String [7];
		for(int i = 0; i<7; i++) {
			buffer[i] = df.format(data.get(i));
		}
		
		JLabel RevenueLabel1 = new JLabel(buffer[0]);//week_day_1_revenue
		RevenueLabel1.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel1.setBounds(30, 45, 170, 29);
		RevenuePanel.add(RevenueLabel1);
		
		JLabel RevenueLabel2 = new JLabel(buffer[1]);//week_day_2_revenue
		RevenueLabel2.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel2.setBounds(30, 70, 170, 29);
		RevenuePanel.add(RevenueLabel2);
		
		JLabel RevenueLabel3 = new JLabel(buffer[2]);//week_day_3_revenue
		RevenueLabel3.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel3.setBounds(30, 95, 170, 29);
		RevenuePanel.add(RevenueLabel3);
		
		JLabel RevenueLabel4 = new JLabel(buffer[3]);//week_day_4_revenue
		RevenueLabel4.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel4.setBounds(30, 120, 170, 29);
		RevenuePanel.add(RevenueLabel4);
		
		JLabel RevenueLabel5 = new JLabel(buffer[4]);//week_day_5_revenue
		RevenueLabel5.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel5.setBounds(30, 145, 170, 29);
		RevenuePanel.add(RevenueLabel5);
		
		JLabel RevenueLabel6 = new JLabel(buffer[5]);//week_day_6_revenue
		RevenueLabel6.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel6.setBounds(30, 170, 170, 29);
		RevenuePanel.add(RevenueLabel6);
		
		JLabel RevenueLabel7 = new JLabel(buffer[6]);//week_day_7_revenue
		RevenueLabel7.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		RevenueLabel7.setBounds(30, 195, 170, 29);
		RevenuePanel.add(RevenueLabel7);
		
		JPanel HolidayPanel = new JPanel();
		HolidayPanel.setBorder(new TitledBorder(null,"Holiday", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		HolidayPanel.setBounds(480, 30, 217, 240);
		WeekDayMainPanel.add(HolidayPanel);
		HolidayPanel.setLayout(null);
		
		JLabel HolidayLabel1 = new JLabel("");//week_day_1_Holiday: print if holiday; if not holiday, shows nothing
		HolidayLabel1.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel1.setBounds(30, 45, 170, 29);
		HolidayPanel.add(HolidayLabel1);
		
		JLabel HolidayLabel2 = new JLabel("");//week_day_2_Holiday
		HolidayLabel2.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel2.setBounds(30, 70, 170, 29);
		HolidayPanel.add(HolidayLabel2);
		
		JLabel HolidayLabel3 = new JLabel("");//week_day_3_Holiday
		HolidayLabel3.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel3.setBounds(30, 95, 170, 29);
		HolidayPanel.add(HolidayLabel3);
		
		JLabel HolidayLabel4 = new JLabel("");//week_day_4_Holiday
		HolidayLabel4.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel4.setBounds(30, 120, 170, 29);
		HolidayPanel.add(HolidayLabel4);
		
		JLabel HolidayLabel5 = new JLabel("");//week_day_5_Holiday
		HolidayLabel5.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel5.setBounds(30, 145, 170, 29);
		HolidayPanel.add(HolidayLabel5);
		
		JLabel HolidayLabel6 = new JLabel("");//week_day_6_Holiday
		HolidayLabel6.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel6.setBounds(30, 170, 170, 29);
		HolidayPanel.add(HolidayLabel6);
		
		JLabel HolidayLabel7 = new JLabel("");//week_day_7_Holiday
		HolidayLabel7.setFont(new Font("Gill Sans MT", Font.ITALIC, 18));
		HolidayLabel7.setBounds(30, 195, 170, 29);
		HolidayPanel.add(HolidayLabel7);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
