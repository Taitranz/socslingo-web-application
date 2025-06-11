package com.socslingo.website.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.socslingo.website.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
   
	User findByUsername(String username);
	
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
}
