package com.colinmoerbe.javatodoapp.todo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Configuration class for Testcontainers PostgreSQL in a Spring test context.
 */
@TestConfiguration
public class TestcontainerConfiguration {

    /**
     * Provides a PostgreSQL container instance configured with version 16.3.
     *
     * @return PostgreSQLContainer<?> instance configured with PostgreSQL version 16.3.
     */
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16.3");
    }
}
