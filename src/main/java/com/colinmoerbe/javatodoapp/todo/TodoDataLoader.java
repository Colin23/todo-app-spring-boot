package com.colinmoerbe.javatodoapp.todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class loads initial To-Do data from a JSON file into the database.
 * This class is intended to be used only in the development environment to
 * prepopulate the database with sample data for testing and development purposes.
 * <p>
 * The JSON file should be located in the {@code /resources/data/todos-example.json} path.
 * The data loading process checks if the database already contains any To-Do items
 * and only loads the data if the database is empty.
 * </p>
 * <p>
 * Usage: This component is automatically run at application startup if the
 * "dev" profile is active.
 * </p>
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
public class TodoDataLoader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoDataLoader.class);
    private static final String TODOS_JSON = "/data/todos-example.json";

    private final ObjectMapper objectMapper;
    private final TodoRepository todoRepository;


    /**
     * Loads initial To-Do data from a JSON file into the database.
     * <p>
     * This method is executed automatically at application startup. It checks if the database
     * already contains any to-do items and, if not, reads the data from the JSON file and saves it to the database.
     * </p>
     *
     * @param args command line arguments (not used)
     */
    @Override
    public void run(String... args) {
        if (todoRepository.count() == 0) {
            LOGGER.info("Loading todos into database from JSON: {}", TODOS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(TODOS_JSON)) {
                final Todos response = objectMapper.readValue(inputStream, Todos.class);
                todoRepository.saveAll(response.todos());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}
