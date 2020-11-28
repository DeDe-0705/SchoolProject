

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;

import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class EachItemReportGUI {

	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public EachItemReportGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Report for each item");
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
		
		JLabel title1 = new JLabel("SALES STATUS OF EACH ITEM");
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
					new SpicinessLevelReportGUI(); 
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
		
		JPanel ramenPanel = new JPanel();
		ramenPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ramen", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		ramenPanel.setBounds(10, 56, 714, 161);
		frmLogin.getContentPane().add(ramenPanel);
		ramenPanel.setLayout(null);
		
		CalculateRevenue data_1 = new CalculateRevenue();
		Double totalRev = data_1.calculate();
		List<Integer> totalBow = data_1.calculateBowls();
		String [] B_Total =new String [1];
		String [] B_Bowls =new String [1];
		DecimalFormat df = new DecimalFormat("#0.00");
		
		for(int i = 0;i<1;i++) {
			B_Total[i] = df.format(totalRev);
			B_Bowls[i] = Integer.toString(totalBow.get(i));
		}
		JLabel bowlLabel = new JLabel("Sold bowls: "+B_Bowls[0]);//Ramen_sold_bowls
		bowlLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		bowlLabel.setBounds(348, 20, 200, 29);
		ramenPanel.add(bowlLabel);
		
		JLabel RaRvnLabel = new JLabel("Revenue: "+B_Total[0]);//Ramen_revenue
		RaRvnLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		RaRvnLabel.setBounds(544, 20, 120, 29);
		ramenPanel.add(RaRvnLabel);
		
		JPanel rp1 = new JPanel();
		rp1.setBorder(new TitledBorder(null, "Soup", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		rp1.setBounds(10, 42, 222, 109);
		ramenPanel.add(rp1);
		rp1.setLayout(null);
		
		Stats stats = new Stats();
		String [] content = new String[9];
		List<Integer> num = new ArrayList<Integer>();
		num = stats.returnData();
		for(int i = 0;i<9;i++) {
			content[i] = Integer.toString(num.get(i));
		}
		
		JLabel tonkotsuLabel = new JLabel("Tonkotsu: "+content[0]+" bowls");//tonkotsu_sold_bowls
		tonkotsuLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		tonkotsuLabel.setBounds(20, 30, 150, 29);
		rp1.add(tonkotsuLabel);
		
		JLabel shoyuLabel = new JLabel("Shoyu: "+content[1]+" bowls");//shoyu_sold_bowls
		shoyuLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		shoyuLabel.setBounds(20, 50, 150, 29);
		rp1.add(shoyuLabel);
		
		JLabel shioLabel = new JLabel("Shio: "+content[2]+" bowls");//shio_sold_bowls
		shioLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		shioLabel.setBounds(20, 70, 150, 29);
		rp1.add(shioLabel);
		
		JPanel rp2 = new JPanel();
		rp2.setBorder(new TitledBorder(null, "Noodles", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		rp2.setBounds(246, 42, 222, 109);
		ramenPanel.add(rp2);
		rp2.setLayout(null);
		
		JLabel softLabel = new JLabel("Soft: "+content[3]+" bowls");//soft_sold_bowls
		softLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		softLabel.setBounds(20, 30, 150, 29);
		rp2.add(softLabel);
		
		JLabel mediumLabel = new JLabel("Medium: "+content[4]+" bowls");//medium_sold_bowls
		mediumLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		mediumLabel.setBounds(20, 50, 150, 29);
		rp2.add(mediumLabel);
		
		JLabel firmLabel = new JLabel("Firm: "+content[5]+" bowls");//firm_sold_bowls
		firmLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		firmLabel.setBounds(20, 70, 150, 29);
		rp2.add(firmLabel);
		
		JPanel rp3 = new JPanel();
		rp3.setBorder(new TitledBorder(null, "Spring onion", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		rp3.setBounds(482, 42, 222, 109);
		ramenPanel.add(rp3);
		rp3.setLayout(null);
		
		JLabel npLabel = new JLabel("No please: "+content[6]+" bowls");//No_please_sold_bowls
		npLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		npLabel.setBounds(20, 30, 150, 29);
		rp3.add(npLabel);
		
		JLabel jalLabel = new JLabel("Just a little: "+content[7]+" bowls");//just_a_little__sold_bowls
		jalLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		jalLabel.setBounds(20, 50, 150, 29);
		rp3.add(jalLabel);
		
		JLabel alLabel = new JLabel("A lot: "+content[8]+" bowls");//a_lot_sold_bowls
		alLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		alLabel.setBounds(20, 70, 150, 29);
		rp3.add(alLabel);
		
		JPanel addOnPanel = new JPanel();
		addOnPanel.setLayout(null);
		addOnPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add-ons", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), new Color(0, 0, 0)));
		addOnPanel.setBounds(10, 216, 714, 172);
		frmLogin.getContentPane().add(addOnPanel);
		
		JPanel ap1 = new JPanel();
		ap1.setBorder(new TitledBorder(null, "Nori", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		ap1.setBounds(10, 53, 170, 109);
		addOnPanel.add(ap1);
		ap1.setLayout(null);
		
		AddOnsRevenue data = new AddOnsRevenue();
		List<Integer> bowl = data.calculateBowls();
		List<Double> price = data.calculateRevenue();
		String [] buffer_1 = new String[4];
		String [] buffer_2 = new String[4];
		String buffer_3 = null;
		
		buffer_3 = df.format(price.get(0)+price.get(1)+price.get(2)+price.get(3));
		
		for(int i = 0; i<4;i++) {
			buffer_1[i] = Integer.toString(bowl.get(i));
			buffer_2[i] = df.format(price.get(i));
		}
		
		JLabel noriLabel1 = new JLabel("Sold amount: "+buffer_1[0]);//nori_sold_amount
		noriLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		noriLabel1.setBounds(20, 30, 150, 29);
		ap1.add(noriLabel1);
		
		JLabel noriLabel2 = new JLabel("Revenue: "+buffer_2[0]);//nori_revenue
		noriLabel2.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		noriLabel2.setBounds(20, 50, 150, 29);
		ap1.add(noriLabel2);
		
		JPanel ap2 = new JPanel();
		ap2.setBorder(new TitledBorder(null, "Boiled egg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ap2.setBounds(185, 53, 170, 109);
		addOnPanel.add(ap2);
		ap2.setLayout(null);
		
		JLabel beLabel1 = new JLabel("Sold amount: "+buffer_1[2]);//Boiled_egg_sold_amount
		beLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		beLabel1.setBounds(20, 30, 150, 29);
		ap2.add(beLabel1);
		
		JLabel beLabel2 = new JLabel("Revenue: "+buffer_2[2]);//Boiled_egg_revenue
		beLabel2.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		beLabel2.setBounds(20, 50, 150, 29);
		ap2.add(beLabel2);
		
		JPanel ap3 = new JPanel();
		ap3.setBorder(new TitledBorder(null, "Chashu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ap3.setBounds(359, 53, 170, 109);
		addOnPanel.add(ap3);
		ap3.setLayout(null);
		
		JLabel chashuLabel1 = new JLabel("Sold amount: "+buffer_1[1]);//Chashu_sold_amount
		chashuLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		chashuLabel1.setBounds(20, 30, 150, 29);
		ap3.add(chashuLabel1);
		
		JLabel chashuLabel2 = new JLabel("Revenue: "+buffer_2[1]);//Chashu_egg_revenue
		chashuLabel2.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		chashuLabel2.setBounds(20, 50, 150, 29);
		ap3.add(chashuLabel2);
		
		JPanel ap4 = new JPanel();
		ap4.setBorder(new TitledBorder(null, "Bamboo shoots", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ap4.setBounds(534, 53, 170, 109);
		addOnPanel.add(ap4);
		ap4.setLayout(null);
		
		JLabel bsLabel1 = new JLabel("Sold amount: "+buffer_1[3]);//Bamboo_shoots_sold_amount
		bsLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		bsLabel1.setBounds(20, 30, 150, 29);
		ap4.add(bsLabel1);
		
		JLabel bsLabel2 = new JLabel("Revenue: "+buffer_2[3]);//Bamboo_shoots_egg_revenue
		bsLabel2.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		bsLabel2.setBounds(20, 50, 150, 29);
		ap4.add(bsLabel2);
		
		JLabel AdRvnLabel = new JLabel("Revenue: "+buffer_3);//Adds_on_revenue
		AdRvnLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
		AdRvnLabel.setBounds(544, 21, 120, 29);
		addOnPanel.add(AdRvnLabel);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
