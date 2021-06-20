package assignment3;

import assignment3.exception.NotEnoughBalanceException;

public interface Payable {
	public abstract void pay(int amount) throws NotEnoughBalanceException;
}
