package edu.baylor.flarn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(PropertiesConfig.class)
public class FlarnApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlarnApplication.class, args);
    }
}
