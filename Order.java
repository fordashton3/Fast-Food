import java.util.ArrayList;

public class Order {
	private final int id;
	private double totalPrice;
	private ArrayList<Item> items = new ArrayList<Item>();

	public Order(int id){
		this.id = id;
	}
	public void addItem(Item item, double price, int quantity){
		items.add(new Item(item.getName(), price, quantity));
	}
	public void removeItem(int index, int quantity){
		items.remove(index);
	}
	public void editQuantity(Item item, int index, int quantity){
		Item.setQuantity(item, quantity);
	}
// TODO - Make sure editQuantity takes in an item from ArrayList as an argument
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
