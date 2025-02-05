package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext{

    /* USE BELOW METHOD FOR YOUR DATABASE CONNECTION FOR BOTH SINGLE AND MULTIPLE SQL SERVER INSTANCE(s) */
    /* DO NOT EDIT THE BELOW METHOD, YOU MUST USE ONLY THIS ONE FOR YOUR DATABASE CONNECTION */
    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber +
                ";databaseName=" + dbName; // + "; integratedSecurity=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }

    /* Method to test the database connection */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Change/update information of your database connection, DO NOT change name of instance variables in this class */
    private final String serverName = "localhost";
    private final String dbName = "FinBank_SWP391";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "123";
    
    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        boolean isConnected = dbContext.testConnection();
        System.out.println("Connection successful: " + isConnected);
    }
}
