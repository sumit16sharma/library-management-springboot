package com.org.library_management.model;

import com.org.library_management.enums.TxnStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Txn extends TimeStamps{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String txdId;

  @Enumerated
  private TxnStatus txnStatus;

  private Double settlementAmount; // depending upon when u are returning the book

  private Date issuedDate;

  private Date submittedDate;

  @ManyToOne
  @JoinColumn
  private User user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn
  private Book book;
}
