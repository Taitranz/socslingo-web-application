package com.socslingo.website.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.socslingo.website.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
}
