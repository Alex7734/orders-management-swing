package controllers;

import logic.ProductBLL;
import model.Product;
import presentation.View;
import util.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static util.ReflectionUtil.populateTable;

/**
 * Controller class for the Product entity.
 * Contains methods for adding, editing and viewing products.
 */
public class ProductController implements IController {
    private final View view;
    private final ProductBLL productBLL;

    public ProductController(View view) {
        this.view = view;
        this.productBLL = new ProductBLL();
    }

    public void initView() {
        this.view.getAddProductButton().addActionListener(e -> addProduct());
        this.view.getEditProductButton().addActionListener(e -> editProduct());
        this.view.getDeleteProductButton().addActionListener(e -> deleteProducts());
        this.view.getViewProductButton().addActionListener(e -> viewProducts());
    }

    private void addProduct() {
        JTextField productNameField = new JTextField(20);
        JTextField productAmountField = new JTextField(20);
        JTextField productPriceField = new JTextField(20);
        JTextField productManufacturerField = new JTextField(20);

        JPanel panel = populatePanel(productNameField, productAmountField, productPriceField, productManufacturerField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            Logger.log("Cancelled");
            return;
        }

        if (productBLL.insertProduct(productNameField.getText(), Integer.parseInt(productAmountField.getText()),
                Double.parseDouble(productPriceField.getText()), productManufacturerField.getText())) {
            JOptionPane.showMessageDialog(view, "Product added successfully");
            viewProducts();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid product data");
        }
    }

    private void editProduct() {
        int selectedRow = view.getProductTable().getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "No row selected");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
        long productId = (Long) model.getValueAt(selectedRow, 0);
        String productName = (String) model.getValueAt(selectedRow, 1);
        int amount = (Integer) model.getValueAt(selectedRow, 2);
        double price = (Double) model.getValueAt(selectedRow, 3);
        String manufacturer = (String) model.getValueAt(selectedRow, 4);

        JTextField productNameField = new JTextField(productName, 20);
        JTextField productAmountField = new JTextField(String.valueOf(amount), 20);
        JTextField productPriceField = new JTextField(String.valueOf(price), 20);
        JTextField productManufacturerField = new JTextField(manufacturer, 20);
        JPanel panel = populatePanel(productNameField, productAmountField, productPriceField, productManufacturerField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            Logger.log("Cancelled");
            return;
        }

        if (productBLL.updateProduct(productId, productNameField.getText(), Integer.parseInt(productAmountField.getText()),
                Double.parseDouble(productPriceField.getText()), productManufacturerField.getText())) {
            JOptionPane.showMessageDialog(view, "Product updated successfully");
            viewProducts();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid product data");
        }
    }

    /**
     * Populates a panel with text fields for editing a product.
     *
     * @param productNameField       the product name text field
     * @param productAmountField     the product amount text field
     * @param productPriceField      the product price text field
     * @param productManufacturerField the product manufacturer text field
     * @return the populated panel
     */
    private JPanel populatePanel(JTextField productNameField, JTextField productAmountField, JTextField productPriceField, JTextField productManufacturerField) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Product Name:"));
        panel.add(productNameField);
        panel.add(new JLabel("Amount:"));
        panel.add(productAmountField);
        panel.add(new JLabel("Price:"));
        panel.add(productPriceField);
        panel.add(new JLabel("Manufacturer:"));
        panel.add(productManufacturerField);
        return panel;
    }

    private void deleteProducts() {
        int selectedRow = view.getProductTable().getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "No row selected");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
        int productId = (int) model.getValueAt(selectedRow, 0);
        productBLL.deleteProduct(productId);
        model.removeRow(selectedRow);
    }

    public void viewProducts() {
        List<Product> products = productBLL.findAllProducts();
        DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
        populateTable(products, model);
    }

    public ProductBLL getProductBLL() {
        return productBLL;
    }
}