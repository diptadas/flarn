package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link CategoryRepository} class defines the DB operations for {@link Category} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
