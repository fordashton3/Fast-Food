package EdwardsOrderClasses;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderTest {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            ArrayList<String> mainDishes = new ArrayList<>();
            mainDishes.add("Chicken");
            mainDishes.add("Beef");
            ArrayList<Double> mainDishPrices = new ArrayList<>();
            mainDishPrices.add(8.00);
            mainDishPrices.add(10.00);

            ArrayList<String> sides = new ArrayList<>();
            sides.add("Fries");
            sides.add("Chips");
            ArrayList<Double> sidePrices = new ArrayList<>();
            sidePrices.add(2.00);
            sidePrices.add(2.50);

            ArrayList<String> drinks = new ArrayList<>();
            drinks.add("Water");
            drinks.add("Soda");
            ArrayList<Double> drinkPrices = new ArrayList<>();
            drinkPrices.add(1.00);
            drinkPrices.add(1.50);

            ArrayList<Order> orders = new ArrayList<>();
            int i = 0;
            while (true) {
                if (i > 0) {
                    System.out.println("Would you like to place another order? (n) to stop  | Enter any other value to continue");
                    String value = input.next();
                    if (value.equalsIgnoreCase("n") || value.equalsIgnoreCase("no")) {
                        input.nextLine();
                        break;
                    }
                }
                System.out.println("Order " + (i + 1));
                System.out.println("------------------------------");

                System.out.println("src.Main Dish Options:");
                for (int j = 0; j < mainDishes.size(); j++) {
                    System.out.println((j + 1) + ". " + mainDishes.get(j) + " ($" + mainDishPrices.get(j) + ")");
                }
                int mainDishChoice = input.nextInt();
                input.nextLine(); // Consume newline character
                String mainDish = mainDishes.get(mainDishChoice - 1);
                double mainDishPrice = mainDishPrices.get(mainDishChoice - 1);
                int quantity1 = getQuantity(input);

                System.out.println("Side Options:");
                for (int j = 0; j < sides.size(); j++) {
                    System.out.println((j + 1) + ". " + sides.get(j) + " ($" + sidePrices.get(j) + ")");
                }
                int sideChoice = input.nextInt();
                input.nextLine(); // Consume newline character
                String side = sides.get(sideChoice - 1);
                double sidePrice = sidePrices.get(sideChoice - 1);
                int quantity2 = getQuantity(input);

                System.out.println("Drink Options:");
                for (int j = 0; j < drinks.size(); j++) {
                    System.out.println((j + 1) + ". " + drinks.get(j) + " ($" + drinkPrices.get(j) + ")");
                }
                int drinkChoice = input.nextInt();
                input.nextLine(); // Consume newline character
                String drink = drinks.get(drinkChoice - 1);
                double drinkPrice = drinkPrices.get(drinkChoice - 1);
                int quantity3 = getQuantity(input);

                ArrayList<String> foods = new ArrayList<>();
                foods.add(mainDish);
                foods.add(side);
                foods.add(drink);

                ArrayList<Integer> quantities = new ArrayList<>();
                quantities.add(quantity1);
                quantities.add(quantity2);
                quantities.add(quantity3);

                ArrayList<Double> unitPrices = new ArrayList<>();
                unitPrices.add(mainDishPrice);
                unitPrices.add(sidePrice);
                unitPrices.add(drinkPrice);

                Order order = new Order(i + 1, foods, quantities, unitPrices);
                orders.add(order);
                i++;
            }
            for (int j = 0; j < orders.size(); j++) {
                orders.get(j).printInvoice();
            }
        } catch (InputMismatchException e) {
            System.out.println("Incorrect Input");
        } catch (IndexOutOfBoundsException e){
            System.out.println("Value out of bounds");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getQuantity(Scanner input){
        System.out.println("Enter quantity:");
        int quantity = input.nextInt();
        return quantity;
    }
}