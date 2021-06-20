package assignment3;

import java.util.ArrayList;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import assignment3.exception.*;

public class Customer implements Observer {
	private String name;
	private ArrayList<Payable> wallet;
	private ArrayList<Product> shoppingCart;
	
	public Customer() {}
	public Customer(String name) {
		this.name = name;
		this.wallet = new ArrayList<Payable>();
		this.shoppingCart = new ArrayList<Product>();
	}
	
	public ArrayList<Payable> getWallet() {
		return this.wallet;
	}
	public ArrayList<Product> getShoppingCart() {
		return this.shoppingCart;
	}

	public String toString() {
		return this.name;
	}
	
	public void addPayable(Payable p) {
		wallet.add(p);
	}
	public void addProductToCart(Product p, int wantedQuantity, LocalDateTime present) throws Exception {
		if(p.getClass().getCanonicalName().equals("assignment3.Food")) {
			Food food = (Food)p;
			if(food.isExpired(present))
				throw new ExpiredException();
			if(food.getQuantity() < wantedQuantity) {
				//to-do
			}
			else shoppingCart.add(new Food(food));
		}
		else {
			Manufactured good = (Manufactured)p;
			if(good.getQuantity() < wantedQuantity) {
				//to-do
			}
			else shoppingCart.add(new Manufactured(good));
		}
	}
	
	public int calPrice() {
		int totalPrice = 0;
		for(Product p: shoppingCart) {
			totalPrice += p.getPrice() * p.getQuantity();
		}
		return totalPrice;
	}
	
	public void printShoppingCart() {
		System.out.println("Product name  Unit Price  Quantity  Amount");
		System.out.println("------------------------------------------");
		for(Product p: shoppingCart) {
			System.out.printf("%-12s%12d%10d%8d\n", p.getName(), p.getPrice(), p.getQuantity(), p.getPrice() * p.getQuantity());
		}
		System.out.println("------------------------------------------");
		System.out.printf("%-12s%30d", "Total price", this.calPrice());
		System.out.println("------------------------------------------");
	}
	
	public void printReceipt(PrintWriter outputStream, int index) {
		outputStream.println("Product name  Unit Price  Quantity  Amount");
		outputStream.println("------------------------------------------");
		for(Product p: shoppingCart) {
			outputStream.printf("%-12s%12d%10d%8d\n", p.getName(), p.getPrice(), p.getQuantity(), p.getPrice() * p.getQuantity());
		}
		outputStream.println("------------------------------------------");
		outputStream.printf("%-12s%30d", "Total price", this.calPrice());
		outputStream.println("------------------------------------------");
		
		Payable payable = this.wallet.get(index);
		if(payable.getClass().getCanonicalName().equals("assignment3.Cash"))
			outputStream.println("Cash");
		if(payable.getClass().getCanonicalName().equals("assignment3.Credit")) {
			Credit credit = (Credit)payable;
			outputStream.println("Credit, " + credit.getBank());
		}
	}
	
	public void payProductsInCart(int index) {
		Payable payable = this.wallet.get(index);
		while(true) {
			try {
				payable.pay(this.calPrice());
				this.shoppingCart = new ArrayList<Product>();
				break;
			} 
			catch(NotEnoughBalanceException e) {
				System.out.println("Select payable again."); //to-do: right statement
			}
		}
	}
	
	@Override
	public void update(InventoryManager generator) {
		
		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
