package com.org.library_management.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserCreationResponse {
  private String username;

  private String userEmail;

  private String userAddress;

  private String userPhone;
}
