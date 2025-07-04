import java.util.*;

public class ShippingService {
    private static final double RATE_PER_KG = 30.0; // Shipping cost per 1kg

    public double calculateShipping(List<Shippable> items, Map<Shippable, Integer> quantityMap) {
        double totalWeightGrams = 0.0;
        for (Shippable item : items) {
            int qty = quantityMap.get(item);
            totalWeightGrams += item.getWeight() * qty;
        }
        return (totalWeightGrams / 1000.0) * RATE_PER_KG;
    }

    public void ship(List<Shippable> items, Map<Shippable, Integer> quantityMap) {
        System.out.println("** Shipment notice **");
        double totalGrams = 0;
        for (Shippable item : items) {
            int qty = quantityMap.get(item);
            double weight = item.getWeight() * qty;
            System.out.printf("%dx %s\t\t%.0fg\n", qty, item.getName(), weight);
            totalGrams += weight;
        }
        System.out.printf("Total package weight %.1fkg\n\n", totalGrams / 1000.0);
    }
}
