package edu.baylor.flarn.repositories;

import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * This class contains custom queries which were implemented
 */
@Component
public class CustomQueries {

    private final EntityManager entityManager;

    public CustomQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * This method is used to remove foreign key associations before delete operations
     */
    @Transactional
    public ResponseBody deleteAssociations(Long userId){
        try {
            entityManager.createNativeQuery(
                    " DELETE  from user_subscription AS sub where sub.subscription_id = '" + userId + "' or sub.subscriber_id = '" + userId + "'")
                    .executeUpdate();
            return new ResponseBody(200, "success");
        }catch (Exception e){
            return new ResponseBody(500,e.getMessage());
        }

    }

}
