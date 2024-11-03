package org.example.views;

import org.example.conf.Database;
import org.example.controllers.TaskController;

import org.example.entities.Task;
import org.example.entities.User;
import org.example.services.TaskService;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class TaskView extends JFrame {
    private final User user;
    private final TaskController taskController;

    private final JTextField jTextFieldContent;
    private final JCheckBox jCheckBoxStatus;
    private final DefaultTableModel tableModel;
    private final JTable taskTable;
    private int rowSelectedCurrent = -1;

    public JTextField getJTextFieldContent() {
        return this.jTextFieldContent;
    }

    public DefaultTableModel getTableModel() {
        return this.tableModel;
    }

    private ActionListener actionListenerAddTask() {
        return e -> {
            String content = this.jTextFieldContent.getText();
            boolean status = this.jCheckBoxStatus.isSelected();
            if (content.isBlank()) {
                JOptionPane.showMessageDialog(null,
                        "Vui lòng nhập nội dung!",
                        "Thông Báo",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                Task task = new Task();
                task.setContent(content);
                task.setStatus(status);
                task.setUser(user);
                this.taskController.addTask(task);
            }
        };
    }

    private ActionListener actionListenerDeleteTask() {
        return e -> {
            if (rowSelectedCurrent > -1) {
                Task task = taskController.taskCurrentByIndex(rowSelectedCurrent);
                taskController.deleteTask(task);
            }
        };
    }

    private ActionListener actionListenerUpdateTask() {
        return e -> {
            if (rowSelectedCurrent > -1) {
                String id = (String) tableModel.getValueAt(rowSelectedCurrent, 0);
                String content = (String) tableModel.getValueAt(rowSelectedCurrent, 1);
                String status = (String) tableModel.getValueAt(rowSelectedCurrent, 2);
                Task task = new Task(
                        Long.parseLong(id),
                        content,
                        status.equalsIgnoreCase("true"),
                        user
                );
                taskController.updateTask(task);
            }
        };
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204)); // Màu xanh dương
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));

        // Thiết lập hiệu ứng khi di chuột qua
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 76)); // Đổi màu khi di chuột vào
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204)); // Trở về màu ban đầu
            }
        });

        return button;
    }

    public TaskView(final User user) {
        this.user = user;
        TaskService taskService = new TaskService();
        this.taskController = new TaskController(this, taskService, user.getId());
        initialize();

        JMenuBar menuBar = new JMenuBar();

        JMenu taskMenu = new JMenu("Task");
        JMenuItem reloadMyTask = new JMenuItem("Reload My Task");
        taskMenu.add(reloadMyTask);
        reloadMyTask.addActionListener(e -> {
            taskController.loadMyTaskFromDB();
        });

        menuBar.add(taskMenu);
        this.setJMenuBar(menuBar);

        JLabel jLabelContent = new JLabel("Content:");
        jTextFieldContent = new JTextField(20);

        JLabel jLabelStatus = new JLabel("Status:");
        jCheckBoxStatus = new JCheckBox("Completed");

        JButton jButtonSubmit = createStyledButton("Add Task");
        jButtonSubmit.addActionListener(actionListenerAddTask());

        JButton jButtonUpdate = createStyledButton("Update Task");
        jButtonUpdate.addActionListener(actionListenerUpdateTask());

        JButton jButtonDelete = createStyledButton("Delete Task");
        jButtonDelete.addActionListener(actionListenerDeleteTask());

        tableModel = new DefaultTableModel();
        taskTable = new JTable(tableModel);
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rowSelectedCurrent = taskTable.getSelectedRow();
            }
        });
        taskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        taskTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        // Tạo panel cho các trường nhập liệu
        JPanel jPanelContent = new JPanel();
        jPanelContent.setLayout(new GridLayout(4, 2, 10, 10));
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanelContent.add(jLabelContent);
        jPanelContent.add(jTextFieldContent);
        jPanelContent.add(jLabelStatus);
        jPanelContent.add(jCheckBoxStatus);
        jPanelContent.add(jButtonSubmit);
        jPanelContent.add(jButtonDelete);
        jPanelContent.add(jButtonUpdate);

        // Thêm panel vào cửa sổ
        this.setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(jPanelContent, BorderLayout.SOUTH);
        this.taskController.loadMyTask();
    }

    private void initialize() {
        this.setTitle(this.user.getName() + "'s Tasks");
        this.setSize(600, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Database.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
