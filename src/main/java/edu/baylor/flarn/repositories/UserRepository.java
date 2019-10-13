package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String email);
}
