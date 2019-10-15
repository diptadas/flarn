package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByUserType(@NotNull UserType userType);

    Optional<User> findByUsername(String username);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userType =:userType WHERE u.id =:userId")
    public int setUserTypeId(@Param("userId") long userId, @Param("userType") UserType userType);

    @Query(value = "select subscribedUsers from User u where u.id =:userId")
    public List<User> findSubscribedUsers(@Param("userId") long userId);

    @Query(value = "select subscriptions from User u where u.id =:userId")
    public List<User> findUserSubscriptions(@Param("userId") long userId);


    // TODO: adding subscription repository

}
