package com.colinmoerbe.javatodoapp.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains unit tests for the {@link TodoController} class.
 * <p>
 * It uses {@link WebMvcTest} to focus solely on testing the web layer, with {@link MockMvc} for simulating HTTP requests.
 * The {@link MockBean} annotation is used to provide a mock implementation of the {@link TodoRepository}.
 * </p>
 */
@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
class TodoControllerTest {

    /** MockMvc instance for performing requests and verifying responses. */
    @Autowired
    private MockMvc mockMvc;

    /** Mocked TodoRepository for simulating database interactions. */
    @MockBean
    private TodoRepository todoRepository;

    /** List of To-Do objects used as test data. */
    private List<Todo> todos = new ArrayList<>();

    /**
     * Sets up the test data before each test method.
     * <p>
     * Initializes the {@code todos} list with predefined To-Do objects and configures
     * the mocked {@code todoRepository} to return this list when {@link ListCrudRepository#findAll()} is called.
     * </p>
     */
    @BeforeEach
    void setUp() {
        todos = List.of(
            new Todo(
                1,
                "Test World",
                "Test world description",
                LocalDate.of(2024, 5, 31),
                LocalDate.of(2024, 5, 31),
                1),
            new Todo(
                2,
                "Test World2",
                "Test world description2",
                LocalDate.of(2024, 5, 31),
                LocalDate.of(2024, 5, 31),
                1)
        );
    }

    /**
     * Tests the {@link TodoController#getTodos()} endpoint.
     * <p>
     * This test performs a GET request to the {@code /api/v1/todos} endpoint and verifies
     * that the response status is OK (200) and that the response content matches the expected JSON.
     * </p>
     *
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    void shouldGetAllTodos() throws Exception {
        final String jsonResponse = """
            [
                {
                    "id": 1,
                    "title": "Test World",
                    "description": "Test world description",
                    "created_at": "2024-05-31",
                    "due_at": "2024-05-31",
                    "version": 1
                },
                {
                    "id": 2,
                    "title": "Test World2",
                    "description": "Test world description2",
                    "created_at": "2024-05-31",
                    "due_at": "2024-05-31",
                    "version": 1
                }
            ]
            """;
        when(todoRepository.findAll()).thenReturn(todos);

        final ResultActions resultActions = mockMvc.perform(get("/api/v1/todos"))
            .andExpect(status().isOk())
            .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}
