package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByUserType(@NotNull UserType userType);

    @Modifying
    @Query("UPDATE USER u SET u.usertype = : newusertype WHERE u.id = :userId")
    public User changeUserType(@Param("userId") long userId, @Param("userType") UserType userType);

}
