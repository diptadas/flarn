package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.resources.ProblemSearchRequest;
import edu.baylor.flarn.repositories.CategoryRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
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

    public List<Problem> searchproblem(ProblemSearchRequest problemSearchRequest) {
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
            return problemRepository.findByDifficultyAndCategoryAndTitleContaining(difficulty, category, title);
        } else if (difficulty != null) {
            return problemRepository.findByDifficultyAndTitleContaining(difficulty, title);
        } else if (category != null) {
            return problemRepository.findByCategoryAndTitleContaining(category, title);
        } else {
            return problemRepository.findByTitleContaining(title);
        }
    }
}
