package assignment2;

public class ExtremeActivity extends Activity {
	private int minHeight;
	private int minWeight;
	
	//constructor
	public ExtremeActivity() {
		
	}
	public ExtremeActivity(String name, String location, int price, int minHei, int minWei) {
		super(name, location, price);
		this.minHeight = minHei;
		this.minWeight = minWei;
	}
	
	//copy-constructor
	public ExtremeActivity(ExtremeActivity act) {
		super(act.getName(), act.getLocation(), act.getPrice());
		this.minHeight = act.minHeight;
		this.minWeight = act.minWeight;
	}
	
	public int getMinHeight() {
		return this.minHeight;
	}
	public int getMinWeight() {
		return this.minWeight;
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if(getClass() != obj.getClass())
			return false;
		else {
			ExtremeActivity activity = (ExtremeActivity)obj;
			return this.getName() == activity.getName() 
					&& this.getLocation() == activity.getLocation() 
					&& this.getPrice() == activity.getPrice();
		}
	}
	
	public int getActualPrice(Person person) {
		if(person.getAge() >= 60)
			return this.getPrice()*130/100;
		else
			return super.getActualPrice(person);
	}
}
