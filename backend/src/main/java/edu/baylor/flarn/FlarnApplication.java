package edu.baylor.flarn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * {@link FlarnApplication} class is the bootstrapper for the application.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@SpringBootApplication
@EnableAsync
public class FlarnApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlarnApplication.class, args);
    }
}
