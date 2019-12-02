package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Problem class defines a problem that users can attempt.
 * Each problem is associated with a moderator (a User) who created the problem.
 * A problem is also associated with a category and a difficulty level.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    String title;

    @NotNull
    String description;

    boolean archived = false;

    @NotNull
    @OneToOne
    KnowledgeSource knowledgeSource;

    // don't use Set, order matters to check the answers from sessions
    @OneToMany(mappedBy = "problem", cascade = {CascadeType.ALL})
    List<Question> questions = new ArrayList<>();

    @NotNull
    Difficulty difficulty;

    @OneToMany(mappedBy = "problem", cascade = {CascadeType.ALL})
    Set<Review> reviews = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    User moderator;

    @OneToMany(mappedBy = "problem")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    Set<Session> sessions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void addQuestion(Question question) { // helper to ensure bidirectional insert
        this.getQuestions().add(question);
        question.setProblem(this);
    }


    public void addReview(Review review) { // helper to ensure bidirectional insert
        this.getReviews().add(review);
        review.setProblem(this);
    }
}
