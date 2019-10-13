package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.KnowledgeSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeSourceRepository extends JpaRepository<KnowledgeSource, Long> {
}
