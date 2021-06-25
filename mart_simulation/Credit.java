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
	
	public Credit(Credit credit) {
		this.bank = credit.bank;
		this.limit = credit.limit;
		this.amountUsed = credit.amountUsed;
	}
	
	public String getBank() {
		return this.bank;
	}
	
	public String toString() {
		return this.bank + ", Amount used: " + this.amountUsed + " won (Limit: " + this.limit + " won)";
	}
	
	@Override
	public void pay(int price) throws NotEnoughBalanceException {
		if(limit - amountUsed < price) {
			String message = (this.limit - this.amountUsed) + " won left. This is not enough.";
			throw new NotEnoughBalanceException(message);
		}
		amountUsed += price;
		limit -= amountUsed;
	}
}
