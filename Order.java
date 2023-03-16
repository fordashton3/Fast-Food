import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@SuppressWarnings("FieldMayBeFinal")
public class Order {
	private final int id;
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Integer> quantities = new ArrayList<>();

	public Order(int id){
		this.id = id;
	}

	public void addItem(Item item, int quantity){
		items.add(item);
		quantities.add(quantity);
	}

	public void removeItem(int index) throws IndexOutOfBoundsException {
		items.remove(index);
		quantities.remove(index);
	}

	public ArrayList<Integer> getQuantities() {
		return quantities;
	}

	public void editQuantity(int index, int quantity) throws IndexOutOfBoundsException {
		quantities.set(index, quantity);
	}

	public int getId() {
		return id;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public double getSubtotal() {
		double total = 0;
		for (int i = 0; i < items.size(); i++){
			total += items.get(i).getPrice() * quantities.get(i);
		}
		return total;
	}

	public double getTax() {
		double taxRate = 0.05;
		return round(getSubtotal() * taxRate, 2) ;
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public double getTotal() {
		return getSubtotal() + getTax();
	}

	public void printInvoice() {
		System.out.println("Order ID: " + id);
		System.out.println("------------------------------");
		System.out.println("Food\tQuantity\tUnit Price");
		System.out.println("------------------------------");
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equalsIgnoreCase("meal")){
				System.out.printf(" \t%13s: %-2d  $%.2f\t%n", items.get(i).getName(),quantities.get(i), items.get(i).getPrice());
			} else {
				System.out.printf("%2d)\t%13s: %-2d  $%.2f\t%n", i + 1, items.get(i).getName(),quantities.get(i), items.get(i).getPrice());
			}
		}
		System.out.printf("%s%n","=".repeat(30));
		System.out.printf("%10s $%.2f%n", "Subtotal:", getSubtotal());
		System.out.printf("%10s $%.2f%n", "Tax:", getTax());
		System.out.printf("%10s $%.2f%n", "Total:", getTotal());
		System.out.printf("%s%n%n","=".repeat(30));
	}

	public void writeInvoice(boolean isFulfilled) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter("Order Invoices.txt", true), true);
		writer.println("Order ID: " + id);
		writer.println("------------------------------");
		writer.println("Food\tQuantity\tUnit Price");
		writer.println("------------------------------");
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equalsIgnoreCase("meal")){
				writer.printf("    \t%13s: %-2d  $%.2f\t%n", items.get(i).getName(),quantities.get(i), items.get(i).getPrice());
			} else {
				writer.printf("%2d)\t%13s: %-2d  $%.2f\t%n", i + 1, items.get(i).getName(),quantities.get(i), items.get(i).getPrice());
			}
		}
		writer.printf("%s%n","=".repeat(30));
		writer.printf("%10s $%.2f%n", "Subtotal:", getSubtotal());
		writer.printf("%10s $%.2f%n", "Tax:", getTax());
		writer.printf("%10s $%.2f%n", "Total:", getTotal());
		writer.printf("Fulfilled: %b%n", isFulfilled);
		writer.printf("%s%n%n","=".repeat(30));
		writer.close();
	}
}
