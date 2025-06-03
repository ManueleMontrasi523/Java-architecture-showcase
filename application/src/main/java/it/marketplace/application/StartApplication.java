package it.marketplace.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Marketplace Microservices Spring Boot application.
 * Configures entity scanning, JPA repositories, and enables scheduling.
 */
@SpringBootApplication(scanBasePackages = "it.marketplace.microservices")
@EntityScan(basePackages = "it.marketplace.database.entity")
@EnableJpaRepositories(basePackages = "it.marketplace.database.repository")
@EnableScheduling
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
