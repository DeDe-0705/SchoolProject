

import java.io.*;
import java.util.*;
import java.text.*;
public class OnedayTime {
	
	private BufferedReader in;
	private String FileName;
	private String FilePath;
	private String TodayTime;
	//Revenue from six different time 
	private Double r1 = 0.0;
	private Double r2 = 0.0;
	private Double r3 = 0.0;
	private Double r4 = 0.0;
	private Double r5 = 0.0;
	private Double r6 = 0.0;
	
	public OnedayTime() {
		
		this.FileName = "Record.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
		this.TodayTime = this.getTime();
		try {
			this.ReadFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getTime() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(new Date());
		return date;
	}
	public void ReadFile() throws IOException{
		
		String Buffer = null;
		String time = null;
		String [] record = new String [13];
		String [] specificTime = new String [2];
		String [] hour = new String [3];
		try {
			in = new BufferedReader(new FileReader(FilePath));
			while((Buffer=in.readLine())!=null) {
				record = Buffer.split("\\#");
				time = record [0];  //Obtain a record of time in the record
				specificTime = time.split("\\s+"); 
				hour = specificTime[1].split(":"); 
				
				if(specificTime[0].equals(TodayTime)) {
					AnalyzeTime(hour,record);
				}
				else {
					//System.out.println("No Data.");
				}
			}
		}catch(IOException e) {
			System.out.println("Error");
		}
		finally{
			in.close();
		}
	}
	public void AnalyzeTime(String[] x, String[] y) {
		
		int hour = Integer.valueOf(x[0]);
		int hour1 = Integer.valueOf(x[1]);
		
		if(hour<=9) {
			if(hour1>0&&hour == 9) {
				r2 = r2 + Double.valueOf(y[12]);
			}
			else{
				r1 = r1 + Double.valueOf(y[12]);
			}
		}
		else if(hour<=11&&hour>9) {
			if(hour1>0&&hour == 11) {
				r3 = r3 + Double.valueOf(y[12]);
			}
			else{
				r2 = r2 + Double.valueOf(y[12]);
			}
		}
		else if(hour<=14&&hour>11) {
			if(hour1>0&&hour == 14) {
				r4 = r4 + Double.valueOf(y[12]);
			}
			else{
				r3 = r3 + Double.valueOf(y[12]);
			}
		}
		else if(hour<=17&&hour>14) {
			if(hour1>0&&hour == 17) {
				r5 = r5 + Double.valueOf(y[12]);
			}
			else{
				r4 = r4 + Double.valueOf(y[12]);
			}
		}
		else if(hour<=20&&hour>17) {
			if(hour1>0&&hour == 20) {
				r6 = r6 + Double.valueOf(y[12]);
			}
			else{
				r5 = r5 + Double.valueOf(y[12]);
			}
		}
		else if(hour<=22&&hour>20) {
			if(hour1>0&&hour == 22) {
				r6 = r6 + 0.0;
			}
			else{
				r6 = r6 + Double.valueOf(y[12]);
			}
		}
		else {
			System.out.println("Suspension of business!");
		}
	}
	public Double TimeRevenue(int x) {
				
		if(x == 1) {
			return r1;
		}
		else if (x == 2) {
			return r2;
		}
		else if (x == 3) {
			return r3;
		}
		else if (x == 4) {
			return r4;
		}
		else if (x == 5) {
			return r5;
		}
		else if (x == 6) {
			return r6;
		}
		else {
			return 0.0;
		}
	}
}
