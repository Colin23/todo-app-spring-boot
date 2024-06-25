package com.colinmoerbe.javatodoapp.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Transactional
@Import(TestcontainerConfiguration.class)
class TodoControllerIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostgreSQLContainer postgreSQLContainer;

    @Test
    void postgresConnectionShouldBeEstablished() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void shouldFindAllTodos() {
        final Todo[] todos = restTemplate.getForObject("/api/v1/todos", Todo[].class);
        assertThat(todos).hasSize(2);
    }

    @Test
    void shouldFindTodoWhenValidTodoID() {
        final ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos/1", HttpMethod.GET, null, Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldThrowNotFoundWhenInvalidTodoID() {
        final ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos/999", HttpMethod.GET, null, Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Tests the creation of a new To-Do item with valid data.
     * <p>
     * This test ensures that a valid To-Do item can be successfully created via a POST request to the /api/v1/todos endpoint.
     * It verifies that the response status is 201 Created and checks the properties of the created To-Do item.
     */
    @Test
    void shouldCreateNewTodoWhenTodoIsValid() {
        final Todo todo = new Todo.Builder(
            "Test World3",
            "Test world description3")
            .dueAt(LocalDateTime.of(2024, 5, 31, 23, 33, 13))
            .completed(true)
            .build();

        final ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos", HttpMethod.POST, new HttpEntity<>(todo), Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(3);
        assertThat(response.getBody().getTitle()).isEqualTo("Test World3");
        assertThat(response.getBody().getDescription()).isEqualTo("Test world description3");
        assertThat(response.getBody().getDueAt()).isEqualTo(LocalDateTime.of(2024, 5, 31, 23, 33, 13));
        assertThat(response.getBody().getCompleted()).isTrue();
    }

    /**
     * Tests the creation of a new To-Do item with invalid data.
     * <p>
     * This test ensures that a To-Do item with invalid data (e.g., blank title) is not created and
     * the response status is 400 Bad Request.
     */
    @Test
    void shouldNotCreateNewTodoWhenValidationFails() {
        final Todo todo = new Todo.Builder(
            "", // title cannot be blank
            "Test world description3")
            .dueAt(LocalDateTime.of(2024, 5, 31, 23, 33, 13))
            .completed(true)
            .build();
        final ResponseEntity<Todo> response = restTemplate.exchange("/api/v1/todos", HttpMethod.POST, new HttpEntity<>(todo), Todo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Tests the update of a To-Do item with valid data.
     * <p>
     * This test ensures that an existing To-Do item can be fully updated via a PUT request to the /api/v1/todos/{id} endpoint.
     * It verifies that the response status is 200 OK and checks the properties of the updated To-Do item.
     * <p>
     * The {@link Rollback} annotation ensures that the database state is reset after the test.
     */
    @Test
    @Rollback
    void shouldUpdateTheWholeTodoWhenTodoIsValid() {
        final ResponseEntity<Todo> initialRequestResponse = restTemplate.exchange("/api/v1/todos/2", HttpMethod.GET, null, Todo.class);
        assertThat(initialRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Todo initialTodo = initialRequestResponse.getBody();
        assertThat(initialTodo).isNotNull();

        final Todo newTodoContent = new Todo.Builder(
            "Test World99",
            "Test world description99")
            .build();
        restTemplate.exchange("/api/v1/todos/2", HttpMethod.PUT, new HttpEntity<>(newTodoContent), Todo.class);

        final ResponseEntity<Todo> newRequestResponse = restTemplate.exchange("/api/v1/todos/2", HttpMethod.GET, null, Todo.class);
        assertThat(newRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Todo updatedTodo = newRequestResponse.getBody();
        assertThat(updatedTodo).isNotNull();

        assertThat(updatedTodo.getId()).isEqualTo(2);
        assertThat(updatedTodo.getTitle()).isEqualTo("Test World99");
        assertThat(updatedTodo.getDescription()).isEqualTo("Test world description99");
        assertThat(updatedTodo.getDueAt()).isNull();
    }

    /**
     * Tests the partial update of a To-Do item with valid data.
     * <p>
     * This test ensures that an existing To-Do item can be partially updated via a PATCH request to the /api/v1/todos/{id} endpoint.
     * It verifies that the response status is 200 OK and checks the properties of the partially updated To-Do item.
     * <p>
     * The {@link Rollback} annotation ensures that the database state is reset after the test.
     *
     */
    @Test
    @Rollback
    void shouldUpdateTodoPartiallyWhenTodoIsValid() {
        final ResponseEntity<Todo> initialRequestResponse = restTemplate.exchange("/api/v1/todos/1", HttpMethod.GET, null, Todo.class);
        assertThat(initialRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Todo initialTodo = initialRequestResponse.getBody();
        assertThat(initialTodo).isNotNull();

        final Todo newTodoContent = new Todo.Builder(
            "Test World99",
            "Test world description99")
            .build();
        restTemplate.exchange("/api/v1/todos/1", HttpMethod.PATCH, new HttpEntity<>(newTodoContent), Todo.class);

        final ResponseEntity<Todo> newRequestResponse = restTemplate.exchange("/api/v1/todos/1", HttpMethod.GET, null, Todo.class);
        assertThat(newRequestResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Todo updatedTodo = newRequestResponse.getBody();
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
        final ResponseEntity<Void> response = restTemplate.exchange("/api/v1/todos/3", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
