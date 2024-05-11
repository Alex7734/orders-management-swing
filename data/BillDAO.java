package data;
import connection.ConnectionFactory;
import model.Bill;
import util.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Data access object class for the Bill model.
 * Contains methods to interact with the database.
 *
 * @see Bill
 */
public class BillDAO {
    /**
     * Finds all the bills in the database.
     *
     * @return an ArrayList of Bill objects.
     * @see Bill
     */
    public ArrayList<Bill> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Bill> bills = new ArrayList<Bill>();
        String query = "SELECT * FROM Bill";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                int price = resultSet.getInt(2);

                bills.add(new Bill(id, price));
            }

            return bills;
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection);
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeResultSet(resultSet);
        }

        return null;
    }

    /**
     * Inserts a bill into the database.
     *
     * @param bill the Bill object to be inserted.
     *             The id and price fields are used.
     * @see Bill
     */
    public void insert(Bill bill) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO Bill (id, price) VALUES ('" +
                bill.id() +
                "', '" +
                bill.price() +
                "')";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection);
            ConnectionFactory.closeStatement(statement);
        }
    }
}
