package org.example.controllers;

import org.example.entities.Task;
import org.example.services.TaskService;
import org.example.views.TaskView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskController {
    private final TaskService taskService;
    private ArrayList<Task> myTask;
    private final TaskView taskView;
    private final long userId;

    public TaskController(TaskView taskView, final TaskService taskService, long userId) {
        this.taskView = taskView;
        this.taskService = taskService;
        this.userId = userId;
        try {
            myTask = taskService.findAll(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task) {
        try {
            this.taskService.update(task);
            JOptionPane.showMessageDialog(null,
                    "Cập nhật thành công", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadMyTaskFromDB() {
        try {
            myTask = taskService.findAll(userId);
            loadMyTask();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task taskCurrentByIndex(int index) {
        return myTask.get(index);
    }

    public void deleteTask(Task task) {
        try {
            this.taskService.delete(task.getId());
            this.myTask.remove(task);
            loadMyTask();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addTask(Task task) {
        try {
            Task taskSave = this.taskService.saveTask(task);
            myTask.add(taskSave);
            loadMyTask();
            this.taskView.getJTextFieldContent().setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadMyTask() {
        this.taskView.getTableModel().setRowCount(0);
        this.taskView.getTableModel().setColumnIdentifiers(new String[]{"Task ID", "Content", "Status"});
        myTask.forEach(task -> {
            this.taskView.getTableModel().addRow(new String[]{String.valueOf(task.getId()), task.getContent(), String.valueOf(task.getStatus())});
        });
    }
}
