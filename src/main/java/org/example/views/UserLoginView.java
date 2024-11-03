package org.example.views;

import org.example.conf.Database;
import org.example.controllers.TaskController;
import org.example.controllers.UserController;
import org.example.entities.User;
import org.example.services.TaskService;
import org.example.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class UserLoginView extends JFrame {
    private final JTextField jTextFieldUsername;
    private final JPasswordField jPasswordField;

    private UserController userController;

    private void initialize() {
        final UserService userService = new UserService();
        this.userController = new UserController(userService);

        this.setTitle("User Login");
        this.setSize(400, 280);
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

    private ActionListener actionListenerLoginButton() {
        return e -> {
            String username = this.jTextFieldUsername.getText();
            String password = new String(this.jPasswordField.getPassword());
            if (username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(null,
                        "Vui lòng nhập đầy đủ tên người dùng và mật khẩu!",
                        "Thông Báo",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                User user = this.userController.login(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null,
                            "Đăng nhập thành công!",
                            "Thông Báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    TaskView taskView = new TaskView(user);
                    taskView.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Tên người dùng hoặc mật khẩu không chính xác!",
                            "Thông Báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private ActionListener actionListenerRegisterMenu() {
        return e -> {
            UserRegisterView userRegisterView = new UserRegisterView();
            userRegisterView.setVisible(true);
            this.dispose();
        };
    }

    public UserLoginView() {
        initialize();

        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Help");
        JMenuItem registerItem = new JMenuItem("Register");
        helpMenu.add(registerItem);
        registerItem.addActionListener(this.actionListenerRegisterMenu());

        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);


        JLabel jLabelTitle = new JLabel("User Login");
        jLabelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        jLabelTitle.setForeground(new Color(0, 102, 204));

        JLabel jLabelUsername = new JLabel("Username:");
        this.jTextFieldUsername = new JTextField(15);

        JLabel jLabelPassword = new JLabel("Password:");
        this.jPasswordField = new JPasswordField(15);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        jLabelUsername.setFont(labelFont);
        jLabelPassword.setFont(labelFont);

        jTextFieldUsername.setBorder(BorderFactory.createCompoundBorder(
                jTextFieldUsername.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        jPasswordField.setBorder(BorderFactory.createCompoundBorder(
                jPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JPanel jPanelContent = new JPanel();
        jPanelContent.setLayout(new GridLayout(2, 2, 10, 10));
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jPanelContent.add(jLabelUsername);
        jPanelContent.add(jTextFieldUsername);
        jPanelContent.add(jLabelPassword);
        jPanelContent.add(jPasswordField);

        JPanel jPanelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jPanelTitle.add(jLabelTitle);

        JButton jButtonLogin = new JButton("Login");
        jButtonLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        jButtonLogin.setBackground(new Color(0, 153, 76));
        jButtonLogin.setForeground(Color.WHITE);
        jButtonLogin.setFocusPainted(false);
        jButtonLogin.setPreferredSize(new Dimension(100, 30));
        jButtonLogin.addActionListener(this.actionListenerLoginButton());

        JPanel jPanelSubmit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelSubmit.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jPanelSubmit.add(jButtonLogin);

        this.setLayout(new BorderLayout());
        this.add(jPanelTitle, BorderLayout.NORTH);
        this.add(jPanelContent, BorderLayout.CENTER);
        this.add(jPanelSubmit, BorderLayout.SOUTH);

    }
}
