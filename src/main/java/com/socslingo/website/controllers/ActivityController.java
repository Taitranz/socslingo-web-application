package com.socslingo.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ActivityController {
    
    @GetMapping("/activity/click-on-correct")
    public String clickOnCorrect() {
        return "activity/click-on-correct";
    }
    
    @GetMapping("/activity/select-pairs")
    public String selectPairs() {
        return "activity/select-pairs";
    }
}
