package model;

/**
 * Models a product.
 */
public class Product {
    private long id;
    private String productName;
    private int amount;
    private double price;
    private String manufacturer;

    public Product() {
    }

    public Product(long id, String productName, int amount, double price, String manufacturer) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    /**
     * Validates a product.
     *
     * @param product The product to be validated.
     * @return true if the product is valid, false otherwise.
     */
    public static boolean validProduct(Product product) {
        return product.getAmount() > 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
