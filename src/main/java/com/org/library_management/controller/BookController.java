package com.org.library_management.controller;

import com.org.library_management.dto.request.BookCreationRequest;
import com.org.library_management.dto.response.BookCreationResponse;
import com.org.library_management.enums.BookFilter;
import com.org.library_management.dto.response.BookFilterResponse;
import com.org.library_management.enums.Operator;
import com.org.library_management.service.impl.BookService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {
  @Autowired
  private BookService bookService;

  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  @PostMapping("/addBook")
  public BookCreationResponse addStudent(@RequestBody @Validated BookCreationRequest request) {
//    LOGGER.info("");
    return bookService.addBook(request);
  }

  @GetMapping("/filter")
  public List<BookFilterResponse> filterBook(@NotNull(message = "filterby must not be null") @RequestParam("filterBy") BookFilter filterBy,
                                             @NotNull(message = "operator must not be null") @RequestParam("operator") Operator operator,
                                             @NotBlank(message = "value must not be blank") @RequestParam("value") String value
  ) {
    return bookService.filter(filterBy, operator, value);
  }
}
