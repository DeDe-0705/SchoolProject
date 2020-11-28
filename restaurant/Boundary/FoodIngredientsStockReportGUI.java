

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class FoodIngredientsStockReportGUI {
	
	private JFrame frmLogin;

	/**
	 * Create the application.
	 */
	public FoodIngredientsStockReportGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Report for food ingredients stock");
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
		
		JLabel title1 = new JLabel("FOOD INGREDIENTS STOCK STATUS");
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
		
		JButton btnNext = new JButton("Finish");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== btnNext) {
					frmLogin.dispose();
					new AdminReportGUI(); 
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
		
		JPanel FoodIngredientsStockPanel = new JPanel();
		FoodIngredientsStockPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),"Food Ingredients Stock", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		FoodIngredientsStockPanel.setBounds(10, 70, 714, 335);
		frmLogin.getContentPane().add(FoodIngredientsStockPanel);
		FoodIngredientsStockPanel.setLayout(null);
		
		JPanel IngredientsPanel = new JPanel();
		IngredientsPanel.setBorder(new TitledBorder(null,"Ingredients", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		IngredientsPanel.setBounds(15, 30, 200, 295);
		FoodIngredientsStockPanel.add(IngredientsPanel);
		IngredientsPanel.setLayout(null);
		
		JLabel TonkotsuLabel = new JLabel("Tonkotsu");
		TonkotsuLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		TonkotsuLabel.setBounds(30, 55, 170, 29);
		IngredientsPanel.add(TonkotsuLabel);
		
		JLabel ShoyuLabel = new JLabel("Shoyu");
		ShoyuLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		ShoyuLabel.setBounds(30, 75, 170, 29);
		IngredientsPanel.add(ShoyuLabel);
		
		JLabel ShioLabel = new JLabel("Shio");
		ShioLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		ShioLabel.setBounds(30, 95, 170, 29);
		IngredientsPanel.add(ShioLabel);
		
		JLabel NoriLabel = new JLabel("Nori");
		NoriLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		NoriLabel.setBounds(30, 115, 170, 29);
		IngredientsPanel.add(NoriLabel);
		
		JLabel ChashuLabel = new JLabel("Chashu");
		ChashuLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		ChashuLabel.setBounds(30, 135, 170, 29);
		IngredientsPanel.add(ChashuLabel);
		
		JLabel EggLabel = new JLabel("Egg");
		EggLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		EggLabel.setBounds(30, 155, 170, 29);
		IngredientsPanel.add(EggLabel);
		
		JLabel BambooShootsLabel = new JLabel("Bamboo shoots");
		BambooShootsLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		BambooShootsLabel.setBounds(30, 175, 170, 29);
		IngredientsPanel.add(BambooShootsLabel);
		
		JLabel SoftLabel = new JLabel("Soft");
		SoftLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		SoftLabel.setBounds(30, 195, 170, 29);
		IngredientsPanel.add(SoftLabel);
		
		JLabel MediumLabel = new JLabel("Medium");
		MediumLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		MediumLabel.setBounds(30, 215, 170, 29);
		IngredientsPanel.add(MediumLabel);
		
		JLabel FirmLabel = new JLabel("Firm");
		FirmLabel.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		FirmLabel.setBounds(30, 235, 170, 29);
		IngredientsPanel.add(FirmLabel);
		
		JPanel StatusPanel = new JPanel();
		StatusPanel.setBorder(new TitledBorder(null,"Status", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 22), null));
		StatusPanel.setBounds(224, 30, 477, 295);
		FoodIngredientsStockPanel.add(StatusPanel);
		StatusPanel.setLayout(null);
		
		JPanel StockPanel = new JPanel();
		StockPanel.setBorder(new TitledBorder(null,"Stock", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 18), null));
		StockPanel.setBounds(15, 30, 200, 255);
		StatusPanel.add(StockPanel);
		StockPanel.setLayout(null);
		
		Stock stock = new Stock();
		List<Integer> data = stock.returnData();
		String [] buffer = new String [10];
		String [] buffer_1 = new String [10];
		for(int i = 0; i<10; i++) {
			buffer[i] = Integer.toString(data.get(i));
			buffer_1[i] = Integer.toString(500-data.get(i));
		}
		
		JLabel StockLabel1 = new JLabel(buffer[0]);//Tonkotsu_stock
		StockLabel1.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel1.setBounds(30, 25, 170, 29);
		StockPanel.add(StockLabel1);
		
		JLabel StockLabel2 = new JLabel(buffer[1]);//Shoyu_stock
		StockLabel2.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel2.setBounds(30, 45, 170, 29);
		StockPanel.add(StockLabel2);
		
		JLabel StockLabel3 = new JLabel(buffer[2]);//Shio_stock
		StockLabel3.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel3.setBounds(30, 65, 170, 29);
		StockPanel.add(StockLabel3);
		
		JLabel StockLabel4 = new JLabel(buffer[3]);//Nori_stock
		StockLabel4.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel4.setBounds(30, 85, 170, 29);
		StockPanel.add(StockLabel4);
		
		JLabel StockLabel5 = new JLabel(buffer[4]);//Chashu_stock
		StockLabel5.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel5.setBounds(30, 105, 170, 29);
		StockPanel.add(StockLabel5);
		
		JLabel StockLabel6 = new JLabel(buffer[5]);//Egg_stock
		StockLabel6.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel6.setBounds(30, 125, 170, 29);
		StockPanel.add(StockLabel6);
		
		JLabel StockLabel7 = new JLabel(buffer[6]);//Bamboo_shoots_stock
		StockLabel7.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel7.setBounds(30, 145, 170, 29);
		StockPanel.add(StockLabel7);
		
		JLabel StockLabel8 = new JLabel(buffer[7]);//Soft_stock
		StockLabel8.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel8.setBounds(30, 165, 170, 29);
		StockPanel.add(StockLabel8);
		
		JLabel StockLabel9 = new JLabel(buffer[8]);//Medium_stock
		StockLabel9.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel9.setBounds(30, 185, 170, 29);
		StockPanel.add(StockLabel9);
		
		JLabel StockLabel10 = new JLabel(buffer[9]);//Firm_stock
		StockLabel10.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		StockLabel10.setBounds(30, 205, 170, 29);
		StockPanel.add(StockLabel10);

		JPanel QuantityDemandedPanel = new JPanel();
		QuantityDemandedPanel.setBorder(new TitledBorder(null,"Quantity Demanded", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans MT", Font.PLAIN, 18), null));
		QuantityDemandedPanel.setBounds(226, 30, 235, 255);
		StatusPanel.add(QuantityDemandedPanel);
		QuantityDemandedPanel.setLayout(null);

		JLabel QDLabel1 = new JLabel(buffer_1[0]);//Tonkotsu_quantity_demanded
		QDLabel1.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel1.setBounds(30, 25, 170, 29);
		QuantityDemandedPanel.add(QDLabel1);
		
		JLabel QDLabel2 = new JLabel(buffer_1[1]);//Shoyu_quantity_demanded
		QDLabel2.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel2.setBounds(30, 45, 170, 29);
		QuantityDemandedPanel.add(QDLabel2);
		
		JLabel QDLabel3 = new JLabel(buffer_1[2]);//Shio_quantity_demanded
		QDLabel3.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel3.setBounds(30, 65, 170, 29);
		QuantityDemandedPanel.add(QDLabel3);
		
		JLabel QDLabel4 = new JLabel(buffer_1[3]);//Nori_quantity_demanded
		QDLabel4.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel4.setBounds(30, 85, 170, 29);
		QuantityDemandedPanel.add(QDLabel4);
		
		JLabel QDLabel5 = new JLabel(buffer_1[4]);//Chashu_quantity_demanded
		QDLabel5.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel5.setBounds(30, 105, 170, 29);
		QuantityDemandedPanel.add(QDLabel5);
		
		JLabel QDLabel6 = new JLabel(buffer_1[5]);//Egg_quantity_demanded
		QDLabel6.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel6.setBounds(30, 125, 170, 29);
		QuantityDemandedPanel.add(QDLabel6);
		
		JLabel QDLabel7 = new JLabel(buffer_1[6]);//Bamboo_shoots_quantity_demanded
		QDLabel7.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel7.setBounds(30, 145, 170, 29);
		QuantityDemandedPanel.add(QDLabel7);
		
		JLabel QDLabel8 = new JLabel(buffer_1[7]);//Soft_quantity_demanded
		QDLabel8.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel8.setBounds(30, 165, 170, 29);
		QuantityDemandedPanel.add(QDLabel8);
		
		JLabel QDLabel9 = new JLabel(buffer_1[8]);//Medium_quantity_demanded
		QDLabel9.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel9.setBounds(30, 185, 170, 29);
		QuantityDemandedPanel.add(QDLabel9);
		
		JLabel QDLabel10 = new JLabel(buffer_1[9]);//Firm_quantity_demanded
		QDLabel10.setFont(new Font("Gill Sans MT", Font.ITALIC, 14));
		QDLabel10.setBounds(30, 205, 170, 29);
		QuantityDemandedPanel.add(QDLabel10);
		
		GUIUtil.toCenter(frmLogin);
		frmLogin.setVisible(true);
	}
}
