package EdwardsOrderClasses;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Order {
    private int orderId;
    private ArrayList<String> foods;
    private ArrayList<Integer> quantities;
    private ArrayList<Double> unitPrices;

    public Order(int orderId, ArrayList<String> foods, ArrayList<Integer> quantities, ArrayList<Double> unitPrices) {
        this.orderId = orderId;
        this.foods = foods;
        this.quantities = quantities;
        this.unitPrices = unitPrices;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ArrayList<String> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<String> foods) {
        this.foods = foods;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
    }

    public ArrayList<Double> getUnitPrices() {
        return unitPrices;
    }

    public void setUnitPrices(ArrayList<Double> unitPrices) {
        this.unitPrices = unitPrices;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (int i = 0; i < foods.size(); i++) {
            total += quantities.get(i) * unitPrices.get(i);
        }
        return total;
    }

    public double calculateTaxAmount() {
        double taxRate = 0.05;
        return calculateTotalAmount() * taxRate;
    }

    public double calculateFinalBillAmount() {
        return calculateTotalAmount() + calculateTaxAmount();
    }

    public void printInvoice() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("src.Order ID: " + orderId);
        System.out.println("------------------------------");
        System.out.println("Food\tQuantity\tUnit Price");
        System.out.println("------------------------------");
        for (int i = 0; i < foods.size(); i++) {
            System.out.println(foods.get(i) + "\t" + quantities.get(i) + "\t\t\t$" + unitPrices.get(i));
        }
        System.out.println("------------------------------");
        System.out.println("Total Amount: $" + df.format(calculateTotalAmount()));
        System.out.println("Tax Amount: $" + df.format(calculateTaxAmount()));
        System.out.println("Final Bill Amount: $" + df.format(calculateFinalBillAmount()));
        System.out.println("");
    }
}