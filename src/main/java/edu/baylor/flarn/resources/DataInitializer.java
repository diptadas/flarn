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

    private List<Category> categories = new ArrayList<>();
    private List<ProblemSet> problemSets = new ArrayList<>();
    private List<User> moderators = new ArrayList<>();
    private List<User> learners = new ArrayList<>();
    private User admin;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Initializing data");

        createAdmin();
        createModerators();
        createLearners();
        createSubscriptions();

        createCategories();
        createProblemSets();
    }

    private void createCategories() {
        for (int i = 0; i < 3; i++) {
            Category category = new Category();
            category.setName("category-" + i);
            entityManager.persist(category);
            entityManager.flush();

            categories.add(category);
        }
    }

    private void createModerators() {
        for (int i = 0; i < 3; i++) {
            User moderator = new User("moderator" + i + "@gm.com",
                    "moderator" + i, "Moderator" + i, "254567908", "part",
                    "temple", "AZ", "0000", new ArrayList<>());
            moderator.setUserType(UserType.MODERATOR);
            entityManager.persist(moderator);
            entityManager.flush();

            moderators.add(moderator);
        }
    }

    private void createLearners() {
        for (int i = 0; i < 3; i++) {
            User learner = new User("learner" + i + "@gm.com",
                    "learner" + i, "Learner" + i, "254567908", "part",
                    "temple", "AZ", "0000", new ArrayList<>());
            learner.setUserType(UserType.LEARNER);
            entityManager.persist(learner);
            entityManager.flush();

            learners.add(learner);
        }
    }

    private void createAdmin() {
        admin = new User("admin@gm.com",
                "admin", "Admin Mock", "254567908", "part",
                "temple", "AZ", "0000", new ArrayList<>());
        admin.setUserType(UserType.ADMIN);
        entityManager.persist(admin);
        entityManager.flush();
    }

    private void createSubscriptions() {
        moderators.get(1).subscribe(moderators.get(0));
        entityManager.persist(moderators.get(1)); // don't need to persist both
        entityManager.flush();

        learners.get(0).subscribe(moderators.get(1));
        learners.get(0).subscribe(admin);
        entityManager.persist(learners.get(0)); // don't need to persist both
        entityManager.flush();

        learners.get(1).subscribe(learners.get(0));
        learners.get(1).subscribe(moderators.get(1));
        entityManager.persist(learners.get(1)); // don't need to persist both
        entityManager.flush();

        learners.get(2).subscribe(learners.get(0));
        learners.get(2).subscribe(learners.get(1));
        entityManager.persist(learners.get(1)); // don't need to persist both
        entityManager.flush();
    }

    private void createProblemSets() {
        for (int i = 0; i < 10; i++) {
            ProblemSet problemSet = new ProblemSet();
            problemSet.setDifficulty(Difficulty.EASY);
            problemSet.setCategory(categories.get(i % 3)); // add category in cyclic order
            problemSet.setModerator(moderators.get(i % 3)); // add moderator in cyclic order

            // add knowledge source
            KnowledgeSource knowledgeSource = new KnowledgeSource();
            knowledgeSource.setContentLink("http://google.com");
            entityManager.persist(knowledgeSource);
            entityManager.flush();

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
                entityManager.flush();

                problemSet.getQuestions().add(question);
            }

            entityManager.persist(problemSet);
            entityManager.flush();

            problemSets.add(problemSet);
        }
    }
}
