package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByProblemIdAndUserId(@NotNull  Long problemId, @NotNull Long userId);
}
