package com.colinmoerbe.javatodoapp.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is currently only for testing the initial setup only.
 */
@RestController
@RequestMapping("/api")
class TodoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    @Value("${test.password}")
    private String testPassword;

    @Value("${test.username}")
    private String testUsername;

    @GetMapping("/v1/todos")
    public String getTodos() {
        LOGGER.info("Successfully called the /v1/todos endpoint");
        return "Testing " + testUsername + " asdf " + testPassword;
    }

    @GetMapping("/v1/todos/{id}")
    public String getTodo(@PathVariable Integer id) {
        return "Test with ID: " + id;
    }

    @PutMapping("/v1/todos/{id}")
    public String updateTodo(@PathVariable Integer id) {
        return "Update method is not yet implemented";
    }

    @PostMapping("/v1/todos")
    public String createTodo() {
        return "Create method is not yet implemented";
    }

    @DeleteMapping("/v1/todos/{id}")
    public String deleteTodo(@PathVariable Integer id) {
        return "Delete method is not yet implemented";
    }
}
