package edu.baylor.flarn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class ProblemSet {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  String title;

  @NotNull
  String description;

  @NotNull
  @OneToOne
  KnowledgeSource knowledgeSource;

  @OneToMany
  Set<Question> question = new HashSet<>();

  @NotNull
  Difficulty difficulty;

  @OneToMany
  Set<Review> reviews = new HashSet<>();

  @ManyToOne
//  @NotNull TODO: Add this later
  User moderator;
}
