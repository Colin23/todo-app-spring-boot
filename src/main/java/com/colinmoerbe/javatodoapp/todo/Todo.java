package com.colinmoerbe.javatodoapp.todo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * This class represents a To-Do item with all its details. These are:
 * <ol>
 *     <li>id</li>
 *     <li>title</li>
 *     <li>description</li>
 *     <li>createdAt</li>
 *     <li>dueAt</li>
 *     <li>completed</li>
 * </ol>
 * This entity is used to store To-Do items in the database.
 * The {@link Entity} annotation marks this class as a JPA entity.
 * The {@link Table} annotation specifies the table in the database to which this entity maps.
 * The {@link Getter} and {@link Setter} annotations from Lombok automatically generate getter and setter methods for all fields.
 */
@Entity
@Table(name = "todo")
@Getter
@Setter
public class Todo {

    /**
     * The ID of the To-Do item.
     * <p>
     * {@link Id} specifies that this field is mapped to the primary key of the table.
     * {@link GeneratedValue} marks the value of this field to be generated automatically with the {@code IDENTITY} strategy.
     * {@link Column} maps this field to the column {@code id} in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * The title of the To-Do item.
     * <p>
     * {@link NotBlank} makes sure the field is not null and contains at least one character.
     * {@link Column} maps this field to the column {@code todo_title} in the database.
     * {@link JsonProperty} sets an alias of the field for JSON properties to {@code todo_title}.
     */
    @NotBlank
    @Column(name = "todo_title", nullable = false)
    @JsonProperty("todo_title")
    private String title;

    /**
     * The description of the To-Do item.
     * <p>
     * {@link Column} maps this field to the column {@code todo_description} in the database.
     * {@link JsonProperty} sets an alias of the field for JSON properties to {@code todo_description}.
     */
    @Column(name = "todo_description")
    @JsonProperty("todo_description")
    private String description;

    /**
     * The date the To-Do item was created.
     * <p>
     * {@link Column} maps this field to the column {@code todo_created_at} in the database.
     * {@link JsonProperty} sets an alias of the field for JSON properties to {@code todo_created_at}.
     */
    @Column(name = "todo_created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("todo_created_at")
    private LocalDateTime createdAt;

    /**
     * The date by which the To-Do item should be completed.
     * <p>
     * {@link Column} maps this field to the column {@code todo_due_at} in the database.
     * {@link JsonProperty} sets an alias of the field for JSON properties to {@code todo_due_at}.
     */
    @Column(name = "todo_due_at")
    @JsonProperty("todo_due_at")
    private LocalDateTime dueAt;

    /**
     * A boolean to represent if a To-Do is completed.
     * <p>
     * {@link Column} maps this field to the column {@code todo_completed} in the database.
     * {@link JsonProperty} sets an alias of the field for JSON properties to {@code todo_completed}.
     */
    @Column(name = "todo_completed", nullable = false)
    @JsonProperty("todo_completed")
    private Boolean completed;

    /** The empty default constructor for JPA */
    public Todo() { }

    /**
     * The usable To-Do constructor without the {@code id} field, so it cannot be set.
     *
     * @param builder The builder constructor of the inner Builder class
     */
    private Todo(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.dueAt = builder.dueAt;
        this.completed = builder.completed;
    }

    /**
     * Sets the {@code createdAt} field to the current timestamp before the entity is persisted.
     * This method ensures that the {@code createdAt} field is not null, aligning with the database schema's constraints.
     * The method is automatically invoked by JPA due to the {@link PrePersist} annotation.
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            // ZoneId for Coordinated Universal Time (UTC) or Central European Summer Time (CEST)
            this.createdAt = LocalDateTime.now(ZoneId.of("UTC+2"));
        }
        if (this.completed == null) {
            this.completed = false;
        }
    }

    /**
     * This inner class is used for the builder pattern.
     * This makes it easier to create To-Do objects with exactly the field values the user intended.
     */
    public static class Builder {
        private final String title;
        private final String description;
        private final LocalDateTime createdAt = null;
        private LocalDateTime dueAt = null;
        private Boolean completed = false;

        /**
         * The Builder constructor for setting the title and the description of a To-Do.
         *
         * @param title The title of a To-Do
         * @param description The description of a To-Do
         */
        public Builder(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public Builder dueAt(LocalDateTime dueAt) {
            this.dueAt = dueAt;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Todo build() {
            return new Todo(this);
        }
    }
}
