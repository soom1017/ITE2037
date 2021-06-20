package assignment3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.InputMismatchException;
import assignment3.exception.*;

public class Shopping {
	public static <T> void printList(ArrayList<T> list) {
		int count = 0;
		System.out.println();
		for(T t: list) {
			System.out.println((count + 1) + ". " + t.toString());
			count++;
		}
	}
	
	public static void main(String[] args) {
		LocalDateTime present = LocalDateTime.of(2021, 5, 27, 15, 0);
		ArrayList<Customer> customerList = new ArrayList<Customer>();

		Mart.getIstance();
		
		Scanner inputStream1 = null;
		Scanner inputStream2 = null;
		try {
			inputStream1 = new Scanner(new FileInputStream("CustomerWallets.txt"));
			inputStream2 = new Scanner(new FileInputStream("Mart.txt"));
		}
		catch(FileNotFoundException e) {
			System.exit(0);
		}
		int customerNum = inputStream1.nextInt();
		int productNum = inputStream2.nextInt();
		
		String junk1 = inputStream1.nextLine();
		String junk2 = inputStream2.nextLine();
		
		String line = null;
		
		//customerList 초기화
		for(int i=0;i<customerNum;i++) {
			line = inputStream1.nextLine();
			String[] customerInfo = line.split(", ");
			
			String customerName = customerInfo[0];
			int payableNum = Integer.parseInt(customerInfo[1]);
			
			Customer customer = new Customer(customerName);
			customerList.add(customer);
			
			for(int j=0;j<payableNum;j++) {
				line = inputStream1.nextLine();
				String[] payableInfo = line.split(",");
				for(int k=0;k<payableInfo.length;k++)
					payableInfo[k] = payableInfo[k].trim();
				
				if(payableInfo[0].equals("Credit")) {
					String bank = payableInfo[1];
					int limit = Integer.parseInt(payableInfo[2]);
					int amountUsed = Integer.parseInt(payableInfo[3]);
					
					customer.addPayable(new Credit(bank, limit, amountUsed));
				}
				if(payableInfo[0].equals("Cash")) {
					String currency = payableInfo[1];
					int amount = Integer.parseInt(payableInfo[2]);
					
					customer.addPayable(new Cash(currency, amount));
				}
			}
		}
		inputStream1.close();
		
		//mart 정보 초기화
		for(int i=0;i<productNum;i++) {
			line = inputStream2.nextLine();
			String[] productInfo = line.split(",");
			for(int j=0;j<productInfo.length;j++)
				productInfo[j] = productInfo[j].trim();
			
			String name = productInfo[1];
			int price = Integer.parseInt(productInfo[2]);
			
			if(productInfo[0].equals("Food")) {
				LocalDateTime expiration = LocalDateTime.of(Integer.parseInt(productInfo[3]),
															Integer.parseInt(productInfo[4]),
															Integer.parseInt(productInfo[5]),
															Integer.parseInt(productInfo[6]),
															Integer.parseInt(productInfo[7]));
				int quantity = Integer.parseInt(productInfo[8]);
				Mart.getIstance().addProduct(new Food(name, price, quantity, expiration));
			}
			if(productInfo[0].equals("Manufactured")) {
				String brand = productInfo[3];
				int quantity = Integer.parseInt(productInfo[4]);
				Mart.getIstance().addProduct(new Manufactured(name, price, quantity, brand));
			}
		}
		inputStream2.close();
		
		//main menu
		System.out.print("1) Manager Mode\n"
							+ "2) Customer Mode\n"
							+ "3) End Program\n"
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
			if(mainInput == 1) { //1) Manger Mode
				System.out.print("\n1) Add Inventory\n"
						+ "2) Replace expired\n"
						+ "Select menu: ");
				int managerModeInput;
				while(true) {
					try {
						managerModeInput = keyboard.nextInt();
						if(managerModeInput > 2 || managerModeInput < 0) {
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
				while(managerModeInput != 0) {
					if(managerModeInput == 1) { // 1) Add Inventory
						printList(Mart.getIstance().getSalesList());
						System.out.print("Select Product: ");
						int num;
						while(true) {
							try {
								num = keyboard.nextInt();
								if(num > productNum)
									throw new InvalidAccessException();
								break;
							}
							catch(InputMismatchException e) {
								keyboard = new Scanner(System.in);
								System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect Product: ");
							}
							catch(InvalidAccessException e) {
								keyboard = new Scanner(System.in);
								System.out.print(e.getMessage() + "\nSelect Product: ");
							}
						}
						System.out.print("Enter quantity: ");
						int addedQuantity = keyboard.nextInt();
						
						Mart.getIstance().addInventory(num - 1, addedQuantity);
					}
					if(managerModeInput == 2) { // 2) Replace expired
						printList(Mart.getIstance().getExpiredFoodList(present));
						System.out.print("Enter increasing date: ");
						int addedExpirationDate = keyboard.nextInt();
						Mart.getIstance().addExpirationDate(present, present.plusDays(addedExpirationDate));
					}
					
					//다시 입력 받기
					System.out.print("\n1) Add Inventory\n"
							+ "2) Replace expired\n"
							+ "Select menu: ");
					while(true) {
						try {
							managerModeInput = keyboard.nextInt();
							if(managerModeInput > 2 || managerModeInput < 0) {
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
			
			if(mainInput == 2) { //2) Customer Mode
				printList(customerList);
				System.out.print("Select Customer: ");
				int customerIndex;
				while(true) {
					try {
						customerIndex = keyboard.nextInt();
						if(customerIndex > customerNum || customerIndex < 1)
							throw new InvalidAccessException();
						break;
					}
					catch(InputMismatchException e) {
						keyboard = new Scanner(System.in);
						System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect Customer: ");
					}
					catch(InvalidAccessException e) {
						keyboard = new Scanner(System.in);
						System.out.print(e.getMessage() + "\nSelect Customer: ");
					}
				}
				
				System.out.print("\n1) Shopping\n"
						+ "2) Print Shopping Cart\n"
						+ "3) Paying\n"
						+ "4) Print Wallet\n"
						+ "Select menu: ");
				int customerModeInput;
				while(true) {
					try {
						customerModeInput = keyboard.nextInt();
						if(customerModeInput > 4 || customerModeInput < 0) {
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
				
				while(customerModeInput != 0) {
					if(customerModeInput == 1) { // 1) Shopping
						printList(Mart.getIstance().getSalesList());
						System.out.print("Select Product: ");
						int num;
						while(true) {
							try {
								num = keyboard.nextInt();
								if(num > productNum)
									throw new InvalidAccessException();
								break;
							}
							catch(InputMismatchException e) {
								keyboard = new Scanner(System.in);
								System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect Product: ");
							}
							catch(InvalidAccessException e) {
								keyboard = new Scanner(System.in);
								System.out.print(e.getMessage() + "\nSelect Product: ");
							}
						}
						System.out.print("Enter quantity: ");
						int wantedQuantity = keyboard.nextInt();
						
						Product p = Mart.getIstance().getSalesList().get(num - 1);
						try {
							customerList.get(customerIndex - 1).addProductToCart(p, wantedQuantity, present);
						}
						catch(ExpiredException e) {
							//to-do
						}
						catch(Exception e) {
							//to-do
						}
					}
					if(customerModeInput == 2) { // 2) Print Shopping Cart
						printList(customerList.get(customerIndex - 1).getShoppingCart());
					}
					if(customerModeInput == 3) { // 3) Paying
						//shopping cart 출력
						System.out.println(present.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
						customerList.get(customerIndex - 1).printShoppingCart();
						printList(customerList.get(customerIndex - 1).getWallet());
						//결제 방식 입력
						System.out.print("Select payment method: ");
						int paymentNum;
						while(true) {
							try {
								paymentNum = keyboard.nextInt();
								if(paymentNum > customerList.get(customerIndex - 1).getWallet().size() || paymentNum < 1) {
									throw new InvalidAccessException();
								}
								break;
							}
							catch(InputMismatchException e) {
								keyboard = new Scanner(System.in);
								System.out.print("**INPUT MISMATCH: please select again.**" + "\nSelect payment method: ");
							}
							catch(InvalidAccessException e) {
								keyboard = new Scanner(System.in);
								System.out.print(e.getMessage() + "\nSelect payment method: ");
							}
						}
						//결제
						customerList.get(customerIndex - 1).payProductsInCart(paymentNum - 1);
						
						//영수증 만들기
						String fileName = "Receipt" + Mart.transactionNum + ".txt";
						File receiptFile = new File(fileName);
						PrintWriter outputStream = null;
						try {
							outputStream = new PrintWriter(new FileOutputStream(fileName));
						}
						catch(FileNotFoundException e) {
							System.out.println("Error opening the file " + fileName);
							System.exit(0);
						}
						customerList.get(customerIndex - 1).printReceipt(outputStream, paymentNum - 1);
						outputStream.close();
						
					}
					if(customerModeInput == 4) { // 4) Print Wallet
						printList(customerList.get(customerIndex - 1).getWallet());
					}
					
					//다시 입력 받기
					System.out.print("\n1) Shopping\n"
							+ "2) Print Shopping Cart\n"
							+ "3) Paying\n"
							+ "4) Print Wallet\n"
							+ "Select menu: ");
					while(true) {
						try {
							customerModeInput = keyboard.nextInt();
							if(customerModeInput > 4 || customerModeInput < 0) {
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
			
			//다시 입력 받기
			System.out.print("\n1) Manager Mode\n"
					+ "2) Customer Mode\n"
					+ "3) End Program\n"
					+ "Select menu: ");
			keyboard = new Scanner(System.in);
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
	}
}
