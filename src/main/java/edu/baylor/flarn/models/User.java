package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User extends org.springframework.security.core.userdetails.User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private UserType userType;

  @OneToMany
  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Set<ProblemSet> createdProblemSets = new HashSet<>();

  public User(UserType userType, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userType = userType;
  }

  public User(UserType userType, String username, String password) {
    super(username, password, new ArrayList<>());
    this.userType = userType;
  }




}
