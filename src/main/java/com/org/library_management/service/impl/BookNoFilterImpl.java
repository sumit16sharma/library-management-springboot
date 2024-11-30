package com.org.library_management.service.impl;

import com.org.library_management.dto.response.BookFilterResponse;
import com.org.library_management.enums.Operator;
import com.org.library_management.model.Book;
import com.org.library_management.repository.BookRepository;
import com.org.library_management.service.BookFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookNoFilterImpl implements BookFilterStrategy {
  @Autowired
  private BookRepository bookRepository;

  @Override
  public List<BookFilterResponse> getFilteredBook(Operator operator, String value) {
    if(!operator.equals(Operator.EQUALS)) {
      throw new IllegalArgumentException("Only Equals is expected with book no");
    }

    List<Book> books = bookRepository.findByBookNo(value);

    return books
      .stream()
      .map(book -> convertToBookFilterResponse(book))
      .collect(Collectors.toList());
  }

  private BookFilterResponse convertToBookFilterResponse(Book book) {
    return BookFilterResponse
      .builder()
      .bookName(book.getTitle())
      .bookNo(book.getBookNo())
      .authorEmail(book.getAuthor().getEmail())
      .authorName(book.getAuthor().getName())
      .bookType(book.getBookType())
      .build();
  }
}
