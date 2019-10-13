package edu.baylor.flarn.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Option {
  String content;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Option(String content) {
    this.content = content;
  }
}
