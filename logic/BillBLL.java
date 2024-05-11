package logic;

import data.BillDAO;
import model.Bill;
import model.Product;
import util.MaxIdFlagEnum;

import java.util.ArrayList;

import static util.IdGenerator.generateId;

/**
 * Business logic class of Bill class.
 * Contains a validator, data access object and a bill object.
 */
public class BillBLL {
    private final BillDAO billDAO;
    private Bill bill;

    public BillBLL() {
        billDAO = new BillDAO();
    }

    public Bill createBill(Product product, int amount) {
        double price = amount * product.getPrice();
        bill = new Bill(generateId(MaxIdFlagEnum.BILL), price);
        return bill;
    }

    public void saveBill() {
        billDAO.insert(bill);
    }

    public ArrayList<Bill> findAllBills() {
        return billDAO.findAll();
    }
}
