package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.KnowledgeSource;
import edu.baylor.flarn.models.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeSourceRepository extends JpaRepository<KnowledgeSource, Long> { }
