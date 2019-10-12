package edu.baylor.flarn.flarn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ProblemSet {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  String title;


}
