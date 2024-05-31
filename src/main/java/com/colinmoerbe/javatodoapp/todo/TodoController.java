package com.colinmoerbe.javatodoapp.todo;

import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.Optional;

/**
 * This class is currently only for testing the initial setup only.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class TodoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    private final TodoRepository todoRepository;

    @Value("${test.password}")
    private String testPassword;

    @Value("${test.username}")
    private String testUsername;

    @GetMapping("/v1/todos")
    List<Todo> getTodos() {
        LOGGER.info("Successfully called the /v1/todos endpoint");
        return todoRepository.findAll();
    }

    @GetMapping("/v1/todos/{id}")
    Optional<Todo> getTodo(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/v1/todos/{id}")
    void updateTodo(@PathVariable Integer id) {

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
