package controllers;

import logic.ClientBLL;
import model.Client;
import presentation.View;
import util.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static util.ReflectionUtil.populateTable;

/**
 * Controller class for the Client entity.
 * Contains the view and the business logic layer.
 */
public class ClientController implements IController{
    private final View view;
    private final ClientBLL clientBLL;

    public ClientController(View view) {
        this.view = view;
        this.clientBLL = new ClientBLL();
    }

    public void initView() {
        this.view.getAddButton().addActionListener(e -> addClient());
        this.view.getEditButton().addActionListener(e -> editClient());
        this.view.getDeleteButton().addActionListener(e -> deleteClient());
        this.view.getViewButton().addActionListener(e -> viewClients());
    }

    private void addClient() {
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField cnpField = new JTextField(20);

        JPanel panel = populatePanel(firstNameField, lastNameField, ageField, addressField, cnpField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            Logger.log("Cancelled");
            return;
        }

        if (clientBLL.insertClient(firstNameField.getText(), lastNameField.getText(),
                Integer.parseInt(ageField.getText()), addressField.getText(), cnpField.getText())) {
            JOptionPane.showMessageDialog(view, "Client added successfully");
            viewClients();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid client data");
        }
    }

    private void editClient() {
        int selectedRow = view.getClientTable().getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "No row selected");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getClientTable().getModel();
        long clientId = (Long) model.getValueAt(selectedRow, 0);
        String firstName = (String) model.getValueAt(selectedRow, 1);
        String lastName = (String) model.getValueAt(selectedRow, 2);
        int age = (Integer) model.getValueAt(selectedRow, 3);
        String address = (String) model.getValueAt(selectedRow, 4);
        String cnp = (String) model.getValueAt(selectedRow, 5);

        JTextField firstNameField = new JTextField(firstName, 20);
        JTextField lastNameField = new JTextField(lastName, 20);
        JTextField ageField = new JTextField(String.valueOf(age), 20);
        JTextField addressField = new JTextField(address, 20);
        JTextField cnpField = new JTextField(cnp, 20);

        JPanel panel = populatePanel(firstNameField, lastNameField, ageField, addressField, cnpField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            Logger.log("Cancelled");
            return;
        }

        if (clientBLL.updateClient(clientId, firstNameField.getText(), lastNameField.getText(),
                Integer.parseInt(ageField.getText()), addressField.getText(), cnpField.getText())) {
            JOptionPane.showMessageDialog(view, "Client updated successfully");
            viewClients();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid client data");
        }
    }

    private JPanel populatePanel(JTextField firstNameField, JTextField lastNameField, JTextField ageField, JTextField addressField, JTextField cnpField) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("CNP:"));
        panel.add(cnpField);
        return panel;
    }

    private void deleteClient() {
        int selectedRow = view.getClientTable().getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "No row selected");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getClientTable().getModel();
        int clientId = (int) model.getValueAt(selectedRow, 0);
        clientBLL.deleteClient(clientId);
        model.removeRow(selectedRow);
        viewClients();
    }

    public void viewClients() {
        List<Client> clients = clientBLL.findAllClients();
        DefaultTableModel model = (DefaultTableModel) view.getClientTable().getModel();
        populateTable(clients, model);
    }

    public ClientBLL getClientBLL() {
        return clientBLL;
    }
}