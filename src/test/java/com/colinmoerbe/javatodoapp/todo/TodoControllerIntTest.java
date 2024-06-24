package com.colinmoerbe.javatodoapp.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class TodoControllerIntTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16.3");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void postgresConnectionShouldBeEstablished() {
        assertThat(POSTGRES.isCreated()).isTrue();
        assertThat(POSTGRES.isRunning()).isTrue();
    }

    @Test
    void shouldFindAllTodos() {
        Todo[] todos = restTemplate.getForObject("/api/v1/todos", Todo[].class);
        assertThat(todos).hasSize(2);
    }

    @Test
    void shouldFindTodoWhenValidTodoID() {
        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos/1", HttpMethod.GET, null, Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldThrowNotFoundWhenInvalidTodoID() {
        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos/999", HttpMethod.GET, null, Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateNewTodoWhenTodoIsValid() {
        Todo todo = new Todo.Builder(
            "Test World3",
            "Test world description3")
            .dueAt(LocalDateTime.of(2024, 5, 31, 23, 33, 13))
            .completed(true)
            .build();

        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos", HttpMethod.POST, new HttpEntity<>(todo), Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(3);
        assertThat(response.getBody().getTitle()).isEqualTo("Test World3");
        assertThat(response.getBody().getDescription()).isEqualTo("Test world description3");
        assertThat(response.getBody().getDueAt()).isEqualTo(LocalDateTime.of(2024, 5, 31, 23, 33, 13));
        assertThat(response.getBody().getCompleted()).isTrue();
    }

    @Test
    void shouldNotCreateNewTodoWhenValidationFails() {
        Todo todo = new Todo.Builder(
            "", // title cannot be blank
            "Test world description3")
            .dueAt(LocalDateTime.of(2024, 5, 31, 23, 33, 13))
            .completed(true)
            .build();
        ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos", HttpMethod.POST, new HttpEntity<>(todo), Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    void shouldUpdateTheWholeTodoWhenTodoIsValid() {
        ResponseEntity<Todo> initialRequestResponse = restTemplate.exchange("/api/v1/todos/2", HttpMethod.GET, null, Todo.class);
        assertThat(initialRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo initialTodo = initialRequestResponse.getBody();
        assertThat(initialTodo).isNotNull();

        Todo newTodoContent = new Todo.Builder(
            "Test World99",
            "Test world description99")
            .build();
        restTemplate.exchange("/api/v1/todos/2", HttpMethod.PUT, new HttpEntity<>(newTodoContent), Todo.class);

        ResponseEntity<Todo> newRequestResponse = restTemplate.exchange("/api/v1/todos/2", HttpMethod.GET, null, Todo.class);
        assertThat(newRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo updatedTodo = newRequestResponse.getBody();
        assertThat(updatedTodo).isNotNull();

        assertThat(updatedTodo.getId()).isEqualTo(2);
        assertThat(updatedTodo.getTitle()).isEqualTo("Test World99");
        assertThat(updatedTodo.getDescription()).isEqualTo("Test world description99");
        assertThat(updatedTodo.getDueAt()).isNull();
    }

    @Test
    @Rollback
    void shouldUpdateTodoPartiallyWhenTodoIsValid() {
        ResponseEntity<Todo> initialRequestResponse = restTemplate.exchange("/api/v1/todos/1", HttpMethod.GET, null, Todo.class);
        assertThat(initialRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo initialTodo = initialRequestResponse.getBody();
        assertThat(initialTodo).isNotNull();

        Todo newTodoContent = new Todo.Builder(
            "Test World99",
            "Test world description99")
            .build();
        restTemplate.exchange("/api/v1/todos/1", HttpMethod.PATCH, new HttpEntity<>(newTodoContent), Todo.class);

        ResponseEntity<Todo> newRequestResponse = restTemplate.exchange("/api/v1/todos/1", HttpMethod.GET, null, Todo.class);
        assertThat(newRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo updatedTodo = newRequestResponse.getBody();
        assertThat(updatedTodo).isNotNull();

        assertThat(updatedTodo.getId()).isEqualTo(1);
        assertThat(updatedTodo.getTitle()).isEqualTo("Test World99");
        assertThat(updatedTodo.getDescription()).isEqualTo("Test world description99");
        assertThat(updatedTodo.getCreatedAt()).isEqualTo("2024-05-31T20:30:10");
        assertThat(updatedTodo.getDueAt()).isEqualTo("2024-05-31T23:33:13");
        assertThat(updatedTodo.getCompleted()).isTrue();
    }

    @Test
    void shouldDeleteWithValidID() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/todos/3", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
