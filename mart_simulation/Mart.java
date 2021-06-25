package assignment3;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Mart extends InventoryManager {
	private ArrayList<Product> salesList;
	public static int transactionNum = 1000;
	
	private Mart() {
		salesList = new ArrayList<Product>();
	}
	private static Mart uniqueInstance;
	public static synchronized Mart getIstance() {
		if(uniqueInstance == null)
			uniqueInstance = new Mart();
		
		return uniqueInstance;	
	}
	
	public ArrayList<Product> getSalesList() {
		return this.salesList;
	}
	public ArrayList<Food> getExpiredFoodList(LocalDateTime present) {
		ArrayList<Food> foodList = new ArrayList<Food>();
		for(Product p: salesList) {
			if(p.getClass().getCanonicalName().equals("assignment3.Food")) {
				Food food = (Food)p;
				if(food.isExpired(present))
					foodList.add(food);
			}
		}
		return foodList;
	}
	
	public void addProduct(Product p) {
		salesList.add(p);
	}
	public synchronized void addInventory(int index, int num) {
		salesList.get(index).addQuantity(num);
		execute();
	}
	public void addExpirationDate(LocalDateTime present, LocalDateTime updatedExpiration) {
		int count = 0;
		for(Product p: salesList) {
			if(p.getClass().getCanonicalName().equals("assignment3.Food")) {
				Food food = (Food)p;
				if(food.isExpired(present))
					salesList.set(count, new Food(food, updatedExpiration));
			}
			count++;
		}
	}
	
	public void decreaseProductQuantity(int index, int wantedQuantity) {
		int presentQuantity = this.salesList.get(index).getQuantity();
		if(wantedQuantity > presentQuantity)
			this.salesList.get(index).setQuantity(0);
		else
			this.salesList.get(index).setQuantity(presentQuantity - wantedQuantity);
	}

	@Override
	public void execute() {
		notifyObservers();
		
	}

}
