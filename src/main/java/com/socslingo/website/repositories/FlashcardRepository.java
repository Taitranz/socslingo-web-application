package com.socslingo.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.socslingo.website.models.Flashcard;
import com.socslingo.website.models.User;
import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    
    List<Flashcard> findByUser(User user);
    
    List<Flashcard> findByUserOrderByCreatedAtDesc(User user);
    
    List<Flashcard> findByFrontContainingIgnoreCaseOrBackContainingIgnoreCase(String front, String back);
}