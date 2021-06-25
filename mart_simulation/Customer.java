package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.PrintWriter;

import java.time.LocalDateTime;

import assignment3.exception.*;

public class Customer implements Observer {
	private InventoryManager manager;
	
	private String name;
	private ArrayList<Payable> wallet;
	private ArrayList<Product> shoppingCart;
	private ArrayList<Product> wishList;
	
	public Customer() {}
	public Customer(String name, InventoryManager manager) {
		this.manager = manager;
		manager.addObserver(this);
		
		this.name = name;
		this.wallet = new ArrayList<Payable>();
		this.shoppingCart = new ArrayList<Product>();
		this.wishList = new ArrayList<Product>();
	}
	
	public ArrayList<Payable> getWallet() {
		return this.wallet;
	}
	public ArrayList<Product> getShoppingCart() {
		return this.shoppingCart;
	}
	
	public void setShoppingCart(ArrayList<Product> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public String toString() {
		return this.name;
	}
	
	public void addPayable(Payable p) {
		wallet.add(p);
	}
	public synchronized void addProductToCart(Product p, int wantedQuantity) {
		//원하는 product = food
		if(p.getClass().getCanonicalName().equals("assignment3.Food")) {
			Food food = (Food)p;
			//수량이 모자란 경우
			if(food.getQuantity() < wantedQuantity) { 
				//observer-pattern
				int lackingQuantity = wantedQuantity - food.getQuantity();
				
				int count = 0;
				boolean sameProductExists = false;
				for(Product existingProduct: shoppingCart) {
					if(existingProduct.equals(food)) { //이미 쇼핑카트에 있는 product의 수만 추가하는 경우
						int presentQuantity = shoppingCart.get(count).getQuantity();
						shoppingCart.set(count, new Food(food, presentQuantity + food.getQuantity()));
						
						sameProductExists = true;
						break;
					}
					count++;	
				}
				if(sameProductExists == false)
					shoppingCart.add(new Food(food, food.getQuantity()));
				//wishList(대기목록)에 product 추가
				wishList.add(new Food(food, lackingQuantity));
			}
			else {
				int count = 0;
				boolean sameProductExists = false;
				for(Product existingProduct: shoppingCart) {
					if(existingProduct.equals(food)) { //이미 쇼핑카트에 있는 product의 수만 추가하는 경우
						int presentQuantity = shoppingCart.get(count).getQuantity();
						shoppingCart.set(count, new Food(food, presentQuantity + wantedQuantity));
						
						sameProductExists = true;
						break;
					}
					count++;	
				}
				if(sameProductExists == false)
					shoppingCart.add(new Food(food, wantedQuantity));
			}
		}
		//원하는 product = manufactured
		if(p.getClass().getCanonicalName().equals("assignment3.Manufactured")) {
			Manufactured good = (Manufactured)p;
			//수량이 모자란 경우
			if(good.getQuantity() < wantedQuantity) { 
				//observer-pattern
				int lackingQuantity = wantedQuantity - good.getQuantity();
				
				int count = 0;
				boolean sameProductExists = false;
				for(Product existingProduct: shoppingCart) {
					if(existingProduct.equals(good)) { //이미 쇼핑카트에 있는 product의 수만 추가하는 경우
						int presentQuantity = shoppingCart.get(count).getQuantity();
						shoppingCart.set(count, new Manufactured(good, presentQuantity + good.getQuantity()));
						
						sameProductExists = true;
						break;
					}
					count++;	
				}
				if(sameProductExists == false)
					shoppingCart.add(new Manufactured(good, good.getQuantity()));
				//wishList(대기목록)에 product 추가
				wishList.add(new Manufactured(good, lackingQuantity));
			}
			else {
				int count = 0;
				boolean sameProductExists = false;
				for(Product existingProduct: shoppingCart) {
					if(existingProduct.equals(good)) { //이미 쇼핑카트에 있는 product의 수만 추가하는 경우
						int presentQuantity = shoppingCart.get(count).getQuantity();
						shoppingCart.set(count, new Manufactured(good, presentQuantity + wantedQuantity));
						
						sameProductExists = true;
						break;
					}
					count++;	
				}
				if(sameProductExists == false)
					shoppingCart.add(new Manufactured(good, wantedQuantity));
			}
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
		System.out.printf("%-12s%30d\n", "Total price", this.calPrice());
		System.out.println("------------------------------------------");
	}
	
	public void printReceipt(PrintWriter outputStream, int index) {
		outputStream.println("Product name  Unit Price  Quantity  Amount");
		outputStream.println("------------------------------------------");
		for(Product p: shoppingCart) {
			outputStream.printf("%-12s%12d%10d%8d\n", p.getName(), p.getPrice(), p.getQuantity(), p.getPrice() * p.getQuantity());
		}
		outputStream.println("------------------------------------------");
		outputStream.printf("%-12s%30d\n", "Total price", this.calPrice());
		outputStream.println("------------------------------------------");
		
		Payable payable = this.wallet.get(index);
		if(payable.getClass().getCanonicalName().equals("assignment3.Cash"))
			outputStream.println("Cash");
		if(payable.getClass().getCanonicalName().equals("assignment3.Credit")) {
			Credit credit = (Credit)payable;
			outputStream.println("Credit, " + credit.getBank());
		}
	}
	
	public void payProductsInCart(int index) throws NotEnoughBalanceException {
		Payable payable = this.wallet.get(index);
		while(true) {
			try {
				payable.pay(this.calPrice());
				break;
			} 
			catch(NotEnoughBalanceException e) {
				System.out.println(e.getMessage());
				throw new NotEnoughBalanceException();
			}
		}
	}
	
	@Override
	public synchronized void update(InventoryManager generator) {
		if(generator.getClass().getCanonicalName().equals("assignment3.Mart")) {
			Mart mart = (Mart)generator;
		}
		for(Iterator<Product> iter = wishList.iterator();iter.hasNext();) {
			int salesListIndex = 0;
			Product wishingProd = iter.next();
			for(Product p: Mart.getIstance().getSalesList()) {
				if(wishingProd.equals(p) && p.getQuantity() > 0) {
					this.addProductToCart(wishingProd, wishingProd.getQuantity());
					iter.remove();
					Mart.getIstance().decreaseProductQuantity(salesListIndex, wishingProd.getQuantity());
				}
				salesListIndex++;
					
			}
		}
		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
