package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * KnowledgeSource class defines the lesson contents for the Problem model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class KnowledgeSource {
    @NotNull
    @Column(columnDefinition="text")
    String content;

    @OneToOne
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    Problem problem;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
