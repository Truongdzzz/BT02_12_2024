package com.example.btvip.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.btvip.model.Category;
import com.example.btvip.model.User;
import com.example.btvip.repository.CategoryRepository;
import com.example.btvip.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class Mutation implements GraphQLMutationResolver {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public Mutation(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }
    // Mutation để tạo User mới
    @MutationMapping
    public User createUser(@Argument String fullname, @Argument String email, @Argument  String password, @Argument  String phone) {
        if (fullname == null || email == null || password == null) {
            throw new IllegalArgumentException("Fullname, email, and password must not be null.");
        }
        try {
            User user = new User();
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            // Lưu vào cơ sở dữ liệu
            System.out.print(fullname.toString());
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }

    // Mutation để cập nhật thông tin User
    @MutationMapping
    public User updateUser(@Argument Long id,@Argument String fullname,@Argument String email,@Argument String password,@Argument String phone) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (fullname != null) user.setFullname(fullname);
        if (email != null) user.setEmail(email);
        if (password != null) user.setPassword(password);
        if (phone != null) user.setPhone(phone);

        userRepository.save(user);
        return user;
    }

    // Mutation để xóa User theo ID
    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mutation để tạo Category mới
    @MutationMapping
    public Category createCategory(@Argument String name,@Argument String images) {
        Category category = new Category();
        category.setName(name);
        category.setImages(images);
        System.out.println(name);
        categoryRepository.save(category);
        return category;
    }

    // Mutation để thêm User vào Category
    @MutationMapping
    public Category addUserToCategory(@Argument Long categoryId,@Argument Long userId) throws EntityNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!category.getUsers().contains(user)) {
            category.getUsers().add(user);
            categoryRepository.save(category);
        }

        return category;
    }

    // Mutation để xóa Category
    @MutationMapping
    public boolean deleteCategory(@Argument Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
