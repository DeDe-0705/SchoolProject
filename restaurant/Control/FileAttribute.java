

import java.text.SimpleDateFormat;
import java.util.*;

public class FileAttribute {
		
		private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //Set date format
		private String a = df.format(new Date()); //Gets the time to place an order
		
		private String membershipnumber;
		private String time;
		private String Price;
		private String Soup;
		private String Noodles;
		private String SpringOnion;
		private String SD_1;
		private String SD_2;
		private String SD_3;
		private String PS_1;
		private String PS_2;
		private String PS_3;
		private String PS_4;
		private String Spi;
		private String DO;
		public List<String> AllData = new ArrayList<String>();
		private String Mark; //Determine whether to log in for a member
		
		private int num_soup1;
		private int num_soup2;
		private int num_soup3;
		private int num_nori;
		private int num_chashu;
		private int num_egg;
		private int num_bamboo;
		private int num_soft;
		private int num_medium;
		private int num_firm;
		private List<Integer> num = new ArrayList<Integer>();
		
		public FileAttribute(String x, String [] y, String z) {
			
			this.Mark = z;
			this.membershipnumber = x;
			this.time = a;
			this.Price = y[0];
			this.Soup = y[1];
			this.Noodles = y[2];
			this.SpringOnion = y[3];
			this.Spi = y[11];
			this.DO = y[12];
			
			//Modify the stock according to the selection£¨soup£©
			if(y[1].equals("Tonkotsu")) {
				this.num_soup1++;
			}
			else if(y[1].equals("Shoyu")) {
				this.num_soup2++;
			}
			else if(y[1].equals("Shio")) {
				this.num_soup3++;
			}
			else {
				System.out.println("Error!");
			}
			if(y[2].equals("Soft")) {
				this.num_soft++;
			}
			else if(y[2].equals("Medium")) {
				this.num_medium++;
			}
			else if(y[2].equals("Firm")) {
				this.num_firm++;
			}
			else {
				System.out.println("Error!");
			}
			//Converts the option ¡Ìor¡Á to the corresponding name
			if(y[4].equals("yes")) {
				this.SD_1 = "Nori";
				this.num_nori++;
			}
			else {
				this.SD_1 = "No";
			}
			if(y[5].equals("yes")) {
				this.SD_2 = "Chashu";
				this.num_chashu++;
			}
			else {
				this.SD_2 = "No";
			}
			if(y[6].equals("yes")) {
				this.SD_3 = "Boiled egg";
				this.num_egg++;
			}
			else {
				this.SD_3 = "No";
			}
			if(y[7].equals("¡Ì")) {
				this.PS_1 = "Extra Nori";
				this.num_nori++;
			}
			else {
				this.PS_1 = "No";
			}
			if(y[8].equals("¡Ì")) {
				this.PS_2 = "Extra Chashu";
				this.num_chashu++;
			}
			else {
				this.PS_2 = "No";
			}
			if(y[9].equals("¡Ì")) {
				this.PS_3 = "Extra Boiled egg";
				this.num_egg++;
			}
			else {
				this.PS_3 = "No";
			}
			if(y[10].equals("¡Ì")) {
				this.PS_4 = "Bamboo shoots";
				this.num_bamboo++;
			}
			else {
				this.PS_4 = "No";
			}
			this.AddData();
			this.AddNum();
		}
		public void AddData() {
			AllData.add(membershipnumber);
			AllData.add(time);
			AllData.add(Price);
			AllData.add(Soup);
			AllData.add(Noodles);
			AllData.add(SpringOnion);
			AllData.add(SD_1);
			AllData.add(SD_2);
			AllData.add(SD_3);
			AllData.add(PS_1);
			AllData.add(PS_2);
			AllData.add(PS_3);
			AllData.add(PS_4);
			AllData.add(Spi);
			AllData.add(DO);
			
		}
		public void AddNum() {
			num.add(num_soup1);
			num.add(num_soup2);
			num.add(num_soup3);
			num.add(num_nori);
			num.add(num_chashu);
			num.add(num_egg);
			num.add(num_bamboo);
			num.add(num_soft);
			num.add(num_medium);
			num.add(num_firm);
		}
		public List<Integer> getStock() {
			return num;
		}
		public String getMark() {
			return this.Mark;
		}
		public String getNum() {
			return this.membershipnumber;
		}
}
