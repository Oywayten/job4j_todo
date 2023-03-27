package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Oywayten 15.03.2023.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    /**
     * <a href="https://stackoverflow.com/a/1638886">How should equals and hashcode be implemented when using JPA and Hibernate</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime created;

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "todo_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;
}
