package com.example.btvip.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.btvip.model.Category;
import com.example.btvip.model.User;
import com.example.btvip.repository.CategoryRepository;
import com.example.btvip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResolver implements GraphQLResolver<User> {
    @Autowired
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    public UserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<Category> getCategories(User user) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getUsers().contains(user))
                .toList();
    }
}
