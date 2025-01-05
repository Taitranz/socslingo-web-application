package com.socslingo.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.socslingo.website.model.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}