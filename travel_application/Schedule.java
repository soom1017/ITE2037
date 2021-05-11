package assignment1;

public class Schedule {
	private String name;
	private int days;
	private Activity[][] plan;
	private int expense;
	
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
	public int addActivity(Activity act, int day, int time) {
		//이미 일정에 존재하는 activity인지
		for(int i=0;i<12;i++) {
			for(int j=0;j<this.days;j++) {
				if(act.equals(this.plan[i][j])) return 0;
			}
		}
		//해당 시간에 이미 일정이 존재하는지 확인
		if(this.plan[time-9][day-1] == null) {
			this.plan[time-9][day-1] = new Activity(act);
			return 1;
		}
		else return 0;
	}
	
	public int removeActivity(int day, int time) {
		//해당 날짜와 시간에 일정이 존재하면 삭제
		if(this.plan[time-9][day-1] != null) {
			this.plan[time-9][day-1] = null;
			return 1;
		}
		else return 0;
	}
	
	public void calExpense() {
		//expense 초기화
		this.setExpense(0);
		//plan의 각 activity들의 price 합 구하기
		for(int i=0;i<12;i++) {
			for(int j=0;j<this.days;j++) {
				
				if(this.plan[i][j] != null)
					this.expense += this.plan[i][j].getPrice();
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
	}
}
