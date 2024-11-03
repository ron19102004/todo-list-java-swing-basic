package org.example.services;

import org.example.conf.Database;
import org.example.entities.User;
import org.example.interfaces.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService implements IService<User> {

    @Override
    public ArrayList<User> findAll(long id) {
        throw new RuntimeException("Phương thức không hỗ trợ");
    }
    public User findByUsername(String username) throws SQLException {
        User user = null;
        final String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );
        }
        return user;
    }

    @Override
    public User findById(long id) throws SQLException {
        User user = null;
        final String sql = "SELECT * FROM users WHERE id=?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
            );
        }
        return user;
    }

    @Override
    public void save(User user) throws SQLException {
        final String sql = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {
        final String sql = "UPDATE users SET name=?, password=? WHERE id=?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setLong(3, user.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(long id) throws SQLException {
        final String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }
}
