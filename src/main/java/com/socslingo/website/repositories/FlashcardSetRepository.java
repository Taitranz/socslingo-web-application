package com.socslingo.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.socslingo.website.models.FlashcardSet;
import com.socslingo.website.models.User;
import java.util.List;

@Repository
public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, Long> {
    
    List<FlashcardSet> findByUser(User user);
    
    List<FlashcardSet> findByUserOrderByCreatedAtDesc(User user);
    
    List<FlashcardSet> findByNameContainingIgnoreCase(String name);
}
