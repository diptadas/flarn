package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long> { }
