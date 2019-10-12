package edu.baylor.flarn.flarn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class KnowledgeSource {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  String contentLink;

  @OneToOne
  ProblemSet problemSet;

}
