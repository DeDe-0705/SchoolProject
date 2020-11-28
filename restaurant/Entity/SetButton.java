

import java.io.*;
import java.util.*;

public class SetButton {
	
	private List<String> Data = new ArrayList<String>();
	private BufferedWriter out;
	private BufferedReader in;
	private String FileName;
	private String FilePath;
	
	public SetButton(List<String> x) throws IOException {
		
		this.Data = x;
		this.FileName = "Button_Signal.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
	}
	public SetButton() {
		this.FileName = "Button_Signal.txt";
		this.FilePath = this.getClass().getResource("/").getPath() + FileName;
	}
	public void WriteSignal() throws IOException {
		
		try {
			out = new BufferedWriter(new FileWriter(FilePath));
			out.write("Tonkotsu#"+Data.get(0)+"\n");
			out.write("Shoyu#"+Data.get(1)+"\n");
			out.write("Shio#"+Data.get(2)+"\n");
			out.write("Nori#"+Data.get(3)+"\n");
			out.write("Chashu#"+Data.get(4)+"\n");
			out.write("Boiled egg#"+Data.get(5)+"\n");
			out.write("Bamboo shoots#"+Data.get(6)+"\n");
			out.write("Soft#"+Data.get(7)+"\n");
			out.write("Medium#"+Data.get(8)+"\n");
			out.write("Firm#"+Data.get(9)+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			out.close();
		}
	}
	public List<String> ReadSignal() throws IOException {
		
		String content = null;
		String Buffer[] = new String [2];
		
		try {
			in = new BufferedReader(new FileReader(FilePath));
			while((content=in.readLine())!=null) {
				Buffer = content.split("\\#");
				Data.add(Buffer[1]);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			in.close();
		}
		return Data;
	}
}
