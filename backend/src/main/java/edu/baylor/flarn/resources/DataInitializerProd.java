package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.services.CategoryService;
import edu.baylor.flarn.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("prod")
@Component
@Slf4j
public class DataInitializerProd implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerProd(UserService userService, CategoryService categoryService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing data");
        createAdmin();
        categoryService.createDefaultCategory();
    }

    private void createAdmin() {
        String adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPassword = System.getenv("ADMIN_PASSWORD");

        if (userService.exists(adminUsername)) return;

        User admin = new User(adminUsername, passwordEncoder.encode(adminPassword), "Flarn Admin",
                "254567908", "South 5th", "Waco", "TX", "76706",
                "Proud admin of flarn project", null, null, UserType.ADMIN);

        admin.setEnabled(true);
        userService.saveUser(admin);
    }
}
