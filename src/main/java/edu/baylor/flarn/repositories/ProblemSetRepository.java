package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long> {

    public List<ProblemSet> findByDifficultyAndCategoryAndTitleLike(@NotNull Difficulty difficulty, Category category, @NotNull String title);

    public List<ProblemSet> findByDifficultyAndTitleLike(@NotNull Difficulty difficulty, @NotNull String title);

    public List<ProblemSet> findByCategoryAndTitleLike(Category category, @NotNull String title);

    public List<ProblemSet> findByTitleLike(@NotNull String title);
}
