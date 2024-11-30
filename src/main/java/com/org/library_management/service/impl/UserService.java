package com.org.library_management.service.impl;

import com.org.library_management.dto.request.UserCreationRequest;
import com.org.library_management.dto.response.UserCreationResponse;
import com.org.library_management.enums.Operator;
import com.org.library_management.enums.UserFilter;
import com.org.library_management.exception.UserAlreadyExistsException;
import com.org.library_management.model.User;
import com.org.library_management.enums.UserType;
import com.org.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public UserCreationResponse addStudent(UserCreationRequest request) {
    if (userRepository.existsByEmail(request.getUserEmail())) {
      throw new UserAlreadyExistsException("A user with this email already exists.");
    }

    if (userRepository.existsByPhoneNo(request.getUserPhone())) {
      throw new UserAlreadyExistsException("A user with this phone number already exists.");
    }

    User user = request.toUser();
    user.setUserType(UserType.STUDENT);

    User userFromDb = userRepository.save(user);

    return UserCreationResponse
      .builder()
      .username(userFromDb.getName())
      .userEmail(userFromDb.getEmail())
      .userAddress(userFromDb.getAddress())
      .userPhone(userFromDb.getPhoneNo())
      .build();
  }

  public List<User> filter(UserFilter filterBy, Operator operator, String value) {
    switch (filterBy) {
      case NAME:
        switch (operator) {
          case EQUALS:
            return userRepository.findByName(value);
          case LIKE:
            return userRepository.findByNameContains(value);
        }
    }

    return new ArrayList<>();
  }

  public User checkIfUserValidWithEmail(String userEmail) {
    return userRepository.findByEmail(userEmail);
  }
}
