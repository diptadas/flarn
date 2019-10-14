package edu.baylor.flarn.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class SessionAnswer {
    int option;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
