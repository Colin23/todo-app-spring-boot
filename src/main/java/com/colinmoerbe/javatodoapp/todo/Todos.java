package com.colinmoerbe.javatodoapp.todo;

import java.util.List;

/**
 * This record represents a list of To-Do items.
 */
public record Todos(
    List<Todo> todos
) {
}
