package com.org.library_management.service;

import com.org.library_management.dto.response.BookFilterResponse;
import com.org.library_management.enums.Operator;

import java.util.List;

public interface BookFilterStrategy {
  List<BookFilterResponse> getFilteredBook(Operator operator, String value);
}
