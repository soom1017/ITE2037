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
	public void addInventory(int index, int num) {
		salesList.get(index).addQuantity(num);
	}
	public void addExpirationDate(LocalDateTime present, LocalDateTime updatedExpiration) {
		for(Product p: salesList) {
			if(p.getClass().getCanonicalName().equals("assignment3.Food")) {
				Food food = (Food)p;
				if(food.isExpired(present))
					p = new Food(food, updatedExpiration);
			}
		}
	}
	
	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
}
