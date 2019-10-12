package edu.baylor.flarn.flarn.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Session {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
}
