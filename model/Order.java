package model;

/**
 * Models an order containing a price, an amount, a product and a client.
 */
public class Order {
    private long id;
    private long productId;
    private long clientId;
    private long billId;
    private int amount;

    public Order() {
    }

    public Order(long id, long productId, long clientId, long billId, int amount) {
        this.id = id;
        this.productId = productId;
        this.clientId = clientId;
        this.billId = billId;
        this.amount = amount;
    }

    /**
     * Validates an order.
     *
     * @param order   The order to be validated.
     * @param product The product to be validated.
     * @return true if the order is valid, false otherwise.
     */
    public static boolean validOrder(Order order, Product product) {
        return order.getAmount() > 0 && order.getAmount() <= product.getAmount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
