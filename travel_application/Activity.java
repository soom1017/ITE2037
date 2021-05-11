package assignment1;

public class Activity {
	private String name;
	private String location;
	private int price;
	
	//constructor
	public Activity() {
		
	}
	public Activity(String name, String location, int price) {
		this.name = name;
		this.location = location;
		this.price = price;
	}
	
	//copy-constructor
	public Activity(Activity anotherActivity) {
		if(anotherActivity != null) {
			this.name = anotherActivity.name;
			this.location = anotherActivity.location;
			this.price = anotherActivity.price;
		}
	}
	
	//getter
	public String getName() {
		return this.name;
	}
	public String getLocation() {
		return this.location;
	}
	public int getPrice() {
		return this.price;
	}
	
	//toString
	public String toString() {
		return (this.getName() + "(" + this.getLocation() + ", " + this.getPrice() + " won)");
	}
	//equals
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if(getClass() != obj.getClass())
			return false;
		else {
			Activity activity = (Activity)obj;
			return this.getName() == activity.getName() 
					&& this.getLocation() == activity.getLocation() 
					&& this.getPrice() == activity.getPrice();
		}
	}
}
