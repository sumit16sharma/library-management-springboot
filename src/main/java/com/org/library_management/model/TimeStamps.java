package com.org.library_management.model;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@MappedSuperclass
public class TimeStamps {
  @CreationTimestamp
  protected Date createdOn;

  @UpdateTimestamp
  protected Date updatedOn;
}
