
package com.socslingo.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import com.socslingo.website.models.User;
import com.socslingo.website.services.UserService;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "authentication/register";
    }

    @GetMapping("/settings")
    public String setting() {
        return "settings";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute User user,
            BindingResult result,
            @RequestParam String confirmPassword,
            Model model) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            return "authentication/register";
        }
        
        // Password confirmation check
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "authentication/register";
        }
        
        // Check if email already exists
        if (userService.userExists(user.getEmail())) {
            model.addAttribute("emailError", "Email already registered");
            return "authentication/register";
        }
        
        // Check if username already exists (if provided)
        if (user.getUsername() != null && !user.getUsername().trim().isEmpty() 
            && userService.usernameExists(user.getUsername())) {
            model.addAttribute("usernameError", "Username already taken");
            return "authentication/register";
        }
        
        try {
            userService.registerUser(user);
            return "redirect:/login?registrationSuccess";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "authentication/register";
        }
    }
}