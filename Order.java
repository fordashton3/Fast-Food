import java.util.ArrayList;

public class Order {
	private final int id;
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Integer> quantities = new ArrayList<Integer>();
	private double totalPrice;

	public Order(int id){
		this.id = id;
	}

	public void addItem(Item item, double price, int quantity){
		items.add(new Item(item.getName(), item.getPrice()));
		quantities.add(quantity);
	}

	public void removeItem(int index) throws IndexOutOfBoundsException {
		items.remove(index);
		quantities.remove(index);
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public ArrayList<Item> getItems() {
		return items;
	}
}
