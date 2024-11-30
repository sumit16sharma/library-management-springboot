package com.org.library_management.service;

import com.org.library_management.enums.BookFilter;
import com.org.library_management.service.impl.BookNoFilterImpl;
import com.org.library_management.service.impl.BookTitleFilterImpl;
import com.org.library_management.service.impl.BookTypeFilterImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookFilterFactory {
  private final Map<BookFilter, BookFilterStrategy> stratergies = new HashMap<>();

  public BookFilterFactory(BookNoFilterImpl bookNoFilter, BookTitleFilterImpl bookTitleFilter, BookTypeFilterImpl bookTypeFilter) {
    stratergies.put(BookFilter.BOOK_NO, bookNoFilter);
    stratergies.put(BookFilter.BOOK_TITLE, bookTitleFilter);
    stratergies.put(BookFilter.BOOK_TYPE, bookTypeFilter);
  }

  public BookFilterStrategy getStrategy(BookFilter filter) {
    return stratergies.get(filter);
  }
}
