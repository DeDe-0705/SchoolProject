

import java.io.IOException;
import java.util.*;


public class AddOnsRevenue {
	
	private List<Integer> data = new ArrayList<Integer>();
	private List<Double> price = new ArrayList<Double>();
	private List<Integer> bowls = new ArrayList<Integer>();
	
	public AddOnsRevenue() {
		Stats stats = new Stats();
		this.data = stats.returnData();
	}
	public List<Double> calculateRevenue() {
		
		int n1 = data.get(9)+data.get(12);
		int n2 = data.get(10)+data.get(13);
		int n3 = data.get(11)+data.get(14);
		int n4 = data.get(15);
		
		Double p1 = 0.0;
		Double p2 = 0.0;
		Double p3 = 0.0;
		Double p4 = 0.0;
		try {
			Price price = new Price();
			List<Double> buffer = new ArrayList<Double>();
			buffer = price.returnPrice();
			
			p1 = n1*buffer.get(1);
			p2 = n2*buffer.get(2);
			p3 = n3*buffer.get(3);
			p4 = n4*buffer.get(4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		price.add(p1);
		price.add(p2);
		price.add(p3);
		price.add(p4);
		
		return price;
	}
	public List<Integer> calculateBowls() {
		bowls.add(data.get(9)+data.get(12));
		bowls.add(data.get(10)+data.get(13));
		bowls.add(data.get(11)+data.get(14));
		bowls.add(data.get(15));
		return bowls;
	}
}
