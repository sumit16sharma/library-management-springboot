package com.org.library_management.repository;

import com.org.library_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByEmail(String email);

  boolean existsByPhoneNo(String phoneNo);

  User findByEmail(String email);

  List<User> findByName(String name);

  List<User> findByNameContains(String name);
}
