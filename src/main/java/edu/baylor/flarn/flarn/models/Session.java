package edu.baylor.flarn.flarn.models;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

  @NotNull
  List<Integer> answers;

  @ManyToOne
  User user;

  @ManyToOne
  ProblemSet problemSet;

}
