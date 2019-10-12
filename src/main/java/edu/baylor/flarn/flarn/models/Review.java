package edu.baylor.flarn.flarn.models;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

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

  @OneToOne
  User user;

  @ManyToOne
  ReviewType reviewType;
}
