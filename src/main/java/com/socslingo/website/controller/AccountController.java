package com.socslingo.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

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
    public String handleRegistration(@RequestParam String username, @RequestParam String password) {
        return "redirect:/login";
    }
}