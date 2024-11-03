package org.example.controllers;

import org.example.entities.User;
import org.example.services.UserService;

import javax.swing.*;
import java.sql.SQLException;

public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) {
        try {
            User user = this.userService.findByUsername(username);
            if (user == null) {
                JOptionPane.showMessageDialog(null,
                        "Người dùng không tồn tại! ", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (user.getPassword().equals(password)) {
                    return user;
                }
                JOptionPane.showMessageDialog(null,
                        "Mật khẩu đăng nhập sai! ", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void addUser(User user) {
        try {
            this.userService.save(user);
            JOptionPane.showMessageDialog(null,
                    "Đăng kí thành công", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
