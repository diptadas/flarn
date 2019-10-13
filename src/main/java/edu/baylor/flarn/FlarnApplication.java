package edu.baylor.flarn;

import edu.baylor.flarn.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//@EnableConfigurationProperties(PropertiesConfig.class)
public class FlarnApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlarnApplication.class, args);
    }
}
