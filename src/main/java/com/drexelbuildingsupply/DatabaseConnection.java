package com.drexelbuildingsupply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    static {
        try {
            Properties properties = new Properties();
            properties.load(DatabaseConnection.class.getResourceAsStream("/database.properties"));
            dbUrl = properties.getProperty("db.url");
            dbUsername = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");
            Class.forName(properties.getProperty("db.driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
