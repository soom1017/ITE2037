package assignment3;

import java.util.ArrayList;

public abstract class InventoryManager {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public void addObserver(Observer observer) {
		observers.add(observer);
		
	}
	public void deleteObserver(Observer observer) {
		observers.remove(observer);
		
	}
	public void notifyObservers() {
		for(int i=0;i<observers.size();i++) {
			observers.get(i).update(this);
		}
	}
	public abstract void execute();
}
