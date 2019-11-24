package edu.baylor.flarn.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class CustomQueries {

    private final EntityManager entityManager;

    public CustomQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void deleteAssociations(Long userId){
        try {
            entityManager.createNativeQuery(
                    " DELETE  from user_subscription AS sub where sub.subscription_id = '" + userId + "' or sub.subscriber_id = '" + userId + "'")
                    .executeUpdate();
        }catch (Exception e){
            System.out.println("pipe "+ e);
        }

    }

}
