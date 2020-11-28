

import java.util.*;
import java.io.*;
public class CalculateRevenue {
	
	private BufferedReader in;
	private String FileName;
	private String FilePath;
	private List<Double> Price = new ArrayList<Double>();
	private int number;
	private int T_bowls;
	public CalculateRevenue() {
		
		this.FileName = "Record.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
	}
	public void readData() throws IOException {
		
		String buffer = null;
		double sTd = 0.0;
		String [] data = new String[13];
		try {
			in = new BufferedReader(new FileReader(FilePath));
			while((buffer=in.readLine())!=null) {
				number++;
				data = buffer.split("\\#");
				sTd = Double.valueOf(data[12]);
				Price.add(sTd);
			}
		}catch(IOException e) {
			System.out.println("Error!");
		}
		finally {
			in.close();
		}
	}
	public double calculate() {
		
		Double total = 0.0;
		try {
			readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0;i<number;i++) {
			total +=Price.get(i);
		}
		number = 0;
		return total;
	}
	public List<Integer> calculateBowls() {
		
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			readData();
			T_bowls = number;
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.add(T_bowls);
		return list;
	}
}
