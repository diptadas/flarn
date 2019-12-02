package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

/**
 * {@link SessionRepository} class defines the DB operations for {@link Session} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByProblemIdAndUserId(@NotNull Long problemId, @NotNull Long userId);
}
