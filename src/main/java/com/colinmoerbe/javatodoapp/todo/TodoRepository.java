package com.colinmoerbe.javatodoapp.todo;

import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for To-Do items.
 */
public interface TodoRepository extends ListCrudRepository<Todo, Integer> {

    Todo findByTitle(String title);
}
