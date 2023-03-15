package ru.job4j.todo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Oywayten 15.03.2023.
 */
@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @NotNull
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;

    @NotNull
    @Column(nullable = false)
    private boolean done;

}
