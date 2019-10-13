package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class ProblemSet {
  @NotNull
  String title;
  @NotNull
  String description;
  @NotNull
  @OneToOne
  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  KnowledgeSource knowledgeSource;
  @OneToMany
  //  @JsonIdentityInfo(
    //    generator = ObjectIdGenerators.PropertyGenerator.class,
    //    property = "id")
    //  @JsonIdentityReference(alwaysAsId=true)
    Set<Question> question = new HashSet<>();
  @NotNull
  Difficulty difficulty;
  @OneToMany
  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  Set<Review> reviews = new HashSet<>();
  @ManyToOne
  //  @NotNull TODO: Add this later
  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  User moderator;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
}
