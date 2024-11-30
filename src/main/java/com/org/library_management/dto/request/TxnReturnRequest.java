package com.org.library_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnReturnRequest {
  @NotBlank
  private String bookNo;

  @NotBlank
  private String txnId;

  @NotBlank
  private String userEmail;
}
