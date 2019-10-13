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
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String content;

  @OneToMany
  //    @JsonIdentityInfo(
  //      generator = ObjectIdGenerators.PropertyGenerator.class,
  //      property = "id")
  //    @JsonIdentityReference(alwaysAsId=true)
  private List<Option> options = new ArrayList<>();

  private int answer;

  @ManyToOne
  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private ProblemSet problemSet;
}