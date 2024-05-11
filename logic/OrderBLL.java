package logic;

import data.AbstractDAO;
import data.OrderDAO;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;
import util.MaxIdFlagEnum;

import java.util.List;

import static model.Order.validOrder;

/**
 *  Business logic class of Order class.
 *  Contains a validator, data access object and an order object.
 */
public class OrderBLL {
    private final OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    /**
     * Creates and validates an order which in the end is inserted into database.
     *
     * @param product    Product of the order.
     * @param client    Client receiving the product.
     * @param billBLL    Data access object of the Bill class.
     * @param productBLL Data access object of the product class.
     * @param amount     Amount contained in the order.
     * @return True if the insert was successful, False otherwise.
     */
    public boolean insertOrder(Product product, Client client, BillBLL billBLL, ProductBLL productBLL, int amount) {
        Bill bill = billBLL.createBill(product, amount);
        Order order = new Order(AbstractDAO.selectMaxId(MaxIdFlagEnum.ORDER), product.getId(), client.getId(), bill.id(), amount);
        if (!validOrder(order, product)) {
            return false;
        }

        orderDAO.insert(order);
        int newAmount = product.getAmount() - amount;
        product.setAmount(newAmount);
        productBLL.updateProduct(product.getId(), product.getProductName(), product.getAmount(), product.getPrice(), product.getManufacturer());
        billBLL.saveBill();

        return true;
    }

    /**
     * Finds all orders in the database.
     *
     * @return List of all orders.
     */
    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }
}
