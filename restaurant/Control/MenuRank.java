

import java.util.*;

public class MenuRank {
	
	private Stats stats;
	private List<Integer> data;
	private String [] s1 = {"Tonkotsu","Shoyu","Shio"};
	private String [] s2 = {"Soft","Medium","Firm"};
	private String [] s3 = {"No please","Just a little","A lot!"};
	private String [] s4 = {"Nori","Chashu","Boiled egg","Bamboo shoots"};
	public MenuRank() {
		
			this.stats = new Stats();
			this.data = new ArrayList<Integer>();
			data = stats.returnData();
			this.rank();
	}
	public void rank() {
		int [] r1 = {data.get(0),data.get(1),data.get(2)};
		int [] r2 = {data.get(3),data.get(4),data.get(5)}; 
		int [] r3 = {data.get(6),data.get(7),data.get(8)};
		int [] r4 = {data.get(9)+data.get(12),data.get(10)+data.get(13),data.get(11)+data.get(14),data.get(15)};
		
		//Soup
		for(int i = 0;i<2;i++) {
			for(int j = 0;j<2-i;j++) {
				if(r1[j]>r1[j+1]) {
					int temp1 = r1[j];
					r1[j] = r1[j+1];
					r1[j+1] = temp1;
					String temp2= s1[j];
					s1[j] = s1[j+1];
					s1[j+1] = temp2;
				}
			}
		}
		//Noodles
		for(int i = 0;i<2;i++) {
			for(int j = 0;j<2-i;j++) {
				if(r2[j]>r2[j+1]) {
					int temp1 = r2[j];
					r2[j] = r2[j+1];
					r2[j+1] = temp1;
					String temp2 = s2[j];
					s2[j] = s2[j+1];
					s2[j+1] = temp2;
				}
			}
		}
		//Spring onion
		for(int i = 0;i<2;i++) {
			for(int j = 0;j<2-i;j++) {
				if(r3[j]>r3[j+1]) {
					int temp1 = r3[j];
					r3[j] = r3[j+1];
					r3[j+1] = temp1;
					String temp2 = s3[j];
					s3[j] = s3[j+1];
					s3[j+1] = temp2;
				}
			}
		}
		//Add-ons
		for(int i = 0;i<3;i++) {
			for(int j = 0;j<3-i;j++) {
				if(r4[j]>r4[j+1]) {
					int temp1 = r4[j];
					r4[j] = r4[j+1];
					r4[j+1] = temp1;
					String temp2 = s4[j];
					s4[j] = s4[j+1];
					s4[j+1] = temp2;
				}
			}
		}
	}
	public List<String> returnFirst() {
		
		List<String> Buffer = new ArrayList<String>();
		Buffer.add(s1[2]);
		Buffer.add(s2[2]);
		Buffer.add(s3[2]);
		Buffer.add(s4[3]);
		
		return Buffer;
	}
}
