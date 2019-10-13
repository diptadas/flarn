package edu.baylor.flarn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
public class Review {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  ReviewType reviewType;

  String commentContent;

  @OneToOne
  User user;
}
