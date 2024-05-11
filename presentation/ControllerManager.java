package presentation;

import controllers.BillController;
import controllers.ClientController;
import controllers.OrderController;
import controllers.ProductController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ControllerManager class is responsible for managing the controllers of the application.
 * It initializes the controllers and adds listeners to the top navigator.
 */
public class ControllerManager {
    private final ClientController clientController;
    private final ProductController productController;
    private final OrderController orderController;
    private final BillController billController;

    public ControllerManager(View view) {
        this.clientController = new ClientController(view);
        this.productController = new ProductController(view);
        this.billController = new BillController(view);
        this.orderController = new OrderController(view, clientController, productController, billController);
        initControllerManager(view);
    }

    private void initControllerManager(View view) {
        initControllers();
        view.setVisible(true);
        refreshClients();
        addTopNavigatorListeners(view);
    }

    private void refreshClients() {
        clientController.viewClients();
    }

    private void refreshProducts() {
        productController.viewProducts();
    }

    private void refreshOrders() {
        orderController.viewOrders();
    }

    private void refreshBills() {
        billController.viewBills();
    }

    private void initControllers() {
        clientController.initView();
        productController.initView();
        orderController.initView();
        billController.initView();
    }

    /**
     * Adds listeners to the top navigator.
     * @param view the view of the application
     */
    private void addTopNavigatorListeners(View view) {
        view.getTabbedPane().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                switch(index) {
                    case 0:
                        refreshClients();
                        break;
                    case 1:
                        refreshProducts();
                        break;
                    case 2:
                        refreshOrders();
                        break;
                    case 3:
                        refreshBills();
                        break;
                }

            }
        });
    }
}
