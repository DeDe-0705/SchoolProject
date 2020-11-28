

import java.io.*;
import java.text.DecimalFormat;
import java.awt.*;
import javax.swing.*;



import java.util.*;
import java.util.List;


import java.awt.event.*;

public class Menuuser extends JFrame{
	
	public static int switch_1;
	public static Check check;
	
	private String membershipnumber;
	private JButton sur, ret;
	private JRadioButton sou1,sou2,sou3,noo1,noo2,noo3,spr1,spr2,spr3,eatin,takeaway;
	private JRadioButton yes1,yes2,yes3,yes4,yes5,yes6,yes7,no1,no2,no3,no4,no5,no6,no7,s0,s1,s2,s3,s4,s5;
	private	JPanel p0,p1,p2,p3,p4,p5,p6,p7;
	private JLabel defaultlabel1, defaultlabel2, defaultlabel3, defaultlabel4, defaultlabel5, defaultlabel7;
	private JLabel pricelabel,dish1,dish2,dish3,exdish1,exdish2,exdish3,exdish4;
	
	ButtonGroup soupgroup = new ButtonGroup();
	ButtonGroup noodgroup = new ButtonGroup();
	ButtonGroup oniogroup = new ButtonGroup();
	ButtonGroup choicegroup1 = new ButtonGroup();
	ButtonGroup choicegroup2 = new ButtonGroup();
	ButtonGroup choicegroup3 = new ButtonGroup();
	ButtonGroup choicegroup4 = new ButtonGroup();
	ButtonGroup choicegroup5 = new ButtonGroup();
	ButtonGroup choicegroup6 = new ButtonGroup();
	ButtonGroup choicegroup7 = new ButtonGroup();
	ButtonGroup spigroup = new ButtonGroup();
	ButtonGroup areagroup = new ButtonGroup();
	
	private int width = 1000;
	private int height = 600;
	private double price;
	private DecimalFormat df = new DecimalFormat("##.00");
	
	//标记额外商品。
	private boolean mark1 = false; 
	private boolean mark2 = false; 
	private boolean mark3 = false; 
	private boolean mark4 = false; 
	
	private boolean key = false; //The mark that start to calculate the price
	private String Mark = null; //Membership or not
	
	String[] men = new String[]{"Tonkotsu",  "Shoyu",         "Shio",
							  "Soft",      "Medium",        "Firm",
							  "No please", "Just a little", "A lot!", 
							  "Nori"     , "Chashu",        "Boiled egg", "Bamboo shoots"};
	double[] men_price = new double[5];
	Font f_big = new Font("TimesRoman", Font.BOLD, 20);
	Font f_sma = new Font("TimesRoman", Font.BOLD, 12);
	Font f_lit = new Font("TimesRoman", Font.BOLD, 10);
	Font f_verysma = new Font("TimesRoman", Font.BOLD, 9);
	
