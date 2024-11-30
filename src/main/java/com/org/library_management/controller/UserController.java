package com.org.library_management.controller;

import com.org.library_management.dto.request.UserCreationRequest;
import com.org.library_management.dto.response.UserCreationResponse;
import com.org.library_management.enums.Operator;
import com.org.library_management.enums.UserFilter;
import com.org.library_management.model.User;
import com.org.library_management.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/addStudent")
  public UserCreationResponse addStudent(@RequestBody @Validated UserCreationRequest request) {
    return userService.addStudent(request);
  }

  @GetMapping("/filter")
  public List<User> filteredByPhone(@RequestParam("filterBy") UserFilter filterBy,
                                    @RequestParam("operator") Operator operator,
                                    @RequestParam("value") String value
                                                  ) {
    return userService.filter(filterBy, operator, value);
  }
}
