package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserIdOrderByDateDesc(@NotNull Long userId);
}
