package com.org.library_management.exception;

import com.org.library_management.model.Txn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>("Invalid value for filterBy or operator: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
  }

  @ExceptionHandler(value = TxnException.class)
  public ResponseEntity<Object> handleTxnException(TxnException txnException) {
    return new ResponseEntity<>(txnException.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = BookException.class)
  public ResponseEntity<Object> handleBookException(BookException bookException) {
    return new ResponseEntity<>(bookException.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = UserException.class)
  public ResponseEntity<Object> handleUserException(UserException userException) {
    return new ResponseEntity<>(userException.getMessage(), HttpStatus.BAD_REQUEST);
  }
}