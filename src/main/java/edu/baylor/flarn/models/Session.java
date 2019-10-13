package edu.baylor.flarn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Session {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotNull
  Date dateStarted;

  @NotNull
  Date dateSubmitted;

  @OneToMany
  List<SessionAnswer> answers = new ArrayList<>();

  @ManyToOne
  User user;

  @ManyToOne
  ProblemSet problemSet;

}
