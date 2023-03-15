package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderTest {
	private static final double TAX_RATE = 0.1; // 10% tax rate

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int id = 1;
		ArrayList<Order> orders = new ArrayList<>();
		Item[] mainDishes = new Item[]{
				new Item("Chicken", 4.99),
				new Item("Beef", 5.99),
				new Item("Plant Meat", 5.99),
		};
		Item[] sideDishes = new Item[]{
				new Item("Fries", 3.99),
				new Item("Chips", 2.99),
				new Item("Salad", 3.99),
		};
		Item[] drinks = new Item[]{
				new Item("Coffee", 2.99),
				new Item("Water", 1.99),
				new Item("Soda", 1.99),
		};

		System.out.println("Welcome to Nacho Daddy!");
		while (true) {
			Order order = new Order(id);
			try {
				System.out.println("What would you like for a main dish? (0 to exit)");
				order = orderLoop(input, mainDishes, order);
				System.out.println("What would you like for a side dish? (0 to exit)");
				order = orderLoop(input, sideDishes, order);
				System.out.println("What would you like for a drink? (0 to exit)");
				order = orderLoop(input, drinks, order);
			} catch (InputMismatchException e) {
				System.out.println("Input Mismatch Exception: Must be an Integer");
			}
			orders.add(order);
			System.out.println("Enter any key to place another order (q to quit)");
			String user = input.nextLine();
			id++;
			if (user.equalsIgnoreCase("q")){
				break;
			}
		}

		// Print out invoice/receipt for each order
		for (Order order : orders) {
			double subtotal = 0;
			System.out.println("src.Order ID: " + order.getId());
			System.out.println("Items:");
			for (int i = 0; i < order.getItems().size(); i++) {
				Item item = order.getItems().get(i);
				int quantity = order.getQuantities().get(i);
				double itemTotal = item.getPrice() * quantity;
				System.out.println(quantity + " " + item.getName() + " @ " + item.getPrice() + " = " + itemTotal);
				subtotal += itemTotal;
			}
			double tax = subtotal * TAX_RATE;
			double total = subtotal + tax;
			System.out.println("Subtotal: $" + subtotal);
			System.out.println("Tax (" + (TAX_RATE * 100) + "%): $" + tax);
			System.out.println("Total: $" + total);
			System.out.println();
		}
	}

	public static Order orderLoop(Scanner input, Item[] items, Order order) throws InputMismatchException {
		int user;
		int quantity = 1;
		while (true) {
			for (int i = 0; i < items.length; i++) {
				System.out.println(i + 1 + ") " + items[i]);
			}
			user = input.nextInt();
			if (user == 0) {
				break;
			}
			while (user > items.length || user < 0) {
				System.out.println("Enter a number corresponding with the food item.");
				user = input.nextInt();
			}
			System.out.println("How many would you like?");
			quantity = input.nextInt();
			while (quantity <= 0) {
				System.out.println("Enter a positive quantity.");
				quantity = input.nextInt();
			}
			order.addItem(items[user - 1], quantity);
			System.out.println("src.Item added to order.");
		}
		return order;
	}

	// TODO - add tax calculations and printout

	public static void printLoop(Order order, Item[] mainDishes) {
		for (int i = 0; i < order.getItems().size(); i++) {
			Item item = order.getItems().get(i);
			if (Arrays.asList(mainDishes).contains(item)) {
				System.out.println("\t- " + item.getName() + " (x" + order.getQuantities().get(i) + "): $" + item.getPrice());
			}
		}
	}
}
