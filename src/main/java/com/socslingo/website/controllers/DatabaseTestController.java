package com.socslingo.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.socslingo.website.models.Flashcard;
import com.socslingo.website.services.FlashcardService;
import java.util.List;

/**
 * Controller for testing database functionality.
 * Provides endpoints to verify that the SQLite database is working correctly.
 */
@Controller
public class DatabaseTestController {
    
    @Autowired
    private FlashcardService flashcardService;

    /**
     * Test endpoint to verify database connectivity and data retrieval.
     * Returns JSON list of all flashcards in the database.
     */
    @GetMapping("/test/database")
    @ResponseBody
    public String testDatabaseConnection() {
        try {
            List<Flashcard> flashcards = flashcardService.getAllFlashcards();
            return String.format("Database test successful! Found %d flashcards in the database. " +
                    "Sample flashcard: '%s' -> '%s'", 
                    flashcards.size(),
                    flashcards.isEmpty() ? "none" : flashcards.get(0).getFront(),
                    flashcards.isEmpty() ? "none" : flashcards.get(0).getBack());
        } catch (Exception exception) {
            return "Database test failed: " + exception.getMessage();
        }
    }

    /**
     * Test endpoint to verify Hiragana flashcards were loaded correctly.
     * Returns count of flashcards and shows first few examples.
     */
    @GetMapping("/test/hiragana")
    @ResponseBody
    public String testHiraganaFlashcards() {
        try {
            List<Flashcard> flashcards = flashcardService.getAllFlashcards();
            StringBuilder response = new StringBuilder();
            response.append("Hiragana test results:\n");
            response.append("Total flashcards: ").append(flashcards.size()).append("\n");
            
            if (!flashcards.isEmpty()) {
                response.append("First 5 flashcards:\n");
                for (int i = 0; i < Math.min(5, flashcards.size()); i++) {
                    Flashcard card = flashcards.get(i);
                    response.append(String.format("%d. %s -> %s\n", 
                        i + 1, card.getFront(), card.getBack()));
                }
            }
            
            return response.toString();
        } catch (Exception exception) {
            return "Hiragana test failed: " + exception.getMessage();
        }
    }
}
