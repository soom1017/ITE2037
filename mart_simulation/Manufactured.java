package assignment3;

import java.time.format.DateTimeFormatter;

public class Manufactured extends Product {
	public String brand;
	
	public Manufactured() {}
	public Manufactured(String name, int price, int quantity, String brand) {
		super(name, price, quantity);
		this.brand = brand;
	}
	public Manufactured(Manufactured manufactured) {
		super(manufactured.getName(), manufactured.getPrice(), manufactured.getQuantity());
		this.brand = manufactured.brand;
	}
	
	public String toString() {
		return "(Quantity: " + this.getQuantity() + ") "
				+ this.getName() + ", " + this.getPrice() + " won, "
				+ "Brand: " + this.brand;
	}
}
