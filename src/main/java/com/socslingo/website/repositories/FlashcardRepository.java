package com.socslingo.website.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socslingo.website.models.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}