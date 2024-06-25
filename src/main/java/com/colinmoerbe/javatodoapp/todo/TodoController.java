package com.colinmoerbe.javatodoapp.todo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * Retrieves a list of all To-Do items.
     *
     * @return a list of To-Do items
     */
    @GetMapping("/v1/todos")
    List<Todo> getTodos() {
        LOGGER.info("Successfully called the GET /api/v1/todos endpoint");
        return todoRepository.findAll();
    }

    /**
     * Retrieves a specific To-Do item by its ID.
     *
     * @param id the ID of the To-Do item to retrieve
     * @return an Optional containing the requested To-Do item, if found
     * @throws TodoNotFoundException if the To-Do item with the specified ID is not found
     */
    @GetMapping("/v1/todos/{id}")
    Optional<Todo> getTodo(@PathVariable Integer id) {
        LOGGER.info("Successfully called the GET /api/v1/todos/{} endpoint", id);
        return Optional.ofNullable(todoRepository.findById(id).orElseThrow(TodoNotFoundException::new));
    }

    /**
     * Updates an existing To-Do item.
     *
     * @param id the ID of the To-Do item to update
     * @param todo the To-Do item with the field values to be updated
     * @return the updated To-Do item
     * @throws TodoNotFoundException if the To-Do item with the specified ID is not found
     */
    @PutMapping("/v1/todos/{id}")
    Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo) {
        LOGGER.info("Successfully called the PUT /api/v1/todos/{} endpoint", id);
        return todoRepository.findById(id)
            .map(existingTodo -> {
                existingTodo.setTitle(todo.getTitle());
                existingTodo.setDescription(todo.getDescription());
                existingTodo.setDueAt(todo.getDueAt());
                existingTodo.setCompleted(todo.getCompleted());
                return todoRepository.save(existingTodo);
            })
            .orElseThrow(TodoNotFoundException::new);
    }

    /**
     * Partially updates an existing To-Do item.
     *
     * @param id the ID of the To-Do item to update
     * @param partialTodo the To-Do item with the field values to be updated
     * @return the updated To-Do item
     * @throws TodoNotFoundException if the To-Do item with the specified ID is not found
     */
    @PatchMapping("/v1/todos/{id}")
    Todo partiallyUpdateTodo(@PathVariable Integer id, @RequestBody Todo partialTodo) {
        LOGGER.info("Successfully called the PATCH /api/v1/todos/{} endpoint", id);
        return todoRepository.findById(id)
            .map(existingTodo -> {
                if (partialTodo.getTitle() != null) {
                    existingTodo.setTitle(partialTodo.getTitle());
                }
                if (partialTodo.getDescription() != null) {
                    existingTodo.setDescription(partialTodo.getDescription());
                }
                if (partialTodo.getDueAt() != null) {
                    existingTodo.setDueAt(partialTodo.getDueAt());
                }
                return todoRepository.save(existingTodo);
            })
            .orElseThrow(TodoNotFoundException::new);
    }

    /**
     * Creates a new To-Do item.
     *
     * @param todo the To-Do item to create
     * @return the created To-Do item
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/todos")
    Todo createTodo(@RequestBody @Valid Todo todo) {
        LOGGER.info("Successfully called the POST /api/v1/todos endpoint");
        return todoRepository.save(todo);
    }

    /**
     * Deletes a specific To-Do item by its ID.
     *
     * @param id the ID of the To-Do item to delete.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/v1/todos/{id}")
    void deleteTodo(@PathVariable Integer id) {
        LOGGER.info("Successfully called the DELETE /api/v1/todos/{} endpoint", id);
        todoRepository.deleteById(id);
    }
}
