package edu.baylor.flarn.flarn.models;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ProblemSet {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotNull
  String title;

  @NotNull
  String description;

  @NotNull
  KnowledgeSource knowledgeSource;

  List<Question> question = new ArrayList<>();

  @NotNull
  Difficulty difficulty;

  @OneToMany
  List<Review> reviews = new ArrayList<>();

  @ManyToOne
  User moderator;
}
