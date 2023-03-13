import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	private static final double TAX_RATE = 0.05; // 5% tax rate

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int id = 1;
		int exit = 0;
		Item[] entrees = new Item[]{
				new Item("Nacho Grande", 4.99),
				new Item("Crunchwrap", 6.49),
				new Item("Taco Box", 11.99),
		};
		Item[] sides = new Item[]{
				new Item("Fries", 3.99),
				new Item("Taco", 2.99),
				new Item("Side Nachos", 3.99),
		};
		Item[] drinks = new Item[]{
				new Item("Coffee", 2.99),
				new Item("Water", 1.99),
				new Item("Soda", 1.99),
		};
		while (true) {
			System.out.println("Welcome to Nacho Daddy!");
			Order order;
			while (true) {
				String user = "";
				order = new Order(id);
				try {
					System.out.println("Select category of choice:");
					order = orderLoop(input, entrees, sides, drinks, order);
				} catch (InputMismatchException e) {
					System.out.println("Input Mismatch Exception: Must be an Integer");
				}
				while (true) {
					System.out.printf("1) Confirm Finish Order%n2) Edit Order%n3) Restart Order%n");
					user = input.nextLine();
					try {
						if (Integer.parseInt(user) == 1) {
							break;
						} else if (Integer.parseInt(user) == 2) {
							editOrder(input, order);
						} else if (Integer.parseInt(user) == 3) {
							order = null;
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
				}
				try {
					if (Integer.parseInt(user) != 3) {
						// Print out invoice
						id++;
						order.printInvoice();
						System.out.println("Submit your payment amount:");
						double payment = 0;
						do {
							try {
								System.out.println("Enter a decimal number");
								payment = input.nextDouble();
							} catch (InputMismatchException e) {
								System.out.println("Enter a Decimal Value for Payment");
								input.nextLine();
							}
						} while (!(payment > 0));
						proccessPayment(payment, order);
					}
				} catch (InputMismatchException | NumberFormatException e) {
					System.out.println(e.getMessage());
				}
				// TODO - add a statement to break into next loop
				do {
					System.out.printf("1) Place Another Order%n2) Exit%n");
					try {
						exit = input.nextInt();
					} catch (InputMismatchException e) {
						System.out.println(e.getMessage());
						input.nextLine();
					}
				} while (exit != 1 && exit != 2);
				if (exit == 2){
					break;
				}
			}
			do {
				System.out.printf("1) Place Order%n2) Exit Program%n");
				try {
					exit = input.nextInt();
				} catch (InputMismatchException e) {
					System.out.println(e.getMessage());
					input.nextLine();
				}
			} while (exit != 1 && exit != 2);
			if (exit == 2){
				break;
			}
		}
	}

	private static void proccessPayment(double payment, Order order) {
		double change = 0.0;
		if (payment >= order.getTotal()) {
			change = payment - order.getTotal();
			System.out.printf("Your change is: $%.2f%nEnjoy your food!%n%n", change);
		} else {
			System.out.println("We are Nacho daddy! Get outta here!");
		}
	}

	public static Order orderLoop(Scanner input, Item[] entrees, Item[] sides, Item[] drinks, Order order) throws InputMismatchException {
		String user = "";
		int quantity = 1;
		while (true) {
			System.out.println("\tEntrees");
			System.out.println("\tSides");
			System.out.println("\tDrinks");
			/*do {
				System.out.println("Enter the name of a category");
				user = input.next();
				input.nextLine();
			} while (user.equals("") /*|| !inputValidation(user, "e", "s", "d"));*/

			while (true) {
				System.out.println("Enter the name of a category");
				try {
					user = Character.toString(input.nextLine().charAt(0));
				} catch (InputMismatchException e) {
					System.out.println("Invalid Character");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				if (user.equalsIgnoreCase("e") || user.equalsIgnoreCase("s") || user.equalsIgnoreCase("d")) {
					break;
				}
			}

			user = Character.toString(user.toLowerCase().charAt(0));
			switch (user) {
				case "e" -> {
					printCatagory(entrees);
					order = selectItem(order, entrees, input);
					System.out.print("Would you like to make that a meal? ");
					do {
						System.out.println("Enter y or n");
						user = input.nextLine();
					} while (!user.equalsIgnoreCase("y") && !user.equalsIgnoreCase("n"));
					if (user.equalsIgnoreCase("y")) {
						printCatagory(sides);
						order = selectItem(order, sides, input);
						printCatagory(drinks);
						order = selectItem(order, drinks, input);
						Item mealDiscount = new Item("Meal", -2);
						order.addItem(mealDiscount, 1);
					}
				}
				case "s" -> {
					printCatagory(sides);
					order = selectItem(order, sides, input);
				}
				case "d" -> {
					printCatagory(drinks);
					order = selectItem(order, drinks, input);
				}
			}
			System.out.println("Item added to order.");
			System.out.printf("1) Continue%n2) Finish Order%n");
			int temp = 0;
			while (true) {
				try {
					temp = 0;
					temp = input.nextInt();
					if (temp == 1 || temp == 2) {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("Enter an Integer");
				} finally {
					input.nextLine();
				}
			}
			if (temp == 2) {
				break;
			}
		}

		return order;
	}

	private static void editOrder(Scanner input, Order order) throws InputMismatchException {
		int index = 0;
		System.out.println("Current Order:");
		System.out.println();
		order.printInvoice();
		int userInput = 0;
		System.out.println("Choose item to edit: ");
		index = input.nextInt() - 1;
		while (true) {
			System.out.printf("1. Quantity%n2. Delete Item%n");
			input.nextLine();
			userInput = input.nextInt();
			if (userInput == 1) {
				System.out.println("Quantity: ");
				order.editQuantity(index, input.nextInt());
				break;
			} else if (userInput == 2) {
				System.out.println("1. Confirm Item Deletion%n2. Cancel Deletion");
				userInput = input.nextInt();
				if (userInput == 1) {
					order.removeItem(index);
					System.out.println("Item Deleted");
					break;
				} else if (userInput == 2) {
					System.out.println("Cancelled");
					break;
				}
			} else {
				System.out.println("Please enter a number.");
			}
		}
	}

	public static void printOrder(Order order, Item[] entrees, Item[] sideDishes, Item[] drinks) {
		System.out.println("Order #" + order.getId());
		System.out.println("==========================");

		System.out.println("Entrees:");
		printLoop(order, entrees);

		System.out.println("Sides:");
		printLoop(order, sideDishes);

		System.out.println("Drinks:");
		printLoop(order, drinks);

		System.out.printf("%10s $%.2f%n", "Subtotal:", order.getSubtotal());
		System.out.printf("%10s $%.2f%n", "Tax:", order.getTax());
		System.out.printf("%10s $%.2f%n", "Total:", order.getTotal());
		System.out.println("==========================\n");
	}

	// TODO - add tax calculations and printout
	public static void printCatagory(Item[] items) {
		for (int i = 0; i < items.length; i++) {
			System.out.printf("%d)\t%s%n", i + 1, items[i].getName());
		}
	}

	public static void printLoop(Order order, Item[] items) {
		for (int i = 0; i < order.getItems().size(); i++) {
			Item item = order.getItems().get(i);
			if (Arrays.asList(items).contains(item)) {
				System.out.println("\t- " + item.getName() + " (x" + order.getQuantities().get(i) + "): $" + item.getPrice());
			}
		}
	}

	public static Order selectItem(Order order, Item[] items, Scanner input) throws InputMismatchException {
		int user;
		do {
			System.out.print("Input a number corresponding with your chosen item: ");
			user = input.nextInt();
		} while (user <= 0 || user > items.length);
		int item = user;
		System.out.println("How many do you want?");
		user = input.nextInt();
		while (user <= 0) {
			System.out.print("Input a whole number");
			user = input.nextInt();
		}
		order.addItem(items[item - 1], user);
		return order;
	}

	/*public static boolean inputValidation(String input, String lett1, String lett2, String lett3) {
		input = Character.toString(input.charAt(0));
		return input.equalsIgnoreCase(lett1) || input.equalsIgnoreCase(lett2) || input.equalsIgnoreCase(lett3);
	}*/
}
