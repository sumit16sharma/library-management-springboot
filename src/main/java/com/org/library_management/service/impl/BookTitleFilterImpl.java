package com.org.library_management.service.impl;

import com.org.library_management.dto.response.BookFilterResponse;
import com.org.library_management.enums.Operator;
import com.org.library_management.service.BookFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookTitleFilterImpl implements BookFilterStrategy {
  @Override
  public List<BookFilterResponse> getFilteredBook(Operator operator, String value) {
    return List.of();
  }
}
