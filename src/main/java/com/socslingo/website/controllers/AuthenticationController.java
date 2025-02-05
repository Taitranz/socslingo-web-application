package com.socslingo.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.socslingo.website.models.User;
import com.socslingo.website.services.UserService;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/settings")
    public String setting() {
        return "settings";
    }



    @PostMapping("/register")
    public String handleRegistration(@RequestParam String username, @RequestParam String email,
            @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.saveUser(user);
        return "redirect:/login";
    }
}