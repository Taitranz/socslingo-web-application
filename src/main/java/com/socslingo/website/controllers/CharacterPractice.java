package com.socslingo.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/character-practice")
public class CharacterPractice {
    
    @GetMapping("/demo")
    public String characterPractice() {
        return "curated-content/character-practice";
    }   


}
