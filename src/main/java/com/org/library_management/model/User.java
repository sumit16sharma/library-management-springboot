package com.org.library_management.model;

import com.org.library_management.enums.UserStatus;
import com.org.library_management.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class User extends TimeStamps{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 30)
  private String name;

  @Column(unique = true, length = 15)
  private String phoneNo;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  private String address;

  @Enumerated
  private UserStatus userStatus;

  @Enumerated
  private UserType userType;

  @Transient
  private String temp;

  @OneToMany(mappedBy = "user")
  private List<Book> bookList;

  @OneToMany(mappedBy = "user")
  private List<Txn> txnList;
}
