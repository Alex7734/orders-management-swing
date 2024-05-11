package connection;

import util.Logger;

import java.sql.*;

/**
 *  This is a helper class which helps us to retrieve and close a connection to the database.
 *  It also supports methods for closing SQL statements and result sets.
 */
public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "alex";
    private static final String PASS = "Sebi123"; // guess where this is from :D
    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     *  Private constructor to prevent instantiation from outside the class.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            Logger.logError(e.getMessage(), e);
        }
    }

    /**
     *  Creates a connection to the database.
     *  @return Connection object if the connection is successful, null otherwise.
     */
    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }

        return connection;
    }

    /**
     *  Retrieves a connection to the database.
     *  @return Connection object if the connection is successful, null otherwise.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     *  Closes the connection to the database.
     *  @param connection Connection object to be closed.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.logError(e.getMessage(), e);
            }
        }
    }

    /**
     *  Closes the SQL statement.
     *  @param statement Statement object to be closed.
     */
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                Logger.logError(e.getMessage(), e);
            }
        }
    }

    /**
     *  Closes the result set.
     *  @param resultSet ResultSet object to be closed.
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                Logger.logError(e.getMessage(), e);
            }
        }
    }
}
