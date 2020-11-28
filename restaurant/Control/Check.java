

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Check extends JFrame {
	
	private JPanel p0,p1,p2,p3,p4;
	private JButton sur, ret;
	private JLabel data_1,data_2,data_3,data_4,data_5,data_6,data_7,data_8,
	data_9,data_10,data_11,data_12,data_13;  
	private JLabel defaultlabel_1,defaultlabel_2,defaultlabel_3;
	private FileAttribute Buffer;
	Font f_mbig = new Font("TimesRoman", Font.BOLD, 25);
	Font f_big = new Font("TimesRoman", Font.BOLD, 15);
	private static final int width = 400;
	private static final int height = 700;
	private String membershipnumber;
	private Payment payment;
	/**
	 * Create the frame.
	 * @param
	 * y is membershipnumber 
	 * @param
	 * x are selected dishes
	 * @param 
	 * m is caching dish information class 
	 */
	public Check(String y ,String [] x, FileAttribute m) {
		
		this.membershipnumber = y;
		this.Buffer = m;  //Read record about the information
		//Add record
		data_1 = new JLabel(x[0]+" $");
		data_1.setFont(f_big);
		data_2 = new JLabel(x[1]);
		data_2.setFont(f_big);
		data_3 = new JLabel(x[2]);
		data_3.setFont(f_big);
		data_4 = new JLabel(x[3]);
		data_4.setFont(f_big);
		data_5 = new JLabel(x[4]);
		data_5.setFont(f_big);
		data_6 = new JLabel(x[5]);
		data_6.setFont(f_big);
		data_7 = new JLabel(x[6]);
		data_7.setFont(f_big);
		data_8 = new JLabel(x[7]);
		data_8.setFont(f_big);
		data_9 = new JLabel(x[8]);
		data_9.setFont(f_big);
		data_10 = new JLabel(x[9]);
		data_10.setFont(f_big);
		data_11 = new JLabel(x[10]);
		data_11.setFont(f_big);
		data_12 = new JLabel(x[11]);
		data_12.setFont(f_big);
		data_13 = new JLabel(x[12]);
		data_13.setFont(f_big);
		
		this.setTitle("Check");
		this.setLayout(null);
		this.setSize(width, height);
		
		this.setLabel();
		this.setOrders();
		this.setButtons();
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);		
		this.setVisible(true);
	}

	public void setButtons() {
		
		sur = new JButton("Confirm");
		ret = new JButton("Back");
		ret.setBounds(20,625,100, 25);
		sur.setBounds(265,625,100, 25);
		
		sur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payment = new Payment(Buffer);  //Enter the payment interface
				
			}
		});
		ret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		this.getContentPane().add(ret);
		this.getContentPane().add(sur);
		
	}
	public void setOrders() {
		
		p1 = new JPanel();
		p1.setBounds(10,60,360,400);
		p1.setLayout(null);
		p1.setBorder(BorderFactory.createEtchedBorder());
		
		//The layout of the data
		JLabel label_1 = new JLabel("Soup: ");
		label_1.setFont(f_big);
		label_1.setBounds(10,5,100,25);
		JLabel label_2 = new JLabel("Noodles: ");
		label_2.setFont(f_big);
		label_2.setBounds(10,40,100,25);
		JLabel label_3 = new JLabel("Spring onion:");
		label_3.setFont(f_big);
		label_3.setBounds(10,75,100,25);
		JLabel label_4 = new JLabel("Nori: ");
		label_4.setFont(f_big);
		label_4.setBounds(10,110,100,25);
		JLabel label_5 = new JLabel("Chashu: ");
		label_5.setFont(f_big);
		label_5.setBounds(10,145,100,25);
		JLabel label_6 = new JLabel("egg: ");
		label_6.setFont(f_big);
		label_6.setBounds(10,180,100,25);
		JLabel label_7 = new JLabel("Spiciness: ");
		label_7.setFont(f_big);
		label_7.setBounds(10,215,100,25);
		JLabel label_8 = new JLabel("Add-ons: ");
		label_8.setFont(f_big);
		label_8.setBounds(10,250,100,25);
		JLabel label_9 = new JLabel("Nori ");
		label_9.setFont(f_big);
		label_9.setBounds(50,290,120,25);
		JLabel label_10 = new JLabel("Chashu ");
		label_10.setFont(f_big);
		label_10.setBounds(210,290,120,25);
		JLabel label_11 = new JLabel("Boiled egg ");
		label_11.setFont(f_big);
		label_11.setBounds(50,330,120,25);
		JLabel label_12 = new JLabel("Bamboo shoots ");
		label_12.setFont(f_big);
		label_12.setBounds(210,330,125,25);
		
		p1.add(label_1);
		p1.add(label_2);
		p1.add(label_3);
		p1.add(label_4);
		p1.add(label_5);
		p1.add(label_6);
		p1.add(label_7);
		p1.add(label_8);
		p1.add(label_9);
		p1.add(label_10);
		p1.add(label_11);
		p1.add(label_12);
		
		p1.add(data_2);
		data_2.setBounds(270,5,100,25);
		p1.add(data_3);
		data_3.setBounds(270,40,100,25);
		p1.add(data_4);
		data_4.setBounds(270,75,100,25);
		p1.add(data_5);
		data_5.setBounds(300,110,100,25);
		p1.add(data_6);
		data_6.setBounds(300,145,100,25);
		p1.add(data_7);
		data_7.setBounds(300,180,100,25);
		p1.add(data_8);
		data_8.setBounds(35,295,15,10);
		p1.add(data_9);
		data_9.setBounds(195,295,15,10);
		p1.add(data_10);
		data_10.setBounds(35,335,15,10);
		p1.add(data_11);
		data_11.setBounds(195,335,15,10);
		p1.add(data_12);
		data_12.setBounds(305,215,100,25);
		
		this.add(p1);
	}
	public void setLabel() {
		
		p0 = new JPanel();
		p0.setBounds(0,0,width,40);
		p0.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
		
		p2 = new JPanel();
		p2.setBounds(10,480,width,20);
		p2.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		
		p3 = new JPanel();
		p3.setBounds(0,520,width,20);
		p3.setLayout(null);
		
		p4 = new JPanel();
		p4.setBounds(0,550,width,40);
		p4.setLayout(null);
		
		defaultlabel_1 = new JLabel("CHECK");
		defaultlabel_1.setFont(f_mbig);
		p0.add(defaultlabel_1);
		
		defaultlabel_2 = new JLabel("DINING OPTIONS: ");
		defaultlabel_2.setFont(f_big);
		p2.add(defaultlabel_2);
		
		p3.add(new JLabel());
		data_13.setBounds(150, 0, 100, 20);
		p3.add(data_13);
		
		defaultlabel_3 = new JLabel("PRICE: ");
		defaultlabel_3.setFont(f_big);
		defaultlabel_3.setBounds(90,10,100,30);
		data_1.setBounds(210,10,100,30);
		
		p4.add(defaultlabel_3);
		p4.add(data_1);
		
		this.add(p0);
		this.add(p2);
		this.add(p3);
		this.add(p4);
	}
	
}
