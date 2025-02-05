package com.socslingo.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/profile")
public class UserController {
    
    @GetMapping("")
    public String user() {
        return "user/profile";
    }


    
}
