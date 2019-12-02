package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link ContactRepository} class defines the DB operations for {@link Contact} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
