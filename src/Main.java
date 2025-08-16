import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Sample products
        Product cheese = new ShippableProduct("Cheese", 100, 5, 200); // 200g
        Product biscuits = new ShippableProduct("Biscuits", 150, 3, 700); // 700g
        Product scratchCard = new NonPerishableProduct("Scratch Card", 10, 10);
        Product tv = new ShippableProduct("TV", 1000, 3, 5000); // 5kg

        // Customer and cart
        Customer customer = new Customer("Abdelrahman", 1000);
        List<CartItem> cart = new ArrayList<>();

        // Add to cart
        addToCart(cart, cheese, 2);
        addToCart(cart, biscuits, 1);
        addToCart(cart, scratchCard, 1);
        System.out.printl("sba7o")

        // Checkout
        checkout(customer, cart, new ShippingService());
    }

    public static void addToCart(List<CartItem> cart, Product product, int quantity) {
        if (!product.isAvailable(quantity)) {
            System.out.println(product.getName() + " not available in required quantity.");
            return;
        }
        cart.add(new CartItem(product, quantity));
    }

    public static void checkout(Customer customer, List<CartItem> cart, ShippingService shippingService) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        double subtotal = 0;
        Map<String, Double> itemTotals = new LinkedHashMap<>();
        Map<Shippable, Integer> shippingItems = new LinkedHashMap<>();

        for (CartItem item : cart) {
            Product p = item.getProduct();

            if (p.isExpired()) {
                System.out.println(p.getName() + " is expired.");
                return;
            }

            if (!p.isAvailable(item.getQuantity())) {
                System.out.println(p.getName() + " is out of stock.");
                return;
            }

            subtotal += item.getTotalPrice();

            // Group products by name for summary
            itemTotals.merge(item.getProduct().getName(), item.getTotalPrice(), Double::sum);

            // For shippable items
            if (p instanceof Shippable) {
                shippingItems.merge((Shippable) p, item.getQuantity(), Integer::sum);
            }
        }

        double shippingFee = shippingService.calculateShipping(new ArrayList<>(shippingItems.keySet()), shippingItems);
        double total = subtotal + shippingFee;

        if (customer.getBalance() < total) {
            System.out.println("Insufficient balance.");
            return;
        }

        // Reduce product stock
        for (CartItem item : cart) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        // Deduct balance
        customer.deductBalance(total);

        // Shipment notice
        if (!shippingItems.isEmpty()) {
            shippingService.ship(new ArrayList<>(shippingItems.keySet()), shippingItems);
        }

        // Print receipt
        System.out.println("** Checkout receipt **");
        for (Map.Entry<String, Double> entry : itemTotals.entrySet()) {
            System.out.printf("%s\t\t%.0f\n", entry.getKey(), entry.getValue());
        }

        System.out.println("------------------------");
        System.out.printf("Subtotal\t%.0f\n", subtotal);
        System.out.printf("Shipping\t%.0f\n", shippingFee);
        System.out.printf("Amount\t\t%.0f\n", total);
    }
}
