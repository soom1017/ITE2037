package assignment3;

import assignment3.exception.*;

public class Credit implements Payable {
	private String bank;
	private int limit;
	private int amountUsed;
	
	public Credit(String bank, int limit, int amountUsed) {
		this.bank = bank;
		this.limit = limit;
		this.amountUsed = amountUsed;
	}
	
	public String getBank() {
		return this.bank;
	}
	
	public String toString() {
		return this.bank + ", Amount used: " + this.amountUsed + " won (Limit: " + this.limit + " won)";
	}
	
	@Override
	public void pay(int price) throws NotEnoughBalanceException {
		if(limit - amountUsed < price)
			throw new NotEnoughBalanceException();
		amountUsed += price;
		limit -= amountUsed;
	}
}
