import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		int id = 1;
		int user = 0;
		Order order;
		Item[] entrees = initializeEntree();
		Item[] sides = initializeSides();
		Item[] drinks = initializeDrinks();
		System.out.println("Welcome to Nacho Daddy!");

		do {
			order = new Order(id);
			order = orderLoop(input, entrees, sides, drinks, order);
			user = inputValidation(input, user, "1) Proceed to Payment%n2) Edit Order%n3) Restart Order%n", 1, 3);
			switch (user) {
				case 1 -> {
					processPayment(input, order, entrees, sides, drinks);
					id++;
					order.printInvoice();
				}
				case 2 -> editOrder(input, order);
				case 3 -> order = null;
			}
			if (order != null) {
				user = inputValidation(input, user, "1) Place New Order%n0) Exit%n", 0, 1);
			}
		} while (user != 0);
		writeStats(entrees, sides, drinks);
	}

	private static Item[] initializeEntree() {
		return new Item[]{
				new Item("Nacho Grande", 4.99),
				new Item("Crunch-wrap", 6.49),
				new Item("Taco Box", 11.99),
		};
	}

	private static Item[] initializeSides() {
		return new Item[]{
				new Item("Fries", 3.99),
				new Item("Taco", 2.99),
				new Item("Side Nachos", 3.99),
		};
	}

	private static Item[] initializeDrinks() {
		return new Item[]{
				new Item("Coffee", 2.99),
				new Item("Water", 1.99),
				new Item("Soda", 1.99),
		};
	}

	private static void processPayment(Scanner input, Order order, Item[] mains, Item[] sides, Item[] drinks) {
		System.out.printf("Payment Method: %n1) Cash%n2) Card%n");
		int paymentMethod = 0;
		double payment = 0;
		double change = 0;
		paymentMethod = inputValidation(input, paymentMethod, "Payment Method: %n1) Cash%n2) Card%n", 1, 2);
		switch (paymentMethod) {
			case 1 -> payment = paymentInputValidation(input, payment, "Enter a decimal number", 0);
			case 2 -> payment = order.getTotal();
		}
		if (payment >= order.getTotal()) {
			change = payment - order.getTotal();
			System.out.printf("Your change is: $%.2f%nEnjoy your food!%n%n", change);
			calcStats(order, mains);
			calcStats(order, sides);
			calcStats(order, drinks);
		} else {
			System.out.printf("Insufficient funds: Order Cancelled%n");
		}
	}

	private static void calcStats(Order order, Item[] items) {
		for (int i = 0; i < order.getItems().size(); i++) {
			for (Item item : items) {
				if (order.getItems().get(i).getName().equalsIgnoreCase(item.getName())) {
					item.setStat(item.getStat() + order.getQuantities().get(i));
				}
			}
		}
	}

	public static void writeStats(Item[] entrees, Item[] sides, Item[] drinks) throws IOException {
		File file = new File("Summary Statistics.txt");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		writer.println("Nacho Daddy: Order Summary and Statistics");
		writer.println("\t\tEntrees");
		for (Item entree : entrees) {
			writer.printf("%-17s: %3d%n", entree.getName(), entree.getStat());
		}
		writer.println("-".repeat(25));
		writer.println("\t\tSides");
		for (Item side : sides) {
			writer.printf("%-17s: %3d%n", side.getName(), side.getStat());
		}
		writer.println("-".repeat(25));
		writer.println("\t\tDrinks");
		for (Item drink : drinks) {
			writer.printf("%-17s: %3d%n", drink.getName(), drink.getStat());
		}
		// =========================
		System.out.println("Nacho Daddy: Order Summary and Statistics");
		System.out.println("\t\tEntrees");
		for (Item entree : entrees) {
			System.out.printf("%-17s: %3d%n", entree.getName(), entree.getStat());
		}
		System.out.println("-".repeat(25));
		System.out.println("\t\tSides");
		for (Item side : sides) {
			System.out.printf("%-17s: %3d%n", side.getName(), side.getStat());
		}
		System.out.println("-".repeat(25));
		System.out.println("\t\tDrinks");
		for (Item drink : drinks) {
			System.out.printf("%-17s: %3d%n", drink.getName(), drink.getStat());
		}
		writer.close();
	}

	public static Order orderLoop(Scanner input, Item[] entrees, Item[] sides, Item[] drinks, Order order) throws InputMismatchException {
		int user = 0;
		while (true) {
			System.out.println("\tEntrees");
			System.out.println("\tSides");
			System.out.println("\tDrinks");

			do {
				try {
					System.out.println("Select category of choice:");
					user = input.nextLine().charAt(0);
				} catch (InputMismatchException e) {
					System.out.println("Invalid Character");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Index is out of bounds for category name");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			} while (user < 1 || user > 3);
			// Allows the user to choose category of food

			switch (user) {
				case 1 -> {
					printCategory(entrees);
					order = selectItem(order, entrees, input);
					System.out.print("Would you like to make that a meal? ");
					do {
						System.out.printf("  1)  Yes%n  2)  No%n");
						user = inputValidation(input, user, "  1)  Yes%n  2)  No%n", 1, 2	);
					} while (user != 1 && user !=2);
					if (user == 1) {
						printCategory(sides);
						order = selectItem(order, sides, input);
						printCategory(drinks);
						order = selectItem(order, drinks, input);
						Item mealDiscount = new Item("Meal", -2);
						order.addItem(mealDiscount, 1);
					}
				}
				case 2 -> {
					printCategory(sides);
					order = selectItem(order, sides, input);
				}
				case 3 -> {
					printCategory(drinks);
					order = selectItem(order, drinks, input);
				}
			}
			System.out.println("Item added to order.");
			System.out.printf("1) Continue%n2) Finish Order%n");
			int temp;
			while (true) {
				try {
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
		int index;
		System.out.println("Current Order:");
		System.out.println();
		order.printInvoice();
		int userInput;
		System.out.println("Choose item to edit: ");
		index = input.nextInt() - 1;
		try {
			while (order.getItems().get(index).getName().equalsIgnoreCase("meal")) {
				System.out.println("Quantity Meal cannot be changed. Select a different item.");
				index = input.nextInt()-1;
			}
		} catch (InputMismatchException e) {
			input.nextLine();
			System.out.println("Enter an Integer");
		}
		while (true) {
			input.nextLine();
			System.out.printf("1. Quantity%n2. Delete Item%n");
			userInput = input.nextInt();
			if (userInput == 1) {
				try {
					System.out.println("Quantity: ");
					order.editQuantity(index, input.nextInt());
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e.getMessage());
				}
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

	public static void printCategory(Item[] items) {
		for (int i = 0; i < items.length; i++) {
			System.out.printf("%d)\t%s%n", i + 1, items[i].getName());
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
		//items[item - 1].setStat(items[item - 1].getStat() + 1);
		return order;
	}

	public static int inputValidation(Scanner input, int userInput, String question, int value1, int value2) {
		while (true) {
			System.out.printf(question);
			try {
				userInput = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("Enter an positive Integer");
			}
			if (!(userInput >= value1 && userInput <= value2)) {
				return userInput;
			}
		}
	}
	public static double paymentInputValidation(Scanner input, double userInput, String question, int value1) {
		while (true) {
			System.out.printf(question);
			try {
				userInput = input.nextDouble();
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("Enter an positive Integer");
			}
			if (!(userInput >= value1)) {
				return userInput;
			}
		}
	}
}
