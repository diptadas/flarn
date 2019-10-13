package edu.baylor.flarn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemSetSearchRequest {
  private String title; // substring matching

  private String category;

  private String difficulty;
}
