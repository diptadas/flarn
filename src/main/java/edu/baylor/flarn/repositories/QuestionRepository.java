package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> { }
