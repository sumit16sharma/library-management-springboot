package com.org.library_management.dto.request;

import com.org.library_management.model.User;
import com.org.library_management.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreationRequest {
  private String userName;

  @NotBlank(message = "User Email must not be blanked")
  @Email(message = "Invalid email format")
  private String userEmail;

  private String userAddress;

  private String userPhone;

  public User toUser() {
    return User.
      builder().
      name(this.userName)
      .email(this.userEmail)
      .phoneNo(this.userPhone)
      .address(this.userAddress)
      .userStatus(UserStatus.ACTIVE)
      .build();
  }
}
