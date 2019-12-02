package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * {@link ActivityRepository} class defines the DB operations for {@link Activity} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserIdOrderByDateDesc(@NotNull Long userId);

    List<Activity> findByUserIdInOrderByDateDesc(@NotNull List<Long> userIds);
}
