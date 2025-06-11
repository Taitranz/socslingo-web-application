package com.socslingo.website.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socslingo.website.models.Flashcard;
import com.socslingo.website.models.User;
import com.socslingo.website.repositories.FlashcardRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing flashcards.
 * Provides business logic for creating, updating, and managing individual flashcards.
 */
@Service
public class FlashcardService {
    
    @Autowired
    private FlashcardRepository flashcardRepository;

    /**
     * Creates a new flashcard for the given user.
     */
    public Flashcard createFlashcard(String front, String back, User user) {
        Flashcard flashcard = new Flashcard();
        flashcard.setFront(front);
        flashcard.setBack(back);
        flashcard.setUser(user);
        return flashcardRepository.save(flashcard);
    }

    /**
     * Retrieves all flashcards for a specific user.
     */
    public List<Flashcard> getFlashcardsByUser(User user) {
        return flashcardRepository.findByUserOrderByCreatedAtDesc(user);
    }

    /**
     * Retrieves all flashcards in the system.
     */
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    /**
     * Retrieves a flashcard by its ID.
     */
    public Optional<Flashcard> getFlashcardById(Long id) {
        return flashcardRepository.findById(id);
    }

    /**
     * Updates an existing flashcard.
     */
    public Flashcard updateFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    /**
     * Deletes a flashcard by its ID.
     */
    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }

    /**
     * Searches for flashcards by content (front or back text).
     */
    public List<Flashcard> searchFlashcards(String searchTerm) {
        return flashcardRepository.findByFrontContainingIgnoreCaseOrBackContainingIgnoreCase(
            searchTerm, searchTerm);
    }
}
