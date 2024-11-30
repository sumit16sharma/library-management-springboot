package com.org.library_management.service.impl;

import com.org.library_management.dto.request.BookCreationRequest;
import com.org.library_management.dto.response.BookCreationResponse;
import com.org.library_management.enums.BookFilter;
import com.org.library_management.dto.response.BookFilterResponse;
import com.org.library_management.enums.Operator;
import com.org.library_management.model.Author;
import com.org.library_management.model.Book;
import com.org.library_management.model.User;
import com.org.library_management.repository.BookRepository;
import com.org.library_management.service.BookFilterFactory;
import com.org.library_management.service.BookFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
  @Autowired
  private BookRepository bookRepository;
  
  @Autowired
  private AuthorService authorService;

  @Autowired
  private BookFilterFactory bookFilterFactory;

  public BookCreationResponse addBook(BookCreationRequest request) {
    Author authorFromDb = authorService.findAuthorInDb(request.getAuthorEmail());

    Book book = request.toBook();

    if(authorFromDb == null) {
      authorFromDb = Author.builder()
        .id(UUID.randomUUID().toString())
        .email(request.getAuthorEmail())
        .name(request.getAuthorName())
        .build();
    }

    book.setAuthor(authorFromDb);
    Book savedBook = bookRepository.save(book);

    return BookCreationResponse.builder()
      .bookName(savedBook.getTitle())
      .bookNo(savedBook.getBookNo())
      .build();
  }

  public List<BookFilterResponse> filter(BookFilter filterBy, Operator operator, String value) {
    BookFilterStrategy bookFilterStrategy = bookFilterFactory.getStrategy(filterBy);
    return bookFilterStrategy.getFilteredBook(operator, value);
  }

  public Book checkIfBookValidWithBookNo(String bookNo) {
    List<Book> books = bookRepository.findByBookNo(bookNo);
    if(books.isEmpty()) return null;

    return books.get(0);
  }

  public void markBookUnavailable(Book bookFromDb, User userFromDb) {
    bookFromDb.setUser(userFromDb);
    bookRepository.save(bookFromDb);
  }

  public void markBookAvailable(Book bookFromDb) {
    bookFromDb.setUser(null);
    bookRepository.save(bookFromDb);
  }
}
