package Grupoa4.DBConnection;

import java.sql.*;
import java.util.*;

public class ConnectDB {
    private final String DB_URL = "jdbc:mariadb//143.106.243.64";
    private final String USERNAME = "si400_2023";
    private final String PASSWORD = "si400_2023";
    private Connection connection  = null;

    public Connection getConnection() {
        return (connection);
    }

    public MariaDBConnection() {
        super();

        try {
            connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("Database connection successful!");
            }
            else {
                System.out.println("Connection failed.");
            }
        }
        catch (SQLException e) {
            System.err.println("Database error:" + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLexception e) {
            System.err.println("ERROR: could not close the database:" + e.getMessage());
        }
    }
}
