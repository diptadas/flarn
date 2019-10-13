package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long> {

    public List<ProblemSet> findByDifficultyAndCategoryAndTitleContaining(@NotNull Difficulty difficulty, Category category, @NotNull String title);

    public List<ProblemSet> findByDifficultyAndTitleContaining(@NotNull Difficulty difficulty, @NotNull String title);

    public List<ProblemSet> findByCategoryAndTitleContaining(Category category, @NotNull String title);

    public List<ProblemSet> findByTitleContaining(@NotNull String title);
}
