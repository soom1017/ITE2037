package assignment2;

import assignment2.exception.InsufficientConditionException;
import assignment2.exception.InvalidAccessException;

public class Schedule {
	private String name;
	private int days;
	private Activity[][] plan;
	private int expense;
	private Person[] member;
	
	public static int scheduleNum = 0;
	
	//constructor
	public Schedule() {
		
	}
	public Schedule(String name, int days) {
		this.name = name;
		this.days = days;
		this.plan = new Activity[12][this.days];
		this.expense = 0;
	}
	public Schedule(String name, int days, Person[] member, int memberNum) {
		this.name = name;
		this.days = days;
		this.plan = new Activity[12][this.days];
		this.expense = 0;
		
		this.member = new Person[memberNum];
		for(int i=0;i<memberNum;i++) {
			this.member[i] = new Person(member[i]);
		}
	}
	
	//copy-constructor
	public Schedule(Schedule anotherSchedule) {
		this.name = anotherSchedule.name;
		this.days = anotherSchedule.days;
		this.plan = new Activity[12][this.days];
		for(int i=0;i<12;i++) {
			for(int j=0;j<this.days;j++) {
				if(anotherSchedule.plan[i][j] != null)
					this.plan[i][j] = new Activity(anotherSchedule.plan[i][j]);
			}
		}
		this.expense = anotherSchedule.expense;
		
		this.member = new Person[anotherSchedule.member.length];
		for(int i=0;i<anotherSchedule.member.length;i++) {
			this.member[i] = new Person(anotherSchedule.member[i]);
		}
		
	}
	
	//getter, setter
	public String getName() {
		return this.name;
	}
	public int getExpense() {
		return this.expense;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setExpense(int expense) {
		this.expense = expense;
	}
	
	//toString
	public String toString() {
		return this.getName();
	}
	
	//etc
	public void addActivity(Activity act, int day, int time) throws Exception {
		//모든 멤버가 가능한 activity인지
		if(act.getClass().getCanonicalName() == "assignment2.ExtremeActivity") {
			ExtremeActivity extremeAct = (ExtremeActivity)act;
			for(Person person: member) {
				if(person.getHeight() < extremeAct.getMinHeight() || person.getWeight() < extremeAct.getMinWeight())
					throw new InsufficientConditionException();
			}
		}
		if(act.getClass().getCanonicalName() == "assignment2.ShowActivity") {
			ShowActivity showAct = (ShowActivity)act;
			for(Person person: member) {
				if(person.getAge() < showAct.getMinAge())
					throw new InsufficientConditionException();
			}
		}
		//이미 일정에 존재하는 activity인지
		for(int i=0;i<12;i++) {
			for(int j=0;j<this.days;j++) {
				if(act.equals(this.plan[i][j])) {
					throw new InvalidAccessException();
				}
			}
		}
		//해당 시간에 이미 일정이 존재하는지 확인
		if(this.plan[time-9][day-1] != null) {
			throw new InvalidAccessException();	
		}
		this.plan[time-9][day-1] = new Activity(act);
	}
	
	public void removeActivity(int day, int time) throws InvalidAccessException {
		//해당 날짜와 시간에 일정이 존재하는지 확인
		if(this.plan[time-9][day-1] == null) {
			throw new InvalidAccessException("no activity exists at that time.");
		}
		this.plan[time-9][day-1] = null;
	}
	
	public void calExpense() {
		//expense 초기화
		this.setExpense(0);
		//plan의 각 activity들의 price 합 구하기
		for(int i=0;i<12;i++) {
			for(int j=0;j<this.days;j++) {
				
				if(this.plan[i][j] != null)
					for(Person person: member)
						this.expense += this.plan[i][j].getActualPrice(person);
			}
		}
	}
	
	public void printPlan() {
		//-----------------------------------------
		String format = "";
		for(int i=0;i< 16 * (1+this.days);i++) {
			format += "-";
		}
		System.out.println(format);
		
		//                  Day1               Day2 ...
		System.out.printf("%-16s", "");
		for(int i=0;i<this.days;i++) {
			System.out.printf("%-16s", "Day " + (i+1));
		}
		System.out.println();
		
		//9:00              ----               ---- ...
		for(int i=0;i<12;i++) {
			System.out.printf("%-16s", (i+9) + ":00");
			for(int j=0;j<this.days;j++) {
				if(this.plan[i][j] != null) {
					System.out.printf("%-16s", this.plan[i][j].getName());
				}
				else
					System.out.printf("%-16s", "----");
			}
			System.out.println();
		}
		//-----------------------------------------
		System.out.println(format);
		
		//Total expenses: ...
		//-----------------------------------------
		this.calExpense();
		System.out.println("Total expenses: " + this.getExpense());
		System.out.println(format);
		
		//Members:
		//...
		//-----------------------------------------
		System.out.println("Members:");
		for(Person person: member)
			System.out.println(person.toString());
		System.out.println(format);
	}
}
