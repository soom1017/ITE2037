package assignment3;

import java.util.ArrayList;

public abstract class InventoryManager {
	private ArrayList<Observer> observers;
	
	public abstract void addObserver(Observer observer);
	public abstract void deleteObserver(Observer observer);
	public abstract void notifyObservers();
}
