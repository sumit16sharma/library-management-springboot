package com.org.library_management.dto.response;

import com.org.library_management.enums.BookType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookFilterResponse {
  private String bookNo;

  private String bookName;

  private BookType bookType;

  private String authorName;

  private String authorEmail;
}
