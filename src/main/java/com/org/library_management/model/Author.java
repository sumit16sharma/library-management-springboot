package com.org.library_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@IdClass(AuthorCompositeKey.class)
public class Author extends TimeStamps{
  @Id
  private String id;

  @Id
  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(length = 30)
  private String name;

  @OneToMany(mappedBy = "author")
  @JsonIgnore // that property will not appear in the JSON output when the object is serialized
  private List<Book> bookList;
}
