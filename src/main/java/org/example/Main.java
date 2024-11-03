package org.example;

import org.example.conf.Database;
import org.example.controllers.TaskController;
import org.example.entities.DatabaseProperties;
import org.example.entities.User;
import org.example.services.TaskService;
import org.example.views.TaskView;
import org.example.views.UserLoginView;
import org.example.views.UserRegisterView;

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
//            UserRegisterView userRegisterView = new UserRegisterView();
//            userRegisterView.setVisible(true);
            UserLoginView userLoginView = new UserLoginView();
            userLoginView.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}