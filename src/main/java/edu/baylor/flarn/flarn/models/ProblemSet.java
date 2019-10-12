package edu.baylor.flarn.flarn.models;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class ProblemSet {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  String title;

  String description;

  KnowledgeSource knowledgeSource;

  List<Question> question;

  Difficulty difficulty;

  List<Review> reviews;

  User moderator;
}
