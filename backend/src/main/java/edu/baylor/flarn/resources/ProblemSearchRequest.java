package edu.baylor.flarn.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link ProblemSearchRequest} class defines different searching criteria for the {@link edu.baylor.flarn.models.Problem} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemSearchRequest {
    private String title; // substring matching

    private String category;

    private String difficulty;
}

