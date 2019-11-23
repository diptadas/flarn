package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByDifficultyAndCategoryAndTitleContaining(@NotNull Difficulty difficulty, Category category, @NotNull String title);

    List<Problem> findByDifficultyAndTitleContaining(@NotNull Difficulty difficulty, @NotNull String title);

    List<Problem> findByCategoryAndTitleContaining(Category category, @NotNull String title);

    List<Problem> findByTitleContaining(@NotNull String title);

    void deleteByIdIn(List<Long> ids);

    @Query(nativeQuery = true, value = "SELECT * FROM Problem_Set as e WHERE e.id NOT IN (:ids) ORDER BY RAND() LIMIT 1")
    Problem findUnsolved(@Param("ids") List<Long> ids);

}
