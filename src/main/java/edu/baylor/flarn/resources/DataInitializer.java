package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.*;
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
        log.info("Initializing data");

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
                    question.getOptions().add("option-" + k);
                }

                entityManager.persist(question);

                problemSet.getQuestion().add(question);
            }

            entityManager.persist(problemSet);
        }

        //List<String> roles = new ArrayList<>();
        User admin = new User("admin@gm.com",
                "admin", "Admin Mock", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<String>());
        admin.setUserType(UserType.ADMIN);
        entityManager.persist(admin);

        User moderator1 = new User("mod1@gm.com",
                "mod1", "Moderator One", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<String>());
        moderator1.setUserType(UserType.MODERATOR);
        entityManager.persist(moderator1);


        User moderator2 = new User("mod2@gm.com",
                "mod2", "Moderator two", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<String>());
        moderator2.setUserType(UserType.MODERATOR);
        moderator2.getSubscriptions().add(moderator1);
        entityManager.persist(moderator2);


        User learner1 = new User("learner1@gm.com",
                "learn1", "Learner One", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<String>());
        learner1.setUserType(UserType.LEARNER);
        learner1.getSubscriptions().add(admin);
        learner1.getSubscriptions().add(moderator2);
        entityManager.persist(learner1);

        User learner2 = new User("learner2@gm.com",
                "learn2", "Learner two", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<String>());
        learner2.setUserType(UserType.LEARNER);
        learner2.getSubscriptions().add(admin);
        learner2.getSubscriptions().add(learner1);
        entityManager.persist(learner2);


        User learner3 = new User("learner3@gm.com",
                "learn3", "Learner One", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<String>());
        learner3.setUserType(UserType.LEARNER);
        learner3.getSubscriptions().add(learner2);
        learner3.getSubscriptions().add(admin);
        learner3.getSubscriptions().add(moderator1);
        learner3.getSubscriptions().add(learner1);
        entityManager.persist(learner3);
    }
}
