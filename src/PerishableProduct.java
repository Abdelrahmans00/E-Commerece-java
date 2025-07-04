//Liskov Substitution

import java.time.LocalDate; //real date time for expiry date

//class for product that expires
public class PerishableProduct extends Product {
    private LocalDate expiryDate;

    public PerishableProduct(String name, double price, int quantity, LocalDate expiryDate){
        super(name,price,quantity);
        this.expiryDate = expiryDate;

    }
     //check if the product is expired based on current date
    @Override
    public boolean isExpired(){
        return LocalDate.now().isAfter(expiryDate);
    }
}
