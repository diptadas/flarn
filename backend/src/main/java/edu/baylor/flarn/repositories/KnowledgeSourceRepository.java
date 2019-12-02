package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.KnowledgeSource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link KnowledgeSourceRepository} class defines the DB operations for {@link KnowledgeSource} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface KnowledgeSourceRepository extends JpaRepository<KnowledgeSource, Long> {
}
