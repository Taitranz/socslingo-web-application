package com.socslingo.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.socslingo.website.models.User;
import com.socslingo.website.services.UserService;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "authentication/register";
    }    @PostMapping("/register")
    public String processRegistration(@RequestParam(required = false) String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {
        
        // Always add form values back to model for retention
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        // Note: Don't retain passwords for security reasons
          // Password confirmation check
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("passwordError", true);
            return "authentication/register";
        }
        
        // Basic validation
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("error", "Email is required");
            model.addAttribute("emailError", true);
            return "authentication/register";
        }
        
        if (password == null || password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters");
            model.addAttribute("passwordError", true);
            return "authentication/register";
        }
          // Check for duplicate email
        if (userService.userExists(email.trim())) {
            model.addAttribute("error", "Email already registered");
            model.addAttribute("emailError", true);
            return "authentication/register";
        }
        
        // Check for duplicate username (if provided)
        if (username != null && !username.trim().isEmpty() && userService.usernameExists(username.trim())) {
            model.addAttribute("error", "Username already taken");
            model.addAttribute("usernameError", true);
            return "authentication/register";
        }
        
        try {
            // Create user object
            User user = new User();
            user.setUsername(username != null && !username.trim().isEmpty() ? username.trim() : null);
            user.setEmail(email.trim());
            user.setPassword(password);
            
            // Register user (make sure userService.registerUser() method exists)
            userService.registerUser(user);
            
            // Success - redirect to login
            return "redirect:/login?registered=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "authentication/register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }
}