package edu.baylor.flarn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Option {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  String content;
}
