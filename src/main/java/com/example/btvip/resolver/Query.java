package com.example.btvip.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.btvip.model.Category;
import com.example.btvip.model.User;
import com.example.btvip.repository.CategoryRepository;
import com.example.btvip.repository.UserRepository;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
    public List<Category> findCategoriesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getCategories();
    }
}
