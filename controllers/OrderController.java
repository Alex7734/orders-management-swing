package controllers;

import logic.ClientBLL;
import logic.OrderBLL;
import model.Order;
import model.Product;
import model.Client;
import logic.BillBLL;
import logic.ProductBLL;
import presentation.View;
import util.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static util.ReflectionUtil.populateTable;

/**
 * Controller class for the Order entity.
 * Contains methods for adding and viewing orders.
 */
public class OrderController implements IController{
    private final View view;
    private final OrderBLL orderBLL;
    private final ClientBLL clientBLL;
    private final BillBLL billBLL;
    private final ProductBLL productBLL;

    public OrderController(View view, ClientController clientController, ProductController productController, BillController billController) {
        this.view = view;
        this.orderBLL = new OrderBLL();
        this.billBLL = billController.getBillBLL();
        this.clientBLL = clientController.getClientBLL();
        this.productBLL = productController.getProductBLL();
    }

    public void initView() {
        this.view.getAddOrderButton().addActionListener(e -> addOrder());
        this.view.getViewOrdersButton().addActionListener(e -> viewOrders());
    }

    /**
     * Method for adding a new order.
     * Displays a dialog with fields for selecting a product, client and quantity.
     * If the order is successfully added, a bill is generated and displayed.
     */
    private void addOrder() {
        List<Product> products = productBLL.findAllProducts();
        List<Client> clients = clientBLL.findAllClients();

        JComboBox<String> productComboBox = getProductComboBox(products);
        JComboBox<String> clientComboBox = getClientComboBox(clients);
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Client:"));
        panel.add(clientComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Order",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            Logger.log("Order addition cancelled");
            return;
        }

        int selectedProductIndex = productComboBox.getSelectedIndex();
        int selectedClientIndex = clientComboBox.getSelectedIndex();
        Product selectedProduct = products.get(selectedProductIndex);
        Client selectedClient = clients.get(selectedClientIndex);
        int quantity = Integer.parseInt(quantityField.getText());

        if (orderBLL.insertOrder(selectedProduct, selectedClient, billBLL, productBLL, quantity)) {
            JOptionPane.showMessageDialog(view, "Order added successfully and bill generated");
            viewOrders();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add order: invalid data or insufficient stock");
        }
    }

    public void viewOrders() {
        List<Order> orders = orderBLL.findAllOrders();
        DefaultTableModel model = (DefaultTableModel) view.getOrdersTable().getModel();
        populateTable(orders, model);
    }

    private JComboBox<String> getProductComboBox(List<Product> products) {
        JComboBox<String> productComboBox = new JComboBox<>();
        for (Product product : products) {
            productComboBox.addItem(product.getProductName() + " (" + product.getAmount() + " available)");
        }
        return productComboBox;
    }

    private JComboBox<String> getClientComboBox(List<Client> clients) {
        JComboBox<String> clientComboBox = new JComboBox<>();
        for (Client client : clients) {
            clientComboBox.addItem(client.getFirstName() + " " + client.getLastName());
        }
        return clientComboBox;
    }
}