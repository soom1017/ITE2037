package assignment2;

public class ShowActivity extends Activity {
	private int minAge;
	
	public ShowActivity() {
		
	}
	public ShowActivity(String name, String location, int price, int minAge) {
		super(name, location, price);
		this.minAge = minAge;
	}
	
	//copy-constructor
	public ShowActivity(ShowActivity act) {
		super(act.getName(), act.getLocation(), act.getPrice());
		this.minAge = act.minAge;
	}
	
	public int getMinAge() {
		return this.minAge;
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if(getClass() != obj.getClass())
			return false;
		else {
			ShowActivity activity = (ShowActivity)obj;
			return this.getName() == activity.getName() 
					&& this.getLocation() == activity.getLocation() 
					&& this.getPrice() == activity.getPrice();
		}
	}
	
	public int getActualPrice(Person person) {
		if(person.getAge() <= 19)
			return this.getPrice()*80/100;
		else
			return super.getActualPrice(person);
	}
}
