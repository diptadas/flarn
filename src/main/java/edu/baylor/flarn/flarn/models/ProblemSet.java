package edu.baylor.flarn.flarn.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ProblemSet {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String title;

  private String description;

  @ManyToOne
  private Category category;

  @OneToMany
  private Question guestion;


  


}
