

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import javax.swing.*;




public class Payment extends JFrame {
	
	private static final int width = 400;
	private static final int height = 700;
	private String OrderNumber = String.valueOf((int)((Math.random()*9+1)*1000));
	private JPanel p0,p1,p2;
	private JButton button_1,button_2,button_3,button_4,button_5;
	private JLabel DefaultLabel_1,DefaultLabel_2;
	private String membershipnumber;
	private FileAttribute Data;
	private String agree;
	private boolean Mark = false; //Mark, which is non - member login does not need to record special orders.
	
	Font f_sbig = new Font("TimesRoman", Font.BOLD, 20);
	Font f_big = new Font("TimesRoman", Font.BOLD, 15);
	Font f_mbig = new Font("TimesRoman", Font.BOLD, 25);
	/**
	 * Create the frame.
	 * @throws IOException Read file error
	 * @param x is caching dish information class 
	 */
	public Payment(FileAttribute x) {
		
		this.Data = x;
		this.membershipnumber = x.getNum();
		FileOpen.getInfoByAccount(membershipnumber);
		this.agree = x.getMark();
		if(agree.equals("No")) {
			Mark = false;
		}
		else{
			Mark = true;
		}
		
		this.setTitle("Payment");
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		this.setButton();
		this.setLabel();
		
		this.setResizable(false);		
		this.setVisible(true);
	}
	public void setButton() {
		
		p1 = new JPanel();
		p1.setBounds(10,60,400,400);
		p1.setLayout(null);
		
		button_1 = new JButton("CASH");
		button_2 = new JButton("CREDIT CARD");
		button_3 = new JButton("QR CODE");
		button_4 = new JButton("10 STAMPS*");
		button_5 = new JButton("Back");
		
		
		button_1.setBounds(35,10, 300, 60);
		button_2.setBounds(35,80, 300, 60);
		button_3.setBounds(35,150, 300, 60);
		button_4.setBounds(35,220, 300, 60);
		button_5.setBounds(20,625,100, 25);
		
		
		button_1.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
			    Result();
			   }
			  });
			  button_2.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
			    Result();
			   }
			  });
			  button_3.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				Result();
			   }
			  });
			  button_4.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				int a = Integer.parseInt(Member.stampnum);
				if(a >= 10) {
				    String stampnum = String.valueOf(a-10);
				    Member.stampnum = stampnum;
				    FileOpen.updateCustomer(Member.membershipnumber,Member.firstname,Member.surname,Member.mobilenumber,Member.email,Member.stampnum);
					Result();
				}
				else {
					NotEnough();
				}
			   }
			  });
			  button_5.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
			    dispose();
			   }
			  });
		
		p1.add(button_1);
		p1.add(button_2);
		p1.add(button_3);
		if(Mark) {
		p1.add(button_4);  //Non-members do not have the label about stamp on the payment interface.
		}
		this.getContentPane().add(button_5);
		this.add(p1);
	}
	public void setLabel() {
		
		p0 = new JPanel();
		p0.setBounds(0,0,width,40);
		p0.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
		
		p2 = new JPanel();
		p2.setBounds(0,500,400,200);
		p2.setLayout(null);
		
		DefaultLabel_1 = new JLabel("PAYMENT");
		DefaultLabel_1.setFont(f_mbig);
		DefaultLabel_2 = new JLabel("CURRENT STAMPS:                                  "+Member.stampnum+"/10");
		
		DefaultLabel_2.setBounds(60,10,400,50);
		
		
		p0.add(DefaultLabel_1);
		if(Mark) {
		p2.add(DefaultLabel_2);
		}
		this.add(p0);
		this.add(p2);
	}
	public void Result() {
		
		JFrame res = new JFrame();
		res.setTitle("Paying");
		res.setLayout(new FlowLayout(FlowLayout.CENTER,70,20));
		res.setSize(350,250);
		res.setLocationRelativeTo(null);
		res.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		res.setResizable(false);		
		res.setVisible(true);
		
		JLabel label_1 = new JLabel("Payment Successful!");
		label_1.setFont(f_mbig);
		JLabel label_2 = new JLabel("A receipt will be printed soon.");
		JLabel label_3 = new JLabel("Your order number is: "+OrderNumber);
		label_3.setFont(f_sbig);
		
		JButton ok = new JButton("OK");
		ok.setSize(100,50);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Record(Data);  //Generate a new order record
				//Update the stock
				Stock stock = new Stock();
				List<Integer> sBuffer = new ArrayList<Integer>();
				sBuffer = Data.getStock();
				try {
					stock.modifyStock(sBuffer);
				} catch (IOException e2) {
					System.out.println("Error!");
				}
				//Update member's stamp information
				if(Mark) {
					new MemberRecord(Data);
					int a = Integer.parseInt(Member.stampnum);
				    String stampnum = String.valueOf(a+1);
				    Member.stampnum = stampnum;
				    FileOpen.updateCustomer(Member.membershipnumber,Member.firstname,Member.surname,Member.mobilenumber,Member.email,Member.stampnum);
				}
				else {
					System.out.println("Non-numbership");
				}
				
				new LoginGUI();
				
				res.dispose();
				if(Mark) {
					MemberGUI.ordering_1.dispose();
					Mark = false;
				}else {
					CusLoginGUI.ordering_2.dispose();
				}
				Menuuser.check.dispose();
				Payment.this.dispose();
			}
		});
		
		res.add(label_1);
		res.add(label_3);
		res.add(label_2);
		res.add(ok);
	}
	public void NotEnough() {
		JFrame res = new JFrame();
		Container container = res.getContentPane();
		res.setTitle("Paying");
		res.setLayout(new FlowLayout(FlowLayout.CENTER,70,50));
		res.setSize(350,250);
		res.setLocationRelativeTo(null);
		res.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		res.setResizable(false);		
		res.setVisible(true);
		
		JLabel label_1 = new JLabel("Not Enough!");
		label_1.setFont(f_mbig);
		JButton ok = new JButton("OK");
		ok.setSize(100,50);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				res.dispose();
			}
		});
		res.add(label_1);
		res.add(ok);
	}
}
