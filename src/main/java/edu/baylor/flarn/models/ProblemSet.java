package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "sequence", initialValue = 1, allocationSize = 1)
public class ProblemSet {
    @NotNull
    String title;

    @NotNull
    String description;

    @NotNull
    @OneToOne
    KnowledgeSource knowledgeSource;

    @OneToMany(mappedBy = "problemSet")
    Set<Question> questions = new HashSet<>();

    @NotNull
    Difficulty difficulty;

    @OneToMany(mappedBy = "problemSet")
    Set<Review> reviews = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    User moderator;

    @OneToMany(mappedBy = "problemSet")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    Set<Session> sessions = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
