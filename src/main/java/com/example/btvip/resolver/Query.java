package com.example.btvip.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.btvip.model.Category;
import com.example.btvip.model.User;
import com.example.btvip.repository.CategoryRepository;
import com.example.btvip.repository.UserRepository;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Query implements GraphQLQueryResolver {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    GraphQLScalarType longScalar = ExtendedScalars.newAliasedScalar("Long")
            .aliasedScalar(ExtendedScalars.GraphQLLong)
            .build();
    @Autowired
    public Query(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }
    @QueryMapping
    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
    @QueryMapping
    public Iterable<User> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        if (users == null) {
            // Trả về danh sách rỗng nếu kết quả null (dù hiếm xảy ra)
            return new ArrayList<>();
        }
        return userRepository.findAll();
    }
    @QueryMapping
    public List<Category> getCategoriesByUser(@Argument Long userId) throws EntityNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return user.getCategories();
    }
    @QueryMapping
    public List<Category> findCategoriesByUserId(@Argument Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getCategories();
    }
}
