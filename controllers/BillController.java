package controllers;

import logic.BillBLL;
import model.Bill;
import presentation.View;

import javax.swing.table.DefaultTableModel;
import java.util.List;

import static util.ReflectionUtil.populateTable;

/**
 * Controller class for Bill.
 * Contains a view and a business logic class.
 */
public class BillController implements IController{
    private final View view;
    private final BillBLL billBLL;

    public BillController(View view) {
        this.view = view;
        this.billBLL = new BillBLL();
    }

    public void initView() {
        this.view.getViewBillButton().addActionListener(e -> viewBills());
    }

    public void viewBills() {
        List<Bill> bills = billBLL.findAllBills();
        DefaultTableModel model = (DefaultTableModel) view.getBillTable().getModel();
        populateTable(bills, model);
    }

    public BillBLL getBillBLL() {
        return billBLL;
    }
}