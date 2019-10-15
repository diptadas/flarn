package edu.baylor.flarn.resources;

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
