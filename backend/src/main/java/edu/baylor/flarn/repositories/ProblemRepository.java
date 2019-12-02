package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * {@link ProblemRepository} class defines the DB operations for {@link Problem} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByArchivedFalse();

    Optional<Problem> findByIdAndArchivedFalse(Long id);

    List<Problem> findByDifficultyAndCategoryAndTitleContainingIgnoreCaseAndArchivedFalse(@NotNull Difficulty difficulty, Category category, @NotNull String title);

    List<Problem> findByDifficultyAndTitleContainingIgnoreCaseAndArchivedFalse(@NotNull Difficulty difficulty, @NotNull String title);

    List<Problem> findByCategoryAndTitleContainingIgnoreCaseAndArchivedFalse(Category category, @NotNull String title);

    List<Problem> findByTitleContainingIgnoreCaseAndArchivedFalse(@NotNull String title);

    @Query(nativeQuery = true, value = "SELECT * FROM Problem as e WHERE e.archived = FALSE AND e.id NOT IN (:ids) ORDER BY RANDOM() LIMIT 1")
    Problem findUnsolvedRandom(@Param("ids") List<Long> ids);

    @Query(nativeQuery = true, value = "SELECT * FROM Problem as e WHERE e.archived = FALSE ORDER BY RANDOM() LIMIT 1")
    Problem findRandom();

    List<Problem> findAllByModeratorId(@NotNull Long id);
}
