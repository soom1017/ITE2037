package assignment2;

import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import assignment2.exception.*;

public class TravelScheduler {
	
	public static void printList(Object[] list) {
		System.out.println();
		for(int i=0;i<list.length;i++) {
			if(list[i] != null)
				System.out.println((i+1) + ") " + list[i].toString());
			else
				System.out.println((i+1) + ") EMPTY SCHEDULE"); // schedule list - empty schedule 가능
		}
	}
	
	public static void main(String[] args) {
		//file 읽기
		Scanner inputStream1 = null;
		Scanner inputStream2 = null;
		try {
			inputStream1 = new Scanner(new FileInputStream("ActivityList.txt"));
			inputStream2 = new Scanner(new FileInputStream("MemberList.txt"));
		} 
		catch(FileNotFoundException e) {
			System.exit(0);
		}
		int activityListSize = inputStream1.nextInt();
		int memberListSize = inputStream2.nextInt();
		
		String junk1 = inputStream1.nextLine();
		String junk2 = inputStream2.nextLine();
		
		//schedule & activity & member list 생성
		Schedule[] scheduleList = new Schedule[5];
		Activity[] activityList = new Activity[activityListSize];
		Person[] memberList = new Person[memberListSize];
		
		//activity list 초기화
		for(int i=0;i<activityList.length;i++) {
			String line = inputStream1.nextLine();
			String[] activityInfo = line.split(",");
			for(int j=0;j<activityInfo.length;j++)
				activityInfo[j] = activityInfo[j].trim();
			
			String name = activityInfo[1];
			String location = activityInfo[2];
			int price = Integer.parseInt(activityInfo[3]);
			
			if(activityInfo[0].equals("Extreme")) {
				int minHeight = Integer.parseInt(activityInfo[4]);
				int minWeight = Integer.parseInt(activityInfo[5]);
				activityList[i] = new ExtremeActivity(name, location, price, minHeight, minWeight);
			}
			else if(activityInfo[0].equals("Show")) {
				int minAge = Integer.parseInt(activityInfo[4]);
				activityList[i] = new ShowActivity(name, location, price, minAge);
			}
			else {
				activityList[i] = new Activity(name, location, price);
			}
		}
		inputStream1.close();
		
		//member list 초기화
		for(int i=0;i<memberList.length;i++) {
			String line = inputStream2.nextLine();
			String[] memberInfo = line.split(",");
			for(int j=0;j<memberInfo.length;j++)
				memberInfo[j] = memberInfo[j].trim();
			
			String name = memberInfo[0];
			int age = Integer.parseInt(memberInfo[1]);
			int height = Integer.parseInt(memberInfo[2]);
			int weight = Integer.parseInt(memberInfo[3]);
			
			memberList[i] = new Person(name, age, height, weight);
		}
		inputStream2.close();
		
		//main menu
		System.out.print("1)Select schedule\n"
						+ "2)Edit schedule\n"
						+ "3)End program\n"
						+ "Select menu: ");
		
		Scanner keyboard = new Scanner(System.in);
		int mainInput;
		while(true) {
			try {
				mainInput = keyboard.nextInt();
				if(mainInput > 3 || mainInput < 0) {
					throw new InvalidAccessException();
				}
				break;
			}
			catch(InputMismatchException e) {
				keyboard = new Scanner(System.in);
				System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect menu: ");
			}
			catch(InvalidAccessException e) {
				keyboard = new Scanner(System.in);
				System.out.print(e.getMessage() + "\nSelect menu: ");
			}
		}
		
		while(mainInput != 3) {
			if(mainInput == 1) { //1) select schedule
				try {
					printList(scheduleList);
					System.out.print("Select a schedule: ");
					int num;
					while(true) {
						try {
							num = keyboard.nextInt();
							break;
						}
						catch(InputMismatchException e) {
							keyboard = new Scanner(System.in);
							System.out.println("**INPUT MISMATCH: please select again.**");
						}
					}
					
					if(num > Schedule.scheduleNum)
						throw new InvalidAccessException("it's an EMPTY schedule.");
					
					if(num != 0 && num <= Schedule.scheduleNum) {
						System.out.print("\n1)Add activity\n"
										+ "2)Remove activity\n"
										+ "3)Print schedule\n"
										+ "Select menu: ");
						int selectInput;
						while(true) {
							try {
								selectInput = keyboard.nextInt();
								if(selectInput < 0 || selectInput > 3) {
									throw new InvalidAccessException();
								}
								break;
							}
							catch(InputMismatchException e) {
								keyboard = new Scanner(System.in);
								System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect menu: ");
							}
							catch(InvalidAccessException e) {
								keyboard = new Scanner(System.in);
								System.out.print(e.getMessage() + "\nSelect menu: ");
							}
						}
						
						while(selectInput != 0) { 
							if(selectInput == 1) { // 1) Add activity
								while(true) {
									printList(activityList);
									System.out.print("Select activity to do: ");
									int actNum = keyboard.nextInt();
									System.out.print("Enter the day to do activity: ");
									int day = keyboard.nextInt();
									System.out.print("Enter the time to do activity(9~20): ");
									int time = keyboard.nextInt();
									
									try {
										scheduleList[num-1].addActivity(activityList[--actNum], day, time);
										break;
									} 
									catch(InsufficientConditionException e) {
										System.out.println("Fail to add activity\n" + e.getMessage());
									} 
									catch(InvalidAccessException e) {
										System.out.println("Fail to add activity\n" + e.getMessage());
									} 
									catch(Exception e) {
										System.out.println("Fail to add activity");
									}
								}
									
							}
							if(selectInput == 2) { // 2) Remove activity
								scheduleList[num-1].printPlan();
								System.out.print("Enter the day to remove activity: ");
								int day = keyboard.nextInt();
								System.out.print("Enter the time to remove activity: ");
								int time = keyboard.nextInt();
								try {
									scheduleList[num-1].removeActivity(day, time);
									System.out.println("Removed successfully");
								}
								catch(InvalidAccessException e) {
									System.out.println(e.getMessage());
								}
							}
							if(selectInput == 3) { // 3) Print schedule
								scheduleList[num-1].printPlan();
							}
							System.out.print("\n1)Add activity\n"
											+ "2)Remove activity\n"
											+ "3)Print schedule\n"
											+ "Select menu: ");
							while(true) {
								try {
									selectInput = keyboard.nextInt();
									if(selectInput > 3 || selectInput < 0) {
										throw new InvalidAccessException();
									}
									break;
								}
								catch(InputMismatchException e) {
									keyboard = new Scanner(System.in);
									System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect menu: ");
								}
								catch(InvalidAccessException e) {
									keyboard = new Scanner(System.in);
									System.out.print(e.getMessage() + "\nSelect menu: ");
								}
							}
						}
					}
				}
				catch(InvalidAccessException e) {
					System.out.println(e.getMessage());
				}
				
			}
			if(mainInput == 2) { //2) edit schedule
				System.out.print("\n1)Make a new schedule\n"
								+ "2)Copy an existing schedule\n"
								+ "Select menu: ");
				int editInput;
				while(true) {
					try {
						editInput = keyboard.nextInt();
						if(editInput > 2 || editInput < 0) {
							throw new InvalidAccessException();
						}
						break;
					}
					catch(InputMismatchException e) {
						keyboard = new Scanner(System.in);
						System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect menu: ");
					}
					catch(InvalidAccessException e) {
						keyboard = new Scanner(System.in);
						System.out.print(e.getMessage() + "\nSelect menu: ");
					}
				}
				
				if(editInput == 1) { // 1) Make a new schedule
					try {
						if(Schedule.scheduleNum >= scheduleList.length) // Schedule list를 초과해서 schedule 생성하려 할 경우
							throw new ArrayFullException("too many schedule.");
						
						System.out.print("Enter a name for the schedule: ");
						String temp = keyboard.nextLine();
						String scheduleName = keyboard.nextLine();
						System.out.print("Enter travel days: ");
						int travelDays = keyboard.nextInt();
						while(true) {
							printList(memberList);
							System.out.print("Select members: ");
							try {
								temp = keyboard.nextLine();
								String[] selectedNumbers = keyboard.nextLine().split(",");
								Person[] members = new Person[selectedNumbers.length];
								
								//memberList 범위 안에 있는 수를 선택했는지 확인 후, schedule의 멤버로 넣음.
								for(int i=0;i<selectedNumbers.length;i++) {
									int num = Integer.parseInt(selectedNumbers[i].trim());
									if(num > memberList.length || num < 1) // 사용자가 선택한 숫자가 memberList 범위를 벗어날 경우
										throw new InvalidAccessException();
									members[i] = new Person(memberList[num-1]);
								}
								scheduleList[Schedule.scheduleNum++] = new Schedule(scheduleName, travelDays, members, members.length);
								System.out.println("successfully created schedule");
								break;
							}
							catch(InvalidAccessException e) {
								System.out.println(e.getMessage());
							}
						}
					
					}
					catch(ArrayFullException e) {
						System.out.println(e.getMessage());
					}
				}
				
				if(editInput == 2) { // 2) Copy an existing schedule
					try {
						if(Schedule.scheduleNum >= scheduleList.length) // Schedule list를 초과해서 schedule 생성(복제)하려 할 경우
							throw new ArrayFullException("too many schedule.");
						while(true) {
							printList(scheduleList);					
							System.out.print("Select the schedule to copy: ");
							try {
								int num = keyboard.nextInt();
								if(num > Schedule.scheduleNum || num < 1) // 존재하지 않는 schedule을 고른 경우
									throw new InvalidAccessException();
								System.out.print("Enter a new schedule name: ");
								String temp = keyboard.nextLine();
								String scheduleName = keyboard.nextLine();
							
								scheduleList[Schedule.scheduleNum] = new Schedule(scheduleList[--num]);
								scheduleList[Schedule.scheduleNum++].setName(scheduleName);
								
								break;
							}
							catch(InvalidAccessException e) {
								System.out.println(e.getMessage());
							}
						}
					}
					catch(ArrayFullException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			System.out.print("\n1)Select schedule\n"
							+ "2)Edit schedule\n"
							+ "3)End program\n"
							+ "Select menu: ");
			while(true) {
				try {
					mainInput = keyboard.nextInt();
					if(mainInput > 3 || mainInput < 0) {
						throw new InvalidAccessException();
					}
					break;
				}
				catch(InputMismatchException e) {
					keyboard = new Scanner(System.in);
					System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect menu: ");
				}
				catch(InvalidAccessException e) {
					keyboard = new Scanner(System.in);
					System.out.print(e.getMessage() + "\nSelect menu: ");
				}
			}
		}
		keyboard.close();
	}

}