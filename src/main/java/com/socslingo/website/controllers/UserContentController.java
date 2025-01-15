// package com.socslingo.website.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.*;

// import com.socslingo.website.models.Flashcard;
// import com.socslingo.website.repositories.FlashcardRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// @Controller
// @RequestMapping("/user-content1")
// public class UserContentController {

//     private static final Logger logger = LoggerFactory.getLogger(UserContentController.class);

//     @Autowired
//     private FlashcardRepository flashcardRepository;

//     @GetMapping("/create-flashcard")
//     public String showCreateFlashcardForm() {
//         return "create";
//     }

//     @PostMapping("/save-flashcard")
//     public String saveFlashcard(@RequestParam String front, @RequestParam String back) {
//         logger.info("Received flashcard with front: {} and back: {}", front, back);
//         Flashcard flashcard = new Flashcard();
//         flashcard.setFront(front);
//         flashcard.setBack(back);
//         flashcardRepository.save(flashcard);
//         logger.info("Flashcard saved with id: {}", flashcard.getId());
//         return "redirect:/user-content/create-flashcard";
//     }

// }