	public Menuuser(String x, String y){
		
		//Set the price
		try {
			Price price = new Price();
			for(int i = 0;i<5;i++) {
			men_price [i] = Double.parseDouble(price.AllData.get(i));
			}
			this.price = men_price [0];
		} catch (IOException e1) {
			System.out.println("Error");
		}
		
		this.membershipnumber = x;
		Mark = y;
		
		this.setTitle("Menu for common user");
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setinterface();
		this.setbuttons();
		try {
			this.CheckStock();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setVisible(true);
		
	}
	
	public void setinterface(){
		p0 = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		
		p0.setBounds(width/5*2,   height/2+30, width/5, height/6);
		p1.setBounds(10,          10,          width/5-20, height/2);
		p2.setBounds(width/5+5,   10,          width/5-20, height/2);
		p3.setBounds(width/5*2+5, 10,          width/5-20, height/2);
		p4.setBounds(width/5*3+5, 10,          width/5-20, height/2);
		p5.setBounds(width/5*4+5, 10,          width/5-25, height/2);
		p6.setBounds(10,          height/2+10, width/5-20, height/3);
		p7.setBounds(width/5*4+5, height/2+10, width/5-25, height/3);
		
		p1.setLayout(new GridLayout(0,1));
		p2.setLayout(new GridLayout(0,1));
		p3.setLayout(new GridLayout(0,1));
		p4.setLayout(new GridLayout(0,1));
		p5.setLayout(new GridLayout(0,1));
		p6.setLayout(new GridLayout(0,1));
		p7.setLayout(new GridLayout(0,1));
		
		p1.setBorder(BorderFactory.createTitledBorder("Soup:"));
		p2.setBorder(BorderFactory.createTitledBorder("Noodles:"));
		p3.setBorder(BorderFactory.createTitledBorder("Spring onion:"));
		p4.setBorder(BorderFactory.createTitledBorder("Side dish for free:"));
		p5.setBorder(BorderFactory.createTitledBorder("Paid side dishes:"));
		p6.setBorder(BorderFactory.createTitledBorder("Spiciness(default=1):"));
		p7.setBorder(BorderFactory.createTitledBorder("dining options:"));
		
        pricelabel    = new JLabel("Price: $" + price );
		pricelabel.setFont(f_big);
		defaultlabel1 = new JLabel("The default soup is Tonkotsu.");
		defaultlabel1.setFont(f_sma);
		defaultlabel2 = new JLabel("The default type is Medium.");
		defaultlabel2.setFont(f_sma);
		defaultlabel3 = new JLabel("<html>The default choice is 'Just little'.</html>");
		defaultlabel3.setFont(f_sma);
		defaultlabel4 = new JLabel("<html>You can pick the side dishes for free.</html>");
		defaultlabel4.setFont(f_lit);
		defaultlabel5 = new JLabel("You should pay for the side dishes.");
		defaultlabel5.setFont(f_verysma);
		defaultlabel7 = new JLabel("Default option is 'Eat in'.");
		defaultlabel7.setFont(f_sma);
		
		p0.add(pricelabel);
		p1.add(defaultlabel1);
		p2.add(defaultlabel2);
		p3.add(defaultlabel3);
		p4.add(defaultlabel4);
		p5.add(defaultlabel5);
		p7.add(defaultlabel7);
		
		this.getContentPane().add(p0);
		this.getContentPane().add(p1);
		this.getContentPane().add(p2);
		this.getContentPane().add(p3);
		this.getContentPane().add(p4);
		this.getContentPane().add(p5);
		this.getContentPane().add(p6);
		this.getContentPane().add(p7);
		
	}
	
	public void setbuttons(){
		sur = new JButton("confirm");
		ret = new JButton("return");
		sur.setBounds(width/10*6,height/6*5,width/10,50);
		ret.setBounds(width/10*3,height/6*5,width/10,50);
		this.getContentPane().add(ret);
		this.getContentPane().add(sur);
		
		
		sur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				String PriceData = Double.toString(price);
				//Reading
				String choice_1 = soupgroup.getSelection().getActionCommand();
				String choice_2 = noodgroup.getSelection().getActionCommand();
				String choice_3 = oniogroup.getSelection().getActionCommand();
				String choice_4 = choicegroup1.getSelection().getActionCommand();
				String choice_5 = choicegroup2.getSelection().getActionCommand();
				String choice_6 = choicegroup3.getSelection().getActionCommand();
				String choice_7 = choicegroup4.getSelection().getActionCommand();
				String choice_8 = choicegroup5.getSelection().getActionCommand();
				String choice_9 = choicegroup6.getSelection().getActionCommand();
				String choice_10 = choicegroup7.getSelection().getActionCommand();
				String choice_11 = spigroup.getSelection().getActionCommand();
				String choice_12 = areagroup.getSelection().getActionCommand();
				
				String [] AllChoice = { PriceData,choice_1,choice_2,choice_3,choice_4,choice_5,choice_6
										,choice_7,choice_8,choice_9,choice_10,choice_11,choice_12};
				
				FileAttribute file = new FileAttribute(membershipnumber,AllChoice,Mark);  //Cache the order information in a specialized class
				check = new Check(membershipnumber,AllChoice,file);  //Jump to payment interface
		}
	});
		ret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dispose();
				new LoginGUI();
			}
			});
		
		//Setting
		sou1 = new JRadioButton(men[0]);
		sou1.setActionCommand(men[0]);
		sou2 = new JRadioButton(men[1]);
		sou2.setActionCommand(men[1]);
		sou3 = new JRadioButton(men[2]);
		sou3.setActionCommand(men[2]);
		
		noo1 = new JRadioButton(men[3]);
		noo1.setActionCommand(men[3]);
		noo2 = new JRadioButton(men[4]);
		noo2.setActionCommand(men[4]);
		noo3 = new JRadioButton(men[5]);
		noo3.setActionCommand(men[5]);
		
		spr1 = new JRadioButton(men[6]);
		spr1.setActionCommand(men[6]);
		spr2 = new JRadioButton(men[7]);
		spr2.setActionCommand(men[7]);
		spr3 = new JRadioButton(men[8]);
		spr3.setActionCommand(men[8]);
		
		dish1 = new JLabel(men[9]);
		dish2 = new JLabel(men[10]);
		dish3 = new JLabel(men[11]);
		exdish1 = new JLabel("Extra " + men[9] + " :$" + men_price[1] );
		exdish2 = new JLabel("Extra " + men[10] + " :$" + men_price[2] );
		exdish3 = new JLabel("Extra " + men[11] + " :$" + men_price[3] );
		exdish4 = new JLabel(men[12] + " :$" + men_price[4] );
		
		yes1 = new JRadioButton("yes");
		yes1.setActionCommand("yes");
		yes2 = new JRadioButton("yes");
		yes2.setActionCommand("yes");
		yes3 = new JRadioButton("yes");
		yes3.setActionCommand("yes");
		
		yes4 = new JRadioButton("yes");
		yes4.setActionCommand("√");
		yes5 = new JRadioButton("yes");
		yes5.setActionCommand("√");
		yes6 = new JRadioButton("yes");
		yes6.setActionCommand("√");
		yes7 = new JRadioButton("yes");
		yes7.setActionCommand("√");
		
		no1 = new JRadioButton("no");
		no1.setActionCommand("no");
		no2 = new JRadioButton("no");
		no2.setActionCommand("no");
		no3 = new JRadioButton("no");
		no3.setActionCommand("no");
		
		no4 = new JRadioButton("no");
		no4.setActionCommand("×");
		no5 = new JRadioButton("no");
		no5.setActionCommand("×");
		no6 = new JRadioButton("no");
		no6.setActionCommand("×");
		no7 = new JRadioButton("no");
		no7.setActionCommand("×");
		
		s0 = new JRadioButton("0(No)");
		s0.setActionCommand("No");
		s1 = new JRadioButton("1");
		s1.setActionCommand("1");
		s2 = new JRadioButton("2");
		s2.setActionCommand("2");
		s3 = new JRadioButton("3");
		s3.setActionCommand("3");
		s4 = new JRadioButton("4");
		s4.setActionCommand("4");
		s5 = new JRadioButton("5(Max)");
		s5.setActionCommand("Max");
		
		eatin = new JRadioButton("Eat in");
		eatin.setActionCommand("Eat in");
		takeaway = new JRadioButton("Take away");
		takeaway.setActionCommand("Take away");

		soupgroup.add(sou1);
		soupgroup.add(sou2);
		soupgroup.add(sou3);
		noodgroup.add(noo1);
		noodgroup.add(noo2);
		noodgroup.add(noo3);
		oniogroup.add(spr1);
		oniogroup.add(spr2);
		oniogroup.add(spr3);
		choicegroup1.add(yes1);
		choicegroup1.add(no1);
		choicegroup2.add(yes2);
		choicegroup2.add(no2);
		choicegroup3.add(yes3);
		choicegroup3.add(no3);
		choicegroup4.add(yes4);
		choicegroup4.add(no4);
		choicegroup5.add(yes5);
		choicegroup5.add(no5);
		choicegroup6.add(yes6);
		choicegroup6.add(no6);
		choicegroup7.add(yes7);
		choicegroup7.add(no7);
		spigroup.add(s0);
		spigroup.add(s1);
		spigroup.add(s2);
		spigroup.add(s3);
		spigroup.add(s4);
		spigroup.add(s5);
		areagroup.add(eatin);
		areagroup.add(takeaway);
		//Default options.
		sou1.setSelected(true);
		noo2.setSelected(true);
		spr2.setSelected(true);
        no1.setSelected(true);
        no2.setSelected(true);
        no3.setSelected(true);
        no4.setSelected(true);
        no5.setSelected(true);
        no6.setSelected(true);
        no7.setSelected(true);
		s1.setSelected(true);
		eatin.setSelected(true);
		
		if(sou1.isSelected()||sou2.isSelected()||sou3.isSelected()) {
			pricelabel.setText("Price: $" + price);
			key = true;
		}
		
		yes4.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						if(key) {
							mark1=true;
							calculate();
						}
					}
				}	
			);
		yes5.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						if(key) {
							mark2=true;
							calculate();
						}
					}
				}	
			);
		yes6.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						if(key) {
							mark3=true;
							calculate();
						}
					}
				}	
			);
		yes7.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						if(key) {
							mark4=true;
							calculate();
						}
					}
				}	
			);
		no4.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						mark1=false;
						calculate();
					}
				}	
			);
		no5.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						mark2=false;
						calculate();
					}
				}	
			);
		no6.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						mark3=false;
						calculate();
					}
				}	
			);
		no7.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						mark4=false;
						calculate();
					}
				}	
			);
		
		p1.add(sou1);
		p1.add(sou2);
		p1.add(sou3);
		p2.add(noo1);
		p2.add(noo2);
		p2.add(noo3);
		p3.add(spr1);
		p3.add(spr2);
		p3.add(spr3);
		p4.add(dish1);
		p4.add(yes1);
		p4.add(no1);
		p4.add(dish2);
		p4.add(yes2);
		p4.add(no2);
		p4.add(dish3);
		p4.add(yes3);
		p4.add(no3);
		p5.add(exdish1);
		p5.add(yes4);
		p5.add(no4);
		p5.add(exdish2);
		p5.add(yes5);
		p5.add(no5);
		p5.add(exdish3);
		p5.add(yes6);
		p5.add(no6);
		p5.add(exdish4);
		p5.add(yes7);
		p5.add(no7);
		p6.add(s0);
		p6.add(s1);
		p6.add(s2);
		p6.add(s3);
		p6.add(s4);
		p6.add(s5);
		p7.add(eatin);
		p7.add(takeaway);
	}
	//Determine whether options are optional based on stock.
	public void CheckStock() throws IOException {
		
		Stock stock = new Stock();
		SetButton set = new SetButton();
		List<String> signal = set.ReadSignal();
		List<Integer> checkmark = new ArrayList<Integer>();
		checkmark = stock.CheckStock();
		
		if(checkmark.get(0) == 1||signal.get(0).equals("no")) {
			sou1.setEnabled(false);
		}
		else {
			sou1.setEnabled(true);
		}
		if(checkmark.get(1) == 1||signal.get(1).equals("no")) {
			sou2.setEnabled(false);
		}
		else {
			sou2.setEnabled(true);
		}
		if(checkmark.get(2) == 1||signal.get(2).equals("no")) {
			sou3.setEnabled(false);
		}
		else {
			sou3.setEnabled(true);
		}
		if(checkmark.get(7) == 1||signal.get(7).equals("no")) {
			noo1.setEnabled(false);
		}
		else {
			noo1.setEnabled(true);
		}
		if(checkmark.get(8) == 1||signal.get(8).equals("no")) {
			noo2.setEnabled(false);
		}
		else {
			noo2.setEnabled(true);
		}
		if(checkmark.get(9) == 1||signal.get(9).equals("no")) {
			noo3.setEnabled(false);
		}
		else {
			noo3.setEnabled(true);
		}
		if(checkmark.get(3) == 1||signal.get(3).equals("no")) {
			yes1.setEnabled(false);
			no1.setEnabled(false);
			yes4.setEnabled(false);
			no4.setEnabled(false);
		}
		else {
			yes1.setEnabled(true);
			no1.setEnabled(true);
			yes4.setEnabled(true);
			no4.setEnabled(true);
		}
		if(checkmark.get(4) == 1||signal.get(4).equals("no")) {
			yes2.setEnabled(false);
			no2.setEnabled(false);
			yes5.setEnabled(false);
			no5.setEnabled(false);
		}
		else {
			yes2.setEnabled(true);
			no2.setEnabled(true);
			yes5.setEnabled(true);
			no5.setEnabled(true);
		}
		if(checkmark.get(5) == 1||signal.get(5).equals("no")) {
			yes3.setEnabled(false);
			no3.setEnabled(false);
			yes6.setEnabled(false);
			no6.setEnabled(false);
		}
		else {
			yes3.setEnabled(true);
			no3.setEnabled(true);
			yes6.setEnabled(true);
			no6.setEnabled(true);
		}
		if(checkmark.get(6) == 1||signal.get(6).equals("no")) {
			yes7.setEnabled(false);
			no7.setEnabled(false);
		}
		else {
			yes7.setEnabled(true);
			no7.setEnabled(true);
		}
 }
	//Calculation
	public void calculate() {
		if(!yes7.isSelected()&&!no7.isSelected()) {
		if(mark1||mark2||mark3) {
			if(mark1) {
				price = men_price[0]+men_price[1];
			}
			else if(mark2) {
				price = men_price[0]+men_price[2];
			}
			else if(mark3) {
				price = men_price[0]+men_price[3];
			}
			else {
				System.out.println("1");
			}
			pricelabel.setText("Price: $" + df.format(price));
			
			if(mark1&&mark2&&mark3&&!mark4) {
				price = men_price[0]+men_price[1]+men_price[2]+men_price[3];
				pricelabel.setText("Price: $" + df.format(price));
			}
			else if(!mark1&&!mark2&&!mark3&&mark4) {
				if(mark1) {
					price = men_price[0]+men_price[4];
				}
				else if(mark2) {
					price = men_price[0]+men_price[4];
				}
				else if(mark3) {
					price = men_price[0]+men_price[4];
				}
				else {
					System.out.println("2");
				}
				pricelabel.setText("Price: $" + df.format(price));
			}
			else if(mark1&&mark2&&mark3&&mark4) {
				price = men_price[0]+men_price[1]+men_price[2]+men_price[3]+men_price[4];
				pricelabel.setText("Price: $" + df.format(price));
			}
			else if(mark1&&mark2) {
				price = men_price[0]+men_price[1]+men_price[2];
				pricelabel.setText("Price: $" + df.format(price));
			}
			else if(mark1&&mark3) {
				price = men_price[0]+men_price[1]+men_price[3];
				pricelabel.setText("Price: $" + df.format(price));
			}
			else if(mark2&&mark3) {
				price = men_price[0]+men_price[2]+men_price[3];
				pricelabel.setText("Price: $" + df.format(price));
			}
		}
	}
		else if(mark4) {
			price = men_price[0]+men_price[4];
			pricelabel.setText("Price: $" + df.format(price));
			if(mark1&&mark2&&mark3) {
				price = men_price[0]+men_price[1]+men_price[2]+men_price[3]+men_price[4];
				pricelabel.setText("Price: $" + df.format(price));
			}
			else if(mark1||mark2||mark3) {
				if(mark1) {
					price = men_price[0]+men_price[1]+men_price[4];
				}
				else if(mark2) {
					price = men_price[0]+men_price[2]+men_price[4];
				}
				else if(mark3) {
					price = men_price[0]+men_price[3]+men_price[4];
				}
				else {
					System.out.println("3");
				}
				pricelabel.setText("Price: $" + df.format(price));
				
				if(mark1&&mark2) {
					price = men_price[0]+men_price[1]+men_price[2]+men_price[4];
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(mark1&&mark3) {
					price = men_price[0]+men_price[1]+men_price[3]+men_price[4];
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(mark2&&mark3) {
					price = men_price[0]+men_price[2]+men_price[3]+men_price[4];
					pricelabel.setText("Price: $" + df.format(price));
				}
			}
		}
		else {
			if(mark1) {
				price = men_price[0]+men_price[1]+men_price[4];
			}
			else if(mark2) {
				price = men_price[0]+men_price[2]+men_price[4];
			}
			else if(mark3) {
				price = men_price[0]+men_price[3]+men_price[4];
			}
			else {
				price = men_price[0];
			}
			pricelabel.setText("Price: $" + df.format(price));
			
			if(mark1||mark2||mark3) {
				if(mark1) {
					price = men_price[0]+men_price[1];
				}
				else if(mark2) {
					price = men_price[0]+men_price[2];
				}
				else if(mark3) {
					price = men_price[0]+men_price[3];
				}
				else {
					System.out.println("5");
				}
				pricelabel.setText("Price: $" + df.format(price));
				
				if(mark1&&mark2&&mark3&&!mark4) {
					price = men_price[0]+men_price[1]+men_price[2]+men_price[3];
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(!mark1&&!mark2&&!mark3&&mark4) {
					if(mark1) {
						price = men_price[0]+men_price[1]+men_price[4];
					}
					else if(mark2) {
						price = men_price[0]+men_price[2]+men_price[4];
					}
					else if(mark3) {
						price = men_price[0]+men_price[3]+men_price[4];
					}
					else {
						System.out.println("6");
					}
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(mark1&&mark2&&mark3&&mark4) {
					price = men_price[0]+men_price[1]+men_price[2]+men_price[3]+men_price[4];
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(mark1&&mark2) {
					price = men_price[0]+men_price[1]+men_price[2];
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(mark1&&mark3) {
					price = men_price[0]+men_price[1]+men_price[3];
					pricelabel.setText("Price: $" + df.format(price));
				}
				else if(mark2&&mark3) {
					price = men_price[0]+men_price[2]+men_price[3];
					pricelabel.setText("Price: $" + df.format(price));
				}
			}
			else {
				price = men_price[0];
				df.format(price);
				pricelabel.setText("Price: $" + df.format(price));
			}
		}
	}
}

	