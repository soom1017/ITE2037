package assignment3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Food extends Product {
	private LocalDateTime expirationDateTime;
	
	public Food() {}
	public Food(String name, int price, int quantity, LocalDateTime expiration) {
		super(name, price, quantity);
		this.expirationDateTime = expiration;
	}
	public Food(Food food, LocalDateTime updatedExpiration) {
		super(food.getName(), food.getPrice(), food.getQuantity());
		this.expirationDateTime = updatedExpiration;
	}
	public Food(Food food, int wantedQuantity) {
		super(food.getName(), food.getPrice(), wantedQuantity);
		this.expirationDateTime = food.expirationDateTime;
	}
	
	public boolean isExpired(LocalDateTime present) {
		if(expirationDateTime.isBefore(present))
			return true;
		else return false;
	}
	
	public String toString() {
		return "(Quantity: " + this.getQuantity() + ") "
				+ this.getName() + ", " + this.getPrice() + " won, "
				+ "Best before: " + expirationDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
