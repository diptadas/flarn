package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.KnowledgeSource;
import edu.baylor.flarn.models.Option;
import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Profile("!prod")
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
  private final EntityManager entityManager;

  public DataInitializer(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    System.out.println("Initializing data");

    // creating some category
    List<Category> categories = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Category category = new Category();
      category.setName("category-" + i);
      entityManager.persist(category);

      categories.add(category);
    }

    // adding problem sets
    for (int i = 0; i < 10; i++) {
      ProblemSet problemSet = new ProblemSet();
      problemSet.setDifficulty(Difficulty.EASY);
      problemSet.setCategory(categories.get(i % 3)); // add category in cyclic order
      // TODO: add moderator

      // add knowledge source
      KnowledgeSource knowledgeSource = new KnowledgeSource();
      knowledgeSource.setContentLink("http://google.com");
      entityManager.persist(knowledgeSource);

      problemSet.setKnowledgeSource(knowledgeSource);
      problemSet.setTitle("ProblemSet-" + i);
      problemSet.setDescription("This is problem-" + i);

      // add questions
      for (int j = 0; j < 3; j++) {
        Question question = new Question();
        question.setContent("What is question-" + j);
        question.setAnswer(j);

        // add options
        for (int k = 0; k < 4; k++) {
          Option option = new Option("option-" + k);
          entityManager.persist(option);

          question.getOptions().add(option);
        }

        entityManager.persist(question);

        problemSet.getQuestion().add(question);
      }

      entityManager.persist(problemSet);
    }
  }
}
