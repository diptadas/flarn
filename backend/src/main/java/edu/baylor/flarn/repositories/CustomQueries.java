package edu.baylor.flarn.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;


public class CustomQueries {

    private final EntityManager entityManager;

    @Autowired
    private EntityManager em;

    public CustomQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void deleteAssociations(Long userId){
        //em.createNativeQuery();
    }
}
