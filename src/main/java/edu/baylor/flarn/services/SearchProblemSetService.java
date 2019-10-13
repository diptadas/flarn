package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.ProblemSetSearchRequest;
import edu.baylor.flarn.repositories.CategoryRepository;
import edu.baylor.flarn.repositories.ProblemSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchProblemSetService {
  private final ProblemSetRepository problemSetRepository;
  private final CategoryRepository categoryRepository;

  public SearchProblemSetService(ProblemSetRepository problemSetRepository, CategoryRepository categoryRepository) {
    this.problemSetRepository = problemSetRepository;
    this.categoryRepository = categoryRepository;
  }

  public List<ProblemSet> searchProblemSet(ProblemSetSearchRequest problemSetSearchRequest) {
    // get category
    Category category = null;
    if (problemSetSearchRequest.getCategory() != null) {
      category = categoryRepository.findByName(problemSetSearchRequest.getCategory());
    }

    // get difficulty
    Difficulty difficulty = null;
    if (problemSetSearchRequest.getDifficulty() != null) {
      try {
        difficulty = Difficulty.valueOf(problemSetSearchRequest.getDifficulty());
      } catch (IllegalArgumentException e) { // unknown difficulty
        log.error(e.toString());
        return new ArrayList<>();
      }
    }

    String title = problemSetSearchRequest.getTitle();
    if (title == null) {
      title = ""; // will match with every one
    }

    if (difficulty != null && category != null) {
      return problemSetRepository.findByDifficultyAndCategoryAndTitleContaining(difficulty, category, title);
    } else if (difficulty != null) {
      return problemSetRepository.findByDifficultyAndTitleContaining(difficulty, title);
    } else if (category != null) {
      return problemSetRepository.findByCategoryAndTitleContaining(category, title);
    } else {
      return problemSetRepository.findByTitleContaining(title);
    }
  }
}
