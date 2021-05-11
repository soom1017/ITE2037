package assignment2;

public class ShowActivity extends Activity {
	private int minAge;
	
	public ShowActivity() {
		
	}
	public ShowActivity(String name, String location, int price, int minAge) {
		super(name, location, price);
		this.minAge = minAge;
	}
	
	public int getMinAge() {
		return this.minAge;
	}
	
	public int getActualPrice(Person person) {
		if(person.getAge() <= 19)
			return this.getPrice()*80/100;
		else
			return super.getActualPrice(person);
	}
}
