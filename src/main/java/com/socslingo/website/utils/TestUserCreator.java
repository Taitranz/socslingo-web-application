package com.socslingo.website.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.socslingo.website.models.User;
import com.socslingo.website.services.UserService;

@Component
public class TestUserCreator implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Check if test user already exists
        if (!userService.userExists("test@example.com")) {
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@example.com");
            testUser.setPassword("password123"); // This will be hashed by UserService
            
            userService.registerUser(testUser);
            System.out.println("Test user created:");
            System.out.println("Email: test@example.com");
            System.out.println("Username: testuser");
            System.out.println("Password: password123");
        } else {
            System.out.println("Test user already exists:");
            System.out.println("Email: test@example.com");
            System.out.println("Username: testuser");
            System.out.println("Password: password123");
        }
    }
}
