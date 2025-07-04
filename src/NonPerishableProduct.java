//Liskov Substitution

public class NonPerishableProduct extends Product{
    public NonPerishableProduct(String name, double price, int quantity){
        super(name,price,quantity);
    }
    //never expire
    @Override
    public boolean isExpired() {
        return false;
    }
}
