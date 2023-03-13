import java.text.DecimalFormat;
import java.util.ArrayList;

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

	public void editItem(Item item, int index){
		items.set(index, item);
	}

	public void editQuantity(int quantity, int index) throws IndexOutOfBoundsException {
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
		return getSubtotal() * taxRate;
	}

	public double getTotal() {
		return getSubtotal() + getTax();
	}

	public void printInvoice() {
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Order ID: " + id);
		System.out.println("------------------------------");
		System.out.println("Food\tQuantity\tUnit Price");
		System.out.println("------------------------------");
		for (int i = 0; i < items.size(); i++) {
			//System.out.println(items.get(i).getName() + "\t" + quantities.get(i) + "\t\t\t$" + items.get(i).getPrice());
			System.out.printf("%2d)\t%13s: %-2d  $%.2f\t%n", i + 1, items.get(i).getName(),quantities.get(i), items.get(i).getPrice());
		}
		System.out.println("------------------------------");
		System.out.printf("%10s $%.2f%n", "Subtotal:", getSubtotal());
		System.out.printf("%10s $%.2f%n", "Tax:", getTax());
		System.out.printf("%10s $%.2f%n", "Total:", getTotal());
		System.out.println("==========================\n");
	}
}
