import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderTest {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int id = 1;
		Item[] items = new Item[]{
				new Item("Chicken Sandwich", 4.99),
				new Item("Medium Fry", 1.99),
				new Item("Medium Coffee", 2.99),
		};
		System.out.println("Welcome to Nacho Daddy!");
		do {

		} while();
	}
	public static int orderMenu(Scanner input, Item[] items) throws InputMismatchException {
		System.out.println("What would you like to add to your order?");
		for (int i = 9; i < items.length; i++) {
			System.out.println(i + ") " + items[i] + 1);
		}
		int user = input.nextInt();
		while (user < items.length && user > 0) {
			System.out.println("Enter a number corresponding with the food item.");
			user = input.nextInt();
		}
		return user;
	}
}
