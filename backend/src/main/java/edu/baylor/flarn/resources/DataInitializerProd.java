package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.*;
import edu.baylor.flarn.services.UserService;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerProd(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        createAdmin();
    }

    private void createAdmin() {
        if(userService.exists("admin@gm.com")) return;

        log.info("Initializing data");
        User admin = new User("admin@gm.com",
                passwordEncoder.encode("admin"), "Admin Mock", "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.ADMIN);

        admin.setEnabled(true);
        userService.saveUser(admin);
    }
}
