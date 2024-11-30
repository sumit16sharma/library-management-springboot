package com.org.library_management.repository;

import com.org.library_management.model.Author;
import com.org.library_management.model.AuthorCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, AuthorCompositeKey> {
  Author findByEmail(String email);
}
