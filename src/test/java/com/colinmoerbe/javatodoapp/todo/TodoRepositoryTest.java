package com.colinmoerbe.javatodoapp.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16.3");

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void postgresConnectionShouldBeEstablished() {
        assertThat(POSTGRES.isCreated()).isTrue();
        assertThat(POSTGRES.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        List<Todo> todos = List.of(new Todo.Builder(
            "Test World",
            "Test world description")
            .dueAt(LocalDateTime.of(2024, 5, 31, 23, 33, 13))
            .completed(true)
            .build()
        );
        todoRepository.saveAll(todos);
    }

    @Test
    void shouldReturnTodoByTitle() {
        Todo todo = todoRepository.findByTitle("Test World");
        assertThat(todo).isNotNull();
    }
}
