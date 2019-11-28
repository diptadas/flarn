package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.repositories.CategoryRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
import edu.baylor.flarn.resources.ProblemSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchProblemService {
    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;

    public SearchProblemService(ProblemRepository problemRepository, CategoryRepository categoryRepository) {
        this.problemRepository = problemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Problem> searchProblem(ProblemSearchRequest problemSearchRequest) {
        // get category
        Category category = null;
        if (problemSearchRequest.getCategory() != null) {
            category = categoryRepository.findByName(problemSearchRequest.getCategory());
        }

        // get difficulty
        Difficulty difficulty = null;
        if (problemSearchRequest.getDifficulty() != null) {
            try {
                difficulty = Difficulty.valueOf(problemSearchRequest.getDifficulty());
            } catch (IllegalArgumentException e) { // unknown difficulty
                log.error(e.toString());
                return new ArrayList<>();
            }
        }

        String title = problemSearchRequest.getTitle();
        if (title == null) {
            title = ""; // will match with every one
        }

        if (difficulty != null && category != null) {
            return problemRepository.findByDifficultyAndCategoryAndTitleContainingIgnoreCaseAndArchivedFalse(difficulty, category, title);
        } else if (difficulty != null) {
            return problemRepository.findByDifficultyAndTitleContainingIgnoreCaseAndArchivedFalse(difficulty, title);
        } else if (category != null) {
            return problemRepository.findByCategoryAndTitleContainingIgnoreCaseAndArchivedFalse(category, title);
        } else {
            return problemRepository.findByTitleContainingIgnoreCaseAndArchivedFalse(title);
        }
    }
}
