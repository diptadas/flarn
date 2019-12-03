package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link QuestionRepository} class defines the DB operations for {@link Question} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
