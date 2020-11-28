
import java.io.*;
import java.util.*;

public class Stats {
	
	private List<String> Data = new ArrayList<String>();
	private String[] Buffer_1;
	private String FileName = "Record.txt";
	private String FilePath = " ";
	private BufferedReader in;
	
	private List<Integer> All = new ArrayList<Integer>();
	private int num_Tonkotsu;
	private int num_Shoyu;
	private int num_Shio;
	
	private int num_Soft;
	private int num_Medium;
	private int num_Firm;
	
	private int num_np;
	private int num_jl;
	private int num_al;
	
	private int num_Nori;
	private int num_Chashu;
	private int num_Boiled;
	
	private int num_ENori;
	private int num_EChashu;
	private int num_EBoiled;
	private int num_Bamboo;
	
	private int num_0;
	private int num_1;
	private int num_2;
	private int num_3;
	private int num_4;
	private int num_5;
	
	public Stats() {
		
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
		try {
			this.analyzeData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int readRecord() throws IOException {
		int Counter = 0;
		String Buffer_2 = null;
		try {
		in = new BufferedReader(new FileReader(FilePath));
		while((Buffer_2 = in.readLine()) != null) {
			Data.add(Buffer_2);
			Counter++;
		}
		}catch(IOException e) {
			System.out.println("File does not exist.");
		}
		finally {
			in.close();
		}
		return Counter;
	}
	public void analyzeData() throws IOException {
		
		String Time = " ";
		
		int Counter = 0;
		Counter = readRecord();  //To get the number of bars about information
		for(int i = 0; i<Counter; i++) {
			Buffer_1 = Data.get(i).split("\\#"); 
			Time = Buffer_1 [0];
			//Soup
			if(Buffer_1 [1].equals("Tonkotsu")) {
				num_Tonkotsu++;
			}
			else if(Buffer_1 [1].equals("Shoyu")) {
				num_Shoyu++;
			}
			else if(Buffer_1 [1].equals("Shio")) {
				num_Shio++;
			}
			else {
				System.out.print("Error!");
			}
			//Noodles
			if(Buffer_1 [2].equals("Soft")) {
				num_Soft++;
			}
			else if(Buffer_1 [2].equals("Medium")) {
				num_Medium++;
			}
			else if(Buffer_1 [2].equals("Firm")) {
				num_Firm++;
			}
			else {
				System.out.print("Error!");
			}
			//Spr
			if(Buffer_1 [3].equals("No please")) {
				num_np++;
			}
			else if(Buffer_1 [3].equals("Just a little")) {
				num_jl++;
			}
			else if(Buffer_1 [3].equals("A lot!")) {
				num_al++;
			}
			else {
				System.out.print("Error!");
			}
			//sd_1
			if(Buffer_1 [4].equals("Nori")) {
				num_Nori++;
			}
			else {
				
			}
			//sd_2
			if(Buffer_1 [5].equals("Chashu")) {
				num_Chashu++;
			}
			else {
				
			}
			//sd_3
			if(Buffer_1 [6].equals("Boiled egg")) {
				num_Boiled++;
			}
			else {
				
			}
			//psd_1
			if(Buffer_1 [7].equals("Extra Nori")) {
				num_ENori++;
			}
			else {
				
			}
			//psd_2
			if(Buffer_1 [8].equals("Extra Chashu")) {
				num_EChashu++;
			}
			else {
				
			}
			//psd_3
			if(Buffer_1 [9].equals("Extra Boiled egg")) {
				num_EBoiled++;
			}
			else {
				
			}
			//psd_4
			if(Buffer_1 [10].equals("Bamboo shoots")) {
				num_Bamboo++;
			}
			else {
				
			}
			//spi
			if(Buffer_1 [11].equals("No")) {
				num_0++;
			}
			else if(Buffer_1 [11].equals("1")) {
				num_1++;
			}
			else if(Buffer_1 [11].equals("2")) {
				num_2++;
			}
			else if(Buffer_1 [11].equals("3")) {
				num_3++;
			}
			else if(Buffer_1 [11].equals("4")) {
				num_4++;
			}
			else if(Buffer_1 [11].equals("Max")) {
				num_5++;
			}
			else {
				System.out.print("Error!");
			}
		}
		addData();
	}
	public void addData() {
		All.add(num_Tonkotsu);//0
		All.add(num_Shoyu);//1
		All.add(num_Shio);//2
		All.add(num_Soft);//3
		All.add(num_Medium);//4
		All.add(num_Firm);//5
		All.add(num_np);//6
		All.add(num_jl);//7
		All.add(num_al);//8
		All.add(num_Nori);//9
		All.add(num_Chashu);//10
		All.add(num_Boiled);//11
		All.add(num_ENori);//12
		All.add(num_EChashu);//13
		All.add(num_EBoiled);//14
		All.add(num_Bamboo);//15
		All.add(num_0);//16
		All.add(num_1);//17
		All.add(num_2);//18
		All.add(num_3);//19
		All.add(num_4);//20
		All.add(num_5);//21
	}
	public List<Integer> returnData() {
		return All;
	}
}
