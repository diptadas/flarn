package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
