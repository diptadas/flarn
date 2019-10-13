package edu.baylor.flarn;

import edu.baylor.flarn.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FlarnApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlarnApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(EntityManager entityManager) {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) {
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
        };
    }

}
