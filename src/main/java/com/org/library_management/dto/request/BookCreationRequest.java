package com.org.library_management.dto.request;

import com.org.library_management.enums.BookType;
import com.org.library_management.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCreationRequest {
  @NotBlank(message = "Book Title must not be Blank")
  private String bookTitle;

  @Positive(message = "Positive values are expected")
  private Double securityNumber;

  @NotBlank(message = "Book Number must not be blank")
  private String bookNo;

  @NotNull(message = "Book Type must not be null")
  private BookType bookType;

  @NotBlank(message = "Author name must not be blank")
  private String authorName;

  @NotBlank(message = "Author Email must not be blank")
  private String authorEmail;

  public Book toBook() {
    return Book.builder()
      .title(this.bookTitle)
      .securityAmount(this.securityNumber)
      .bookNo(this.bookNo)
      .bookType(this.bookType)
      .build();
  }
}
