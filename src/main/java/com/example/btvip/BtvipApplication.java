package com.example.btvip;

import com.example.btvip.model.User;
import com.example.btvip.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class BtvipApplication {
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(BtvipApplication.class, args);
    }
//    @PostConstruct
//    public void init() {
//        // Tạo và lưu người dùng mới
//        User user = new User();
//        user.setFullname("AAA");
//        user.setEmail("BBB");
//        user.setPassword("password");
//        user.setPhone("1111");
//
//        // Lưu đối tượng người dùng vào cơ sở dữ liệu
//        userRepository.save(user);
//    }
}
