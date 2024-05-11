package presentation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class View extends JFrame {
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JTable clientTable;

    private JScrollPane scrollPane;
    private JTabbedPane tabbedPane;

    private JButton addProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JButton viewProductButton;
    private JTable productTable;

    private JButton addOrderButton;
    private JButton viewOrdersButton;
    private JPanel ordersButtonPanel;
    private JTable ordersTable;

    private JButton viewBillButton;

    private JTable billTable;

    public View() {
        super("Orders Management System");
        createUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createUI() {
        //Client tab
        addButton = new JButton("Add New Client");
        editButton = new JButton("Edit Client");
        deleteButton = new JButton("Delete Client");
        viewButton = new JButton("View All Clients");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        clientTable = new JTable(new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Age", "Address", "CNP"}, 0));
        scrollPane = new JScrollPane(clientTable);
        clientTable.setFillsViewportHeight(true);

        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.add(buttonPanel, BorderLayout.NORTH);
        clientPanel.add(scrollPane, BorderLayout.CENTER);

        addProductButton = new JButton("Add New Product");
        editProductButton = new JButton("Edit Product");
        deleteProductButton = new JButton("Delete Product");
        viewProductButton = new JButton("View All Products");

        JPanel productButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productButtonPanel.add(addProductButton);
        productButtonPanel.add(editProductButton);
        productButtonPanel.add(deleteProductButton);
        productButtonPanel.add(viewProductButton);

        productTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Product Name", "Amount", "Price", "Manufacturer"}, 0));;
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productTable.setFillsViewportHeight(true);

        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(productButtonPanel, BorderLayout.NORTH);
        productPanel.add(productScrollPane, BorderLayout.CENTER);

        addOrderButton = new JButton("Add New Order");
        viewOrdersButton = new JButton("View All Orders");
        ordersButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ordersButtonPanel.add(addOrderButton);
        ordersButtonPanel.add(viewOrdersButton);

        ordersTable = new JTable(new DefaultTableModel(new Object[]{"Order ID", "Product ID", "Client ID", "Bill ID", "Amount"}, 0));
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        ordersTable.setFillsViewportHeight(true);

        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.add(ordersButtonPanel, BorderLayout.NORTH);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);

        billTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Price"}, 0));
        JScrollPane billScrollPane = new JScrollPane(billTable);
        billTable.setFillsViewportHeight(true);

        JPanel billPanel = new JPanel(new BorderLayout());
        viewBillButton = new JButton("View All Bills");
        billPanel.add(viewBillButton, BorderLayout.NORTH);
        billPanel.add(billScrollPane, BorderLayout.CENTER);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Client", clientPanel);
        tabbedPane.addTab("Product", productPanel);
        tabbedPane.addTab("Orders", ordersPanel);
        tabbedPane.addTab("Bill", billPanel);

        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public JButton getAddButton() {
        return addButton;
    }
    public JButton getEditButton() {
        return editButton;
    }
    public JButton getDeleteButton() {
        return deleteButton;
    }
    public JButton getViewButton() {
        return viewButton;
    }
    public JTable getClientTable() {
        return clientTable;
    }


    //Product
    public JButton getAddProductButton() {
        return addProductButton;
    }
    public JButton getEditProductButton() {
        return editProductButton;
    }
    public JButton getDeleteProductButton() {
        return deleteProductButton;
    }
    public JButton getViewProductButton() {
        return viewProductButton;
    }
    public JTable getProductTable() {
        return productTable;
    }

    //Orders
    public JButton getAddOrderButton() {
        return addOrderButton;
    }
    public JButton getViewOrdersButton() {
        return viewOrdersButton;
    }
    public JTable getOrdersTable() {
        return ordersTable;
    }

    //Bill
    public JTable getBillTable() {
        return billTable;
    }

    public JButton getViewBillButton() {
        return viewBillButton;
    }


    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}