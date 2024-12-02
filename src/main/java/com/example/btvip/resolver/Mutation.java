package com.example.btvip.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.btvip.model.Category;
import com.example.btvip.model.User;
import com.example.btvip.repository.CategoryRepository;
import com.example.btvip.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mutation implements GraphQLMutationResolver {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    public Mutation(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }
    // Mutation để tạo User mới
    @MutationMapping
    public User createUser(@Arguments String fullname, @Arguments String email, @Arguments String password, @Arguments String phone) {
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
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }

    // Mutation để cập nhật thông tin User
    @QueryMapping
    public User updateUser(Long id, String fullname, String email, String password, String phone) throws EntityNotFoundException {
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
    @QueryMapping
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mutation để tạo Category mới
    @QueryMapping
    public Category createCategory(String name, String images) {
        Category category = new Category();
        category.setName(name);
        category.setImages(images);

        categoryRepository.save(category);
        return category;
    }

    // Mutation để thêm User vào Category
    @QueryMapping
    public Category addUserToCategory(String categoryId, Long userId) throws EntityNotFoundException {
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
    @QueryMapping
    public boolean deleteCategory(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
