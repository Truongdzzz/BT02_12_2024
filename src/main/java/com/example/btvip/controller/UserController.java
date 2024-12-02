package com.example.btvip.controller;

import com.example.btvip.model.User;
import com.example.btvip.repository.UserRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @QueryMapping
    Iterable<User> getUsers() {
        return userRepository.findAll();
    }
    @QueryMapping
    public User createUser(String fullname, String email, String password, String phone) {
        // Kiểm tra thông tin đầu vào nếu cần
        if (fullname == null || email == null || password == null) {
            throw new IllegalArgumentException("Fullname, email, and password must not be null.");
        }

        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);

        // Lưu vào cơ sở dữ liệu
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            // Xử lý lỗi lưu trữ
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }
}
