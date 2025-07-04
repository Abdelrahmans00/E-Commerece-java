//open-close principle

public abstract class Product {
    //Define product
    protected String name ;
    protected double price;
    protected int quantity;

    //constructor
    public Product(String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //getters
    public String getName(){return name;}
    public double getPrice(){return price;}
    public int getQuality(){return quantity;}

    public void reduceQuantity(int q){this.quantity -= q;}
    public boolean isAvailable(int q){return quantity>= q;}

    public abstract boolean isExpired();
}
