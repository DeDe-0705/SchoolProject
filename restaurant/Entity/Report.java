

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Report {
	
	private String FileName;
	private String FilePath;
	private BufferedWriter out;
	private  MenuRank rank;
	private Stats stats;
	private String data [] = new String [5];
	private Date date = new Date();
	public Report() {
		
		this.FileName = "Report.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
		this.rank = new MenuRank();
		this.stats = new Stats();
		try {
			this.setTime();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void WriteFile() throws IOException {
		
		readData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //Set date format
		String nowDay = df.format(date);
		
		out = new BufferedWriter(new FileWriter(FilePath,true));
		out.write(nowDay);
		for(int i =0;i<5;i++) {
			out.write("#");
			out.write(data[i]);
		}
		out.newLine();
		out.close();
	}
	public void setTime() throws IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.ENGLISH);
		
		String nowDay = sdf.format(date);
		if(nowDay.equals("Mon")) {
			WriteFile();
		}
		else {
			System.out.println("Not up to the appointed time");
		}
	}
	public void readData() {
		
		
		List<String> Buffer_1 = new ArrayList<String>();
		List<Integer> Buffer_2 = new ArrayList<Integer>();
		
		Buffer_1 = rank.returnFirst();
		Buffer_2 = stats.returnData();
		
		String spi [] = {"No","level_1","level_2","level_3","level_4","Max"};
		int spiRank [] = {Buffer_2.get(16),Buffer_2.get(17),Buffer_2.get(18),Buffer_2.get(19),Buffer_2.get(20),Buffer_2.get(21)};
		for(int i = 0;i<5;i++) {
			for(int j = 0;j<5-i;j++) {
				if(spiRank[j]>spiRank[j+1]) {
					int temp1 = spiRank[j];
					spiRank[j] = spiRank[j+1];
					spiRank[j+1] = temp1;
					String temp2= spi[j];
					spi[j] = spi[j+1];
					spi[j+1] = temp2;
				}
			}
		}
		for(int i =0;i<4;i++) {
			data[i] = Buffer_1.get(i);
		}
		data[4] = spi[5];
	}
}
