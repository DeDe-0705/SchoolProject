

import java.io.*;
import java.util.*;

public class Record {
	
	private String [] record = new String [13];
	private String FileName = "Record.txt";
	private String FilePath = " ";
	private FileAttribute Data;
	private BufferedWriter out;
	
	private List<String> x;  //Reads data from the class of buffer
	
	public Record(FileAttribute a) {
		
		this.Data = a;
		x = Data.AllData;
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;  //Set the path of file
		
		this.record [0] = x.get(1);
		this.record [1] = x.get(3);
		this.record [2] = x.get(4);
		this.record [3] = x.get(5);
		this.record [4] = x.get(6);
		this.record [5] = x.get(7);
		this.record [6] = x.get(8);
		this.record [7] = x.get(9);
		this.record [8] = x.get(10);
		this.record [9] = x.get(11);
		this.record [10] = x.get(12);
		this.record [11] = x.get(13);
		this.record [12] = x.get(2); //price
		try {
		this.RecordWrite();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void RecordWrite() throws IOException {
		
		try {
		out = new BufferedWriter(new FileWriter(FilePath,true));
		for(int i =0;i<13;i++) {
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
