package src;

public class Item {
	private final String name;
	private final double price;
	private int stat;

	public Item(String name, double price) {
		this.name = name;
		this.price = price;
		stat = 0;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public String toString() {
		return String.format("%s: %.2f", name, price);
	}
}
