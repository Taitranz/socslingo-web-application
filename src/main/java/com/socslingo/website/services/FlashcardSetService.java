package com.socslingo.website.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socslingo.website.models.FlashcardSet;
import com.socslingo.website.models.User;
import com.socslingo.website.models.Flashcard;
import com.socslingo.website.repositories.FlashcardSetRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing flashcard sets.
 * Provides business logic for creating, updating, and managing flashcard sets.
 */
@Service
public class FlashcardSetService {
    
    @Autowired
    private FlashcardSetRepository flashcardSetRepository;

    /**
     * Creates a new flashcard set for the given user.
     */
    public FlashcardSet createFlashcardSet(String name, String description, User user) {
        FlashcardSet flashcardSet = new FlashcardSet(name, description, user);
        return flashcardSetRepository.save(flashcardSet);
    }

    /**
     * Retrieves all flashcard sets for a specific user.
     */
    public List<FlashcardSet> getFlashcardSetsByUser(User user) {
        return flashcardSetRepository.findByUserOrderByCreatedAtDesc(user);
    }

    /**
     * Retrieves a flashcard set by its ID.
     */
    public Optional<FlashcardSet> getFlashcardSetById(Long id) {
        return flashcardSetRepository.findById(id);
    }

    /**
     * Updates an existing flashcard set.
     */
    public FlashcardSet updateFlashcardSet(FlashcardSet flashcardSet) {
        return flashcardSetRepository.save(flashcardSet);
    }

    /**
     * Deletes a flashcard set by its ID.
     */
    public void deleteFlashcardSet(Long id) {
        flashcardSetRepository.deleteById(id);
    }

    /**
     * Adds a flashcard to a flashcard set.
     */
    public void addFlashcardToSet(Long setId, Flashcard flashcard) {
        Optional<FlashcardSet> optionalSet = flashcardSetRepository.findById(setId);
        if (optionalSet.isPresent()) {
            FlashcardSet flashcardSet = optionalSet.get();
            flashcardSet.addFlashcard(flashcard);
            flashcardSetRepository.save(flashcardSet);
        }
    }

    /**
     * Removes a flashcard from a flashcard set.
     */
    public void removeFlashcardFromSet(Long setId, Flashcard flashcard) {
        Optional<FlashcardSet> optionalSet = flashcardSetRepository.findById(setId);
        if (optionalSet.isPresent()) {
            FlashcardSet flashcardSet = optionalSet.get();
            flashcardSet.removeFlashcard(flashcard);
            flashcardSetRepository.save(flashcardSet);
        }
    }

    /**
     * Searches for flashcard sets by name (case-insensitive).
     */
    public List<FlashcardSet> searchFlashcardSetsByName(String name) {
        return flashcardSetRepository.findByNameContainingIgnoreCase(name);
    }
}
