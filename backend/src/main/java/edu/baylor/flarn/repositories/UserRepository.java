package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserType(@NotNull UserType userType);

    Optional<User> findByUsername(String username);

    @Query(value = "select subscribedUsers from User u where u.id =:userId")
    List<User> findSubscribedUsers(@Param("userId") long userId);

    @Query(value = "select subscriptions from User u where u.id =:userId")
    List<User> findUserSubscriptions(@Param("userId") long userId);

    Optional<User> findByConfirmationToken(String confirmationToken);
}
