package com.org.library_management.repository;

import com.org.library_management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
  List<Book> findByBookNo(String bookNo);
}
