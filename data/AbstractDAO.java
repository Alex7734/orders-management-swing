package data;

import connection.ConnectionFactory;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;
import util.Logger;
import util.MaxIdFlagEnum;

import java.beans.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

/**
 * This class implements methods for queries on database using reflection techniques.
 *
 * @param <T> The type of the object, which is a class.
 *           It is a generic type. It extends Object.
 *           The class is used to create objects from database.
 *
 * @see ConnectionFactory
 * @see MaxIdFlagEnum
 */
public class AbstractDAO<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Retrieves all objects from database.
     * It uses reflection to create objects.
     *
     * @return A list of objects.
     */
    public ArrayList<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Logger.log("SELECT * FROM " + generateSafeTableName(type.getSimpleName()));
        String query = "SELECT " + " * " + " FROM " + generateSafeTableName(type.getSimpleName());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeResultSet(resultSet);
            ConnectionFactory.closeConnection(connection);
        }

        return null;
    }

    /**
     * Retrieves an object from database.
     *
     * @param id The ID of the object to be retrieved.
     *           It is a long value.
     * @return The object retrieved from database.
     */
    public T findById(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            ArrayList<T> list = createObjects(resultSet);
            if (list.isEmpty()) {
                return null;
            }

            return list.getFirst();
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeResultSet(resultSet);
            ConnectionFactory.closeConnection(connection);
        }

        return null;
    }

    /**
     * Inserts a new object in database.
     *
     * @param t The object to be inserted.
     *          It is an instance of the class type.
     *
     * @throws SQLException If an SQL exception occurs.
     * @throws IllegalAccessException If an illegal access exception occurs.
     *
     * @see ConnectionFactory
     */
    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            StringBuilder fieldsString = new StringBuilder("(");
            StringBuilder valuesString = new StringBuilder("('");
            Field[] fields = type.getDeclaredFields();

            for (int i = 0; i < fields.length - 1; i++) {
                fields[i].setAccessible(true);
                fieldsString.append(fields[i].getName()).append(", ");
                valuesString.append(fields[i].get(t)).append("', '");
            }
            fields[fields.length - 1].setAccessible(true);
            fieldsString.append(fields[fields.length - 1].getName()).append(")");
            valuesString.append(fields[fields.length - 1].get(t)).append("')");

            String query = "INSERT INTO " +
                    generateSafeTableName(type.getSimpleName()) +
                    fieldsString +
                    " VALUES " +
                    valuesString;
            Logger.log(query);
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeConnection(connection);
        }
    }

    /**
     * Updates an object in database.
     *
     * @param t The object to be updated.
     * @throws SQLException If an SQL exception occurs.
     * @throws IllegalAccessException If an illegal access exception occurs.
     *
     */
    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        long id = -1;
        try {
            connection = ConnectionFactory.getConnection();
            Field[] fields = type.getDeclaredFields();
            StringBuilder updateString = new StringBuilder();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals("id"))
                    id = (long) field.get(t);
            }

            for (int i = 0; i < fields.length - 1; i++) {
                fields[i].setAccessible(true);
                updateString.append(fields[i].getName()).append(" = '").append(fields[i].get(t)).append("', ");
            }
            updateString.append(fields[fields.length - 1].getName()).append(" = '").append(fields[fields.length - 1].get(t)).append("'");
            String query = "UPDATE " +
                    generateSafeTableName(type.getSimpleName()) +
                    " SET " +
                    updateString +
                    " WHERE id = ?";

            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeConnection(connection);
        }
    }

    /**
     * Deletes an object from database.
     * The object is identified by its ID.
     *
     * @param id The ID of the object to be deleted.
     * @throws SQLException If an SQL exception occurs.
     */
    public void delete(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM " +
                generateSafeTableName(type.getSimpleName()) +
                " WHERE id = ?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeConnection(connection);
        }
    }

    /**
     * Selects the maximum ID from a table, based on the flag.
     * The flag is used to determine the table from which to select the maximum ID.
     *
     * @param flag The flag used to determine the table from which to select the maximum ID.
     * @return The maximum ID from the table.
     */
    public static long selectMaxId(MaxIdFlagEnum flag) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Class<?> cls = null;
        switch (flag) {
            case BILL -> cls = Bill.class;
            case CLIENT -> cls = Client.class;
            case ORDER -> cls = Order.class;
            case PRODUCT -> cls = Product.class;
        }

        String query = "SELECT MAX(id) " + " FROM " + generateSafeTableName(Objects.requireNonNull(cls).getSimpleName());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        } finally {
            ConnectionFactory.closeStatement(statement);
            ConnectionFactory.closeConnection(connection);
            ConnectionFactory.closeResultSet(resultSet);
        }

        return -1;
    }

    /**
     * Creates objects from a ResultSet.
     * It uses reflection to create objects.
     *
     * @param resultSet The ResultSet from which to create objects.
     *                  It contains the data from the SQL table.
     * @return A list of objects.
     */
    private ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> objects = new ArrayList<>();
        Constructor<T> constructor = null;

        try {
            constructor = type.getConstructor();
        } catch (NoSuchMethodException e) {
            Logger.logError(e.getMessage(), e);
        }

        try {
            while (resultSet.next()) {
                Objects.requireNonNull(constructor).setAccessible(true);
                T instance = constructor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method setMethod = propertyDescriptor.getWriteMethod();
                    setMethod.invoke(instance, value);
                }

                objects.add(instance);
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | IntrospectionException e) {
            Logger.logError(e.getMessage(), e);
        }
        return objects;
    }

    /**
     * Creates a select query for a specific object.
     *
     * @return A string containing the select query.
     */
    private String createSelectQuery() {

        return "SELECT " +
                " * " +
                " FROM " +
                generateSafeTableName(type.getSimpleName()) +
                " WHERE id = ?";
    }

    /**
     * Generates a safe table name for SQL queries.
     *
     * @param name The name of the table.
     * @return A string containing the safe table name.
     */
    private static String generateSafeTableName(String name) {
        if (name.equals("Order"))
            return  "`" + name + "`";
        return name;
    }
}
