package org.example.views;

import org.example.conf.Database;
import org.example.controllers.UserController;
import org.example.entities.User;
import org.example.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class UserRegisterView extends JFrame {
    private final JTextField jTextFieldName;
    private final JTextField jTextFieldUsername;
    private final JPasswordField jPasswordField;

    private UserController userController;

    private void initialize() {
        final UserService userService = new UserService();
        this.userController = new UserController(userService);

        this.setTitle("User Register");
        this.setSize(400, 320);
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

    private ActionListener actionListenerSubmitButton() {
        return e -> {
            String name = this.jTextFieldName.getText();
            String username = this.jTextFieldUsername.getText();
            String password = new String(this.jPasswordField.getPassword());
            if (name.isBlank() || username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(null,
                        "Yêu cầu nhập đầy đủ các trường!",
                        "Thông Báo",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                User user = new User();
                user.setName(name);
                user.setPassword(password);
                user.setUsername(username);
                this.userController.addUser(user);
            }
        };
    }
    private ActionListener actionListenerLoginMenu() {
        return e -> {
            UserLoginView userLoginView = new UserLoginView();
            userLoginView.setVisible(true);
            this.dispose();
        };
    }
    public UserRegisterView() {
        initialize();

        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Help");
        JMenuItem loginItem = new JMenuItem("Login");
        helpMenu.add(loginItem);
        loginItem.addActionListener(this.actionListenerLoginMenu());

        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);

        JLabel jLabelTitle = new JLabel("User Register");
        jLabelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        jLabelTitle.setForeground(new Color(0, 102, 204));

        JLabel jLabelName = new JLabel("Name:");
        this.jTextFieldName = new JTextField(15);

        JLabel jLabelUsername = new JLabel("Username:");
        this.jTextFieldUsername = new JTextField(15);

        JLabel jLabelPassword = new JLabel("Password:");
        this.jPasswordField = new JPasswordField(15);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        jLabelName.setFont(labelFont);
        jLabelUsername.setFont(labelFont);
        jLabelPassword.setFont(labelFont);

        jTextFieldName.setBorder(BorderFactory.createCompoundBorder(
                jTextFieldName.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        jTextFieldUsername.setBorder(BorderFactory.createCompoundBorder(
                jTextFieldUsername.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        jPasswordField.setBorder(BorderFactory.createCompoundBorder(
                jPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JPanel jPanelContent = new JPanel();
        jPanelContent.setLayout(new GridLayout(3, 2, 10, 10));
        jPanelContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jPanelContent.add(jLabelName);
        jPanelContent.add(jTextFieldName);
        jPanelContent.add(jLabelUsername);
        jPanelContent.add(jTextFieldUsername);
        jPanelContent.add(jLabelPassword);
        jPanelContent.add(jPasswordField);

        JPanel jPanelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jPanelTitle.add(jLabelTitle);

        JButton jButtonSubmit = new JButton("Submit");
        jButtonSubmit.setFont(new Font("Arial", Font.PLAIN, 14));
        jButtonSubmit.setBackground(new Color(0, 153, 76));
        jButtonSubmit.setForeground(Color.WHITE);
        jButtonSubmit.setFocusPainted(false);
        jButtonSubmit.setPreferredSize(new Dimension(100, 30));
        jButtonSubmit.addActionListener(this.actionListenerSubmitButton());

        JPanel jPanelSubmit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelSubmit.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        jPanelSubmit.add(jButtonSubmit);

        this.setLayout(new BorderLayout());
        this.add(jPanelTitle, BorderLayout.NORTH);
        this.add(jPanelContent, BorderLayout.CENTER);
        this.add(jPanelSubmit, BorderLayout.SOUTH);
    }
}
