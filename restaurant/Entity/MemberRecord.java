

import java.util.List;
import java.io.*;

public class MemberRecord {
	
	private String membershipnumber;
	private String [] record = new String [15];
	private String FileName;
	private String FilePath;
	
	private FileAttribute Buffer;
	private List<String> x; 
	private BufferedWriter out; 
	
	
	public MemberRecord(FileAttribute z) {
		
		this.Buffer = z;
		x = Buffer.AllData;
		this.FileName = x.get(0) + ".txt";
		this.FilePath = this.getClass().getResource("/").getPath() + this.FileName;  
		
		this.membershipnumber = x.get(0);
		this.record[0] = x.get(1);
		this.record[1] = x.get(2); //price
		this.record[2] = x.get(3);
		this.record[3] = x.get(4);
		this.record[4] = x.get(5);
		this.record[5] = x.get(6);
		this.record[6] = x.get(7);
		this.record[7] = x.get(8);
		this.record[8] = x.get(9);
		this.record[9] = x.get(10);
		this.record[10] = x.get(11);
		this.record[11] = x.get(12);
		this.record[12] = x.get(13); //Spiciness
		this.record[13] = x.get(14); //Dining option£©
		
		try {
			this.WriteFile();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void WriteFile() throws IOException{
		
		try {
			out = new BufferedWriter(new FileWriter(FilePath,true));
			for(int i =0;i<14;i++) {
			out.write(record[i]);
			out.write("#");
			}
			out.newLine();
			}catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				out.close();
			}
	}
}
