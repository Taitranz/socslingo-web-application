package com.socslingo.website.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.socslingo.website.models.User;
import com.socslingo.website.repositories.UserRepository;
import java.util.Optional;


@Service
public class UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public void registerUser(User user) {
        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByEmailOrUsername(String identifier) {
        // Try to find by email first
        Optional<User> user = userRepository.findByEmail(identifier);
        if (user.isPresent()) {
            return user;
        }
        // If not found by email, try username
        return userRepository.findByUsername(identifier);
    }
    
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
