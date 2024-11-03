package org.example.conf;

import org.example.entities.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    private Database(){}
    public static Connection getConnection(){
        return Database.connection;
    }
    public static void connect(DatabaseProperties databaseProperties) throws SQLException {
        new com.mysql.jdbc.Driver();
        String URL = "jdbc:mysql://" + databaseProperties.getHost() + ":" +
                databaseProperties.getPort() + "/" + databaseProperties.getDatabaseName();
        Database.connection = DriverManager.getConnection(URL, databaseProperties.getUsername(), databaseProperties.getPassword());
        if (Database.connection != null){
            System.out.println("Connect database successfully!");
        }
    }
    public static void close() throws SQLException {
        if (Database.connection != null){
            Database.connection.close();
            System.out.println("Database disconnection!");
        }
    }
}
