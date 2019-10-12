package edu.baylor.flarn.flarn.models;

import lombok.Data;
import org.springframework.security.core.userdetails.User;


import javax.persistence.*;
import java.util.List;

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


  

  String description;

  KnowledgeSource knowledgeSource;

  List<Question> question;

  Difficulty difficulty;

  List<Review> reviews;

  User moderator;
}
