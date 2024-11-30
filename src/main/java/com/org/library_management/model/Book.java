package com.org.library_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.library_management.enums.BookType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Book extends TimeStamps {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 50)
  private String title;

  @Column(length = 20, unique = true)
  private String bookNo;

  @Enumerated
  private BookType bookType;

  @Column(nullable = false)
  private Double securityAmount;

  @ManyToOne
  @JoinColumn
  private User user;

  @ManyToOne(cascade = CascadeType.ALL) // cascade means to update/save the child without explicitly saving or updating it
  @JoinColumns({
    @JoinColumn(name = "author_id", referencedColumnName = "id"),
    @JoinColumn(name = "author_email", referencedColumnName = "email")
  })
  @JsonIgnoreProperties(value = "bookList") // to ignore bookList as it will form a infinite json data
  private Author author;

  @OneToMany(mappedBy = "book")
  private List<Txn> txnList;
}
