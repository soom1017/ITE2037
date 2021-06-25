package assignment3;

public class Product {
	private String name;
	private int price;
	private int quantity;
	
	public Product() {}
	public Product(String name, int price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getName() {
		return this.name;
	}
	public int getPrice() {
		return this.price;
	}
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addQuantity(int num) {
		this.quantity += num;
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if(this.getClass() != obj.getClass())
			return false;
		else {
			Product p = (Product) obj;
			if(this.getName().equals(p.getName()))
				return true;
			else return false;
		}
	}
}
