package assignment2;

public class ExtremeActivity extends Activity {
	private int minHeight;
	private int minWeight;
	
	public ExtremeActivity() {
		
	}
	public ExtremeActivity(String name, String location, int price, int minHei, int minWei) {
		super(name, location, price);
		this.minHeight = minHei;
		this.minWeight = minWei;
	}
	
	public int getMinHeight() {
		return this.minHeight;
	}
	public int getMinWeight() {
		return this.minWeight;
	}
	
	public int getActualPrice(Person person) {
		if(person.getAge() >= 60)
			return this.getPrice()*70/100;
		else
			return super.getActualPrice(person);
	}
}
