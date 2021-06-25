package assignment3;

import assignment3.exception.NotEnoughBalanceException;

public class Cash implements Payable {
	private String currency;
	private int amount;
	
	public Cash() {}
	public Cash(String currency, int amount) {
		this.currency = currency;
		this.amount = amount;
	}
	public Cash(Cash cash) {
		this.currency = cash.currency;
		this.amount = cash.amount;
	}
	
	public String toString() {
		return this.currency + ", " + this.amount + " won";
	}
	
	@Override
	public void pay(int amount) throws NotEnoughBalanceException {
		if(this.amount < amount) {
			String message = this.amount + " won is not enough.";
			throw new NotEnoughBalanceException(message);
		}	
		this.amount -= amount;
	}
}
