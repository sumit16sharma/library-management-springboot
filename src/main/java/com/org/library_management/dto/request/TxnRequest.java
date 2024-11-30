package com.org.library_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TxnRequest {
  @NotBlank(message = "User Email must not be Blank")
  @Email
  private String userEmail;

  @NotBlank(message = "bookNo must not be Blank")
  private String bookNo;
}
