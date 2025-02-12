package com.socslingo.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthenticationBetaController {
    

    @GetMapping("/login-beta")
    public String loginBeta() {
        return "authorization/loginBETA";
    }

    @GetMapping("/register-beta")
    public String registerBeta() {
        return "authorization/registerBETA";
    }


    @GetMapping("/landing-page")
    public String landingPage() {
        return "authorization/landing-page";
    }
}
