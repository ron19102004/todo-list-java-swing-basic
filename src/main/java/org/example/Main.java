package org.example;

import org.example.conf.Database;
import org.example.entities.DatabaseProperties;

import org.example.views.UserLoginView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            DatabaseProperties databaseProperties = new DatabaseProperties();
            databaseProperties.setHost("localhost");
            databaseProperties.setPort(3306);
            databaseProperties.setUsername("root");
            databaseProperties.setPassword("");
            databaseProperties.setDatabaseName("todolistswing");
            Database.connect(databaseProperties);
            new UserLoginView().setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}