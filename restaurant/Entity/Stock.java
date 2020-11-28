
import java.io.*;
import java.util.*;

public class Stock {
	
	private String FileName;
	private String FilePath;
	private BufferedReader in;
	private BufferedWriter out;
	
	private List<Integer> Num = new ArrayList<Integer>();
	private int num_Tonkotsu;
	private int num_Shoyu;
	private int num_Shio;
	private int num_Nori;
	private int num_Chashu;
	private int num_egg;
	private int num_Bamboo;
	private int num_soft;
	private int num_medium;
	private int num_firm;
	
	public  Stock() {
		
		this.FileName = "Stock.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
		try {
			this.readStock();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addList() {
		
		Num.clear();		//Update
		Num.add(num_Tonkotsu);
		Num.add(num_Shoyu);
		Num.add(num_Shio);
		Num.add(num_Nori);
		Num.add(num_Chashu);
		Num.add(num_egg);
		Num.add(num_Bamboo);
		Num.add(num_soft);
		Num.add(num_medium);
		Num.add(num_firm);
	}
	public String [] ItoS(List<Integer> x) {
		
		String Result [] = new String [10];
		for(int i =0;i<10;i++) {
			Result [i] = x.get(i).toString();
		}
		return Result;
	}
	public List<Integer> returnData() {
		return Num;
	}
	public void writeStock() throws IOException{
		
		String Data [] = ItoS(Num);
		
		try {
			out = new BufferedWriter(new FileWriter(FilePath));
			out.write("Tonkotsu#"+Data[0]+"\n");
			out.write("Shoyu#"+Data[1]+"\n");
			out.write("Shio#"+Data[2]+"\n");
			out.write("Nori#"+Data[3]+"\n");
			out.write("Chashu#"+Data[4]+"\n");
			out.write("Boiled egg#"+Data[5]+"\n");
			out.write("Bamboo shoots#"+Data[6]+"\n");
			out.write("Soft#"+Data[7]+"\n");
			out.write("Medium#"+Data[8]+"\n");
			out.write("Firm#"+Data[9]+"\n");
		}catch(IOException e) {
			System.out.println("Error!");
		}
		finally {
			out.close();
		}
	}
	public void readStock() throws IOException {
		
		int Counter  = 0;
		String Buffer = null;
		String Buffer_2 [] = {};
		
		try {
		    in = new BufferedReader(new FileReader(FilePath));
		    while((Buffer = in.readLine())!=null) {
		    	Counter++;
		    	Buffer_2 = Buffer.split("\\#");
		    	if(Counter == 1) {
		    		num_Tonkotsu = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 2) {
		    		num_Shoyu = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 3) {
		    		num_Shio = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 4) {
		    		num_Nori = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 5) {
		    		num_Chashu = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 6) {
		    		num_egg = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 7) {
		    		num_Bamboo = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 8) {
		    		num_soft = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 9) {
		    		num_medium = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else if(Counter == 10) {
		    		num_firm = Integer.parseInt(Buffer_2[1]);
		    	}
		    	else {
		    		System.out.println("Error!");
		    	}
		    }
		}catch(IOException e) {
			System.out.println("Error!");
		}
		finally {
			addList();
			in.close();
		}
	}
	public void modifyStock(List<Integer> num) throws IOException {
		
		readStock();
			num_Tonkotsu = Num.get(0) - num.get(0);
			num_Shoyu = Num.get(1) - num.get(1);
			num_Shio = Num.get(2) - num.get(2);
			num_Nori = Num.get(3) - num.get(3);
			num_Chashu = Num.get(4) - num.get(4);
			num_egg = Num.get(5) - num.get(5);
			num_Bamboo = Num.get(6) - num.get(6);
			num_soft = Num.get(7) - num.get(7);
			num_medium = Num.get(8) - num.get(8);
			num_firm = Num.get(9) - num.get(9);
		addList();
		writeStock();
	}
	public void addStock(int mark,int num) throws IOException {
		
		if(mark == 1) {
			num_Tonkotsu = num;
		}
		else if(mark == 2) {
			num_Shoyu = num;
		}
		else if(mark == 3) {
			num_Shio = num;
		}
		else if(mark == 4) {
			num_Nori = num;
		}
		else if(mark == 5) {
			num_Chashu = num;
		}
		else if(mark == 6) {
			num_egg = num;
		}
		else if(mark == 7) {
			num_Bamboo = num;
		}
		else if(mark == 8) {
			num_soft = num;
		}
		else if(mark == 9) {
			num_medium = num;
		}
		else if(mark == 10) {
			num_firm = num;
		}
		else {
			System.out.println("Error!");
		}
		
		addList();
		writeStock();
	}
	public List<Integer> CheckStock() throws IOException {
		
		readStock();
		List<Integer> Check = new ArrayList<Integer>();
		if(Num.get(0) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(1) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(2) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(3) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(4) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(5) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(6) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(7) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(8) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		if(Num.get(9) == 0) {
			Check.add(1);
		}
		else {
			Check.add(0);
		}
		return Check;
	}
}
