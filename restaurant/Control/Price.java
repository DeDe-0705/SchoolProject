
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Price {
	
	private String mainPrice;
	private String PS_1;
	private String PS_2;
	private String PS_3;
	private String PS_4;
	public List<String> AllData = new ArrayList<String>();
	private String Content;
	private String [] Gain;
	private String FileName;
	private String FilePath;
	private RandomAccessFile in;
	private BufferedWriter out;
	
	public Price() throws IOException {
		
		this.FileName = "Price.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName; //Set the path of file
		this.Read();
		this.AddData();
	}
	public void AddData() {
		this.AllData.add(mainPrice);
		this.AllData.add(PS_1);
		this.AllData.add(PS_2);
		this.AllData.add(PS_3);
		this.AllData.add(PS_4);	
	}
	public List<Double> returnPrice(){
		List<Double> list = new ArrayList<Double>();
		for(int i =0;i<5;i++) {
			list.add(Double.valueOf(AllData.get(i)));
		}
		return list;
	}
	public void WriteIn() throws IOException {
		
		try {
			
			out = new BufferedWriter(new FileWriter(FilePath));
			out.write("mainPrice#" + mainPrice+"\n");
			out.write("Nori#" + PS_1 +"\n");
			out.write("Chashu#" + PS_2 +"\n");
			out.write("Boiled egg#" + PS_3 +"\n");
			out.write("Bamboo shoots#" + PS_4);
		}catch(IOException e) {
			System.out.println("Error!");
		}
		finally {
			out.close();
		}
		
		AllData.clear();
		AddData();
	}
	//Read information about the price of each item of the current file about the price of each item.
	public void Read() throws IOException {
		
		int count = 0;
		try {
			in = new RandomAccessFile(FilePath,"r");
			in.seek(0);
		while((Content = in.readLine())!=null) {
			count++;
			Gain = Content.split("\\#");
			if(count == 1) {
				this.mainPrice =Gain[1];
			}
			else if(count == 2) {
				this.PS_1 =Gain[1];
			}
			else if(count == 3) {
				this.PS_2 =Gain[1];
			}
			else if(count == 4) {
				this.PS_3 =Gain[1];
			}
			else if(count == 5) {
				this.PS_4 =Gain[1];
			}
			else {
				System.out.println("Error!");
			}
		}
		}catch(IOException e) {
			System.out.println("File is not exist.");
		}
		finally{
			in.close();
		}
	}
	public void SetPrice(String x, String newPrice) throws IOException {
		
		//Set the price according to the mark 'x'.
		if(x.equals("1")) {
			this.mainPrice = newPrice;
		}
		else if(x.equals("2")) {
			this.PS_1 = newPrice;
		}
		else if(x.equals("3")) {
			this.PS_2 = newPrice;
		}
		else if(x.equals("4")) {
			this.PS_3 = newPrice;
		}
		else if(x.equals("5")) {
			this.PS_4 = newPrice;
		}
		else {
			System.out.println("Error!");
		}
		WriteIn();
	}
}
