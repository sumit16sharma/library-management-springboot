package com.org.library_management.service.impl;

import com.org.library_management.dto.request.TxnRequest;
import com.org.library_management.dto.request.TxnReturnRequest;
import com.org.library_management.enums.TxnStatus;
import com.org.library_management.exception.BookException;
import com.org.library_management.exception.TxnException;
import com.org.library_management.exception.UserException;
import com.org.library_management.model.Book;
import com.org.library_management.model.Txn;
import com.org.library_management.model.User;
import com.org.library_management.repository.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {
  @Autowired
  private TxnRepository txnRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private BookService bookService;

  @Value("${user.valid.days}")
  private int validUpto;

  @Value("${user.delayed.finePerDay}")
  private int finePerDay;

  @Transactional(rollbackFor = { BookException.class, UserException.class }) // transactional rollback only on unchecked Exception otherwise i can mention when to rollback when custom exception comes
  public String createIssue(TxnRequest txnRequest) throws UserException, BookException {
    User userFromDb = userService.checkIfUserValidWithEmail(txnRequest.getUserEmail());

    if(userFromDb == null) {
      throw new UserException("User is not Valid");
    }

    Book bookFromDb = bookService.checkIfBookValidWithBookNo(txnRequest.getBookNo());

    if(bookFromDb == null) {
      throw new BookException("Book is not Valid");
    }

    if(bookFromDb.getUser() != null) {
      throw new BookException("Book is already assigned to another user");
    }

    return createTxn(userFromDb, bookFromDb);
  }

  @Transactional
  public String createTxn(User userFromDb, Book bookFromDb) throws BookException {
    // create txn
    String txnId = UUID.randomUUID().toString();

    Txn txn = Txn
      .builder()
      .txdId(txnId)
      .user(userFromDb)
      .book(bookFromDb)
      .txnStatus(TxnStatus.ISSUED)
      .issuedDate(new Date())
      .build();

    Txn savedTxn = txnRepository.save(txn);

    bookService.markBookUnavailable(bookFromDb, userFromDb);

    return savedTxn.getTxdId();
  }

  @Transactional(rollbackFor = { BookException.class, UserException.class })
  public Double returnTxn(TxnReturnRequest txnReturnRequest) throws UserException, BookException {
    User userFromDb = userService.checkIfUserValidWithEmail(txnReturnRequest.getUserEmail());

    if(userFromDb == null) {
      throw new UserException("User is not valid");
    }

    Book bookFromDb = bookService.checkIfBookValidWithBookNo(txnReturnRequest.getBookNo());

    if(bookFromDb == null) {
      throw new BookException("Book is not Valid");
    }

    if(bookFromDb.getUser() != null && bookFromDb.getUser().equals(userFromDb)) {
      Txn txnFromDb = txnRepository.findByTxdId(txnReturnRequest.getTxnId());

      if(txnFromDb == null) {
        throw new TxnException("No Txn has been found in my DB with this txnId");
      }

      Double amount = calculateSettlementAmount(txnFromDb, bookFromDb);

      if(amount == bookFromDb.getSecurityAmount()) {
        txnFromDb.setTxnStatus(TxnStatus.RETURNED);
      } else {
        txnFromDb.setTxnStatus(TxnStatus.FINED);
      }

      txnFromDb.setSettlementAmount(amount);

      // mark the book as available
      bookFromDb.setUser(null);
      txnRepository.save(txnFromDb);
      return amount;
    } else {
      throw new TxnException("Book is assigned to someone else, or not at all assigned");
    }
  }

  private Double calculateSettlementAmount(Txn txnFromDb, Book bookFromDb) {
    long issueTime = txnFromDb.getIssuedDate().getTime();
    long returnTime = System.currentTimeMillis();
    long diff = returnTime - issueTime;

    int daysPassed = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    if(daysPassed > validUpto) {
      int fineAmount = (daysPassed - validUpto) * finePerDay;
      return bookFromDb.getSecurityAmount() - fineAmount;
    }

    return bookFromDb.getSecurityAmount();
  }
}
