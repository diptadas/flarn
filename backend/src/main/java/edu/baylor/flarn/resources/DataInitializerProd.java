package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Profile("prod")
@Component
@Slf4j
public class DataInitializerProd implements CommandLineRunner {
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerProd(EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing data");
        createAdmin();
    }

    private void createAdmin() {
        User admin = new User("admin@gm.com",
                passwordEncoder.encode("admin"), "Admin Mock", "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.ADMIN);

        admin.setEnabled(true);
        entityManager.persist(admin);
        entityManager.flush(); // flush everything
    }
}
