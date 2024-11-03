package org.example.services;

import org.example.conf.Database;
import org.example.entities.Task;
import org.example.entities.User;
import org.example.interfaces.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TaskService implements IService<Task> {
    @Override
    public ArrayList<Task> findAll(long id) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        final String sql = "SELECT t.id AS task_id, t.content, t.status, u.id AS user_id, u.name, u.username " +
                "FROM tasks t " +
                "JOIN users u ON t.userId = u.id " +
                "WHERE t.userId = ?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            Task task = new Task(
                    resultSet.getLong("task_id"),
                    resultSet.getString("content"),
                    resultSet.getBoolean("status"),
                    user
            );
            tasks.add(task);
        }
        return tasks;
    }


    @Override
    public Task findById(long id) throws SQLException {
        Task task = null;
        final String sql = "SELECT t.id AS task_id, t.content, t.status, u.id AS user_id, u.name, u.username" +
                "FROM tasks t " +
                "JOIN users u ON t.userId = u.id " +
                "WHERE t.id = ?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            task = new Task(
                    resultSet.getLong("task_id"),
                    resultSet.getString("content"),
                    resultSet.getBoolean("status"),
                    user
            );
        }

        return task;
    }

    public Task saveTask(Task task) throws SQLException {
        final String sql = "INSERT INTO tasks (content, status, userId) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, task.getContent());
        preparedStatement.setBoolean(2, task.getStatus());
        preparedStatement.setLong(3, task.getUser().getId());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long taskId = generatedKeys.getLong(1);
                    task.setId(taskId);
                }
            }
        }
        return task;
    }

    @Override
    public void save(Task task) throws SQLException {
        throw new RuntimeException("Phương thức không hỗ trợ");
    }

    @Override
    public void update(Task task) throws SQLException {
        final String sql = "UPDATE tasks SET content = ?, status = ? WHERE id = ?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, task.getContent());
        preparedStatement.setBoolean(2, task.getStatus());
        preparedStatement.setLong(3, task.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(long id) throws SQLException {
        final String sql = "DELETE FROM tasks WHERE id = ?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }
}
