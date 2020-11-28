

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeekRevenue {
	
	private Date nowDay;
	private BufferedReader in;
	private String FileName;
	private String FilePath;
	private List<Double> revenueList = new ArrayList<Double> ();
	private double r1;
	private double r2;
	private double r3;
	private double r4;
	private double r5;
	private double r6;
	private double r7;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.ENGLISH);
	
	public WeekRevenue() {
		this.FileName = "Record.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
		this.nowDay = new Date();  //Get the date of now
		try {
			this.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readFile() throws IOException{
		try {
			in = new BufferedReader(new FileReader(FilePath));
			int week_1 = 0;
			int week_2 = 0;
			String Buffer = null;
			String date = null;
			String revenue = null;
			double d_revenue = 0;
			String [] year = new String[2];
			String [] content = new String[13];
			
			while((Buffer = in.readLine())!=null) {
				content = Buffer.split("\\#");
				revenue = content[12]; //Gain the value of revenue in each record
				d_revenue = Double.valueOf(revenue);
				year = content[0].split("\\s+");
				date = year[0];  //Get the date in the record
				
				try {
					Date date1 = format.parse(date);
					week_1 = getWeekofYear(date1);
					week_2 = getWeekofYear(nowDay);
					
					if(week_1 == week_2) {
						
					    String a = getWeek(date1);
					    
						if(a.equals("Monday")) {
							r1=r1+d_revenue;
						}
						else if(a.equals("Tuesday")) {
							r2=r2+d_revenue;
						}
						else if(a.equals("Wednesday")) {
							r3=r3+d_revenue;
						}
						else if(a.equals("Thursday")) {
							r4=r4+d_revenue;
						}
						else if(a.equals("Friday")) {
							r5=r5+d_revenue;
						}
						else if(a.equals("Saturday")) {
							r6=r6+d_revenue;
						}
						else if(a.equals("Sunday")) {
							r7=r7+d_revenue;
						}
						else {
							System.out.println("No Revenue!");
						}
  				     }
				  else {
					 System.out.println("No Revenue!");	
					 }
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}catch(IOException e){
			System.out.println("Error!");
		}
		finally {
			in.close();
		}
		AddList();
	}
	//Determine the day of the week for the specified date
	public int getWeekofYear(Date date) {
		
		int WeekofYear = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		WeekofYear = cal.get(Calendar.WEEK_OF_YEAR);
		
		return WeekofYear;
	}
	public String getWeek(Date date) {
		
		String week = sdf.format(date);
		return week;
	}
	public void AddList() {
		revenueList.add(r1);
		revenueList.add(r2);
		revenueList.add(r3);
		revenueList.add(r4);
		revenueList.add(r5);
		revenueList.add(r6);
		revenueList.add(r7);
	}
	public List<Double> returnData(){
		return revenueList;
	}
}