package com.org.library_management.controller;

import com.org.library_management.dto.request.TxnRequest;
import com.org.library_management.dto.request.TxnReturnRequest;
import com.org.library_management.exception.BookException;
import com.org.library_management.exception.UserException;
import com.org.library_management.service.impl.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {
  @Autowired
  private TxnService txnService;

  @PostMapping("/issue")
  public ResponseEntity<String> createIssue(@Valid @RequestBody TxnRequest txnRequest) throws UserException, BookException {
    String txnId = txnService.createIssue(txnRequest);

    if(txnId != null && !txnId.isEmpty()) {
      return new ResponseEntity<>(txnId, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/return")
  public Double returnTxn(@RequestBody TxnReturnRequest txnReturnRequest) throws UserException, BookException {
    return txnService.returnTxn(txnReturnRequest);
  }
}
