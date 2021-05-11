package assignment2;

public class Person {
	private String name;
	private int age;
	private int height;
	private int weight;
	
	public Person() {
		
	}
	public Person(String name, int age, int height, int weight) {
		this.name = name;
		this.age = age;
		this.height = height;
		this.weight = weight;
	}
	public Person(Person anotherPerson) {
		this.name = anotherPerson.name;
		this.age = anotherPerson.age;
		this.height = anotherPerson.height;
		this.weight = anotherPerson.weight;
	}
	
	public String getName() {
		return this.name;
	}
	public int getAge() {
		return this.age;
	}
	public int getHeight() {
		return this.height;
	}
	public int getWeight() {
		return this.weight;
	}
	
	public String toString() {
		return (this.name + ", " + this.age + " years old, " + this.height + "cm, " + this.weight + "kg");
	}
}
