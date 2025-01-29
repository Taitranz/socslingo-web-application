package com.socslingo.website.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user-content")
public class UserCreatedContentController {

    @GetMapping("/dashboard")
    public String userCreated() {
        return "user-create/user-content-dashboard";
    }

    @GetMapping("/create-deck")
    public String createDeck() {
        return "user-create/create-deck";
    }


}
