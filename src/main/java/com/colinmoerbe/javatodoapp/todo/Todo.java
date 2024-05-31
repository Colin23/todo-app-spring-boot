package com.colinmoerbe.javatodoapp.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;

/**
 * This class represents a To-Do item with all its details. These are:
 * <ol>
 *     <li>id</li>
 *     <li>title</li>
 *     <li>description</li>
 *     <li>createdAt</li>
 *     <li>dueAt</li>
 *     <li>version</li>
 * </ol>
 * This entity is used to store To-Do items in the database.
 * The {@link Entity} annotation marks this class as a JPA entity.
 * The {@link Getter} and {@link Setter} annotations from Lombok automatically generate getter and setter methods for all fields.
 * The {@link AllArgsConstructor} and {@link NoArgsConstructor} annotations generate constructors with and without parameters respectively.
 * The {@link JsonProperty} annotation is used to specify the JSON property names for the {@code createdAt} and {@code dueAt} fields.
*/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    /**
     * The unique identifier for the to-do item.
     * This value is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** The title of the To-Do item. */
    private String title;

    /** The description of the To-Do item */
    private String description;

    /**
     * The date the To-Do item was created.
     * Mapped to the JSON property {@code created_at}.
     */
    @JsonProperty("created_at")
    private LocalDate createdAt;

    /**
     * The date by which the To-Do item should be completed.
     * Mapped to the JSON property {@code due_at}.
     */
    @JsonProperty("due_at")
    private LocalDate dueAt;

    /** The version of the to-do item. */
    @Version
    private Integer version;
}
