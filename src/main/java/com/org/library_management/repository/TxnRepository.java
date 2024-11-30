package com.org.library_management.repository;

import com.org.library_management.model.Book;
import com.org.library_management.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {
  Txn findByTxdId(String txnId);
}
