package com.socslingo.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.socslingo.website.model.Flashcard;
import com.socslingo.website.repository.FlashcardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/flashcard")
public class FlashcardController {

    private static final Logger logger = LoggerFactory.getLogger(FlashcardController.class);

    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping("/create")
    public String showCreateForm() {
        return "create";
    }

    @PostMapping("/save")
    public String saveFlashcard(@RequestParam String front, @RequestParam String back) {
        logger.info("Received flashcard with front: {} and back: {}", front, back);
        Flashcard flashcard = new Flashcard();
        flashcard.setFront(front);
        flashcard.setBack(back);
        flashcardRepository.save(flashcard);
        logger.info("Flashcard saved with id: {}", flashcard.getId());
        return "redirect:/flashcard/create";
    }

}