package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * {@link UserRepository} class defines the DB operations for {@link User} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserType(@NotNull UserType userType);

    List<User> findByEnabledTrue();

    Optional<User> findByUsernameAndEnabledTrue(String username);

    Optional<User> findByUsername(String username);

    // To do: filter deactivated users
    @Query(value = "select subscribers from User u where u.id =:userId")
    List<User> findSubscribers(@Param("userId") long userId);

    // To do: filter deactivated users
    @Query(value = "select subscriptions from User u where u.id =:userId")
    List<User> findUserSubscriptions(@Param("userId") long userId);

    List<User> findByFullNameContainingIgnoreCase(@NotNull String name);

    List<User> findAllByOrderByPointsDesc();

    List<User> findByIdInOrderByPointsDesc(@NotNull List<Long> userIds);

}
