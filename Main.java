import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int id = 1;
        int user;
        int cashOrders = 0;
        int fulfilledOrders = 0;
        double totalBilled = 0;
        Order order;
        Item[] entrees = initializeEntree();
        Item[] sides = initializeSides();
        Item[] drinks = initializeDrinks();
        System.out.println("Welcome to Nacho Daddy!");

        do {
            order = new Order(id);
            orderLoop(input, entrees, sides, drinks, order);
            user = inputValidation(input, "------- Order Options -------%n\t1) Proceed to Payment%n\t2) Restart Order%n", 1, 2);
            switch (user) {
                case 1 -> {
                    order.printInvoice();
                    fulfilledOrders += processPayment(input, order, entrees, sides, drinks, cashOrders, fulfilledOrders);
                    totalBilled += order.getTotal();
                    id++;
                }
                case 2 -> order = null;
            }
            if (order != null) {
                user = inputValidation(input, "\t1) Place New Order%n\t0) Exit%n", 0, 1);
            }
        } while (user != 0);
        try{
            writeStats(entrees, sides, drinks, id, fulfilledOrders, totalBilled);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Determines the items and prices for the Entrées
     */
    private static Item[] initializeEntree() {
        return new Item[]{
                new Item("Nacho Grande", 4.99),
                new Item("Crunch-wrap", 6.49),
                new Item("Taco Box", 11.99),
        };
    }

    /*
    Determines the items and prices for the Sides
     */
    private static Item[] initializeSides() {
        return new Item[]{
                new Item("Fries", 3.99),
                new Item("Taco", 2.99),
                new Item("Side Nachos", 3.99),
        };
    }

    /*
    Determines the items and prices for the Drinks
     */
    private static Item[] initializeDrinks() {
        return new Item[]{
                new Item("Coffee", 2.99),
                new Item("Water", 1.99),
                new Item("Soda", 1.99),
        };
    }

    /*
    Processes the methods and payment of the customer, cancels orders accordingly
    */
    private static int processPayment(Scanner input, Order order, Item[] mains, Item[] sides, Item[] drinks, int cashOrders, int fulfilledOrders) {
        int paymentMethod;
        double payment = 0;
        double change;
        boolean payed = false;
        do {
            paymentMethod = inputValidation(input, "-------- Payment Method ------- %n1) Cash%n2) Card%n", 1, 2);
            switch (paymentMethod) {
                case 1 -> payment = paymentInputValidation(input, "\tCash Amount: ", 0);
                case 2 -> payment = order.getTotal();
            }
            if (payment >= order.getTotal()) {
                change = payment - order.getTotal();
                System.out.printf("Your change is: $%.2f %nEnjoy your food!%n%n", change);
                calcStats(order, mains);
                calcStats(order, sides);
                calcStats(order, drinks);
                paymentMethod = 0;
                payed = true;
                fulfilledOrders++;
            } else {
                paymentMethod = inputValidation(input, "Insufficient funds: Would you like to cancel your order?%n\t0) Yes%n\t1) No%n", 0, 1);
                if (paymentMethod == 1) {
                    input.nextLine();
                    cashOrders++;
                } else {
                    fulfilledOrders++;
                }
            }
        } while (paymentMethod != 0);
        try {
            order.writeInvoice(payed);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return fulfilledOrders;
    }

    /*
    Calculates the summary statistics
     */
    private static void calcStats(Order order, Item[] items) {
        for (int i = 0; i < order.getItems().size(); i++) {
            for (Item item : items) {
                if (order.getItems().get(i).getName().equalsIgnoreCase(item.getName())) {
                    item.setStat(item.getStat() + order.getQuantities().get(i));
                }
            }
        }
        order.getTotal();
    }

    /*
    Writes the summary statistics to a file
     */
    public static void writeStats(Item[] entrees, Item[] sides, Item[] drinks, int id, int fulfilledOrders, double total) throws IOException {
        File file = new File("Summary Statistics.txt");
        PrintWriter writer = new PrintWriter((new FileWriter(file)), true);
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
        writer.println("-".repeat(30));
        writer.println("\tSummary");
        writer.printf("%21s:  %d", "Total Orders%n ", id);
        writer.printf("%21s:  %d", "Orders Fulfilled%n", fulfilledOrders);
        writer.printf("%21s:  %d", "Orders Cancelled%n", id - fulfilledOrders);
        writer.printf("%21s:  %d", "Orders Fulfilled%n", fulfilledOrders);
        writer.printf("%21s:  %f", "Total Billed%n", total);
        writer.close();

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
        System.out.println("-".repeat(30));
        System.out.println("\tSummary");
        System.out.printf("%21s:  %d", "Total Orders", id);
        System.out.printf("%21s:  %d", "Orders Fulfilled", fulfilledOrders);
        System.out.printf("%21s:  %d", "Orders Cancelled", id - fulfilledOrders);
        System.out.printf("%21s:  %d", "Orders Fulfilled", fulfilledOrders);
        System.out.printf("%21s:  %f", "Total Billed", total);
    }

    /*
    Constructs and edits the order to the user's choice until they go to payment
     */
    public static void orderLoop(Scanner input, Item[] entrees, Item[] sides, Item[] drinks, Order order) throws InputMismatchException {
        int user;
        do {
            System.out.printf("---------- Menu ----------%n\t1) Entrees%n\t2) Sides%n\t3) Drinks%n");
            // Allows the user to choose category of food
            user = inputValidation(input, "Select category of choice: ", 1, 3);
            switch (user) {
                case 1 -> {
                    printCategory(entrees, "Entrees");
                    selectItem(order, entrees, input);
                    user = inputValidation(input, "Would you like to make that a meal?%n\t1) Yes%n\t2) No%n", 1, 2);
                    if (user == 1) {
                        printCategory(sides, "Sides");
                        selectItem(order, sides, input);
                        printCategory(drinks, "Drinks");
                        selectItem(order, drinks, input);
                        Item mealDiscount = new Item("Meal", -2);
                        order.addItem(mealDiscount, 1);
                    }
                }
                case 2 -> {
                    printCategory(sides, "Sides");
                    selectItem(order, sides, input);
                }
                case 3 -> {
                    printCategory(drinks, "Drinks");
                    selectItem(order, drinks, input);
                }
            }
            System.out.println("Item added to order.");
                user = inputValidation(input, "\t1) Continue%n\t2) Edit Order%n\t0) Finish Order%n", 0, 2);
                if (user == 2) {
                    editOrder(input, order);
                    user = inputValidation(input, "\t1) Continue%n\t0) Finish Order%n", 0, 1);
                }
        } while (user != 0);
    }

    /*
    Allows the user to edit the item quantities and delete items
     */
    private static void editOrder(Scanner input, Order order) throws InputMismatchException {
        int index;
        int userInput;
        int orderSize = order.getItems().size() - 1;
        do {
            System.out.println(orderSize);
            System.out.println("Current Order:");
            System.out.println();
            order.printInvoice();
            index = inputValidation(input, "Choose item to edit: ", 1, orderSize);
            if (orderSize > 1) {
                userInput = inputValidation(input, "\t1) Quantity%n\t2) Delete Item%n", 1, 2);
                    switch (userInput) {
                        case 1 -> order.editQuantity(index, quantityInputValidation(input, "Quantity: ", 1));
                        case 2 -> {
                            userInput = inputValidation(input, "\t1) Confirm Item Deletion%n\t2) Cancel Deletion%n", 1, 2);
                            switch (userInput) {
                                case 1 -> {
                                    order.removeItem(index);
                                    System.out.println("Item Deleted");
                                }
                                case 2 -> System.out.println("Cancelled");
                            }
                        }
                    }
            } else {
                userInput = inputValidation(input, "\t1) Quantity%n\t2) Stop Editing Item%n", 1, 2);
                if (userInput == 1) {
                    order.editQuantity(index, quantityInputValidation(input, "Quantity: ", 1));
                }
            }
            userInput = inputValidation(input, "Edit another item: %n\t1) Yes%n\t0) No%n", 0, 1);
            orderSize = order.getItems().size() - 1;
        } while (userInput != 0);
    }

	public static void printCategory(Item[] items, String category) {
		System.out.printf("%5s%7s%-5s%n", "-".repeat(8), category, "-".repeat(8));
		for (int i = 0; i < items.length; i++) {
			System.out.printf("%d)\t%s%n", i + 1, items[i].getName());
		}
	}

    /*
    Allows the user to select item they would like to add to their order
     */
    public static void selectItem(Order order, Item[] items, Scanner input) throws InputMismatchException {
        int item = inputValidation(input, "Input number for given option: ", 1, items.length) - 1;
        int quantity = quantityInputValidation(input, "How many do you want?%n", 1);
        order.addItem(items[item], quantity);
    }

	public static int inputValidation(Scanner input, String question, int value1, int value2) {
		int userInput = 0;
		while (true) {
			System.out.printf(question);
			try {
				userInput = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println(" Error: Enter a positive integer");
			}
			if (userInput >= value1 && userInput <= value2) {
				return userInput;
			}
		}
	}

	public static int quantityInputValidation(Scanner input, String question, int value1) {
		int userInput = 0;
		while (true) {
			System.out.printf(question);
			try {
				userInput = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println(" Error: Enter a positive integer");
			}
			if (userInput >= value1) {
				return userInput;
			}
		}
	}

    /*

     */
    public static double paymentInputValidation(Scanner input, String question, int value1) {
        double userInput = -1;
        while (true) {
            System.out.printf(question);
            try {
                userInput = input.nextDouble();
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("!!  Error: Enter a positive Integer  !!");
            }
            if (userInput >= value1) {
                return userInput;
            }
        }
    }
}
