package assignment1;
import java.util.Scanner;

public class TravelScheduler {
	
	public static void printList(Object[] list) {
		for(int i=0;i<list.length;i++) {
			if(list[i] != null)
				System.out.println((i+1) + ") " + list[i].toString());
			else
				System.out.println((i+1) + ") EMPTY SCHEDULE"); // schedule list - empty schedule 가능
		}
	}

	public static void main(String[] args) {
		
		//schedule & activity list 생성
		Schedule[] scheduleList = new Schedule[5];
		Activity[] activityList = new Activity[8];
		activityList[0] = new Activity("Hiking", "Mountain", 0); 
		activityList[1] = new Activity("Horse Riding", "Hill", 3000); 
		activityList[2] = new Activity("Visiting Museum", "Museum", 8000); 
		activityList[3] = new Activity("Watching movie", "Theater", 11000); 
		activityList[4] = new Activity("Fishing", "Sea", 15000); 
		activityList[5] = new Activity("Surffing", "Beach", 20000); 
		activityList[6] = new Activity("Camping", "Field", 30000); 
		activityList[7] = new Activity("Paragliding", "Mountain", 50000);
		
		//main menu
		System.out.print("1)Select schedule\n"
						+ "2)Edit schedule\n"
						+ "3)End program\n"
						+ "Select menu: ");
		Scanner keyboard = new Scanner(System.in);
		int mainInput = keyboard.nextInt();
		
		while(mainInput != 3) {
			if(mainInput == 1) { //1) select schedule
				printList(scheduleList);
				System.out.print("Select a schedule: ");
				int num = keyboard.nextInt();
				
				if(num != 0 && num <= Schedule.scheduleNum) {
					System.out.print("1)Add activity\n"
									+ "2)Remove activity\n"
									+ "3)Print schedule\n"
									+ "Select menu: ");
					int selectInput = keyboard.nextInt();
					
					while(selectInput != 0) { 
						if(selectInput == 1) { // 1) Add activity
							printList(activityList);
							System.out.print("Select activity to do: ");
							int actNum = keyboard.nextInt();
							System.out.print("Enter the day to do activity: ");
							int day = keyboard.nextInt();
							System.out.print("Enter the time to do activity(9~20): ");
							int time = keyboard.nextInt();
							
							if(scheduleList[num-1].addActivity(activityList[--actNum], day, time) == 0)
								System.out.println("Fail to add activity");
								
						}
						if(selectInput == 2) { // 2) Remove activity
							scheduleList[num-1].printPlan();
							System.out.print("Enter the day to remove activity: ");
							int day = keyboard.nextInt();
							System.out.print("Enter the time to remove activity: ");
							int time = keyboard.nextInt();
							
							if(scheduleList[num-1].removeActivity(day, time) != 0)
								System.out.println("Removed successfully");
						}
						if(selectInput == 3) { // 3) Print schedule
							scheduleList[num-1].printPlan();
						}
						System.out.print("1)Add activity\n"
										+ "2)Remove activity\n"
										+ "3)Print schedule\n"
										+ "Select menu: ");
						selectInput = keyboard.nextInt();
					}
				}
				
			}
			if(mainInput == 2) { //2) edit schedule
				System.out.print("1)Make a new schedule\n"
								+ "2)Copy an existing schedule\n"
								+ "Select menu: ");
				int editInput = keyboard.nextInt();
				
				if(editInput == 1) { // 1) Make a new schedule
					System.out.print("Enter a name for the schedule: ");
					String temp = keyboard.nextLine();
					String scheduleName = keyboard.nextLine();
					System.out.print("Enter travel days: ");
					int travelDays = keyboard.nextInt();
					
					scheduleList[Schedule.scheduleNum++] = new Schedule(scheduleName, travelDays);
				}
				
				if(editInput == 2) { // 2) Copy an existing schedule
					printList(scheduleList);					
					System.out.print("Select the schedule to copy: ");
					int num = keyboard.nextInt();
					
					if(num <= Schedule.scheduleNum) {
						System.out.print("Enter a new schedule name: ");
						String temp = keyboard.nextLine();
						String scheduleName = keyboard.nextLine();
					
						scheduleList[Schedule.scheduleNum] = new Schedule(scheduleList[--num]);
						scheduleList[Schedule.scheduleNum++].setName(scheduleName);
					}
				}
			}
			System.out.print("1)Select schedule\n"
							+ "2)Edit schedule\n"
							+ "3)End program\n"
							+ "Select menu: ");
			mainInput = keyboard.nextInt();
		}
		keyboard.close();
	}

}
